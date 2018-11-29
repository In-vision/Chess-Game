package interfaces;


import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class Configuraciones extends VBox {

	public Button voice1, voice2, voice3, board1, board2, board3, back;
	public Image image, defBoard, tournamentBoard, transBoard;
	public ImageView board;

	public Configuraciones() throws IOException {
		FXMLLoader fxmlResource = new FXMLLoader(getClass().getResource("menuConfiguraciones.fxml"));
		VBox vBox = fxmlResource.load();

		voice1 = (Button) fxmlResource.getNamespace().get("default1");
		voice2 = (Button) fxmlResource.getNamespace().get("v2");
		voice3 = (Button) fxmlResource.getNamespace().get("v3");

		board1 = (Button) fxmlResource.getNamespace().get("default2");
		board2 = (Button) fxmlResource.getNamespace().get("theme2");
		board3 = (Button) fxmlResource.getNamespace().get("theme3");

		// Back button
		back = (Button) fxmlResource.getNamespace().get("backButton");

		board = (ImageView) fxmlResource.getNamespace().get("boardImage");

		image = new Image("/interfaces/backgroundchess.jpg");
		defBoard = new Image("/interfaces/defBoard.png");
		tournamentBoard = new Image("/interfaces/TournamentBoard.png");
		transBoard = new Image("/interfaces/TranslucidBoard.png");

		board.setFitHeight(150);
		board.setFitWidth(150);

		board.setImage(defBoard);
		this.getChildren().add(vBox);
	}
}
