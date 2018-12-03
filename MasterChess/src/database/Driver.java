package database;

import java.sql.*;

public class Driver {

	final String CONN = "jdbc:mysql://localhost:3306/masterchess?useTimezone=true&serverTimezone=GMT";
	public Connection masterChessConn;
	public Statement masterChessStatement;
	public PreparedStatement mcPS;

	private final String insertPlayer = "insert into masterchess.users (USER_ID, USERNAME, PASSWORD, EMAIL) "
			+ "values (?, ?, ?, ?)";
	private final String selectUserID = "select USER_ID FROM masterchess.users WHERE USERNAME = ?";
	private final String selectGamesFromUser = "select count(GAME_ID) FROM masterchess.GAMES WHERE USER_ID = ?";
	private final String insertGame = "insert into masterchess.games (GAME_ID, USER_ID, GAME_PATH) VALUES (?, ?, ?)";

	public Driver() {
		try {
			// Load Driver to get a Connection
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Get a connection
			//                                                  "Isaac", ""
			masterChessConn = DriverManager.getConnection(CONN, "Ian", "");
			// Create Statement
			masterChessStatement = masterChessConn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResultSet selectAllUsers() throws SQLException {
		ResultSet rs = masterChessStatement.executeQuery("Select * from masterchess.users;");
		return rs;
	}

	public ResultSet selectAllGames() throws SQLException {
		ResultSet rs = masterChessStatement.executeQuery("Select * from masterchess.games");
		return rs;
	}

	public boolean insertGamePath(int gameID, int userID, String path) throws SQLException {

		mcPS = masterChessConn.prepareStatement(insertGame);
		mcPS.setInt(1, gameID);
		mcPS.setInt(2, userID);
		mcPS.setString(3, path);
		mcPS.execute();
		return true;
	}

	public boolean insertPlayer(String username, String email, String password) throws SQLException {
		// Create Prepared statement
		System.out.println("In Driver");
		System.out.println("To insert");
		int playerID = Integer.MAX_VALUE;
		playerID = selectMaxPlayerID() + 1;

		System.out.println(playerID + ", " + username + ", " + password + ", " + email);
		mcPS = masterChessConn.prepareStatement(insertPlayer);
		mcPS.setInt(1, (playerID));
		mcPS.setString(2, username);
		mcPS.setString(3, password);
		mcPS.setString(4, email);
		mcPS.execute();

		return true;
	}

	private int selectMaxPlayerID() throws SQLException {
		ResultSet rs = masterChessStatement.executeQuery("Select max(USER_ID) from masterchess.users");
		int maxID = 0;
		if (rs.next()) {
			maxID = rs.getInt(1);

		}

		return maxID;
	}

	public int selectUserID(String username) throws SQLException {
		ResultSet rs;
		mcPS = masterChessConn.prepareStatement(selectUserID);
		mcPS.setString(1, username);
		rs = mcPS.executeQuery();
		int userID = 0;
		if (rs.next()) {
			userID = rs.getInt(1);
		}
		return userID;
	}

	public int selectGamesFromUser(int userID) throws NumberFormatException, SQLException {
		int noGames = 0;
		ResultSet rs;
		mcPS = masterChessConn.prepareStatement(selectGamesFromUser);
		mcPS.setInt(1, userID);
		rs = mcPS.executeQuery();
		if (rs.next()) {
			noGames = rs.getInt(1);
		}
		return noGames;
	}

	public static void main(String[] args) {
		try {

			Driver driverMasterChess = new Driver();
			ResultSet myResult = driverMasterChess.selectAllUsers();

			// All Users
			System.out.println("All Users");
			while (myResult.next()) {
				System.out.println(myResult.getInt(1) + ", " + myResult.getString(2) + ", " + myResult.getString(3)
						+ ", " + myResult.getString(4));
			}
			System.out.println();
			// All Games
			System.out.println("All Games");
			myResult = driverMasterChess.selectAllGames();
			while (myResult.next()) {
				System.out.println(myResult.getInt(1) + ", " + myResult.getInt(2) + ", " + myResult.getString(3));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
