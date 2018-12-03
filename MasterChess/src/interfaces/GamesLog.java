package interfaces;

import java.io.IOException;

import i18n.I18N;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GamesLog extends VBox{

	public VBox root;
	public Button back, load1, delete1, load2, delete2, load3, delete3;
	public Text save1, save2, save3;
	
	public GamesLog() throws IOException{
		super();
		FXMLLoader fxmlResource = new FXMLLoader(getClass().getResource("GamesLogScreen.fxml"));
		root = fxmlResource.load();		
		back = (Button) fxmlResource.getNamespace().get("backButton");
		back.textProperty().bind(I18N.createStringBinding("back"));
		
		save1 = (Text) fxmlResource.getNamespace().get("textSave1");
		save1.textProperty().bind(I18N.createStringBinding("save1"));
		load1 = (Button) fxmlResource.getNamespace().get("buttonLoadSave1");
		delete1 = (Button) fxmlResource.getNamespace().get("buttonDeleteSave1");
		
		save2 = (Text) fxmlResource.getNamespace().get("textSave2");
		save2.textProperty().bind(I18N.createStringBinding("save2"));
		load2 = (Button) fxmlResource.getNamespace().get("buttonLoadSave2");
		delete2 = (Button) fxmlResource.getNamespace().get("buttonDeleteSave2");
		
		save3 = (Text) fxmlResource.getNamespace().get("textSave3");
		save3.textProperty().bind(I18N.createStringBinding("save3"));
		load3 = (Button) fxmlResource.getNamespace().get("buttonLoadSave3");
		delete3 = (Button) fxmlResource.getNamespace().get("buttonDeleteSave3");
		
		
		this.getChildren().add(root);
		
	}

}
