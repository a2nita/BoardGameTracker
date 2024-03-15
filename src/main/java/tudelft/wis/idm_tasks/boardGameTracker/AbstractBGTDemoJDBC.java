package tudelft.wis.idm_tasks.boardGameTracker;

import com.github.javafaker.Faker;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BgtDataManager;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BoardGame;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.PlaySession;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player;
import tudelft.wis.idm_tasks.boardGameTracker.jdbcImplementations.BgtDataManager_JDBC;
import tudelft.wis.idm_tasks.boardGameTracker.jdbcImplementations.BoardGame_JDBC;
import tudelft.wis.idm_tasks.boardGameTracker.jdbcImplementations.Player_JDBC;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * An abstract implementation of a test class for the BGT. Can create random data
 * and has some other helper methods.
 *
 * @author Christoph Lofi, Alexandra Neagu
 */
public abstract class AbstractBGTDemoJDBC {
    
    private static Faker faker = new Faker();
    private static final Random RND = new Random();

    /**
     * Returns a random subset of the specified collection of requested size.
     *
     * @param <T>   type parameter
     * @param c     collection to pick from
     * @param count requested size of the subset
     * @return list of randomly picked elements of the collection
     */
    public static <T> List<T> rndSubset(final Collection<T> c, final int count) {
        if (c == null) {
            throw new IllegalArgumentException("Expecting non-null parameter");
        }
        if (count < 0) {
            throw new IllegalArgumentException("Size of the subset cannot be negative: " + count);
        }
        if (count > c.size()) {
            throw new IllegalArgumentException("Subset cannot be bigger than the original set: " + count);
        }
        
        final List<T> ret = new ArrayList<T>(count);
        final List<T> tmp = new ArrayList<T>(c);
        
        for (int i = 0; i < count; i++) {
            final int toRemove = RND.nextInt(tmp.size());
            ret.add(tmp.get(toRemove));
            tmp.remove(toRemove);
        }
        
        return ret;
    }

    /**
     * Returns an instance of BgtDataFactory which can create new BGT objects.
     *
     * @return BgtDataManager instance
     */
    public abstract BgtDataManager getBgtDataManager();

    /**
     * Creates several players and games, and adds them to the DB.
     *
     * @param numOfPlayers  the num of players
     * @return collection of the play sessions
     * @throws BgtException the bgt exception
     */
    public Collection<Player_JDBC> createDummyPlayers(int numOfPlayers) throws BgtException, SQLException {
        Collection<Player_JDBC> players = new LinkedList<>();
        Collection<BoardGame> games = new LinkedList<BoardGame>();
        BgtDataManager dbManager = getBgtDataManager();

        // Create 5 games
        {
            games.add(dbManager.createNewBoardgame("Eclipse: Second Dawn", "https://boardgamegeek.com/boardgame/246900/eclipse-second-dawn-galaxy"));
            games.add(dbManager.createNewBoardgame("Everdell", "https://boardgamegeek.com/boardgame/199792/everdell"));
            games.add(dbManager.createNewBoardgame("Wingspan", "https://boardgamegeek.com/boardgame/266192/wingspan"));
            games.add(dbManager.createNewBoardgame("Cthulhu Wars", "https://boardgamegeek.com/boardgame/139976/cthulhu-warsf"));
            games.add(dbManager.createNewBoardgame("Nemesis: Lockdown", "https://boardgamegeek.com/boardgame/310100/nemesis-lockdown"));
        }

        // Create players
        for (int i = 0; i < numOfPlayers; i++) {
            Player newPlayer = dbManager.createNewPlayer(faker.name().fullName(), faker.pokemon().name());
            Collection<BoardGame> playerGames = rndSubset(games, RND.nextInt(3));
            for (BoardGame game : playerGames) {
                newPlayer.getGameCollection().add(game);
            }
            // Those games in the gameCollection are added AFTER the player was created. 
            // We thus need to persist it again to reflect that change.
            dbManager.persistPlayer(newPlayer);
            
            players.add((Player_JDBC) newPlayer);
        }

        return players;
    }

    public Collection<BoardGame_JDBC> createDummyBoardgames() throws BgtException, SQLException {
        Collection<BoardGame_JDBC> games2 = new LinkedList<>();
        BgtDataManager_JDBC dbManager2 = (BgtDataManager_JDBC) getBgtDataManager();

        // Create 5 games
        {
            games2.add(dbManager2.createNewBoardgame("Eclipse: Second Dawn", "https://boardgamegeek.com/boardgame/246900/eclipse-second-dawn-galaxy"));
            games2.add(dbManager2.createNewBoardgame("Everdell", "https://boardgamegeek.com/boardgame/199792/everdell"));
            games2.add(dbManager2.createNewBoardgame("Wingspan", "https://boardgamegeek.com/boardgame/266192/wingspan"));
            games2.add(dbManager2.createNewBoardgame("Cthulhu Wars", "https://boardgamegeek.com/boardgame/139976/cthulhu-warsf"));
            games2.add(dbManager2.createNewBoardgame("Nemesis: Lockdown", "https://boardgamegeek.com/boardgame/310100/nemesis-lockdown"));
        }

        return games2;
    }
    
}
