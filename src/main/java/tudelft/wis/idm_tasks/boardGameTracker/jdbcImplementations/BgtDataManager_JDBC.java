package tudelft.wis.idm_tasks.boardGameTracker.jdbcImplementations;

import tudelft.wis.idm_tasks.basicJDBC.interfaces.JDBCManager;
import tudelft.wis.idm_tasks.basicJDBC.interfaces.JDBCManagerImp;
import tudelft.wis.idm_tasks.boardGameTracker.BgtException;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BgtDataManager;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BoardGame;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.PlaySession;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class BgtDataManager_JDBC implements BgtDataManager {

    private ArrayList< Player_JDBC > players = new ArrayList<>();
    private ArrayList< BoardGame_JDBC > games = new ArrayList<>();
    private ArrayList< PlaySession_JDBC > sessions = new ArrayList< PlaySession_JDBC>();


    public Connection getConnection () {
        try {
            JDBCManager DbManager = new JDBCManagerImp();
            Connection conn = DbManager.getConnection();
            return conn;
        } catch (ClassNotFoundException classExpt) {
            System.out.println("Class not found!");
            return null;
        } catch (SQLException sqlExpt){
            System.out.println("SQL query unsuccessful!");
            return null;
        }
    }
    /**
     * Creates a new player and stores it in the DB.
     *
     * @param name the player name
     * @param nickname the player nickname
     * @return the new player
     * @throws java.sql.SQLException DB trouble
     */
    public Player createNewPlayer(String name, String nickname) throws BgtException, SQLException {
        Player_JDBC player = new Player_JDBC(name, nickname);
        Connection conn = this.getConnection();
        String query = "INSERT INTO players (id, name, nickname) VALUES (" + player.getId() + ", " + name + ", " + nickname + " )";
        try (Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery(query);
        } catch (SQLException e){
            throw new BgtException(e);
        }
        players.add(player);
        return player;
    }
    // @TODO: Implement this method.

    /**
     * Searches for player in the database by a substring of their name.
     *
     * @param name the name substring to use, e.g., searching for "hris" will find "Christoph"
     * @return collection of all players containing the param substring in their names
     * @throws BgtException the bgt exception
     */
    public Collection<Player> findPlayersByName(String name) throws BgtException{
        Collection<Player> players1 = new ArrayList<>();
        Connection conn = this.getConnection();
        String query = "SELECT name FROM players WHERE name='"+ name + "';";
        try (Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                String id1 = rs.getString("id");;
                for (int i = 0; i < players.size(); i++){
                    if (Objects.equals(String.valueOf(players.get(i).getId()), id1)) {
                        players1.add(players.get(i));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return players1;
    }
    // @TODO: Implement this method.

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
        Connection conn = this.getConnection();
        BoardGame_JDBC boardGame = new BoardGame_JDBC(name, bggURL);
        String query = "INSERT INTO boardgames (id, name, bggURL) VALUES (" + ((BoardGame_JDBC) boardGame).getId() + name + "," + bggURL + " )";
        try (Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        games.add(boardGame);
        return boardGame;
    }
    // @TODO: Implement this method.

    /**
     * Searches for game in the database by a substring of their name.
     *
     * @param name the name substring to use, e.g., searching for "clips" will
     * find "Eclipse: Second Dawn of the Galaxy""
     * @return collection of all boardgames containing the param substring in their names
     */
    public Collection<BoardGame> findGamesByName(String name) throws BgtException{
        Connection conn = this.getConnection();
        Collection<BoardGame> boardGames = new ArrayList<>();
        String query = "SELECT name FROM boardgames WHERE name='"+ name + "';";
        try (Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                String id1 = rs.getString("id");;
                for (int i = 0; i < games.size(); i++){
                    if (Objects.equals(String.valueOf(games.get(i).getId()), id1)) {
                        boardGames.add(games.get(i));
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return boardGames;
    }
    // @TODO: Implement this method.

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
        Connection conn = this.getConnection();
        String query = "INSERT INTO playsessions (date, host, game, playtime, players, winner) VALUES (" + date + "," + host + ", " + game + ", " + playtime + ", " + players + ", " + winner + " )";
        return null;
    }
    // @TODO: Implement this method.

    /**
     * Finds all play sessions from a specific date
     *
     * @param date the date to search from
     * @return collection of all play sessions from the param date
     * @throws BgtException the bgt exception
     */
    public Collection<PlaySession> findSessionByDate(Date date) throws BgtException{
        Connection conn = this.getConnection();
        String query = "SELECT * FROM playsessions WHERE date = '" + date + "';";
        return null;
    }
    // @TODO: Implement this method.

    /**
     * Persists a given player to the DB. Note that this player might already exist and only needs an update :-)
     * @param player the player
     */
    public void persistPlayer(Player player) throws SQLException, BgtException {
        Player_JDBC playerJdbc = (Player_JDBC) player;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getId().equals(playerJdbc.getId())) {
                Connection conn = this.getConnection();
                String query = "UPDATE players SET name='" + playerJdbc.getName() + "', nickname='" + playerJdbc.getNickName() + "';";
                try (Statement stmt = conn.createStatement()) {
                    ResultSet rs = stmt.executeQuery(query);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (!players.contains(playerJdbc)) {
            createNewPlayer(playerJdbc.getName(), playerJdbc.getNickName());
        }
    }
    // @TODO: Implement this method.
    /**
     * Persists a given session to the DB. Note that this session might already exist and only needs an update :-)
     * @param session the session
     */
    public void persistPlaySession(PlaySession session) {}
    // @TODO: Implement this method.

    /**
     * Persists a given game to the DB. Note that this game might already exist and only needs an update :-)
     * @param game the game
     */
    public void persistBoardGame(BoardGame game) {}
    // @TODO: Implement this method.
}
