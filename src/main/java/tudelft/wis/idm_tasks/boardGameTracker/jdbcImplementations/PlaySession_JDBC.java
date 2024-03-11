package tudelft.wis.idm_tasks.boardGameTracker.jdbcImplementations;

import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BoardGame;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.PlaySession;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player;

import java.util.Collection;
import java.util.Date;

public class PlaySession_JDBC implements PlaySession {

    private Date date;
    private Player host;
    private BoardGame game;
    private int playTime;
    private Collection<Player> players;
    private Player winner;

    public Date getDate(){
        return this.date;
    }
    // @TODO: Implement this method.

    /**
     * Returns the player who hosted or organized this game session.
     *
     * @return player who hosted/organized this game session
     */
    public Player getHost(){
        return this.host;
    }
    // @TODO: Implement this method.

    /**
     * Returns the game which was played.
     *
     * @return game which was played
     */
    public BoardGame getGame(){
        return this.game;
    }
    // @TODO: Implement this method.

    /**
     * Returns all the players who joined the session.
     *
     * @return collection of players who joined the session
     */
    public Collection<Player> getAllPlayers(){
        return this.players;
    }
    // @TODO: Implement this method.

    /**
     * Returns the winner of the game. This is somewhat naively assuming that
     * thee is only one player, but yeah, simplicity. Can be Null if nobody won.
     *
     * @return the player who is the winner, or Null if there is no winner
     */
    public Player getWinner(){
        return this.winner;
    }
    // @TODO: Implement this method.

    /**
     * Returns the approximate playtime, in minutes, for the session.
     *
     * @return an integer representing the approximate playtime in minutes for this session
     */
    public int getPlaytime(){
        return this.playTime;
    }
    // @TODO: Implement this method.

    /**
     * Creates a human-readable String representation of this object.
     *
     * @return the string representation of the object
     */
    public String toVerboseString(){
        return "";
    }
    // @TODO: Implement this method.
}
