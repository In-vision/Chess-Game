package chess;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import i18n.I18N;
//import database.Driver;
import interfaces.Configuraciones;
import interfaces.GamesLog;
import interfaces.MenuPrincipal;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Interface extends Application {
	
	public static void main(String[] args) {
		// Automatic VM reset, thanks to Joseph Rachmuth.
		try {
			launch(args);
			System.exit(0);
		} catch (Exception error) {
			error.printStackTrace();
			System.exit(0);

		}
	}

//	private Driver mcDriver;
	private String playerTurnStr;
	private ChessBoard board;
	private Login login;
	private Signup signup;
	private MenuPrincipal mainMenu;
	private GamesLog gameLogs;
	private Configuraciones config;

	private boolean playerIsWhite = true; // white player = server
	private Stage stage = null;
	private boolean cantPlayAnymore = false;
	private Label resultP1, resultP2;
	private int langID = 1, voiceID = 1, themeID = 1;
	private int gameID = 0;
	
	private String userName;
	
	@Override
	public void start(Stage mainStage) throws IOException {
		stage = mainStage;
		stage.setTitle("Chess Game");

		stage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/app_icon.png")));

		BorderPane root = new BorderPane();
		Scene mainScene = new Scene(root);

		stage.setScene(mainScene);
		stage.setResizable(false);

		// add stylesheet
		mainScene.getStylesheets().add("/res/stylesheet.css");

		// draw chessboard
		board = new ChessBoard(playerIsWhite, this.langID, this.voiceID, this.themeID, true);
		login = new Login();
		signup = new Signup();
		mainMenu = new MenuPrincipal();
//		mcDriver = new Driver();
		gameLogs = new GamesLog();
		config = new Configuraciones();

		root.setCenter(login); // sized 400x400
		login.login.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			try {
				initMainMenu(stage, mainMenu, 1);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		
		mainMenuConf();
		loadGames();
		config.voice1.getStyleClass().add("selected");
		config.board1.getStyleClass().add("selected");
		
		confButtonsFunctionality();
		
//		login.signup.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> initSignupScene(stage, signup));
//		
//		signup.signup.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
//			try {
//				insertPlayer();
//				initMainMenu(stage, mainMenu, 2);
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//		});

		signup.backButt.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			initLogin(stage, login);
			board = new ChessBoard(playerIsWhite, this.langID, this.voiceID, this.themeID, true);
		});

		stage.show();
	}

	private void initMainMenu(Stage mainStage, MenuPrincipal mainMenu, int requestCode) throws SQLException {
		boolean areValidCredentials = true;
//		if (requestCode == 1) {
//			ResultSet rs = null;
//			areValidCredentials = true;
//			String userNorMail = login.username.getText();
//			String lPassword = login.password.getText();
//			
//			System.out.println("Credentials: ");
//			System.out.println("User: " + userNorMail);
//			System.out.println("Password: " + lPassword);
//
//			rs = mcDriver.selectAllUsers();
//
//			if (rs == null) {
//				System.out.println("A problem has occurred during the connection");
//			} else {
//				String username, password, email;
//				while (rs.next()) {
//					username = rs.getString(2);
//					password = rs.getString(3);
//					email = rs.getString(4);
//
//					System.out.println("Username: " + username + ", Password: " + password + ", Email: " + email);
//
//					if ((userNorMail.equals(username) || userNorMail.equals(email)) && (lPassword.equals(password))) {
//						System.out.println("Valid user");
//						areValidCredentials = true;
//						this.userName = username;
//						break;
//					}
//				}
//			}
//
//		}
		
		BorderPane root = new BorderPane();
		root.setCenter(mainMenu);
		Scene mainMenuScene = new Scene(root);
		mainMenuScene.getStylesheets().add("/interfaces/menuPrincipalv1-2.css");
		
		if (areValidCredentials && requestCode == 1) {
			mainStage.setScene(mainMenuScene);
			mainStage.show();
		} else if (!areValidCredentials && requestCode == 1) {
			inDisplayDeniedLogin();
		}

		if (requestCode == 2) {
			mainStage.setScene(mainMenuScene);
			mainStage.show();
		}

	}
	
	private void initGameLogs(Stage mainStage, GamesLog gamelogs) {
		BorderPane root = new BorderPane();
		root.setCenter(gamelogs);
		Scene mainMenuScene = new Scene(root);
		mainMenuScene.getStylesheets().add("/interfaces/gameslog.css");
		mainStage.setScene(mainMenuScene);	
		mainStage.show();
	}

	private void initConfig(Stage mainStage, Configuraciones conf) {
		BorderPane root = new BorderPane();
		root.setCenter(conf);
		Scene confMenu = new Scene(root);
		confMenu.getStylesheets().add("/interfaces/menuConfiguraciones.css");
		mainStage.setScene(confMenu);
		mainStage.show();
	}

	private void initLogin(Stage mainStage, Login login) {
		BorderPane root = new BorderPane();
		root.setCenter(login);
		Scene loginScene = new Scene(root);
		loginScene.getStylesheets().add("/res/stylesheet.css");
		mainStage.setScene(loginScene);
		mainStage.show();

	}

//	private void insertPlayer() throws SQLException {
//		String username = signup.username.getText();
//		String email = signup.email.getText();
//		String password = signup.password.getText();
//		System.out.println(username + ", " + password + ", " + email);
//		mcDriver.insertPlayer(username, email, password);
//
//	}

//	private void initSignupScene(Stage mainStage, Signup signup) {
//		BorderPane root = new BorderPane();
//		root.setCenter(signup);
//		Scene signupScene = new Scene(root);
//		signupScene.getStylesheets().add("/res/stylesheet.css");
//		mainStage.setScene(signupScene);
//		mainStage.show();
//	}
	
	private Locale mx = new Locale("es", "MX");
	private Locale it = new Locale("it", "IT");
	Label p1 = I18N.labelForKey("player1");
	Label p2 = I18N.labelForKey("player2");
	
	@Override
	public void init() {
		I18N.setSupportedLocales(Locale.ENGLISH, mx, it);
		I18N.setResourceBundlePath("res.messages");
	}
	
	private void initBoardScene(Stage mainStage, boolean isNewGame, int calledFrom) throws SQLException {
		
		if(isNewGame) {
			board = new ChessBoard(playerIsWhite, this.langID, this.voiceID, this.themeID, isNewGame);
		}
		else {
			board = new ChessBoard(playerIsWhite, this.langID, this.voiceID, this.themeID, isNewGame);
			
			String path = userName + calledFrom + ".txt";
			Path myGame = Paths.get(path);
			if(!Files.exists(myGame)) {
				board = new ChessBoard(playerIsWhite, this.langID, this.voiceID, this.themeID, true);
			}
			else {
				List<String> lines;
				try {
					lines = Files.readAllLines(myGame);
					board = new ChessBoard(lines);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}

		this.cantPlayAnymore = false;
		BorderPane rootTmp = new BorderPane();
		BorderPane nestedBP = new BorderPane();
		Label letA = new Label("A");
		Label letB = new Label("B");
		Label letC = new Label("C");
		Label letD = new Label("D");
		Label letE = new Label("E");
		Label letF = new Label("F");
		Label letG = new Label("G");
		Label letH = new Label("H");

		Label num1 = new Label("1");
		Label num2 = new Label("2");
		Label num3 = new Label("3");
		Label num4 = new Label("4");
		Label num5 = new Label("5");
		Label num6 = new Label("6");
		Label num7 = new Label("7");
		Label num8 = new Label("8");
		HBox letters = new HBox();
		letA.setStyle("-fx-font-weight: bold;");
		letB.setStyle("-fx-font-weight: bold;");
		letC.setStyle("-fx-font-weight: bold;");
		letD.setStyle("-fx-font-weight: bold;");
		letE.setStyle("-fx-font-weight: bold;");
		letF.setStyle("-fx-font-weight: bold;");
		letG.setStyle("-fx-font-weight: bold;");
		letH.setStyle("-fx-font-weight: bold;");
		num1.setStyle("-fx-font-weight: bold;");
		num2.setStyle("-fx-font-weight: bold;");
		num3.setStyle("-fx-font-weight: bold;");
		num4.setStyle("-fx-font-weight: bold;");
		num5.setStyle("-fx-font-weight: bold;");
		num6.setStyle("-fx-font-weight: bold;");
		num7.setStyle("-fx-font-weight: bold;");
		num8.setStyle("-fx-font-weight: bold;");
		HBox.setMargin(letA, new Insets(0, 0, 0, 39));
		HBox.setMargin(letB, new Insets(0, 0, 0, 40));
		HBox.setMargin(letC, new Insets(0, 0, 0, 44));
		HBox.setMargin(letD, new Insets(0, 0, 0, 41));
		HBox.setMargin(letE, new Insets(0, 0, 0, 41));
		HBox.setMargin(letF, new Insets(0, 0, 0, 42));
		HBox.setMargin(letG, new Insets(0, 0, 0, 44));
		HBox.setMargin(letH, new Insets(0, 0, 0, 40));

		letters.getChildren().addAll(letA, letB, letC, letD, letE, letF, letG, letH);
		letters.maxWidth(400);

		VBox numbers = new VBox();
		numbers.getChildren().addAll(num1, num2, num3, num4, num5, num6, num7, num8);
		numbers.minWidth(20);
		VBox.setMargin(num1, new Insets(20, 7, 0, 7));
		VBox.setMargin(num2, new Insets(30, 7, 0, 7));
		VBox.setMargin(num3, new Insets(34, 7, 0, 7));
		VBox.setMargin(num4, new Insets(31, 7, 0, 7));
		VBox.setMargin(num5, new Insets(33, 7, 0, 7));
		VBox.setMargin(num6, new Insets(32, 7, 0, 7));
		VBox.setMargin(num7, new Insets(34, 7, 0, 7));
		VBox.setMargin(num8, new Insets(30, 7, 0, 7));
		nestedBP.setCenter(board);
		nestedBP.setLeft(numbers);
		nestedBP.setBottom(letters);

		VBox players = new VBox();
		Label vs;
		vs = new Label("VS");
		resultP1 = new Label("-");
		resultP2 = new Label("-");
		resultP1.setStyle("-fx-font-weight: bold; -fx-font-size: 24");
		resultP2.setStyle("-fx-font-weight: bold; -fx-font-size: 24");
		p1.setStyle("-fx-font-weight: bold; -fx-font-size: 16");
		vs.setStyle("-fx-font-weight: bold; -fx-font-size: 24");
		p2.setStyle("-fx-font-weight: bold; -fx-font-size: 16");
		HBox p1HBox = new HBox();
		HBox p2HBox = new HBox();
		p1HBox.getChildren().addAll(p1, resultP1);
		HBox.setMargin(resultP1, new Insets(0, 25, 0, 25));
		p2HBox.getChildren().addAll(p2, resultP2);
		HBox.setMargin(resultP2, new Insets(0, 25, 0, 25));

		players.getChildren().addAll(p2HBox, vs, p1HBox);
		VBox.setMargin(p2HBox, new Insets(40, 0, 0, 0));
		VBox.setMargin(vs, new Insets(100, 0, 50, 35));
		VBox.setMargin(p1HBox, new Insets(75, 0, 0, 0));
		Label playerTurn = new Label();
		playerTurn.setPrefWidth(580);
		playerTurn.maxWidth(580);
		MenuBar menuBar = generateMenuBar();
		HBox menuCombo = new HBox();
		Button spanish = I18N.buttonForKey("spanish");
		Button italian = I18N.buttonForKey("italian");
		Button english = I18N.buttonForKey("english");
		
		spanish.setOnAction(e -> {
			I18N.setLocale(mx);
		});
		italian.setOnAction(e -> {
			I18N.setLocale(it);
		});
		english.setOnAction(e -> {
			I18N.setLocale(new Locale("en"));
		});
		menuCombo.getChildren().addAll(menuBar, english, spanish, italian);
		menuCombo.getStyleClass().add("combob");
		board.prefWidthProperty().bind(rootTmp.widthProperty());
		board.prefHeightProperty().bind(rootTmp.heightProperty());
		board.setLabelTurn(playerTurn);

		rootTmp.setCenter(nestedBP);
		rootTmp.setBottom(playerTurn);
		rootTmp.setTop(menuCombo);
		rootTmp.setRight(players);

		BorderPane.setMargin(players, new Insets(0, 0, 0, 10));
		BorderPane.setAlignment(playerTurn, Pos.CENTER);
		playerTurn.setTextAlignment(TextAlignment.CENTER);
		Scene boardScene = new Scene(rootTmp, 580, 480);
		boardScene.getStylesheets().add("/res/stylesheet.css");
		mainStage.setScene(boardScene);
		mainStage.titleProperty().bind(I18N.createStringBinding("title"));
		mainStage.show();

	}
	
	private Locale getLocale(int selectedIndex) {
		switch(selectedIndex) {
			case 0:;
				return mx;
		case 2:
				return it;
		default:
				return new Locale("en");
		}
	}

	public void changePlayerStr() {
		System.out.println("Change player str");
		if (playerTurnStr.equals("Blancas")) {
			playerTurnStr = "Negras";
		}
		playerTurnStr = "Blancas";
	}

	// Quits program
	public void onQuit() {
		Platform.exit();
		System.exit(0);
	}

	public void inDisplayDeniedLogin() {
		Alert infoAlert = new Alert(AlertType.INFORMATION);
		infoAlert.setTitle("Login Denied");
		infoAlert.setHeaderText(null);

		// set window icon
		Stage alertStage = (Stage) infoAlert.getDialogPane().getScene().getWindow();
		alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/about.png")));
		infoAlert.setContentText("Username or Email invalid. \n" + "Check your password.");
		infoAlert.showAndWait();
	}

// Display 'about' menu
	public void onDisplayAbout() {
		Alert infoAlert = new Alert(AlertType.INFORMATION);
		infoAlert.setTitle("About this program");
		infoAlert.setHeaderText(null);

		// Set window icon
		Stage alertStage = (Stage) infoAlert.getDialogPane().getScene().getWindow();
		alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/about.png")));

		infoAlert.setContentText("Programmed by Isaac Cabrera, Ian Diaz and Luis Beltrán\n");
		infoAlert.showAndWait();
	}
	

	private void setEndGame() {
		if (this.cantPlayAnymore)
			return;
		ChessBoard.isPlayable = false;
		resultP1.setText("0");
		resultP2.setText("1");
		this.cantPlayAnymore = true;
	}

	private void setDraw() {
		if (this.cantPlayAnymore)
			return;
		ChessBoard.isPlayable = false;
		resultP1.setText("1/2");
		resultP2.setText("1/2");
		this.cantPlayAnymore = true;
	}
	
	public void onDisplayFullDatabase() {
		Alert infoAlert = new Alert(AlertType.INFORMATION);
		infoAlert.setTitle(I18N.getMessage("databaseFull"));
		infoAlert.setHeaderText(null);

		// Set window icon
		Stage alertStage = (Stage) infoAlert.getDialogPane().getScene().getWindow();
		alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/about.png")));

		infoAlert.setContentText(I18N.getMessage("contextText"));
		infoAlert.showAndWait();
	}
	
//	private void saveGame() throws IOException, SQLException {
//		String[] savedFileStrings = new String[66];
//		int lineCounter = 0, count = 0;
//		String turn = (ChessBoard.playerTurn)? "true" : "false";
//		savedFileStrings[lineCounter] = turn;
//		lineCounter++;
//		savedFileStrings[lineCounter] = String.valueOf(board.kingInCheck);
//		lineCounter++;
//		String x, y, isThreatenedByWhite, isThreatenedByBlack,whiteThreads, blackThreads,piece, color;
//		for (int row = 0; row < 8; row++) {
//			for (int column = 0; column < 8; column++) {
//				Space temp = ChessBoard.spaces[row][column];
//				x = String.valueOf(temp.getX());
//				y = String.valueOf(temp.getY());
//				isThreatenedByWhite = String.valueOf(temp.isThreatenedByWhite());
//				isThreatenedByBlack = String.valueOf(temp.isThreatenedByBlack());
//				whiteThreads = String.valueOf(temp.getWhiteThreads());
//				blackThreads = String.valueOf(temp.getBlackThreads());
//				piece = (temp.getPiece() == null)? null : temp.getPiece().getName();
//				color = (piece == null)? null: temp.getPieceColor();
//				if(piece != null) count++;
//				String concat = x + "," + y + "," + isThreatenedByWhite + "," +
//						isThreatenedByBlack+","+whiteThreads+","+blackThreads+","+piece+","+color;
//				savedFileStrings[lineCounter] = concat;
//				lineCounter++;
////				System.out.println(concat);
//				
//			}
//		}
//		System.out.println("hola : " + userName);
//		int userID = mcDriver.selectUserID(userName);
//		int noGames = mcDriver.selectGamesFromUser(userID);
//		if(noGames == 3) {
//			onDisplayFullDatabase();
//			return;
//		}
//		int gameID = noGames + 1;
//		System.out.println("Save game: " + userID + ", " + noGames);
//		String fileName = userName + gameID + ".txt";
//		System.out.println(fileName);
//		Path myPath = Paths.get(fileName);
//		Files.write(myPath, Arrays.asList(savedFileStrings));
//		
//		mcDriver.insertGamePath(gameID, userID, fileName);
////		System.out.println("Reading file...");
////		List<String> taqlo = Files.readAllLines(myPath);
////		for(String aa: taqlo) {
////			System.out.println(aa);
////		}
//	}
	
	// Generate main menu bar
	private MenuBar generateMenuBar() {
		MenuBar menuBar = new MenuBar();

		Menu exitMenu = new Menu(I18N.getMessage("exit"));
		MenuItem menuItemQuit = new MenuItem(I18N.getMessage("quit"));
		MenuItem menuItemSignout = new MenuItem(I18N.getMessage("signOut"));
		MenuItem menuItemSave = new MenuItem(I18N.getMessage("save&Quit"));
		menuItemQuit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
		menuItemSignout.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
		menuItemSave.setAccelerator(new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN));
		menuItemQuit.setOnAction(e -> onQuit());
//		menuItemSignout.setOnAction(e -> initLogin(stage, login));
//		menuItemSave.setOnAction(e -> {
//			try {
//				saveGame();
//			} catch (IOException | SQLException e2) {
//				// TODO Auto-generated catch block
//				e2.printStackTrace();
//			}
//		});
		exitMenu.getItems().addAll(menuItemQuit, menuItemSignout, menuItemSave);

		Menu gameMenu = new Menu(I18N.getMessage("game"));
		MenuItem menuItemDraw = new MenuItem(I18N.getMessage("draw"));
		MenuItem menuItemResign = new MenuItem(I18N.getMessage("resign"));
		MenuItem menuItemReset = new MenuItem(I18N.getMessage("reset"));
		menuItemDraw.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN));
		menuItemDraw.setOnAction(e -> setDraw());
		menuItemResign.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));
		menuItemResign.setOnAction(e -> setEndGame());
		menuItemReset.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
		menuItemReset.setOnAction(e -> {
			try {
				board = new ChessBoard(playerIsWhite, this.langID, this.voiceID, this.themeID, true);
				initBoardScene(stage, true, 0);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		gameMenu.getItems().addAll(menuItemDraw, menuItemResign, menuItemReset);

		Menu menuHelp = new Menu(I18N.getMessage("help"));
		MenuItem menuItemAbout = new MenuItem(I18N.getMessage("about"));
		menuItemAbout.setAccelerator(new KeyCodeCombination(KeyCode.F1));
		menuItemAbout.setOnAction(e -> onDisplayAbout());
		menuHelp.getItems().add(menuItemAbout);

		menuBar.getMenus().addAll(exitMenu, gameMenu, menuHelp);
		return menuBar;
	}

	public void confButtonsFunctionality() {
		//Botones de lenguaje
		
		//Botones de voces
		config.voice1.setOnAction(e -> {
			Stream.of(config.voice2, config.voice3).forEach(button2 -> button2.getStyleClass().remove("selected"));
			config.voice1.getStyleClass().add("selected");
			this.voiceID = 1;
		});
		config.voice2.setOnAction(e -> {
			Stream.of(config.voice1, config.voice3).forEach(button2 -> button2.getStyleClass().remove("selected"));
			config.voice2.getStyleClass().add("selected");
			this.voiceID = 2;
		});
		config.voice3.setOnAction(e -> {
			Stream.of(config.voice1, config.voice2).forEach(button2 -> button2.getStyleClass().remove("selected"));
			config.voice3.getStyleClass().add("selected");
			this.voiceID = 3;
		});
		
		//Botones de colores de tablero
		config.board1.setOnAction(e -> {
			Stream.of(config.board2, config.board3).forEach(button2 -> button2.getStyleClass().remove("selected"));
			config.board1.getStyleClass().add("selected");
			config.board.setImage(config.defBoard);
			this.themeID = 1;
		});
		config.board2.setOnAction(e -> {
			Stream.of(config.board1, config.board3).forEach(button2 -> button2.getStyleClass().remove("selected"));
			config.board2.getStyleClass().add("selected");
			config.board.setImage(config.tournamentBoard);
			this.themeID = 2;
		});
		config.board3.setOnAction(e -> {
			Stream.of(config.board1, config.board2).forEach(button2 -> button2.getStyleClass().remove("selected"));
			config.board3.getStyleClass().add("selected");
			config.board.setImage(config.transBoard);
			this.themeID = 3;
		});
		config.back.setOnAction(e -> {
			try {
				initMainMenu(stage, mainMenu, 2);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

	}
	
	private void loadGames() {
		this.gameLogs.load1.setOnAction(e -> {
			try {
				initBoardScene(stage, false, 1);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		this.gameLogs.load2.setOnAction(e -> {
			try {
				
				initBoardScene(stage, false, 2);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		this.gameLogs.load3.setOnAction(e -> {
			try {
				initBoardScene(stage, false ,3);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		this.gameLogs.back.setOnAction(e -> {
			try {
				initMainMenu(stage, mainMenu, 2);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
	
	private void mainMenuConf() {
		mainMenu.conf.setOnAction(e -> {
			initConfig(stage, config);
		});
		
		mainMenu.singlePlayer.setOnAction(e -> {
			initGameLogs(stage, gameLogs);
		});
		
		mainMenu.quit.setOnAction(e -> {
			stage.close();
		});
	}
}