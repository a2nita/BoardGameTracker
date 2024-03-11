package tudelft.wis.idm_tasks.boardGameTracker.jpaImplementations;

import jakarta.persistence.*;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BoardGame;

@Entity(name = "BoardGame")
@Table(name ="boardgames", indexes = {
        @Index(columnList = "name"), @Index(columnList = "bggURL")})
public class BoardGame_JPA implements BoardGame {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String bggURL;

    public BoardGame_JPA(String name, String bggURL){
        this.name = name;
        this.bggURL = bggURL;
    }

    public BoardGame_JPA() {}

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

        return "Boardgame name: " + name + ", Boardgame URL: " + bggURL;
    }
    // @TODO: Implement this method.
}
