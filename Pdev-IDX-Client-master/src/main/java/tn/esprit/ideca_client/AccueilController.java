package tn.esprit.ideca_client;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import contracts.NotificationsRemote;
import contracts.UtilisateurRemote;
import ideca.entity.Notification;
import ideca.entity.Utilisateur;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccueilController implements Initializable {
	@FXML
	private ImageView msg;
	@FXML
	private JFXButton quizBtn;

	@FXML
	private Label firstNameLabel;

	Logger logger = Logger.getAnonymousLogger();
	@FXML
	private Label countNotification;

	@FXML
	private Label notifications;

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
	private JFXButton tradeRequestBtn;

	@FXML
	private Button profilePic;

	// video
	@FXML
	private MediaView video;

	@FXML
	private MediaView video1;

	@FXML
	private MediaView video2;
	private String jndiNameNotification = "Ideca_intelixx-ear/Ideca_intelixx-ejb/NotificationsService!contracts.NotificationsRemote";
	private String jndiNameUser = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";
	private ObservableList<Object> listNotification;
	private final String notSeen = "notSeen";

	public static final Timer timer = new Timer();

	@FXML
	private Label timerLabel;

	@FXML
	private Label timerLabel1;
	String s = new File("src\\main\\resources\\images\\v7.mp4").getAbsolutePath();
	final File file = new File(s);
	final Media media = new Media(file.toURI().toString());
	final MediaPlayer mediaPlayer = new MediaPlayer(media);

	String s1 = new File("src\\main\\resources\\images\\v4.mp4").getAbsolutePath();
	final File file1 = new File(s1);
	final Media media1 = new Media(file1.toURI().toString());
	final MediaPlayer mediaPlayer1 = new MediaPlayer(media1);

	String s2 = new File("src\\main\\resources\\images\\v2.mp4").getAbsolutePath();
	final File file2 = new File(s2);
	final Media media2 = new Media(file2.toURI().toString());
	final MediaPlayer mediaPlayer2 = new MediaPlayer(media2);

	@FXML
	void gotonotification(MouseEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxml/NotificationsTrader.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Notifications Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "an exception was throwne", e);

		}
	}

	public void initialize(URL location, ResourceBundle resources) {
		
		Context context;

		try {
			context = new InitialContext();
			
			
		
		NotificationsRemote proxy = (NotificationsRemote) context.lookup(jndiNameNotification);

		UtilisateurRemote proxyUser = (UtilisateurRemote) context.lookup(jndiNameUser);

		Utilisateur user = proxyUser.findById(Sign_inController.userConnected);
		int compteur = 0;
		listNotification = FXCollections.observableArrayList();

		for (Notification notification : proxy.listeNotificatioForTrader(user.getId())) {
			if (("tradeRequest").equals(notification.getType()) || ("tradeResponse").equals(notification.getType())
					|| ("information").equals(notification.getType())) {
				if (notSeen.equals(notification.getEtat())) {
					compteur++;
				}
				listNotification.add(notification);
			}
		}
		countNotification.setText("( " + compteur + " )");
		} catch (NamingException e1) {
			//  Auto-generated catch block
			e1.printStackTrace();
		}
		
		play();
		refresh();

		try {
			profileUser();
		} catch (NamingException e) {
			logger.log(Level.SEVERE, "an exception was thrown !!!", e);

		}

		profileUser.setVisible(false);
		profilePic.setOnMouseEntered(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event) {

				profileUser.setVisible(true);
				profilePic.setOnMouseClicked(new EventHandler<MouseEvent>() {
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

	public void Notif_with_action(String title, String text, String type, Utilisateur trader) throws NamingException {
		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/NotificationsService!contracts.NotificationsRemote";
		Context context = new InitialContext();
		NotificationsRemote proxy = (NotificationsRemote) context.lookup(jndiName);

		Date today = new Date();
		Notification notification = new Notification();
		notification.setId_trader(trader.getId());
		notification.setId_BackofficeUser(0);
		notification.setDate(today);
		notification.setText(text);
		notification.setEtat("notSeen");
		notification.setType("information");

		proxy.ajouterNotification(notification);

	}

	@FXML
	void accueilAction(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root;
			root = FXMLLoader.load(getClass().getResource("/fxml/Accueil.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Deconnect Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "an exception   was thrown", e);
		}
	}

	@FXML
	void editProfileAction(ActionEvent event) {

		try {
			timer.cancel();

			mediaPlayer.stop();
			mediaPlayer1.stop();

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/Profile.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Edit Profile Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "an exception was thrown   ", e);
		}
	}

	@FXML
	void tradeRequestBtnAction(ActionEvent event) {
		try {
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxml/TradeRequest.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Trade Request Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "an exception was thrownc", e);

		}
	}

	///////// video

	public void play() {// play video

		video.setMediaPlayer(mediaPlayer);
		mediaPlayer.play();
		mediaPlayer.setCycleCount(100);

		video1.setMediaPlayer(mediaPlayer1);
		mediaPlayer1.play();
		mediaPlayer1.setCycleCount(100);

		video2.setMediaPlayer(mediaPlayer2);
		mediaPlayer2.play();
		mediaPlayer2.setCycleCount(100);
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
			Pane root;
			root = FXMLLoader.load(getClass().getResource("/fxml/Sign_in.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Deconnect Ideca2.0");
			Notifications notif = Notifications.create().title("Session Closed").text("You are Disconected")
					.hideCloseButton().position(Pos.BOTTOM_RIGHT).onAction(new EventHandler<ActionEvent>() {

						public void handle(ActionEvent arg0) {
							// la methode auto generated

						}
					});

			notif.showConfirm();

			primaryStage.show();

		} catch (Exception e) {
			logger.log(Level.SEVERE, "an exception   was thrown", e);
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
			Notifications notif = Notifications.create().title("Session Closed").text("You are Disconected")
					.hideCloseButton().position(Pos.BOTTOM_RIGHT).onAction(new EventHandler<ActionEvent>() {

						public void handle(ActionEvent arg0) {
							// la methode est auto generated

						}
					});

			notif.showConfirm();

			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "an exception was thrown  ", e);
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
			primaryStage.setTitle("Edit Profile Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "an exception was thrown here", e);
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
			primaryStage.setTitle("Portfolio Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "an exception was thrown   ", e);
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
			primaryStage.setTitle("List Traders Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "an exception", e);
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
			primaryStage.setTitle("Help Data Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "an exception was thrown a", e);
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
			primaryStage.setTitle("List of BackOfficeUsers Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {

			logger.log(Level.SEVERE, "an exception was  thrown", e);

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
			primaryStage.setTitle("Pricing of FXoption Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "an  exception was thrown", e);
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
			primaryStage.setTitle("Valuation of EquityOption Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "  an exception was thrown", e);
		}
	}

	@FXML
	void tradeBtnAction(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/Trade.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Pricing of EquityOption Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "an exception was thrown", e);
		}
	}

	@FXML
	void PricingofEquityOptionAction(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxml/EquityOption.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Pricing of EquityOption Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "an exception was thrown", e);

		}
	}

}
