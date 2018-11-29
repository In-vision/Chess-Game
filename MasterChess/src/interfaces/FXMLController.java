package interfaces;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class FXMLController {
	@FXML VBox root;
	
	@FXML HBox hBoxSave1;
	@FXML HBox hBoxSave2;
	@FXML HBox hBoxSave3;
	@FXML HBox hBoxBackButton;
	
	@FXML Text textSave1;
	@FXML Button buttonLoadSave1;
	@FXML Button buttonDeleteSave1;
	
	@FXML Text textSave2;
	@FXML Button buttonLoadSave2;
	@FXML Button buttonDeleteSave2;
	
	@FXML Text textSave3;
	@FXML Button buttonLoadSave3;
	@FXML Button buttonDeleteSave3;
	
	@FXML Button backButton;
	
	public FXMLController() {
		
	}
	
	@FXML
	private void initialize() {
		hBoxSave1.setId("hbox");
		hBoxSave2.setId("hbox");
		hBoxSave3.setId("hbox");
		
		hBoxBackButton.setId("hbox-back");
		
		Image load = new Image(getClass().getResourceAsStream("multimedia.png"));
		Image delete = new Image(getClass().getResourceAsStream("trash.png"));
		Image back = new Image(getClass().getResourceAsStream("left-arrow.png"));
		
		buttonLoadSave1.setGraphic(new ImageView(load));
		buttonDeleteSave1.setGraphic(new ImageView(delete));
		
		buttonLoadSave2.setGraphic(new ImageView(load));
		buttonDeleteSave2.setGraphic(new ImageView(delete));
		
		buttonLoadSave3.setGraphic(new ImageView(load));
		buttonDeleteSave3.setGraphic(new ImageView(delete));
		
		backButton.setGraphic(new ImageView(back));

		
	}

}
