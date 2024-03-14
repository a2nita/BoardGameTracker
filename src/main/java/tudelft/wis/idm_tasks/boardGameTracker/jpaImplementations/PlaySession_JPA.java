package tudelft.wis.idm_tasks.boardGameTracker.jpaImplementations;

import jakarta.persistence.*;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BoardGame;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.PlaySession;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player;

import javax.annotation.processing.Generated;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity(name = "PlaySession")
@Table(name = "playsessions", indexes = {
        @Index(columnList="date"), @Index(columnList="host"), @Index(columnList="game"), @Index(columnList="playtime"), @Index(columnList = "players"), @Index(columnList = "winner")})
public class PlaySession_JPA implements PlaySession {

    @Id
    @GeneratedValue
    private UUID id;
    private Date date;
    private Player host;
    private BoardGame game;
    private int playTime;
    private Collection<Player> players;
    private Player winner;

    public PlaySession_JPA(Date date, Player host, BoardGame game, int playtime, Collection<Player> players, Player winner){
        this.date = date;
        this.host = host;
        this.game = game;
        this.playTime = playtime;
        this.players = players;
        this.winner = winner;
    }
    public PlaySession_JPA() {}

    public Date getDate(){
        return this.date;
    }

    /**
     * Returns the player who hosted or organized this game session.
     *
     * @return player who hosted/organized this game session
     */
    public Player getHost(){
        return this.host;
    }

    /**
     * Returns the game which was played.
     *
     * @return game which was played
     */
    public BoardGame getGame(){
        return this.game;
    }

    /**
     * Returns all the players who joined the session.
     *
     * @return collection of players who joined the session
     */
    public Collection<Player> getAllPlayers(){
        return this.players;
    }

    /**
     * Returns the winner of the game. This is somewhat naively assuming that
     * thee is only one player, but yeah, simplicity. Can be Null if nobody won.
     *
     * @return the player who is the winner, or Null if there is no winner
     */
    public Player getWinner(){
        return this.winner;
    }

    /**
     * Returns the approximate playtime, in minutes, for the session.
     *
     * @return an integer representing the approximate playtime in minutes for this session
     */
    public int getPlaytime(){
        return this.playTime;
    }

    /**
     * Creates a human-readable String representation of this object.
     *
     * @return the string representation of the object
     */
    public String toVerboseString(){

        return "Game date: " + date + ", host: " + host + ", game: " + game + ", playtime: " + playTime + ", winner: " + winner ;
    }

}
