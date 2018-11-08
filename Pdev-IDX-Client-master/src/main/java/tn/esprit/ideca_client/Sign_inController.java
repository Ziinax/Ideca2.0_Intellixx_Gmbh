package tn.esprit.ideca_client;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import contracts.NotificationsRemote;
import contracts.UtilisateurRemote;
import ideca.entity.Notification;
import ideca.entity.Utilisateur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Sign_inController implements Initializable {
	public static int userConnected;
	@FXML
	private Text emailLabel;

	@FXML
	private Text pwdLabel;

	static final  String FILE_PATH = "idUser";
	@FXML
	private JFXPasswordField pwdInput;

	@FXML
	private JFXTextField emailInput;

	@FXML
	private JFXButton signInAction;

	@FXML
	private JFXButton pwdforgotten;

	@FXML
	private JFXButton btn_exit;
	@FXML
	private Label labelStatus;
	Logger logger = Logger.getAnonymousLogger();
	String error = "Exception";

	@FXML
	void inscriptionAction(ActionEvent event) {

		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/Sign_up.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Inscription Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, error, e);
		}
	}

	@FXML
	void signInAction(ActionEvent event) throws NamingException, IOException {
		
		if (emailInput.getText().isEmpty()) {
			emailLabel.setText("this Field is empty!");
		} else {
			emailLabel.setText("");
		}
		if (pwdInput.getText().isEmpty()) {
			pwdLabel.setText("this Field is empty!");
		} else {
			pwdLabel.setText("");
		}

		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";
		Context context = new InitialContext();
		UtilisateurRemote proxy = (UtilisateurRemote) context.lookup(jndiName);

		Utilisateur user = proxy.verifConnexion(emailInput.getText(), pwdInput.getText()+"tl7sue3glpwcw80ksscgcoo80sc0s48");

		if (user == null) {
			labelStatus.setText("Verifiez vos paramètres de connexion! Ou votre compte n'est pas encore validé!");
			
				Stage primaryStage = new Stage();
				Pane root = FXMLLoader.load(getClass().getResource("/fxml/PopupInvalidLogIn.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("PopupInvalidLogIn Ideca2.0");
				primaryStage.show();
			

		} else {

			signInAction.getScene().getWindow().hide();
			userConnected = user.getId();
			
			

			Sign_inController.userConnected = user.getId();

			if ("Trader".equals(user.getType())) {
				try {
					pwdInput.setText("");
					emailInput.setText("");

					((Node) event.getSource()).getScene().getWindow().hide();
					Stage primaryStage = new Stage();
					Pane root = FXMLLoader.load(getClass().getResource("/fxml/Accueil.fxml"));
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.setTitle("Acceuil Ideca2.0");
					primaryStage.show();
				} catch (Exception e) {
					logger.log(Level.SEVERE, error, e);
				}
			} else if ("BackOfficeUser".equals(user.getType())) {
				
					pwdInput.setText("");
					emailInput.setText("");

					((Node) event.getSource()).getScene().getWindow().hide();
					Stage primaryStage = new Stage();
					Pane root = FXMLLoader.load(getClass().getResource("/fxml/AccueilBackOfiiceUser.fxml"));
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.setTitle("AccueilBackOfiiceUser Ideca2.0");
					primaryStage.show();
				
			}
		}
		

	}
	 
	public void Notif_with_action(String title, String text,String type, Utilisateur trader) throws NamingException{
		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/NotificationsService!contracts.NotificationsRemote";
		Context context = new InitialContext();
		NotificationsRemote proxy = (NotificationsRemote) context.lookup(jndiName);
		
		Date today = new Date();
		Notification notification = new Notification();
		notification.setId_trader(trader.getId());
		notification.setId_BackofficeUser(0);
		notification.setDate(today);
		notification.setText(trader.getLasttName());
		notification.setEtat("notSeen");
		notification.setType("connexion");
		
		
		proxy.ajouterNotification(notification); //work
		Notifications notif =  Notifications.create()
				.title(title)
				.text(text)
				.hideCloseButton()
				.position(Pos.BOTTOM_RIGHT).onAction(new EventHandler<ActionEvent>() {
					
					
					public void handle(ActionEvent arg0) {
						//  Auto-generated method stub
						
					}
				})
				;
	
			notif.showConfirm();
		
		
	}

	@FXML
	void pwdforgottenAction(ActionEvent event) throws IOException {

		
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MotdePasseOublie.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle("Mot de passe oublié? ");
			stage.setScene(new Scene(root1));
			stage.show();

		

	}

	public void initialize(URL location, ResourceBundle resources) {
		// Auto-generated method stub

	}

	@FXML
	void fermerAction(ActionEvent event) {
		btn_exit.getScene().getWindow().hide(); // 1ere méthode
		// Platform.exit(); //2eme méthode
	}

}
