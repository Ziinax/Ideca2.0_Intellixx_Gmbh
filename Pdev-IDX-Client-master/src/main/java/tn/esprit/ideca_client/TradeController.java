package tn.esprit.ideca_client;


import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;

import contracts.EquityOptionRemote;
import contracts.FXOptionsRemote;
import contracts.NotificationsRemote;
import contracts.TradeRemote;
import contracts.UtilisateurRemote;
import ideca.entity.EquityOptions;
import ideca.entity.FXOptions;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TradeController implements Initializable {
	Logger logger = Logger.getAnonymousLogger();
	@FXML
	private JFXButton quizBtn;
    @FXML
    private JFXButton tradeRequestBtn;
	@FXML
	private Label firstNameLabel;
	private String signIn="Sign_in Ideca2.0";

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

	// video
	@FXML
	private MediaView video;

	@FXML
	private MediaView video1;


	@FXML
	private MediaView video2;

	public static final  Timer timer = new Timer();
	@FXML
	private Button btnTradeCapture;

	@FXML
	private Label timerLabel;
	@FXML
	private Label timerLabel1;
	@FXML
	private TableView Table_Fx;

	@FXML
	private TableColumn col_name;

	@FXML
	private TableColumn col_strike;

	@FXML
	private TableColumn col_spot;

	@FXML
	private TableColumn col_risk;

	@FXML
	private TableColumn col_maturity;

	@FXML
	private TableColumn col_volatility;

	@FXML
	private TableColumn col_type;

	@FXML
	private TableColumn col_shares;

	@FXML
	private TableColumn col_premium;

	@FXML
	private TableColumn col_foreign;

	@FXML
	private TableView Table_Equity1;

	@FXML
	private TableColumn col_name1;

	@FXML
	private TableColumn col_strike1;

	@FXML
	private TableColumn col_spot1;

	@FXML
	private TableColumn col_risk1;

	@FXML
	private TableColumn col_maturity1;

	@FXML
	private TableColumn col_volatility1;

	@FXML
	private TableColumn col_type1;

	@FXML
	private TableColumn col_shares1;
	@FXML
	private ComboBox<Trader> comboParty;
	@FXML
	private TextField idOption;

	@FXML
	private TextField idCounterparty;
	private String jndiNameUser = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";


	@FXML
	private TableColumn col_premium1;
	ObservableList listEquityOptions;
	ObservableList listFXOptions;

	public void initialize(URL location, ResourceBundle resources) {
idCounterparty.setVisible(false);
idOption.setVisible(false);
		try {
			playEquity();
		} catch (NamingException e1) {
			//  Auto-generated catch block
			logger.log(Level.SEVERE, "an exception was thrownea", e1);
		}
		try {
			playFx();
		} catch (NamingException e1) {
			//  Auto-generated catch block
			logger.log(Level.SEVERE, "an exception was thrownev", e1);
		}
		Refresh();

		try {
			ProfileUser();
		} catch (NamingException e) {
			logger.log(Level.SEVERE, "an exception was thrownea", e);

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
		Context context;
		
		try {
			context = new InitialContext();
			UtilisateurRemote proxy = (UtilisateurRemote) context.lookup(jndiNameUser);
			Trader user = (Trader) proxy.findById(Sign_inController.userConnected);
			for (int i = 0; i < proxy.listeUser("Trader").size(); i++) {
				
				
				comboParty.getItems().add(proxy.getAllTraders(user.getId()).get(i));

			}
		} catch (NamingException e) {
			//  Auto-generated catch block
			logger.log(Level.SEVERE, "an exception was throwne", e);
		}
		comboParty.setDisable(true);

	}

	@FXML
	void clickTableEquity(MouseEvent event) {
		if (Table_Equity1 != null) {

			List<EquityOptions> tables = Table_Equity1.getSelectionModel().getSelectedItems();
			if (tables.size() == 1) {
				final EquityOptions selectedDisc = tables.get(0);
				if (selectedDisc != null) {
					comboParty.setDisable(false);
					idOption.setText(String.valueOf(tables.get(0).getId()));
				}
			}
		}
	}
	
    @FXML
    void clickTableFx(MouseEvent event) {
    	if (Table_Fx != null) {

			List<FXOptions> tables = Table_Fx.getSelectionModel().getSelectedItems();
			if (tables.size() == 1) {
				final FXOptions selectedDisc = tables.get(0);
				if (selectedDisc != null) {
					comboParty.setDisable(false);
					idOption.setText(String.valueOf(tables.get(0).getId()));
				}
			}
		}
    }
	@FXML
	void captureTrade(ActionEvent event) throws NamingException {

		String jndiNameTrade ="Ideca_intelixx-ear/Ideca_intelixx-ejb/TradeService!contracts.TradeRemote";
		String jndiNameOption ="Ideca_intelixx-ear/Ideca_intelixx-ejb/EquityOptionService!contracts.EquityOptionRemote";
		
		Context context;
		context = new InitialContext();
		
		UtilisateurRemote proxy = (UtilisateurRemote) context.lookup(jndiNameUser);
		Trader user = (Trader) proxy.findById(Sign_inController.userConnected);

		EquityOptionRemote proxyOption = (EquityOptionRemote) context.lookup(jndiNameOption);
		TradeRemote proxyTrade = (TradeRemote) context.lookup(jndiNameTrade);
    	
		Trade trade=new Trade(proxy.findByIdTrader(user.getId()),proxy.findByIdTrader(Integer.parseInt(idCounterparty.getText())), proxyOption.getEquityOps(Integer.parseInt(idOption.getText())));

		proxyTrade.demandTrade(trade);
		Notif_with_action("trade request", "Your request has been sent","tradeRequest",user);
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
		notification.setText(trader.getLasttName()+" demand a trade");
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
	void counterpartySelection(ActionEvent event) {
idCounterparty.setText(String.valueOf(comboParty.getSelectionModel().getSelectedItem().getId()));
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

	

	public void heureactu() {

		Date dNow = new Date();
		SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
		SimpleDateFormat mdy = new SimpleDateFormat("MM/dd/yyyy");
		timerLabel1.setText(hm.format(dNow));
		timerLabel.setText(mdy.format(dNow));

	}

	public void Refresh() {// 
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					public void run() {
						try {
							heureactu();

						} catch (Exception ex) {
							Logger.getLogger(TradeController.class.getName()).log(Level.SEVERE, null, ex);
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
			Pane root = loader.load(getClass().getResource("/fxml/trade.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Pricing of EquityOption Ideca2.0");
			primaryStage.show();
		
	}

	void playEquity() throws NamingException {
		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/EquityOptionService!contracts.EquityOptionRemote";

		Context context;

		
			context = new InitialContext();
			EquityOptionRemote proxy = (EquityOptionRemote) context.lookup(jndiName);

			col_name1.setCellValueFactory(new PropertyValueFactory("name"));
			col_strike1.setCellValueFactory(new PropertyValueFactory("OptionStrikePrice"));
			col_spot1.setCellValueFactory(new PropertyValueFactory("CurrentStockPrice"));
			col_risk1.setCellValueFactory(new PropertyValueFactory("RiskFreeInterestRate"));
			col_maturity1.setCellValueFactory(new PropertyValueFactory("TimeUntilExercise"));
			col_volatility1.setCellValueFactory(new PropertyValueFactory("volatility"));
			col_type1.setCellValueFactory(new PropertyValueFactory("type"));
			col_shares1.setCellValueFactory(new PropertyValueFactory("numberOfShares"));
			col_premium1.setCellValueFactory(new PropertyValueFactory("price"));
			listEquityOptions = FXCollections.observableArrayList();

			for (EquityOptions equityoption : proxy.getAllEquityOps()) {
				listEquityOptions.add(equityoption);
			}
			Table_Equity1.setItems(listEquityOptions);

		

	}

	void playFx() throws NamingException {
		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/FXOptionsService!contracts.FXOptionsRemote";

		Context context;

		
			context = new InitialContext();
			FXOptionsRemote proxy = (FXOptionsRemote) context.lookup(jndiName);

			col_name.setCellValueFactory(new PropertyValueFactory("name"));
			col_strike.setCellValueFactory(new PropertyValueFactory("StrikePrice"));
			col_spot.setCellValueFactory(new PropertyValueFactory("SpotRate"));
			col_risk.setCellValueFactory(new PropertyValueFactory("InterestRateCurrency1"));
			col_foreign.setCellValueFactory(new PropertyValueFactory("InterestRateCurrency2"));
			col_maturity.setCellValueFactory(new PropertyValueFactory("TimeToMaturity"));
			col_volatility.setCellValueFactory(new PropertyValueFactory("volatility"));
			col_type.setCellValueFactory(new PropertyValueFactory("type"));
			col_shares.setCellValueFactory(new PropertyValueFactory("Nominal"));
			col_premium.setCellValueFactory(new PropertyValueFactory("price"));

			listFXOptions = FXCollections.observableArrayList();

			for (FXOptions equityoption : proxy.findAllFXOption()) {
				listFXOptions.add(equityoption);
			}
			Table_Fx.setItems(listFXOptions);

		

	}
	@FXML
	void listTradeAction(ActionEvent event) throws IOException {
		

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/TradesSattlment.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Ajout Quiz Ideca2.0");
			primaryStage.show();
		
	}
	 @FXML
	    void tradeRequestBtnAction(ActionEvent event) throws IOException {
		 

				((Node) event.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				Pane root = FXMLLoader.load(getClass().getResource("/fxml/TradeRequest.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Ajout Quiz Ideca2.0");
				primaryStage.show();
			
	    }
}
