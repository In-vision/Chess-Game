package interfaces;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class FXMLControllerPB {

	@FXML VBox root;
	@FXML HBox hBoxTitle;
	@FXML HBox hBoxProgress;
	@FXML HBox hBoxStatus;
	
	@FXML Text textTitle;
	
	@FXML ProgressBar progressBar;
	@FXML ProgressIndicator progressIndicator;
	
	@FXML Text textStatus;
	
	@FXML
	private void initialize() {
		textTitle.setId("loadText");
		textStatus.setId("loadText");
	}
}
