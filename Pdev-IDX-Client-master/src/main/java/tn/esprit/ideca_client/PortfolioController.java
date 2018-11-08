package tn.esprit.ideca_client;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.jfoenix.controls.JFXButton;

import contracts.TradeRemote;
import contracts.UtilisateurRemote;
import ideca.entity.Trade;
import ideca.entity.Trader;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PortfolioController implements Initializable {
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
	private Label EmailLabel;
	@FXML
	private JFXButton editProfile;

	@FXML
	private Pane ProfileUser;

	@FXML
	private Button ProfilePic;
	@FXML
	private TableView tableTrade;

	@FXML
	private TableColumn traderDestinationName;

	@FXML
	private TableColumn status;
	@FXML
	private Label amount;
	@FXML
	private TableColumn option;

	@FXML
	private TableColumn dateCapture;

	@FXML
	private TableColumn<?, ?> SettlementDate;
	// video
	@FXML
	private MediaView video;

	@FXML
	private MediaView video1;

	@FXML
	private MediaView video2;

	public static final Timer timer = new Timer();

	@FXML
	private Label timerLabel;
	@FXML
	private Label timerLabel1;
	ObservableList listTrades;
	Logger logger = Logger.getAnonymousLogger();
	private String singIn="Sign_in Ideca2.0";

	void affichage() throws NamingException {

		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";
		String jndiNameTrade = "Ideca_intelixx-ear/Ideca_intelixx-ejb/TradeService!contracts.TradeRemote";

		Context context;

		context = new InitialContext();
		UtilisateurRemote proxy = (UtilisateurRemote) context.lookup(jndiName);
		Trader user = (Trader) proxy.findById(Sign_inController.userConnected);
		MainApp.popupDestination = user.getId();

		TradeRemote proxyTrade = (TradeRemote) context.lookup(jndiNameTrade);

		traderDestinationName.setCellValueFactory(new PropertyValueFactory("trader_destination"));
		status.setCellValueFactory(new PropertyValueFactory("etat"));
		option.setCellValueFactory(new PropertyValueFactory("equityOption"));
		dateCapture.setCellValueFactory(new PropertyValueFactory("dateDemand"));
		SettlementDate.setCellValueFactory(new PropertyValueFactory("dateSettle"));

		listTrades = FXCollections.observableArrayList();

		for (Trade trade : proxyTrade.getAllTradesByTraderSource(user.getId())) {

			listTrades.add(trade);

		}

		tableTrade.setItems(listTrades);

	}

	public void initialize(URL location, ResourceBundle resources) {

		try {
			affichage();
		} catch (NamingException e1) {
			// Auto-generated catch block
			logger.log(Level.SEVERE, "an exception was thrown", e1);
		}
		Refresh();

		try {
			ProfileUser();
		} catch (NamingException e) {
			// Auto-generated catch block
			logger.log(Level.SEVERE, "an exception was thrown", e);
		}

		ProfileUser.setVisible(false);
		ProfilePic.setOnMouseEntered(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event) {
				// Auto-generated method stub
				ProfileUser.setVisible(true);
				ProfilePic.setOnMouseClicked(new EventHandler<MouseEvent>() {

					public void handle(MouseEvent event) {
						ProfileUser.setVisible(false);

					}
				});

			}

		});
	}

	public void ProfileUser() throws NamingException {

		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";
		Context context = new InitialContext();
		UtilisateurRemote proxy = (UtilisateurRemote) context.lookup(jndiName);

		Utilisateur trader = proxy.findById(Sign_inController.userConnected);
		Trader traderConnected = proxy.findByIdTrader(Sign_inController.userConnected);
		firstNameLabel.setText(trader.getFirstName());
		lastNameLabel.setText(trader.getLasttName());
		EmailLabel.setText(trader.getEmail());
		firstNameLabel1.setText(trader.getFirstName());
		lastNameLabel1.setText(trader.getLasttName());
		amount.setText(String.valueOf(traderConnected.getAmount()) + " $");
	}

	@FXML
	void EditProfileAction(ActionEvent event) throws IOException {

		timer.cancel();

		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/fxml/Profile.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Acceuil Ideca2.0");
		primaryStage.show();

	}

	public void heureactu() {
		Date dNow = new Date();
		SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
		SimpleDateFormat mdy = new SimpleDateFormat("MM/dd/yyyy");
		timerLabel1.setText(hm.format(dNow));
		timerLabel.setText(mdy.format(dNow));
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

	public void Refresh() {// rafraichir l'heure tt 50s
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					public void run() {
						try {
							heureactu();

						} catch (Exception ex) {
							Logger.getLogger(PortfolioController.class.getName()).log(Level.SEVERE, null, ex);
						}
					}
				});
			}
		}, 0, 50000);
	}

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
		primaryStage.setTitle(singIn);
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
		primaryStage.setTitle(singIn);
		primaryStage.show();

	}

	@FXML
	void go(ActionEvent event) throws IOException {

		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/fxml/EquityOption.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle(singIn);
		primaryStage.show();

	}

	@FXML
	void personalDataBtnAction(ActionEvent event) throws IOException {

		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/fxml/Profile.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle(singIn);
		primaryStage.show();

	}

	@FXML
	void portfolioBtnAction(ActionEvent event) throws IOException {

		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/fxml/Portfolio.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle(singIn);
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
		primaryStage.setTitle(singIn);
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
		primaryStage.setTitle(singIn);
		primaryStage.show();

	}

	@FXML
	void listBackOfficeUserbtnActtion(ActionEvent event) throws IOException {

		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/fxml/ListBackOfficeUser.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle(singIn);
		primaryStage.show();

	}

	@FXML
	void PricingofFXOptionVtn(ActionEvent event) throws IOException {

		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/fxml/FxOption.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle(singIn);
		primaryStage.show();

	}

	@FXML
	void PricingofEquityOptionAction(ActionEvent event) throws IOException {

		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/fxml/EquityOption.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle(singIn);
		primaryStage.show();

	}

	@FXML
	void tradeBtnAction(ActionEvent event) throws IOException {

		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/fxml/trade.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Pricing of EquityOption Ideca2.0");
		primaryStage.show();

	}

	@FXML
	void clickTableTrade(MouseEvent event) {
		// we didn't finish this project yet
	}
}
