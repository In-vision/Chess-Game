package interfaces;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


public class MenuPrincipal extends VBox {
	
	public VBox vBox;
	public Button singlePlayer;
	public Button about;
	public Button quit;
	public Button conf;
	
	public  MenuPrincipal() throws IOException{
		super();
		FXMLLoader fxmlResource = new FXMLLoader(getClass().getResource("MenuPrincipalv1p2.fxml"));
		vBox = fxmlResource.load();

		singlePlayer = (Button) fxmlResource.getNamespace().get("singlePlayerButton");
		quit = (Button) fxmlResource.getNamespace().get("exitButton");
		conf = (Button) fxmlResource.getNamespace().get("confButton");
		
		this.getChildren().add(vBox);


	}


}
