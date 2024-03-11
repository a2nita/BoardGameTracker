package tudelft.wis.idm_tasks.boardGameTracker.jdbcImplementations;

import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BoardGame;

import java.util.UUID;

public class BoardGame_JDBC implements BoardGame {

    private String name;
    private String bggURL;
    private UUID id;

    public BoardGame_JDBC(String name, String bggURL) {
        this.name = name;
        this.bggURL = bggURL;
        this.id = UUID.randomUUID();
    }

    public String getName(){
        return this.name;
    }
    // @TODO: Implement this method.

    /**
     * Returns the game's BoardGamesGeek.com URL.
     *
     * @return the URL as a string
     */
    public String getBGG_URL(){
        return this.bggURL;
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
