package chess;

import java.io.IOException;
//import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
//import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
//import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
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

	private ChessBoard board;
	private boolean playerIsWhite = true; // white player = server
	private boolean offlineMode = true;
 
	@Override
	public void start(Stage mainStage) throws IOException {
		mainStage.setTitle("Chess Game");
		
		mainStage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/app_icon.png")));

		BorderPane root = new BorderPane();
		Scene mainScene = new Scene(root);
		mainStage.setScene(mainScene);

		// add stylesheet
		mainScene.getStylesheets().add("/res/stylesheet.css");

		// draw chessboard
		board = new ChessBoard(playerIsWhite);
		root.setCenter(board); // sized 400x400

		// add menuBar
		MenuBar menuBar = generateMenuBar();
		root.setTop(menuBar);

		mainStage.show();
	}

	// Quits program
	public void onQuit() {
		Platform.exit();
		System.exit(0);
	}

	// Display 'about' menu
	public void onDisplayAbout() {
		Alert infoAlert = new Alert(AlertType.INFORMATION);
		infoAlert.setTitle("About this program");
		infoAlert.setHeaderText(null);

		// set window icon
		Stage alertStage = (Stage) infoAlert.getDialogPane().getScene().getWindow();
		alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/about.png")));

		// the graphic replaces the standard icon on the left
		// infoAlert.setGraphic( new ImageView( new Image("assets/icons/cat.png", 64,
		// 64, true, true) ) );

		infoAlert.setContentText("Programmed by\n"
				+ "Chess icons by \"Colin M.L. Burnett\".\n\n"
				+ "Networking package & chat client based on \n\"JavaFX Software: Chat (Server-Client)\" \nby Almas Baimagambetov.\n\n"
				+ "App icon by BlackVariant.");
		infoAlert.showAndWait();
	}

	// Generate main menu bar
	private MenuBar generateMenuBar() {
		MenuBar menuBar = new MenuBar();

		Menu gameMenu = new Menu("Game");
		menuBar.getMenus().add(gameMenu);

		MenuItem menuItemQuit = new MenuItem("Quit");
		menuItemQuit.setOnAction(e -> onQuit());
		// menuItemQuit.setGraphic( new ImageView( new Image("assets/icons/quit.png",
		// 16, 16, true, true) ) );
		menuItemQuit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
		gameMenu.getItems().add(menuItemQuit);

		Menu menuHelp = new Menu("Help");
		menuBar.getMenus().add(menuHelp);

		MenuItem menuItemAbout = new MenuItem("About");
		// menuItemAbout.setGraphic( new ImageView( new Image("assets/icons/about.png",
		// 16, 16, true, true) ) );
		// Note: Accelerator F1 does not work if TextField is
		// in focus. This is a known issue in JavaFX.
		// https://bugs.openjdk.java.net/browse/JDK-8148857
		menuItemAbout.setAccelerator(new KeyCodeCombination(KeyCode.F1));
		menuItemAbout.setOnAction(e -> onDisplayAbout());
		menuHelp.getItems().add(menuItemAbout);

		return menuBar;
	}
}