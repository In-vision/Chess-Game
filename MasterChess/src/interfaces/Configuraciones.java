package interfaces;


import java.io.IOException;

import i18n.I18N;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class Configuraciones extends VBox {

	public Button voice1, voice2, voice3, board1, board2, board3, back;
	public Image image, defBoard, tournamentBoard, transBoard;
	public ImageView board;
	public Label confText, voiceLabel, themeLabel;

	public Configuraciones() throws IOException {
		FXMLLoader fxmlResource = new FXMLLoader(getClass().getResource("menuConfiguraciones.fxml"));
		VBox vBox = fxmlResource.load();
		
		confText = (Label) fxmlResource.getNamespace().get("configuracionLabel");
		confText.textProperty().bind(I18N.createStringBinding("configuration"));
		voiceLabel = (Label) fxmlResource.getNamespace().get("voiceLabel");
		voiceLabel.textProperty().bind(I18N.createStringBinding("chooseVinG"));
		themeLabel = (Label) fxmlResource.getNamespace().get("themeLabel");
		themeLabel.textProperty().bind(I18N.createStringBinding("chooseColorBoard"));
		
		voice1 = (Button) fxmlResource.getNamespace().get("default1");
		voice2 = (Button) fxmlResource.getNamespace().get("v2");
		voice3 = (Button) fxmlResource.getNamespace().get("v3");

		board1 = (Button) fxmlResource.getNamespace().get("default2");
		board1.textProperty().bind(I18N.createStringBinding("brown"));
		board2 = (Button) fxmlResource.getNamespace().get("theme2");
		board2.textProperty().bind(I18N.createStringBinding("tournament"));
		board3 = (Button) fxmlResource.getNamespace().get("theme3");
		board3.textProperty().bind(I18N.createStringBinding("translucid"));

		// Back button
		back = (Button) fxmlResource.getNamespace().get("backButton");
		back.textProperty().bind(I18N.createStringBinding("back"));

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
