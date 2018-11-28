package chess;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Driver;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
//import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
//import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
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
	private Driver mcDriver;
	private String playerTurnStr;
	private ChessBoard board;
	private Login login;
	private Signup signup;
	private boolean playerIsWhite = true; // white player = server
//	private boolean offlineMode = true;
	private Stage stage = null;
 
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
		board = new ChessBoard(playerIsWhite);
		login = new Login();
		signup = new Signup();
		mcDriver = new Driver();
		
		root.setCenter(login); // sized 400x400
		login.login.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			try {
				initBoardScene(stage, board, 1);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		
		login.signup.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> initSignupScene(stage, signup));
		
		signup.signup.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			try {
				insertPlayer();
				initBoardScene(stage, board, 2);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		
		signup.backButt.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> initLogin(stage, login));
		
		stage.show();
	}
	
	private void initLogin(Stage mainStage, Login login) {
		System.out.println("init login");
		BorderPane root = new BorderPane();
		root.setCenter(login);
		Scene loginScene = new Scene(root);
		loginScene.getStylesheets().add("/res/stylesheet.css");
		mainStage.setScene(loginScene);
		mainStage.show();
		
	}

	private void insertPlayer() throws SQLException {
		String username = signup.username.getText();
		String email = signup.email.getText();
		String password = signup.password.getText();
		System.out.println( username + ", " + password + ", " + email);
		mcDriver.insertPlayer(username, email, password);
	
	}

	private void initSignupScene(Stage mainStage, Signup signup) {
		BorderPane root = new BorderPane();
		root.setCenter(signup);
		Scene signupScene = new Scene(root);
		signupScene.getStylesheets().add("/res/stylesheet.css");
		mainStage.setScene(signupScene);
		mainStage.show();
	}

	private void initBoardScene(Stage mainStage,ChessBoard board, int requestCode) throws SQLException {
		System.out.println("Init board scene");
		boolean areValidCredentials = true;
		if(requestCode == 1) {
			ResultSet rs = null;
			areValidCredentials = false;
			String userNorMail = login.username.getText();
			String lPassword = login.password.getText();
			
			System.out.println("Credentials: ");
			System.out.println("User: " + userNorMail);
			System.out.println("Password: " + lPassword);
			
			rs = mcDriver.selectAllUsers();
			
			if(rs == null) {
				System.out.println("A problem has occurred during the connection");
			}else {
				String username, password, email;
				while(rs.next()) {
					username = rs.getString(2);
					password = rs.getString(3);
					email = rs.getString(4);
					
					System.out.println("Username: " + username + ", Password: " + password + ", Email: " + email);
					
					if( (userNorMail.equals(username) || userNorMail.equals(email)) && (lPassword.equals(password))) {
						System.out.println("Valid user");
						areValidCredentials = true; 
						break;
					}
				}
			}
			

		}
		
		
		BorderPane root = new BorderPane();
		Label playerTurn = new Label();
		MenuBar menuBar = generateMenuBar();
		HBox menuCombo = new HBox();
		ObservableList<String> options = 
			    FXCollections.observableArrayList(
			        "Spanish",
			        "English",
			        "French"
			    );
		
		ComboBox<String> languages = new ComboBox<>(options);
		languages.setPromptText("Select a language");
		
		menuCombo.getChildren().addAll(menuBar, languages);
		menuCombo.getStyleClass().add("combob");
		board.prefWidthProperty().bind(root.widthProperty());
		board.prefHeightProperty().bind(root.heightProperty());
		board.setLabelTurn(playerTurn);
		
		root.setCenter(board);
		root.setBottom(playerTurn);
		root.setTop(menuCombo);
		
		Scene boardScene = new Scene(root, 400, 440);
		boardScene.getStylesheets().add("/res/stylesheet.css");
		
		if(areValidCredentials && requestCode == 1) {
			mainStage.setScene(boardScene);
		}else if(!areValidCredentials && requestCode == 1){
			inDisplayDeniedLogin();
		}
		
		if(requestCode == 2) {
			mainStage.setScene(boardScene);
		}
		
		mainStage.show();

	}
	
	public void changePlayerStr(){
		System.out.println("Change player str");
		if(playerTurnStr.equals("Blancas")) {
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
		infoAlert.setContentText("Username or Email invalid. \n" +
								"Check your password.");
		infoAlert.showAndWait();
	}
	
	// Display 'about' menu
	public void onDisplayAbout() {
		Alert infoAlert = new Alert(AlertType.INFORMATION);
		infoAlert.setTitle("About this program");
		infoAlert.setHeaderText(null);

		//Set window icon
		Stage alertStage = (Stage) infoAlert.getDialogPane().getScene().getWindow();
		alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/about.png")));

		infoAlert.setContentText("Programmed by Isaac Cabrera, Ian Diaz and Luis Beltrán\n");
		infoAlert.showAndWait();
	}

	// Generate main menu bar
	private MenuBar generateMenuBar() {
		MenuBar menuBar = new MenuBar();

		Menu exitMenu = new Menu("Exit");
		MenuItem menuItemQuit = new MenuItem("Quit");
		MenuItem menuItemSignout = new MenuItem("Sign out");
		menuItemQuit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
		menuItemSignout.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));		
		menuItemQuit.setOnAction(e -> onQuit());
		menuItemSignout.setOnAction(e -> initLogin(stage, login));
		exitMenu.getItems().addAll(menuItemQuit, menuItemSignout);
		
		Menu gameMenu = new Menu("Game");
		MenuItem menuItemDraw = new MenuItem("Draw");
		MenuItem menuItemResign = new MenuItem("Resign");
		MenuItem menuItemReset = new MenuItem("Reset");
		menuItemDraw.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN));
		menuItemResign.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));
		menuItemReset.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
		gameMenu.getItems().addAll(menuItemDraw, menuItemResign, menuItemReset);

		Menu menuHelp = new Menu("Help");
		MenuItem menuItemAbout = new MenuItem("About");
		menuItemAbout.setAccelerator(new KeyCodeCombination(KeyCode.F1));
		menuItemAbout.setOnAction(e -> onDisplayAbout());
		menuHelp.getItems().add(menuItemAbout);

		menuBar.getMenus().addAll(exitMenu, gameMenu, menuHelp);		
		return menuBar;
	}
}