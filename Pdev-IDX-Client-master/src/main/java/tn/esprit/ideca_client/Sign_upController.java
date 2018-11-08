package tn.esprit.ideca_client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import contracts.NotificationsRemote;
import contracts.UtilisateurRemote;

import ideca.entity.BackOfficeUser;
import ideca.entity.Notification;
import ideca.entity.EnumGrade;



import ideca.entity.Trader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Sign_upController implements Initializable {

	@FXML
	private JFXPasswordField pwdText;

	@FXML
	private JFXTextField emailText;

	@FXML
	private JFXTextField lastNameText;

	@FXML
	private JFXTextField firstNameText;

	@FXML
	private JFXButton btnLogin;

	@FXML
	private JFXButton btnSignUp;

	@FXML
	private Label labelStatus;
	@FXML
	private Label emptyEmail;
	@FXML
	private Label emptyPwdRep;
	@FXML
	private Label emptyPwd;
	@FXML
	private Label emptyLastName;

	@FXML
	private Label emptyFirstName;
	@FXML
	private Label emptyPwdOne;
	@FXML
	private Label emptyPwdTwo;
	private String trader="Trader" ; 
	Logger logger = Logger.getAnonymousLogger();
	@FXML
	private JFXPasswordField passwordVerifText;
	@FXML
	private JFXComboBox<String> gendesList;

	ObservableList<String> genders = FXCollections.observableArrayList(trader, "BackOfficeUser");

	@FXML
	void logInAction(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/Sign_in.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Sign_in Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "an exception was thrown ", e);
		}
	}

	@FXML
	void signUpAction(ActionEvent event) throws NamingException, IOException {

		if (firstNameText.getText().isEmpty()) {
			emptyFirstName.setText("this Field is empty!");
		} else {
			emptyFirstName.setText("");
		}
		if (lastNameText.getText().isEmpty()) {
			emptyLastName.setText("this Field is empty! ");
		} else {
			emptyLastName.setText("");
		}
		if (emailText.getText().isEmpty()) {
			emptyEmail.setText("this Field is empty! ");
		} else {
			emptyEmail.setText("");
			if (emailText.getText().indexOf('@') == -1) {
				emptyEmail.setText("The character must have an @!");
				btnSignUp.setDisable(true);
			} else {
				emptyEmail.setText("");
				btnSignUp.setDisable(false);
			}
			if (emailText.getText().indexOf('.') == -1) {
				emptyEmail.setText("The character must have a .");
				btnSignUp.setDisable(true);
			} else {
				emptyEmail.setText("");
				btnSignUp.setDisable(false);
			}

			if (pwdText.getText().length() < 8 ) {
				btnSignUp.setDisable(true);
				emptyPwd.setText("the length of the password must be between 8 and 15 caracters!");
			} else {
				emptyPwd.setText("");
				btnSignUp.setDisable(false);
			}

			if (!passwordVerifText.getText().equals(pwdText.getText()) ) {
				emptyPwdRep.setText("mismatch password!");
				btnSignUp.setDisable(true);

			} else {
				emptyPwdRep.setText("");
				btnSignUp.setDisable(false);
			}
		}
		if (!("").equals(firstNameText.getText())  && !("").equals(lastNameText.getText())
				&& !("").equals(emailText) && !("").equals(pwdText) 
				&& !("").equals(passwordVerifText)) {
			Trader tradera = new Trader();
			BackOfficeUser backOfiiceUser = new BackOfficeUser();
			String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";
			Context context = new InitialContext();
			UtilisateurRemote proxy = (UtilisateurRemote) context.lookup(jndiName);
			Boolean verifAjout = null;

			if (gendesList.getValue().equals(tradera)) {
				tradera.setLasttName(lastNameText.getText());
				tradera.setFirstName(firstNameText.getText());
				tradera.setEmail(emailText.getText());
				tradera.setPwd(pwdText.getText() + "tl7sue3glpwcw80ksscgcoo80sc0s48");
				tradera.setType(gendesList.getValue());// type de
				tradera.setGrade(EnumGrade.First_Level);

				// //utilisateur
				tradera.setEtat("0");// etat a verifier par le backoffice
				verifAjout = proxy.ajouterUtilisateur(tradera);
				Notif_with_action("Account Request","A Request was Sent to the BackOffice","Succes",(Trader)tradera);
			} else if (("BackOfficeUser").equals(gendesList.getValue())) {
				backOfiiceUser.setLasttName(lastNameText.getText());
				backOfiiceUser.setFirstName(firstNameText.getText());
				backOfiiceUser.setEmail(emailText.getText());
				backOfiiceUser.setPwd(pwdText.getText() + "tl7sue3glpwcw80ksscgcoo80sc0s48");
				backOfiiceUser.setType(gendesList.getValue());// type
																// de
				verifAjout = proxy.ajouterUtilisateur(backOfiiceUser);
				

			}

			if (verifAjout) {

				

					firstNameText.clear();
					lastNameText.clear();
					emailText.clear();
					pwdText.clear();
					passwordVerifText.clear();

					Stage primaryStage = new Stage();
					Pane root = FXMLLoader.load(getClass().getResource("/fxml/PopUpSignUp.fxml"));
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.setTitle("Acceuil Ideca2.0");
					primaryStage.show();
				
			} else {

				
					Stage primaryStage = new Stage();
					Pane root = FXMLLoader.load(getClass().getResource("/fxml/PopUpSignUpNonValid.fxml"));
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.setTitle("Acceuil Ideca2.0");
					primaryStage.show();
				
			}

		}
	}

	public void initialize(URL location, ResourceBundle resources) {
		gendesList.setValue(trader);
		gendesList.setItems(genders);

	}

	@FXML
	void retourAction(ActionEvent event) throws IOException {
		// btn_exit.getScene().getWindow().hide(); // 1ere méthode
		// Platform.exit(); //2eme méthode

		

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/Sign_in.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Sign_in Ideca2.0");
			primaryStage.show();
		
	}
	
	
	
	
	
	
	public void Notif_with_action(String title, String text,String type, Trader trader) throws NamingException{
		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/NotificationsService!contracts.NotificationsRemote";
		Context context = new InitialContext();
		NotificationsRemote proxy = (NotificationsRemote) context.lookup(jndiName);
		
		Date today = new Date();
		Notification notification = new Notification();
		notification.setId_trader(trader.getId());
		notification.setId_BackofficeUser(0);
		notification.setDate(today);
		notification.setText("A new Trader would like to Join Ideca");
		notification.setEtat("notSeen");
		notification.setType("registration");
		
		proxy.ajouterNotification(notification); //work
		Notifications notif =  Notifications.create()
				.title(title)
				.text(text)
				.hideCloseButton()
				.position(Pos.BOTTOM_RIGHT)
				.onAction(new EventHandler<ActionEvent>() {
					
					public void handle(ActionEvent arg0) {
						// aa Auto-generated method stub
						
					}
				})
				;
		if(("Succes").equals(type))
		{
			notif.showConfirm();
		}
		
	}
		

	public void Notif_simple(String title, String text)  {
		//this method is not used yet
	}

	public void Notif_with_action(String title, String text, String type) {
	//this method is not used yet
	}
}
