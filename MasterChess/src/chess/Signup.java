package chess;

import i18n.I18N;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Signup extends VBox{
	public Label memberLogin, forgotPass;
	public TextField username;
	public TextField email;
	public PasswordField password;
	public Button signup;
	public Button backButt;
	public Rectangle userAvatar;
	public Image avat;
	public HBox cancel;
	
	public Signup() {
		super();
		
		cancel = new HBox();
		cancel.setAlignment(Pos.CENTER);
		backButt = new Button("Cancel");
		backButt.getStyleClass().add("buttonLogin");
		backButt.setAlignment(Pos.CENTER);
		
		memberLogin = I18N.labelForKey("login");
		
		username = new TextField();
		username.setPromptText(I18N.getMessage("username"));
		
		email = new TextField("Email");
		email.setPromptText("Email");
		
		password = new PasswordField();
		password.setPromptText(I18N.getMessage("password"));
		
		signup = I18N.buttonForKey("signUp");
		
		username.setMaxWidth(250);
		username.setPrefWidth(250);
		
		password.setMaxWidth(250);
		password.setPrefWidth(250);
		
		userAvatar = new Rectangle(100, 100);
		avat = new Image("pieces/boy.png");	
		userAvatar.setFill(new ImagePattern(avat));
		
		signup.getStyleClass().add("buttonLogin");
		
		memberLogin.getStyleClass().add("labelLogin");
		memberLogin.setTextFill(Color.web("#ffffff"));
		
		cancel.getChildren().addAll(signup, backButt);
		HBox.setMargin(signup, new Insets(0, 6, 0, 0));
		
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(12, 12, 12, 12));
		
		this.getChildren().addAll(userAvatar, memberLogin, username, email, password, cancel);
		this.getStyleClass().add("login");
		
		VBox.setMargin(userAvatar, new Insets(48, 0, 12, 0));
		VBox.setMargin(memberLogin, new Insets(12, 0, 24, 0));
		VBox.setMargin(username, new Insets(0, 24, 12, 24));
		VBox.setMargin(email, new Insets(0, 24, 12, 24));
		VBox.setMargin(password, new Insets(0, 24, 12, 24));
		VBox.setMargin(signup, new Insets(0, 12, 96 ,0));
		VBox.setMargin(cancel, new Insets(0, 0, 96, 0));
			
	}
	
}
