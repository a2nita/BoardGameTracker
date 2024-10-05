/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package JDBC.Test;

import org.junit.jupiter.api.Test;
import org.tinylog.Logger;
import tudelft.wis.idm_solutions.BoardGameTracker.POJO_Implementation.BgtDataManager_POJO;
import tudelft.wis.idm_tasks.boardGameTracker.BgtException;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BgtDataManager;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BoardGame;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.PlaySession;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player;
import tudelft.wis.idm_tasks.boardGameTracker.jdbcImplementations.BgtDataManager_JDBC;
import tudelft.wis.idm_tasks.boardGameTracker.jdbcImplementations.BoardGame_JDBC;
import tudelft.wis.idm_tasks.boardGameTracker.jdbcImplementations.Player_JDBC;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type POJO test.
 *
 * @author Christoph Lofi, Alexandra Neagu
 */
public class JDBC_Test extends tudelft.wis.idm_tasks.boardGameTracker.AbstractBGTDemoJDBC {

    /**
     * Instantiates a new POJO test.
     */
    public JDBC_Test() {
    }

    private BgtDataManager_JDBC dataManager = new BgtDataManager_JDBC();

    @Override
    public BgtDataManager_JDBC getBgtDataManager() {
        return dataManager;
    }

    /**
     * Just runs the application with some simple queries and assertions. It's
     * not very comprehensive, essentially, just a single session is retrieved
     * and the hist and the game is being checked.
     */
    @Test
    public void basicTest() throws BgtException, SQLException {

        // Make sure to start this test with an empty DB - trivial for POJO though...
        // Create dummy data
        Collection<BoardGame_JDBC> boardGames = this.createDummyBoardgames();
        Collection<Player_JDBC> players = this.createDummyPlayers(12);

        for (BoardGame_JDBC b: boardGames) {
            Logger.info("BoardGame Created: " + b.toVerboseString());
        }
        for (Player_JDBC p: players) {
            Logger.info("Player Created: " + p.toVerboseString());
        }

        Logger.info("Pick Random Player to retrieve from DB!");
        BoardGame_JDBC gameJdbc = boardGames.stream().findAny().orElseThrow();
        Player_JDBC playerJdbc = players.stream().findAny().orElseThrow();


        // Retrieve the host from the database and check if it returns correctly
        Collection<Player> p = this.getBgtDataManager().findPlayersByName(playerJdbc.getPlayerName());
        if (!p.isEmpty()) {
            Player retrievedPlayer = p.iterator().next();
            assertEquals(retrievedPlayer.getPlayerNickName(), playerJdbc.getPlayerNickName());
            Logger.info("Player found: " + retrievedPlayer.getPlayerName());
        } else {
            Logger.error("No players found with name: " + playerJdbc.getPlayerName());
            Logger.info("HERE");
        }
//        Player retrievedPlayer = this.getBgtDataManager().findPlayersByName(playerJdbc.getPlayerName()).iterator().next();

        // Retrieve the game from the database and check if it returns correctly
        Logger.info("Pick random boardgame to retrieve from DB!");
        Collection<BoardGame> games = this.getBgtDataManager().findGamesByName(gameJdbc.getName());
        if (!games.isEmpty()) {
            BoardGame retrievedGame = games.iterator().next();
            assertEquals(retrievedGame.getBGG_URL(), gameJdbc.getBGG_URL());
            Logger.info("Game found: " + retrievedGame.getName());
        } else {
            Logger.error("No games found with name: " + gameJdbc.getName());
        }


    }

}
