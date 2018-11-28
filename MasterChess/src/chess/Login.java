package chess;

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

public class Login extends VBox{
	public Label memberLogin, forgotPass;
	public TextField username;
	public PasswordField password;
	public Button login;
	public Button signup;
	public HBox hb;
	public Rectangle userAvatar;
	public Image avat;
	
	public Login() {
		super();
		
		hb = new HBox();
		hb.setPadding(new Insets(0, 12, 0, 12));
		
		memberLogin = new Label("Login to your account");
		
		username = new TextField();
		username.setPromptText("Username");
		
		password = new PasswordField();
		password.setPromptText("Password");
		
		login = new Button("Log in");
		signup = new Button("Sign up");
		
		username.setMaxWidth(250);
		username.setPrefWidth(250);
		
		password.setMaxWidth(250);
		password.setPrefWidth(250);
		
		userAvatar = new Rectangle(100, 100);
		avat = new Image("pieces/boy.png");	
		userAvatar.setFill(new ImagePattern(avat));
		
		login.getStyleClass().add("buttonLogin");
		signup.getStyleClass().add("buttonLogin");
		
		memberLogin.getStyleClass().add("labelLogin");
		memberLogin.setTextFill(Color.web("#ffffff"));
		
		hb.getChildren().addAll(login, signup);
		hb.setAlignment(Pos.CENTER);
		
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(12, 12, 12, 12));
		
		this.getChildren().addAll(userAvatar, memberLogin, username, password, hb);
		this.getStyleClass().add("login");
		
		VBox.setMargin(userAvatar, new Insets(48, 0, 12, 0));
		VBox.setMargin(memberLogin, new Insets(12, 0, 24, 0));
		VBox.setMargin(username, new Insets(0, 24, 12, 24));
		VBox.setMargin(password, new Insets(0, 24, 12, 24));
		VBox.setMargin(hb, new Insets(0, 12, 96 ,0));
		
		HBox.setMargin(login, new Insets(0, 12, 0 , 0));
			
	}
	
}

/*
public Insets top, right, bottom, left)
*/
