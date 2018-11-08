package tn.esprit.ideca_client;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;

import contracts.UtilisateurRemote;
import ideca.entity.Trader;
import ideca.entity.Utilisateur;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import contracts.EquityOptionRemote;
import contracts.MarketDataRemote;
import contracts.NotificationsRemote;
import contracts.UtilisateurRemote;
import ideca.entity.EquityOptions;
import ideca.entity.MarketData;
import ideca.entity.Notification;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class EquityOptionController implements Initializable {

	@FXML
	private Label LBtype;
	@FXML
	private Label PremiumPerShare;
	@FXML
	private JFXButton btnclear;
	@FXML
	private Label LBvalue;
	@FXML
	private JFXButton btnpersist;
	@FXML
	private JFXTextField eur_usdInput;

	@FXML
	private JFXTextField eur_GbpInput;

	@FXML
	private JFXTextField eur_JPYInput;

	@FXML
	private JFXTextField aud_usdInput;

	@FXML
	private JFXTextField uds_jpyInput;
	@FXML
	private JFXButton lissUserBtn;

	@FXML
	private JFXButton marketDataBnt;

	@FXML
	private JFXButton equityOptionBtn;

	@FXML
	private JFXButton fxOptionBnt;

	@FXML
	private Pane ProfilePane;

	@FXML
	private ImageView profile_pic;

	@FXML
	private JFXButton btnApple;

	@FXML
	private JFXButton btnIbm;

	@FXML
	private JFXButton btnFb;

	@FXML
	private JFXButton btnMiscrosoft;

	@FXML
	private JFXButton btnDanone;

	@FXML
	private TextField spotRateInput;

	@FXML
	private TextField freeRiskRateInput;

	@FXML
	private JFXButton btnSave;

	@FXML
	private JFXComboBox<String> modelCombo;

	@FXML
	private DatePicker dateInput;

	@FXML
	private TextField maturityInput;

	@FXML
	private TextField strikePriceInput;

	@FXML
	private TextField equityVolatilityInput;

	@FXML
	private JFXCheckBox call;

	@FXML
	private JFXCheckBox put;
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

	public Timer timer = new Timer();

	@FXML
	private Label timerLabel;
	@FXML
	private Label timerLabel1;

	@FXML
	void btnSaveAction(ActionEvent event) throws NamingException {
		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/EquityOptionService!contracts.EquityOptionRemote";
		Context context = new InitialContext();
		EquityOptionRemote proxy = (EquityOptionRemote) context.lookup(jndiName);
		EquityOptions equityoption = new EquityOptions();

		equityoption.setCurrentStockPrice(Double.parseDouble(spotRateInput.getText()));
		equityoption.setOptionStrikePrice(Double.parseDouble(strikePriceInput.getText()));
		equityoption.setNumberOfShares(Integer.parseInt(maturityInput.getText()));
		equityoption.setName("Equity Option name");
		equityoption.setVolatility(Double.parseDouble(equityVolatilityInput.getText()));
		equityoption.setRiskFreeInterestRate(Double.parseDouble(freeRiskRateInput.getText()));
		equityoption.setTimeUntilExercise(dateCalculator(String.valueOf(dateInput.getValue())));

		if (call.isSelected())

		{
			equityoption.setType("Call");

		}

		if (put.isSelected()) {
			equityoption.setType("Put");

		}

		if (equityoption.getType().equals("Call")) {
			equityoption.setPrice(equityoption.getNumberOfShares() * proxy.pricingCallEquityOptions(equityoption));
		}

		if (equityoption.getType().equals("Put")) {
			equityoption.setPrice(equityoption.getNumberOfShares() * proxy.pricingPutEquityOptions(equityoption));

		}
		LBtype.setText(equityoption.getType());
		LBvalue.setText(String.valueOf(equityoption.getPrice()));
		PremiumPerShare.setText(String.valueOf(equityoption.getPrice() / (double) equityoption.getNumberOfShares()));

	}

	@FXML
	void btnpersistAction(ActionEvent event) throws NamingException {
		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/EquityOptionService!contracts.EquityOptionRemote";
		Context context = new InitialContext();
		EquityOptionRemote proxy = (EquityOptionRemote) context.lookup(jndiName);
		EquityOptions equityoption = new EquityOptions();

		equityoption.setCurrentStockPrice(Double.parseDouble(spotRateInput.getText()));
		equityoption.setOptionStrikePrice(Double.parseDouble(strikePriceInput.getText()));
		equityoption.setNumberOfShares(Integer.parseInt(maturityInput.getText()));
		equityoption.setName("Equity Option " + String.valueOf(equityoption.getCurrentStockPrice()));
		equityoption.setVolatility(Double.parseDouble(equityVolatilityInput.getText()));
		equityoption.setRiskFreeInterestRate(Double.parseDouble(freeRiskRateInput.getText()));
		equityoption.setTimeUntilExercise(dateCalculator(String.valueOf(dateInput.getValue())));
		String jndiNameTrader = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";
		Context contextTrader = new InitialContext();

		UtilisateurRemote proxyTrader = (UtilisateurRemote) contextTrader.lookup(jndiNameTrader);
        Trader trader=proxyTrader.findByIdTrader(Sign_inController.userConnected);
		equityoption.setTrader(trader);

		if (call.isSelected())

		{
			equityoption.setType("Call");

		}

		if (put.isSelected()) {
			equityoption.setType("Put");

		}

		if (equityoption.getType().equals("Call")) {
			equityoption.setPrice(equityoption.getNumberOfShares() * proxy.pricingCallEquityOptions(equityoption));
		}

		if (equityoption.getType().equals("Put")) {
			equityoption.setPrice(equityoption.getNumberOfShares() * proxy.pricingPutEquityOptions(equityoption));

		}
		proxy.ajouterEquityOption(equityoption);
		if(trader.getType().equals("Trader"))
		{
			
			Notif_with_action("Request Send","A Request was Sent to the BackOffice","Succes",(Trader)trader);
			
		}
		spotRateInput.clear();
		strikePriceInput.clear();
		maturityInput.clear();

		equityVolatilityInput.clear();
		freeRiskRateInput.clear();
		dateInput.setValue(null);

		LBtype.setText(null);
		LBvalue.setText(null);
		PremiumPerShare.setText(null);

		if (call.isSelected())

		{
			call.setSelected(false);

		}

		if (put.isSelected()) {
			put.setSelected(false);

		}
	}

	public void Notif_with_action(String Title, String Text,String Type, Trader trader) throws NamingException{
		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/NotificationsService!contracts.NotificationsRemote";
		Context context = new InitialContext();
		NotificationsRemote proxy = (NotificationsRemote) context.lookup(jndiName);
		
		Date today = new Date();
		Notification notification = new Notification();
		notification.setId_trader(trader.getId());
		notification.setId_BackofficeUser(trader.getBackOfficeUser().getId());
		notification.setDate(today);
		notification.setText(Text);
		notification.setEtat("notSeen");
		notification.setType("information");
		
		proxy.ajouterNotification(notification); //work
		Notifications notif =  Notifications.create()
				.title(Title)
				.text(Text)
				.hideCloseButton()
				.position(Pos.BOTTOM_RIGHT)
				.onAction(new EventHandler<ActionEvent>() {
					
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						
					}
				})
				;
		if(Type.equals("Succes"))
		{
			notif.showConfirm();
		}
		if(Type.equals("Information"))
		{
			notif.showInformation();
		}
		if(Type.equals("Error"))
		{
			notif.showError();
		}
		
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		modelCombo.getItems().addAll("Black Scholes Model");
		spotRateInput.setDisable(true);
		equityVolatilityInput.setDisable(true);
		eur_usdInput.setDisable(true);
		eur_GbpInput.setDisable(true);
		eur_JPYInput.setDisable(true);
		aud_usdInput.setDisable(true);
		uds_jpyInput.setDisable(true);

		try {
			eur_usdInput.setText(String.valueOf(GetSpotRate("AAPL")) + " $");
			eur_GbpInput.setText(String.valueOf(GetSpotRate("IBM")) + " $");
			eur_JPYInput.setText(String.valueOf(GetSpotRate("FB")) + " $");
			aud_usdInput.setText(String.valueOf(GetSpotRate("MSFT")) + " $");
			uds_jpyInput.setText(String.valueOf(GetSpotRate("DANOY")) + " $");
			ProfileUser();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Refresh();

		
		

		ProfileUser.setVisible(false);
		ProfilePic.setOnMouseEntered(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
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
    void tradeRequestBtnAction(ActionEvent event) {
		try {
	   ((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Pane root = FXMLLoader.load(getClass().getResource("/fxml/TradeRequest.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Trade Request Ideca2.0");
		primaryStage.show();
	} catch (Exception e) {

	}
    }
	
	public Double GetSpotRate(String symbol) throws NamingException {
		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/MarketDataService!contracts.MarketDataRemote";
		Context context = new InitialContext();
		MarketDataRemote proxy = (MarketDataRemote) context.lookup(jndiName);

		double open = proxy.GetOpen(symbol).doubleValue();

		return open;
	}

	public MarketData GetVolatility(String symbol) throws NamingException {
		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/MarketDataService!contracts.MarketDataRemote";
		Context context = new InitialContext();
		MarketDataRemote proxy = (MarketDataRemote) context.lookup(jndiName);

		double open = proxy.GetVolatility(symbol);

		BigDecimal price = proxy.GetOpen(symbol);
		MarketData marketdata = new MarketData();
		marketdata.setVolatility(open);
		marketdata.setOpen(price);

		return marketdata;
	}

	@FXML
	void btnAppleClicked(ActionEvent event) throws NamingException {
	
		spotRateInput.setText(String.valueOf(GetVolatility("AAPL").getOpen()));
		equityVolatilityInput.setText(String.valueOf(GetVolatility("AAPL").getVolatility()));
	}

	@FXML
	void btnDanoneClicked(ActionEvent event) throws NamingException {
		spotRateInput.setText(String.valueOf(GetVolatility("DANOY").getOpen()));
		equityVolatilityInput.setText(String.valueOf(GetVolatility("DANOY").getVolatility()));
	}

	@FXML
	void btnFbClicked(ActionEvent event) throws NamingException {
		spotRateInput.setText(String.valueOf(GetVolatility("FB").getOpen()));
		equityVolatilityInput.setText(String.valueOf(GetVolatility("FB").getVolatility()));
	}

	@FXML
	void btnIbmClicked(ActionEvent event) throws NamingException {
		spotRateInput.setText(String.valueOf(GetVolatility("IBM").getOpen()));
		equityVolatilityInput.setText(String.valueOf(GetVolatility("IBM").getVolatility()));
	}

	@FXML
	void btnMiscrosoftClicked(ActionEvent event) throws NamingException {
		spotRateInput.setText(String.valueOf(GetVolatility("MSFT").getOpen()));
		equityVolatilityInput.setText(String.valueOf(GetVolatility("MSFT").getVolatility()));
	}

	@FXML
	void checkedcallEvent(ActionEvent event) {
		put.setSelected(false);
	}

	@FXML
	void checkedputEvent(ActionEvent event) {
		call.setSelected(false);
	}

	public double dateCalculator(String date) {
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

		String da2 = String.valueOf(sqlDate);

		int year1 = Integer.valueOf(date.substring(0, 4));
		int mounth1 = Integer.valueOf(date.substring(5, 7));
		int day1 = Integer.valueOf(date.substring(8, 10));

		int year2 = Integer.valueOf(da2.substring(0, 4));
		int mounth2 = Integer.valueOf(da2.substring(5, 7));
		int day2 = Integer.valueOf(da2.substring(8, 10));

		int date1 = year1 * 365 + mounth1 * 30 + day1;
		int date2 = year2 * 365 + mounth2 * 30 + day2;

		return (double) ((date1 - date2) / 365.00);

	}

	@FXML
	void btnclearAction(ActionEvent event) {

		spotRateInput.clear();
		strikePriceInput.clear();
		maturityInput.clear();

		equityVolatilityInput.clear();
		freeRiskRateInput.clear();
		dateInput.setValue(null);

		LBtype.setText(null);
		LBvalue.setText(null);
		PremiumPerShare.setText(null);

		if (call.isSelected())

		{
			call.setSelected(false);

		}

		if (put.isSelected()) {
			put.setSelected(false);

		}
	}

	

	public void ProfileUser() throws NamingException {

		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";
		Context context = new InitialContext();
		UtilisateurRemote proxy = (UtilisateurRemote) context.lookup(jndiName);

		System.out.println(proxy.findById(Sign_inController.userConnected));
		Utilisateur trader = proxy.findById(Sign_inController.userConnected);
		firstNameLabel.setText(trader.getFirstName());
		lastNameLabel.setText(trader.getLasttName());
		EmailLabel.setText(trader.getEmail());
		firstNameLabel1.setText(trader.getFirstName());
		lastNameLabel1.setText(trader.getLasttName());

	}

	@FXML
	void EditProfileAction(ActionEvent event) {

		try {
			timer.cancel();
			/*
			 * mediaPlayer.stop(); mediaPlayer1.stop();
			 */
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxml/Profile.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Acceuil Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {

		}
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
							Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
						}
					}
				});
			}
		}, 0, 50000);
	}

	//////////////////////////////////////////

	@FXML
	void Deconnect(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxml/Sign_in.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Inscription Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {

		}

	}

	@FXML
	void DeconnexAction(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxml/Sign_in.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Sign_in Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {

		}
	}

	@FXML
	void quizBtn(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxml/QuizTrade.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Sign_in Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {

		}
	}

	@FXML
	void go(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxml/EquityOption.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Sign_in Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {

		}
	}

	

	@FXML
    void personalDataBtnAction(ActionEvent event) {
    	 try {

				((Node) event.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxml/Profile.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Sign_in Ideca2.0");
				primaryStage.show();
			} catch (Exception e) {

			}
    }
	   @FXML
	    void portfolioBtnAction(ActionEvent event) {
	    	try {

				((Node) event.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxml/Portfolio.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Sign_in Ideca2.0");
				primaryStage.show();
			} catch (Exception e) {

			}
	    }

	    @FXML
	    void listTradersAction(ActionEvent event) {
	    	try {

				((Node) event.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxml/ListTraderUser.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Sign_in Ideca2.0");
				primaryStage.show();
			} catch (Exception e) {

			}
	    }
	    @FXML
	    void helpDataBtnAction(ActionEvent event) {
	    	try {

				((Node) event.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxml/HelpData.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Sign_in Ideca2.0");
				primaryStage.show();
			} catch (Exception e) {

			}
	    }

	    @FXML
	    void listBackOfficeUserbtnActtion(ActionEvent event) {
	    	try {

				((Node) event.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxml/ListBackOfficeUser.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Sign_in Ideca2.0");
				primaryStage.show();
			} catch (Exception e) {

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
					primaryStage.setTitle("Sign_in Ideca2.0");
					primaryStage.show();
				} catch (Exception e) {

				}
	    }
	    @FXML
	    void PricingofFXOptionVtn(ActionEvent event) {
		   try {

				((Node) event.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxml/FxOption.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Sign_in Ideca2.0");
				primaryStage.show();
			} catch (Exception e) {

			}
	    }
	    @FXML
	    void tradeBtnAction(ActionEvent event) {
	    	try {

				((Node) event.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxml/trade.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Pricing of EquityOption Ideca2.0");
				primaryStage.show();
			} catch (Exception e) {

			}
	    }

}