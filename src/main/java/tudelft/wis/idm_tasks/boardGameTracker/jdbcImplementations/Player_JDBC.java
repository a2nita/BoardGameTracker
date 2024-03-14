package tudelft.wis.idm_tasks.boardGameTracker.jdbcImplementations;

import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BoardGame;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player;

import java.util.Collection;
import java.util.LinkedList;
import java.math.*;
import java.util.UUID;

public class Player_JDBC implements Player {

    private String name;
    private String nickName;
    private UUID id;
    private Collection<BoardGame> gameCollection = new LinkedList<BoardGame>();

    public Player_JDBC(String name, String nickName) {
        this.name = name;
        this.nickName = nickName;
        this.id = UUID.randomUUID();
    }

    public String getPlayerName() {
        return this.name;
    }
    // @TODO: Implement this method.


    /**
     * Returns the nickname of the player.
     *
     * @return nickname of the player
     */
    public String getPlayerNickName(){
        return this.nickName;
    }
    // @TODO: Implement this method.


    /**
     * Returns all the boardgames this player owns (if any).
     * @return collection of boardgames this player owns
     */
    public Collection<BoardGame> getGameCollection(){
        return this.gameCollection;
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

    @Override
    public UUID getId() {
        return id;
    }
    // @TODO: Implement this method.

}
