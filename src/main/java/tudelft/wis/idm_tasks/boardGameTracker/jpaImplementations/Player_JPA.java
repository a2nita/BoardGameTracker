package tudelft.wis.idm_tasks.boardGameTracker.jpaImplementations;

import jakarta.persistence.*;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BoardGame;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;

@Entity(name = "Player")
@Table(name ="players", indexes = {
        @Index(columnList = "name"), @Index(columnList = "id")})
public class Player_JPA implements Player {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String nickName;

    @ManyToMany(targetEntity = BoardGame_JPA.class, fetch = FetchType.EAGER)
    private Collection<BoardGame> gameCollection;

    public Player_JPA(String name, String nickname){
        this.name = name;
        this.nickName = nickname;
    }
    public Player_JPA(){}

    @Override
    public String getPlayerName() {

        return name;
    }


    /**
     * Returns the nickname of the player.
     *
     * @return nickname of the player
     */
    @Override
    public String getPlayerNickName(){
        return nickName;
    }


    /**
     * Returns all the boardgames this player owns (if any).
     * @return collection of boardgames this player owns
     */
    @Override
    public Collection<BoardGame> getGameCollection(){

        return gameCollection;
    }


    /**
     * Creates a human-readable String representation of this object.
     *
     * @return the string representation of the object
     */
    public String toVerboseString(){
        String s1 = "Player name: " + name + ", Player nickname: " + nickName;
        return s1;
    }

}
