package interfaces;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class GamesLog extends VBox{

	public VBox root;
	public Button back, load1, delete1, load2, delete2, load3, delete3;
	
	public GamesLog() throws IOException{
		super();
		FXMLLoader fxmlResource = new FXMLLoader(getClass().getResource("GamesLogScreen.fxml"));
		root = fxmlResource.load();		
		back = (Button) fxmlResource.getNamespace().get("backButton");
		load1 = (Button) fxmlResource.getNamespace().get("buttonLoadSave1");
		delete1 = (Button) fxmlResource.getNamespace().get("buttonDeleteSave1");
		load2 = (Button) fxmlResource.getNamespace().get("buttonLoadSave2");
		delete2 = (Button) fxmlResource.getNamespace().get("buttonDeleteSave2");
		load3 = (Button) fxmlResource.getNamespace().get("buttonLoadSave3");
		delete3 = (Button) fxmlResource.getNamespace().get("buttonDeleteSave3");
		this.getChildren().add(root);
		
	}

}
