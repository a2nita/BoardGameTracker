package tudelft.wis.idm_tasks.boardGameTracker.jpaImplementations;

import jakarta.persistence.*;
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

    private EntityManager em;


    public BgtDataManager_JPA() {
        EntityManagerFactory emf2 = Persistence.createEntityManagerFactory("JPATest");
        em = emf2.createEntityManager();
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
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(newPlayer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            // Handle the exception
        }
//        } finally {
//            if (em != null && em.isOpen()) {
//                em.close();
//            }
//        }
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

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Collection<Player> coll = new ArrayList<>();

        try {
            String jpql = "SELECT p FROM Player p WHERE p.name LIKE :search";
            TypedQuery<Player_JPA> query = em.createQuery(jpql, Player_JPA.class);
            query.setParameter("search", "%" + name + "%");
            Collection<Player_JPA> players = query.getResultList();
            for (Player_JPA p : players) {
                coll.add(new Player_JPA(p.getPlayerName(), p.getPlayerNickName()));
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            // Handle the exception
            throw new BgtException("Error finding players by name: " + e.getMessage(), e);
        }
//        } finally {
//            if (em != null && em.isOpen()) {
//                em.close();
//            }
//        }
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
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(newGame);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            // Handle the exception
        }
//        } finally {
//            if (em != null && em.isOpen()) {
//                em.close();
//            }
//        }
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
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Collection<BoardGame> coll = new ArrayList<>();

        try {
        String jpql = "SELECT b FROM BoardGame_JPA b WHERE b.name LIKE :search ";
        TypedQuery<BoardGame_JPA> query = em.createQuery(jpql, BoardGame_JPA.class);
        query.setParameter("search", "%" + name + "%");
        Collection<BoardGame_JPA> games = query.getResultList();
        for (BoardGame_JPA g : games){
            coll.add(new BoardGame_JPA(g.getName(), g.getBGG_URL()));
        }
        transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            // Handle the exception
            throw new BgtException("Error finding players by name: " + e.getMessage(), e);
        }
//        } finally {
//            if (em != null && em.isOpen()) {
//                em.close();
//            }
//        }
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
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(newSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            // Handle the exception
        }
//        } finally {
//            if (em != null && em.isOpen()) {
//                em.close();
//            }
//        }
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

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Collection<PlaySession> coll = new ArrayList<>();
        try{
                String jpql = "SELECT s FROM PlaySession s WHERE s.date = :search ";
                TypedQuery<PlaySession_JPA> query = em.createQuery(jpql, PlaySession_JPA.class);
                query.setParameter("search", date);
                Collection<PlaySession_JPA> sessions = query.getResultList();
                for (PlaySession_JPA s : sessions){
                    coll.add(new PlaySession_JPA(s.getDate(), s.getHost(), s.getGame(), s.getPlaytime(), s.getAllPlayers(), s.getWinner() ));
                }
                transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            // Handle the exception
            throw new BgtException("Error finding players by name: " + e.getMessage(), e);
        }
//        } finally {
//            if (em != null && em.isOpen()) {
//                em.close();
//            }
//        }
        return coll;
    }

    /**
     * Persists a given player to the DB. Note that this player might already exist and only needs an update :-)
     * @param player the player
     */
    public void persistPlayer(Player player){

        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            if (!em.contains(player)){
                em.persist(player);
            } else {
                em.merge(player);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            // Handle the exception
        }
//        } finally {
//            if (em != null && em.isOpen()) {
//                em.close();
//            }
//        }
    }

    /**
     * Persists a given session to the DB. Note that this session might already exist and only needs an update :-)
     * @param session the session
     */
    public void persistPlaySession(PlaySession session){

        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            if (!em.contains(session)) {
                em.persist(session);
            } else {
                em.merge(session);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            // Handle the exception
        }
//        } finally {
//            if (em != null && em.isOpen()) {
//                em.close();
//            }
//        }
    }

    /**
     * Persists a given game to the DB. Note that this game might already exist and only needs an update :-)
     * @param game the game
     */
    public void persistBoardGame(BoardGame game){

        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            if (!em.contains(game)){
                em.persist(game);
            } else {
                em.merge(game);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            // Handle the exception
        }
//        } finally {
//            if (em != null && em.isOpen()) {
//                em.close();
//            }
//        }
    }

    public EntityManager getEntityManager() {
        return em;
    }
}
