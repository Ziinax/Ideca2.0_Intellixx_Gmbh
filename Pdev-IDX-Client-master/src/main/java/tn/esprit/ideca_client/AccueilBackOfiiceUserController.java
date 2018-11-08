package tn.esprit.ideca_client;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import contracts.NotificationsRemote;
import contracts.UtilisateurRemote;
import ideca.entity.Notification;
import ideca.entity.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AccueilBackOfiiceUserController implements Initializable {
	@FXML
	private JFXButton quizBtn;

	@FXML
	private JFXButton lissUserBtn;

	Logger logger = Logger.getAnonymousLogger();

	@FXML
	private JFXButton marketDataBnt;

	@FXML
	private JFXButton statistiqueBtn;

	@FXML
	private JFXButton fxOptionBnt;

	@FXML
	private Pane profilePane;

	

	@FXML
	private JFXTextField eur_JPYInput;

	@FXML
	private JFXTextField aud_usdInput;

	@FXML
	private JFXTextField uds_jpyInput;
	

    @FXML
    private JFXButton notification;
    

    @FXML
    private Label nb_notifications;


	@FXML
    void listNotificationsAction(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxml/Notifications.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Notifications Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {

		}
    }
	
public void initialize(URL location, ResourceBundle resources) {
	String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/NotificationsService!contracts.NotificationsRemote";
	String jndiNameUser = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";
	
	Context context;

	try {
		context = new InitialContext();
		NotificationsRemote proxy = (NotificationsRemote) context.lookup(jndiName);
		
		
		UtilisateurRemote proxyUser = (UtilisateurRemote) context.lookup(jndiNameUser);

		Utilisateur user = proxyUser.findById(Sign_inController.userConnected);
	
		int compteur=0;

		for (Notification notification : proxy.listeNotificatioForBackOffUser(user.getId())) {// get a remplir
			if(notification.getEtat().equals("notSeen"))
			{compteur++;}
			
		}
		nb_notifications.setText("( "+compteur+" )");
	} catch (NamingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	}
	@FXML
	void lissUserBtnAction(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/DisplayTraders.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("List of Users Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "r an exception was thrown !", e);
		}
	}

	@FXML
	void equitySymbolAction(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/EquitySymbol.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("List of Users Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "r an exception was thrown !!!", e);
		}
	}

	@FXML
	void marketDataBntAction(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/MarketData.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Market data Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "r an exception was thrown !!!", e);
		}
	}

	@FXML
	void quizBtnAction(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/AjoutQuestion.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Ajout Quiz Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "an exception was thrown !!!", e);
		}
	}
	@FXML
	void statistiqueBtnAction(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/Statistique.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Statistique Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "s an exception was thrown !!!", e);
		}
	}

	@FXML
	void listBackOfficeUsersAction(ActionEvent event) {

		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/Traders_backOffice.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("List Traders/BackOfficeUsers Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "l an exception was thrown !!!", e);
		}

	}

	@FXML
	void listActiveTraders(ActionEvent event) {

		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/Traders_backOffice.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("List Traders/BackOfficeUsers Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "   an exception was thrown !!!", e);
		}

	}

	@FXML
	void listTradeAction(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/TradesSattlment.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Ajout Quiz Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "r  an exception was thrown !!!", e);

		}
	}

	@FXML
	void deconnection(ActionEvent event) {

		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/Sign_in.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Sign in Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "r  an exception was thrown !!!", e);

		}
	}

	@FXML
	void goAccueil(ActionEvent event) {

		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/AccueilBackOfiiceUser.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Sign in Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "r  an exception was thrown !!!", e);

		}
	}
}
