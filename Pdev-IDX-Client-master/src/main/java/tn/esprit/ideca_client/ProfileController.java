package tn.esprit.ideca_client;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import contracts.UtilisateurRemote;
import ideca.entity.Trader;
import ideca.entity.Utilisateur;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ProfileController implements Initializable {
	@FXML
	private JFXButton quizBtn;
	public static int userConnected;
	@FXML
	private JFXButton aboutUsButton;

	@FXML
	private TextField firstNameInput;

	@FXML
	private TextField lastNameInput;

	@FXML
	private TextField cinInput;

	@FXML
	private TextField countryLabel;

	@FXML
	private TextField nationaliteLabel;

	@FXML
	private DatePicker dateOfBirthLabel;

	@FXML
	private JFXButton btnSave;

	@FXML
	private TextField gradeInput;

	@FXML
	private TextField addressLabel;

	@FXML
	private JFXButton profileButton;

	@FXML
	private JFXButton contactSupportButton;

	@FXML
	private JFXButton personalDataButton;

	@FXML
	private JFXCheckBox female;

	@FXML
	private JFXCheckBox male;
	@FXML
	private Label firstNameLabel;

	@FXML
	private Label lastNameLabel;
	@FXML
	private Button deconnex;
	@FXML
	private Label firstNameLabel1;

	@FXML
	private Label lastNameLabel1;

	@FXML
	private Label EmailLabel;
	@FXML
	private JFXButton editProfile;

	@FXML
	private Pane ProfileUser;

	@FXML
	private Button ProfilePic;
	private String signIn="Sign_in Ideca2.0" ; 
	private String fxmlProfile="/fxml/Profile.fxml";
	Logger logger = Logger.getAnonymousLogger();

	public static final Timer timer = new Timer();

	@FXML
	private Label timerLabel;
	@FXML
	private Label timerLabel1;
public void initialize(URL location, ResourceBundle resources) {

		try {
			profileUser();
			ProfileUserAccueil();
		} catch (NamingException e1) {
			
			logger.log(Level.SEVERE, "an exception was thrown", e1);
		}

		Refresh();

		ProfileUser.setVisible(false);
		ProfilePic.setOnMouseEntered(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event) {
				
				ProfileUser.setVisible(true);
				ProfilePic.setOnMouseClicked(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent event) {
						ProfileUser.setVisible(false);

					}
				});

			}

		});
		
	}

	public UtilisateurRemote accessServer() throws NamingException {

		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";
		Context context = new InitialContext();
		return (UtilisateurRemote) context.lookup(jndiName);
		
	}
	 @FXML
	    void tradeRequestBtnAction(ActionEvent event) throws IOException {
			
		   ((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/TradeRequest.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Trade Request Ideca2.0");
			primaryStage.show();
		
	    }

	public void profileUser() throws NamingException {
		UtilisateurRemote proxy = accessServer();
		Utilisateur trader = proxy.findById(Sign_inController.userConnected);
		firstNameInput.setText(trader.getFirstName());
		lastNameInput.setText(trader.getLasttName());
		cinInput.setText(trader.getCin());
		countryLabel.setText(trader.getCountry());
		nationaliteLabel.setText(trader.getNationalite());
		addressLabel.setText(trader.getAdresse());
		
		
		String gender = trader.getGender();
		if ("Female".equals(gender)) {
			female.setSelected(true);
		} else if ("Male".equals(gender)) {
			male.setSelected(true);
		} else {
			male.setSelected(false);
			female.setSelected(false);

		}


	}
	@FXML
	void ContactSupportAction(ActionEvent event) throws IOException {
		

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/ContactData.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			String title="Acceuil Ideca2.0 ";
			primaryStage.setTitle(title);
			primaryStage.show();
		
	}
	@FXML
	void genderCheck(ActionEvent event) {
		if (female.isSelected()) {
			male.setSelected(false);
			male.setAllowIndeterminate(true);
		}

		else if (male.isSelected()) {
			female.setSelected(false);
			female.setAllowIndeterminate(true);
		} else if (male.isSelected() || female.isSelected()) {
			male.setSelected(false);
			female.setSelected(false);
		}
	}

	@FXML
	void PersonalDataAction(ActionEvent event) throws IOException {

		

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource(fxmlProfile));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Acceuil Ideca2.0");
			primaryStage.show();
		

	}

	@FXML
	void btnSaveAction(ActionEvent event) throws NamingException {

		UtilisateurRemote proxy = accessServer();
		Trader user = (Trader) proxy.findById(Sign_inController.userConnected);
		user.setLasttName(lastNameInput.getText());
		user.setFirstName(firstNameInput.getText());
		user.setAdresse(addressLabel.getText());
		user.setCin(cinInput.getText());
		user.setCountry(countryLabel.getText());
		user.setNationalite(nationaliteLabel.getText());
		user.setDateOfBirth(java.sql.Date.valueOf(dateOfBirthLabel.getValue()));
				

		if (female.isSelected())

		{
			user.setGender("Female");

		}

		if (male.isSelected()) {
			user.setGender("Male");

		}

		proxy.updateUtilisateur(user);

	}

	@FXML
	void acceuilAction(ActionEvent event) throws IOException {

		

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxml/Accueil.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Acceuil Ideca2.0");
			primaryStage.show();
		

	}

	@FXML
	void aboutUsButtonAction(ActionEvent event) {
		// this method is not used yet
	}

	public void ProfileUserAccueil() throws NamingException {

		UtilisateurRemote proxy = accessServer();

		
		Utilisateur trader = proxy.findById(Sign_inController.userConnected);
		firstNameLabel.setText(trader.getFirstName());
		lastNameLabel.setText(trader.getLasttName());
		EmailLabel.setText(trader.getEmail());
		firstNameLabel1.setText(trader.getFirstName());
		lastNameLabel1.setText(trader.getLasttName());

	}

	
	@FXML
	void EditProfileAction(ActionEvent event) throws IOException {

		
			timer.cancel();
			
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource(fxmlProfile));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Edit Profile Ideca2.0");
			primaryStage.show();
		
	}


	public void heureactu() {

		Date dNow = new Date();
		SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
		SimpleDateFormat mdy = new SimpleDateFormat("MM/dd/yyyy");
		timerLabel1.setText(hm.format(dNow));
		timerLabel.setText(mdy.format(dNow));

	}

	public void Refresh() {
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					public void run() {
						try {
							heureactu();

						} catch (Exception ex) {
							Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
						}
					}
				});
			}
		}, 0, 50000);
	}

	//////////////////////////////////////////

	@FXML
	void Deconnect(ActionEvent event) throws IOException {
		

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxml/Sign_in.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Inscription Ideca2.0");
			primaryStage.show();
		
	}

	@FXML
	void DeconnexAction(ActionEvent event) throws IOException {
		

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxml/Sign_in.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle(signIn);
			primaryStage.show();
		
	}

	@FXML
	void quizBtn(ActionEvent event) throws IOException {
	

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxml/QuizTrade.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle(signIn);
			primaryStage.show();
		
	}

	@FXML
	void go(ActionEvent event) throws IOException {
		

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxml/DisplayConterParties.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle(signIn);
			primaryStage.show();
		
	}

	@FXML
	void personalDataBtnAction(ActionEvent event) throws IOException {
		

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource(fxmlProfile));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle(signIn);
			primaryStage.show();
		
	}

	@FXML
	void helpDataBtnAction(ActionEvent event) throws IOException {
		

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxml/HelpData.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle(signIn);
			primaryStage.show();
		
	}

	@FXML
	void listBackOfficeUserbtnActtion(ActionEvent event) throws IOException {
		

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/ListBackOfficeUser.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle(signIn);
			primaryStage.show();
		
	}

	@FXML
	void portfolioBtnAction(ActionEvent event) throws IOException {
		

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/Portfolio.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle(signIn);
			primaryStage.show();
		
	}

	@FXML
	void listTradersAction(ActionEvent event) throws IOException {
		

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxml/ListTraderUser.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle(signIn);
			primaryStage.show();
		
	}

	@FXML
	void PricingofFXOptionVtn(ActionEvent event) throws IOException {
		

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/FxOption.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle(signIn);
			primaryStage.show();
		
	}

	@FXML
	void PricingofEquityOptionAction(ActionEvent event) throws IOException {
		

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/EquityOption.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle(signIn);
			primaryStage.show();
		
	}

	@FXML
	void tradeBtnAction(ActionEvent event) throws IOException {
		

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/trade.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Pricing of EquityOption Ideca2.0");
			primaryStage.show();
		
	}

	@FXML
	void deleteAcountTrader(ActionEvent event) throws NamingException, IOException {

		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";
		Context context = new InitialContext();
		UtilisateurRemote proxy=(UtilisateurRemote) context.lookup(jndiName);
		proxy.supprimerUtilisateur(69);
		
		

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/Sign_up.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Pricing of EquityOption Ideca2.0");
			primaryStage.show();
		
		
	}


}
