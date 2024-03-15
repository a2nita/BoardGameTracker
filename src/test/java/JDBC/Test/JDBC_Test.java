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

        BoardGame_JDBC gameJdbc = boardGames.stream().findAny().orElseThrow();
        Player_JDBC playerJdbc = players.stream().findAny().orElseThrow();

        // Retrieve the host from the database and check if it returns correctly
        Player retrievedPlayer = this.getBgtDataManager().findPlayersByName(playerJdbc.getPlayerName()).iterator().next();
        Logger.info("Player check passed: " + retrievedPlayer.getPlayerName() + "; collectionSize: " + retrievedPlayer.getGameCollection().size());

        // Retrieve the game from the database and check if it returns correctly
        BoardGame retrievedGame = this.getBgtDataManager().findGamesByName(gameJdbc.getName()).iterator().next();
        assertEquals(retrievedGame.getBGG_URL(), gameJdbc.getBGG_URL());


        // Remove a game from the host's collection, add  it again
        BoardGame firstGame = playerJdbc.getGameCollection().iterator().next();
        int numOfGames = playerJdbc.getGameCollection().size();
        playerJdbc.getGameCollection().remove(firstGame);
        this.getBgtDataManager().persistPlayer(playerJdbc);

        // Load the host again from DB
        Player hostFromDB = this.getBgtDataManager().findPlayersByName(playerJdbc.getPlayerName()).iterator().next();
        assertEquals(numOfGames - 1, hostFromDB.getGameCollection().size());

        // Add the game again
        hostFromDB.getGameCollection().add(firstGame);
        this.getBgtDataManager().persistPlayer(playerJdbc);

        // Load the host again from DB
        Player hostFromDB2 = this.getBgtDataManager().findPlayersByName(playerJdbc.getPlayerName()).iterator().next();
        assertEquals(numOfGames, hostFromDB2.getGameCollection().size());

    }

}
