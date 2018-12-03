package interfaces;

import java.io.IOException;

import i18n.I18N;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class MenuPrincipal extends VBox {
	
	public VBox vBox;
	public Label title;
	public Button singlePlayer;
	public Button about;
	public Button quit;
	public Button conf;
	
	public  MenuPrincipal() throws IOException{
		super();
		FXMLLoader fxmlResource = new FXMLLoader(getClass().getResource("MenuPrincipalv1p2.fxml"));
		vBox = fxmlResource.load();
		
		title = (Label) fxmlResource.getNamespace().get("turistaMundialLabel");
		title.textProperty().bind(I18N.createStringBinding("playChess"));
		singlePlayer = (Button) fxmlResource.getNamespace().get("singlePlayerButton");
		singlePlayer.textProperty().bind(I18N.createStringBinding("singlePlayer"));
		quit = (Button) fxmlResource.getNamespace().get("exitButton");
		quit.textProperty().bind(I18N.createStringBinding("quit"));
		conf = (Button) fxmlResource.getNamespace().get("confButton");
		
		this.getChildren().add(vBox);


	}


}
