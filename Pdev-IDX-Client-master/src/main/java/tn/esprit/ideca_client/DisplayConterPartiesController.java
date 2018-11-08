package tn.esprit.ideca_client;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.jfoenix.controls.JFXButton;

import contracts.UtilisateurRemote;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DisplayConterPartiesController implements Initializable {
	Logger logger = Logger.getAnonymousLogger();
	@FXML
	private JFXButton quizBtn;

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
	private Label emailLabel;
	@FXML
	private JFXButton editProfile;

	@FXML
	private Pane profileUser;

	@FXML
	private Button profilePic;

	public static final Timer timer = new Timer();

	@FXML
	private TableColumn<?, ?> lastName;

	@FXML
	private TableColumn<?, ?> country;

	@FXML
	private TableView<?> tableConterParties;

	@FXML
	private Label lastNameLabel13;

	@FXML
	private Label lastNameLabel12;

	@FXML
	private TableColumn<?, ?> firstName;

	@FXML
	private Label timerLabel1;

	@FXML
	private TableColumn<?, ?> nationality;

	@FXML
	private GridPane sidebar;

	@FXML
	private Label lastNameLabel11;

	@FXML
	private TableColumn<?, ?> grade;

	@FXML
	private Label fullname;

	@FXML
	private Label timerLabel;

	@FXML
	private TableColumn<?, ?> email;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		refresh();

		try {
			profileUser();
		} catch (NamingException e) {

			logger.log(Level.SEVERE, "exception was thrown   ", e);
		}

		profileUser.setVisible(false);
		profilePic.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				profileUser.setVisible(true);
				profilePic.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						profileUser.setVisible(false);

					}
				});

			}

		});
	}

	public void profileUser() throws NamingException {

		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";
		Context context = new InitialContext();
		UtilisateurRemote proxy = (UtilisateurRemote) context.lookup(jndiName);
		Utilisateur trader = proxy.findById(Sign_inController.userConnected);
		firstNameLabel.setText(trader.getFirstName());
		lastNameLabel.setText(trader.getLasttName());
		emailLabel.setText(trader.getEmail());
		firstNameLabel1.setText(trader.getFirstName());
		lastNameLabel1.setText(trader.getLasttName());

	}

	@FXML
	void editProfileAction(ActionEvent event) {

		try {
			timer.cancel();
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/Profile.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Acceuil Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "an exception was thrown ", e);
		}
	}

	public void heureactu() {// affichage heure

		Date dNow = new Date();
		SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
		SimpleDateFormat mdy = new SimpleDateFormat("MM/dd/yyyy");
		timerLabel1.setText(hm.format(dNow));
		timerLabel.setText(mdy.format(dNow));

	}

	public void refresh() {// rafraichir l'heure tt 50s
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
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
	void deconnect(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/Sign_in.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Deconnection Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "an exception was thrown   ", e);
		}

	}

	@FXML
	void quizBtn(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/QuizTrade.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Quiz Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "an exception was thrown   ", e);

		}
	}

	@FXML
	void go(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/EquityOption.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("goIdeca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "an  exception was thrown   ", e);
		}
	}

	@FXML
	void personalDataBtnAction(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/Profile.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Prsonal data Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "an exception was thrown  ", e);
		}
	}

	@FXML
	void portfolioBtnAction(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/Portfolio.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Protfolio Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "exception was thrown   ", e);
		}
	}

	@FXML
	void listTradersAction(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/ListTraderUser.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("ListTrader Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "an   exception was thrown   ", e);
		}
	}

	@FXML
	void helpDataBtnAction(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/HelpData.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Help  Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "an exception was thrown ", e);
		}
	}

	@FXML
	void listBackOfficeUserbtnActtion(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/ListBackOfficeUser.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("List BackOfficeUser Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {

			logger.log(Level.SEVERE, "an exception was  thrown   ", e);

		}
	}

	@FXML
	void pricingofFXOptionVtn(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/FxOption.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Sign_in Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "  an exception was thrown   ", e);
		}
	}

	@FXML
	void pricingofEquityOptionAction(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/EquityOption.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Sign_in Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "  an exception was thrown   ", e);
		}
	}

	@FXML
	void tradeBtnAction(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/trade.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Trade Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "an exception  was thrown   ", e);
		}
	}
}