package tudelft.wis.idm_tasks.boardGameTracker.jpaImplementations;

import jakarta.persistence.*;
import tudelft.wis.idm_solutions.BoardGameTracker.POJO_Implementation.BoardGame_POJO;
import tudelft.wis.idm_solutions.BoardGameTracker.POJO_Implementation.PlaySession_POJO;
import tudelft.wis.idm_solutions.BoardGameTracker.POJO_Implementation.Player_POJO;
import tudelft.wis.idm_tasks.basicJDBC.interfaces.JDBCManager;
import tudelft.wis.idm_tasks.basicJDBC.interfaces.JDBCManagerImp;
import tudelft.wis.idm_tasks.boardGameTracker.BgtException;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BgtDataManager;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BoardGame;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.PlaySession;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


public class BgtDataManager_JPA implements BgtDataManager {

    private EntityManager entityManager;

    public BgtDataManager_JPA() {
        try (EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPATest")) {
            entityManager = entityManagerFactory.createEntityManager();
        }
    }
    /**
     * Creates a new player and stores it in the DB.
     *
     * @param name the player name
     * @param nickname the player nickname
     * @return the new player
     * @throws SQLException DB trouble
     */
    @Override
    public Player createNewPlayer(String name, String nickname) throws BgtException {
        Player_JPA newPlayer = new Player_JPA(name, nickname);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(newPlayer);
        transaction.commit();
        return newPlayer;

    }

    /**
     * Searches for player in the database by a substring of their name.
     *
     * @param name the name substring to use, e.g., searching for "hris" will find "Christoph"
     * @return collection of all players containing the param substring in their names
     * @throws BgtException the bgt exception
     */
    public Collection<Player> findPlayersByName(String name) throws BgtException {
        String jpql = "SELECT p FROM Player p WHERE p.name LIKE :search";
        TypedQuery<Player_JPA> query = entityManager.createQuery(jpql, Player_JPA.class);
        query.setParameter("search", "%" + name + "%");
        Collection<Player_JPA> players = query.getResultList();
        Collection<Player> coll = new ArrayList<>();
        for (Player_JPA p : players){
            coll.add(new Player_POJO(p.getPlayerName(), p.getPlayerNickName()));
        }
        return coll;
    }

    /**
     * Creates a new board game and stores it in the DB.
     * <p>
     * Note: These "create" methods are somewhat unnecessary. However, I put
     * them here to make the task test a bit easier. You can call an appropriate
     * persist method for this.
     *
     * @param name the name of the game
     * @param bggURL the URL of the game at BoardGameGeek.com
     * @return the new game
     * @throws SQLException DB trouble
     */
    public BoardGame createNewBoardgame(String name, String bggURL) throws BgtException{
        BoardGame_JPA newGame = new BoardGame_JPA(name, bggURL);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(newGame);
        transaction.commit();
        return newGame;
    }

    /**
     * Searches for game in the database by a substring of their name.
     *
     * @param name the name substring to use, e.g., searching for "clips" will
     * find "Eclipse: Second Dawn of the Galaxy""
     * @return collection of all boardgames containing the param substring in their names
     */
    public Collection<BoardGame> findGamesByName(String name) throws BgtException{
        String jpql = "SELECT b FROM Boardgame b WHERE b.name LIKE :search ";
        TypedQuery<BoardGame_JPA> query = entityManager.createQuery(jpql, BoardGame_JPA.class);
        query.setParameter("search", "%" + name + "%");
        Collection<BoardGame_JPA> games = query.getResultList();
        Collection<BoardGame> coll = new ArrayList<>();
        for (BoardGame_JPA g : games){
            coll.add(new BoardGame_POJO(g.getName(), g.getBGG_URL()));
        }
        return coll;
    }

    /**
     * Creates a new play session and stores it in the DB.
     *
     * @param date the date of the session
     * @param host the session host
     * @param game the game which was played
     * @param playtime the approximate playtime in minutes
     * @param players all players
     * @param winner the one player who won (NULL in case of no winner; multiple
     * winners not supported)
     * @return the new play session
     */
    public PlaySession createNewPlaySession(Date date, Player host, BoardGame game, int playtime, Collection<Player> players, Player winner) throws BgtException{
        PlaySession_JPA newSession = new PlaySession_JPA(date, host, game, playtime, players, winner);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(newSession);
        transaction.commit();
        return newSession;
    }

    /**
     * Finds all play sessions from a specific date
     *
     * @param date the date to search from
     * @return collection of all play sessions from the param date
     * @throws BgtException the bgt exception
     */
    public Collection<PlaySession> findSessionByDate(Date date) throws BgtException{
        String jpql = "SELECT s FROM PlaySession s WHERE s.date = :search ";
        TypedQuery<PlaySession_JPA> query = entityManager.createQuery(jpql, PlaySession_JPA.class);
        query.setParameter("search", date);
        Collection<PlaySession_JPA> sessions = query.getResultList();
        Collection<PlaySession> coll = new ArrayList<>();
        for (PlaySession_JPA s : sessions){
            coll.add(new PlaySession_POJO(s.getDate(), s.getHost(), s.getGame(), s.getPlaytime(), s.getAllPlayers(), s.getWinner() ));
        }
        return coll;
    }

    /**
     * Persists a given player to the DB. Note that this player might already exist and only needs an update :-)
     * @param player the player
     */
    public void persistPlayer(Player player){
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        if (entityManager.contains(player)){
            entityManager.merge(player);
        } else {
            entityManager.persist(player);
        }
        transaction.commit();
    }

    /**
     * Persists a given session to the DB. Note that this session might already exist and only needs an update :-)
     * @param session the session
     */
    public void persistPlaySession(PlaySession session){
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        if (entityManager.contains(session)){
            entityManager.merge(session);
        } else {
            entityManager.persist(session);
        }
        transaction.commit();
    }

    /**
     * Persists a given game to the DB. Note that this game might already exist and only needs an update :-)
     * @param game the game
     */
    public void persistBoardGame(BoardGame game){
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        if (entityManager.contains(game)){
            entityManager.merge(game);
        } else {
            entityManager.persist(game);
        }
        transaction.commit();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
