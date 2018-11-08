package tn.esprit.ideca_client;


import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;


import contracts.NotificationsRemote;
import contracts.TradeRemote;
import contracts.UtilisateurRemote;
import ideca.entity.EnumEtatTrade;
import ideca.entity.Notification;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TradeRequestController implements Initializable {
	@FXML
	private Button ProfilePic;
	private String signIn="Sign_in Ideca2.0";
	@FXML
	private Label timerLabel;

	@FXML
	private Label timerLabel1;

	@FXML
	private Pane ProfileUser;

	@FXML
	private Label firstNameLabel;

	@FXML
	private Label lastNameLabel;

	@FXML
	private Label EmailLabel;

	@FXML
	private JFXButton editProfile;

	@FXML
	private TableView tableTrade;

	@FXML
	private TableColumn traderSourceName;

	@FXML
	private TableColumn status;
    @FXML
    private JFXButton tradeRequestBtn;

	@FXML
	private TableColumn option;

	@FXML
	private TableColumn dateCapture;

	@FXML
	private TableColumn SettlementDate;

	@FXML
	private Button btnSettleTrade;

	@FXML
	private Button btnUnsettleTrade;

	@FXML
	private GridPane sidebar;

	@FXML
	private Label fullname;

	@FXML
	private Label firstNameLabel1;

	@FXML
	private Label lastNameLabel1;

	@FXML
	private Label lastNameLabel11;

	@FXML
	private Label lastNameLabel12;

	@FXML
	private Label lastNameLabel13;
	@FXML
	private TextField idTradeRequest;
	@FXML
	private Button deconnex;

	@FXML
	private JFXButton quizBtn;

	private String jndiNameTrade = "Ideca_intelixx-ear/Ideca_intelixx-ejb/TradeService!contracts.TradeRemote";
	private String jndiNameUser = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";

	ObservableList listTrades;

	public Timer timer = new Timer();
	Logger logger = Logger.getAnonymousLogger();



	public void initialize(URL location, ResourceBundle resources) {
idTradeRequest.setVisible(false);
		try {
			affichage();
		} catch (NamingException e1) {
			logger.log(Level.SEVERE, "an exception was throwne", e1);
		}

		Refresh();

		try {
			ProfileUser();
		} catch (NamingException e) {
			logger.log(Level.SEVERE, "an exception was throwne", e);
		}

		ProfileUser.setVisible(false);
		ProfilePic.setOnMouseEntered(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event) {
				//  Auto-generated method stub
				ProfileUser.setVisible(true);
				ProfilePic.setOnMouseClicked(new EventHandler<MouseEvent>() {

					public void handle(MouseEvent event) {
						ProfileUser.setVisible(false);

					}
				});

			}

		});
	}

	@FXML
	void settleTrade(ActionEvent event) throws NamingException {

		Context context = new InitialContext();

		TradeRemote proxy = (TradeRemote) context.lookup(jndiNameTrade);
		UtilisateurRemote proxyUser = (UtilisateurRemote) context.lookup(jndiNameUser);
		Trader user = (Trader) proxyUser.findById(Sign_inController.userConnected);

		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText("Would You Like To Proceed Your Choice?");
		alert.setContentText("Please choose an option.");

		ButtonType yesButton = new ButtonType("Yes");
		ButtonType noButton = new ButtonType("No");
		ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(yesButton, noButton, cancelButton);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent()&&result.get() == yesButton) {
			if (idTradeRequest.getText() != null) {
				proxy.settleTrade(
						proxy.findByIdSourceIdDestination(Integer.parseInt(idTradeRequest.getText()), user.getId()));
						Notif_with_action("Settelment", "Trade Settled", "tradeSettlement", user);
						Trade trade=proxy.findByIdSourceIdDestination(Integer.parseInt(idTradeRequest.getText()), user.getId());
						Trader userSource = (Trader) proxyUser.findById(Integer.parseInt(idTradeRequest.getText()));

						user.setAmount(user.getAmount()-trade.getEquityOption().getPrice());
						userSource.setAmount(userSource.getAmount()+trade.getEquityOption().getPrice());
						proxyUser.updateUtilisateur(user);
						proxyUser.updateUtilisateur(userSource);

						
			}
			affichage();
		} else  {
			event.consume();
		} 

	}
	
	public void Notif_with_action(String title, String text,String type, Trader trader) throws NamingException{
		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/NotificationsService!contracts.NotificationsRemote";
		Context context = new InitialContext();
		NotificationsRemote proxy = (NotificationsRemote) context.lookup(jndiName);
		
		Date today = new Date();
		Notification notification = new Notification();
		notification.setId_trader(trader.getId());
		notification.setId_BackofficeUser(trader.getBackOfficeUser().getId());
		notification.setDate(today);
		notification.setText(text+" by "+trader.getLasttName());
		notification.setEtat("notSeen");
		notification.setType("forBackoffice");
		
		proxy.ajouterNotification(notification); //work
		notification.setText(text);
		notification.setType("tradeRequest");
		proxy.ajouterNotification(notification); //work

		Notifications notif =  Notifications.create()
				.title(title)
				.text(text)
				.hideCloseButton()
				.position(Pos.BOTTOM_RIGHT)
				.onAction(new EventHandler<ActionEvent>() {
					
					public void handle(ActionEvent arg0) {
						//  Auto-generated method stub
						
					}
				})
				;
	
			notif.showConfirm();
		
	}

	@FXML
	void unsettleTrade(ActionEvent event) throws NamingException {

		Context context = new InitialContext();

		TradeRemote proxy = (TradeRemote) context.lookup(jndiNameTrade);
		UtilisateurRemote proxyUser = (UtilisateurRemote) context.lookup(jndiNameUser);
		Trader user = (Trader) proxyUser.findById(Sign_inController.userConnected);

		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText("Would You Like To Proceed Your Choice?");
		alert.setContentText("Please choose an option.");

		ButtonType yesButton = new ButtonType("Yes");
		ButtonType noButton = new ButtonType("No");
		ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(yesButton, noButton, cancelButton);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent()&&result.get() == yesButton) {
			if (idTradeRequest.getText() != null) {
				proxy.unsettleTrade(
						proxy.findByIdSourceIdDestination(Integer.parseInt(idTradeRequest.getText()), user.getId()));
				Notif_with_action("Settelment", "Trade Unsettled", "tradeSettlement", user);

			}
			affichage();
		} else  {
			event.consume();
		}
	}

	@FXML
	void clickTableTradeRequest(MouseEvent event) throws IOException {
		if (tableTrade != null) {

			List<Trade> tables = tableTrade.getSelectionModel().getSelectedItems();
			if (tables.size() == 1) {
				final Trade selectedDisc = tables.get(0);
				if (selectedDisc != null) {
					MainApp.popupSource=tables.get(0).getTrader_source().getId() ;
					idTradeRequest.setText(String.valueOf(tables.get(0).getTrader_source().getId()));
					
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/OptionInformation.fxml"));
					Parent root1 = (Parent) fxmlLoader.load();
					Stage stage = new Stage();
					stage.setTitle("Trade Informations ") ;
					stage.setScene(new Scene(root1)); 
					stage.show();
					
					
					if (selectedDisc.getEtat()==EnumEtatTrade.SETTLED ||selectedDisc.getEtat()==EnumEtatTrade.UNSETTLED )
					{
						btnSettleTrade.setDisable(true);
						btnUnsettleTrade.setDisable(true);
						
					}
					else{
						btnSettleTrade.setDisable(false);
						btnUnsettleTrade.setDisable(false);
					}
				}
			}
		}

	}

	void affichage() throws NamingException {


		Context context;

		context = new InitialContext();
		UtilisateurRemote proxy = (UtilisateurRemote) context.lookup(jndiNameUser);
		Trader user = (Trader) proxy.findById(Sign_inController.userConnected);
		MainApp.popupDestination=user.getId() ;

		TradeRemote proxyTrade = (TradeRemote) context.lookup(jndiNameTrade);

		traderSourceName.setCellValueFactory(new PropertyValueFactory("trader_source"));
		status.setCellValueFactory(new PropertyValueFactory("etat"));
		option.setCellValueFactory(new PropertyValueFactory("equityOption"));
		dateCapture.setCellValueFactory(new PropertyValueFactory("dateDemand"));
		SettlementDate.setCellValueFactory(new PropertyValueFactory("dateSettle"));

		listTrades = FXCollections.observableArrayList();

		for (Trade trade : proxyTrade.getAllTradesByTraderDestination(user.getId())) {

			listTrades.add(trade);

		}

		tableTrade.setItems(listTrades);

	}

	public void ProfileUser() throws NamingException {

		Context context = new InitialContext();
		UtilisateurRemote proxy = (UtilisateurRemote) context.lookup(jndiNameUser);

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
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxml/Profile.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Acceuil Ideca2.0");
			primaryStage.show();
		
	}

	


	public void heureactu() {// affichage heure

		Date dNow = new Date();
		SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
		SimpleDateFormat mdy = new SimpleDateFormat("MM/dd/yyyy");
		timerLabel1.setText(hm.format(dNow));
		timerLabel.setText(mdy.format(dNow));

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
			Pane root = loader.load(getClass().getResource("/fxml/EquityOption.fxml"));
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
			Pane root = loader.load(getClass().getResource("/fxml/Profile.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle(signIn);
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
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxml/ListBackOfficeUser.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle(signIn);
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
			primaryStage.setTitle(signIn);
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
			primaryStage.setTitle(signIn);
			primaryStage.show();
		
	}

	@FXML
	void tradeBtnAction(ActionEvent event) throws IOException {
		

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxml/Trade.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Pricing of EquityOption Ideca2.0");
			primaryStage.show();
		
	}
    @FXML
    void tradeRequestBtnAction(ActionEvent event) throws IOException {
    	

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxml/TradeRequest.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Pricing of EquityOption Ideca2.0");
			primaryStage.show();
		
    }
}
