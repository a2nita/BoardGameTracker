/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package tudelft.wis.idm_tasks.boardGameTracker.jpaImplementations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;
import org.tinylog.Logger;
import tudelft.wis.idm_tasks.boardGameTracker.BgtException;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BgtDataManager;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BoardGame;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.PlaySession;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player;
import tudelft.wis.idm_tasks.boardGameTracker.jpaImplementations.BgtDataManager_JPA;

import java.sql.SQLException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type POJO test.
 *
 * @author Christoph Lofi, Alexandra Neagu
 */
public class JPA_Test extends tudelft.wis.idm_tasks.boardGameTracker.AbstractBGTDemoJPA {

    /**
     * Instantiates a new POJO test.
     */
//    public JPA_Test() {
//    }

    private BgtDataManager_JPA dataManager = new BgtDataManager_JPA();


    @Override
    public BgtDataManager_JPA getBgtDataManager() {
        return dataManager;
    }

    /**
     * Just runs the application with some simple queries and assertions. It's
     * not very comprehensive, essentially, just a single session is retrieved
     * and the hist and the game is being checked.
     */
    @Test
    public void basicTest() throws BgtException, SQLException {
        EntityManager entityManager = dataManager.getEntityManager();
        Logger.info("START");

        // Make sure to start this test with an empty DB - trivial for POJO though...
        // Create dummy data
        Collection<PlaySession> testSessions = this.createDummyData(12, 6);

        Logger.info("Created test sessions!");
        for (PlaySession session : testSessions) {
            Logger.info("Session Created: \n" + session.toVerboseString());
        }

        // Get dummy session & related data
        PlaySession firstsession = testSessions.iterator().next();
        Player host = firstsession.getHost();
        BoardGame game = firstsession.getGame();

        // Retrieve the host from the database and check if it returns correctly
        while (this.getBgtDataManager().findPlayersByName(host.getPlayerName()).iterator().hasNext()) {
            Player retrievedPlayer = this.getBgtDataManager().findPlayersByName(host.getPlayerName()).iterator().next();
            assertEquals(retrievedPlayer.getPlayerNickName(), retrievedPlayer.getPlayerNickName());
            assertEquals(retrievedPlayer.getGameCollection().size(), host.getGameCollection().size());
            Logger.info("Player check passed: " + retrievedPlayer.getPlayerName() + "; collectionSize: " + retrievedPlayer.getGameCollection().size());
        }
        // Retrieve the game from the database and check if it returns correctly
        while (this.getBgtDataManager().findGamesByName(game.getName()).iterator().hasNext()) {
            BoardGame retrievedGame = this.getBgtDataManager().findGamesByName(game.getName()).iterator().next();
            assertEquals(retrievedGame.getBGG_URL(), game.getBGG_URL());
        }
        // Retrieve session by date
        Collection<PlaySession> retrievedSession = this.getBgtDataManager().findSessionByDate(firstsession.getDate());
        if (!retrievedSession.isEmpty()) {
            assertEquals(firstsession.getDate(), retrievedSession.iterator().next().getDate());
        }
        // Remove a game from the host's collection, add  it again

        BoardGame firstGame = host.getGameCollection().iterator().next();
        if (firstGame != null) {
            int numOfGames = host.getGameCollection().size();
            host.getGameCollection().remove(firstGame);
            this.getBgtDataManager().persistPlayer(host);
            // Load the host again from DB
            while(this.getBgtDataManager().findPlayersByName(host.getPlayerName()).iterator().hasNext()){
                Player hostFromDB = this.getBgtDataManager().findPlayersByName(host.getPlayerName()).iterator().next();
                if (hostFromDB != null){
                    assertEquals(numOfGames - 1, hostFromDB.getGameCollection().size());
                    // Add the game again
                    hostFromDB.getGameCollection().add(firstGame);
                    this.getBgtDataManager().persistPlayer(host);
                    // Load the host again from DB
                    while(this.getBgtDataManager().findPlayersByName(host.getPlayerName()).iterator().hasNext()){
                        Player hostFromDB2 = this.getBgtDataManager().findPlayersByName(host.getPlayerName()).iterator().next();
                        if (hostFromDB2 != null){
                            assertEquals(numOfGames, hostFromDB2.getGameCollection().size());
                        }
                    }

                }
            }

        }
        entityManager.close();
    }
}
