package tn.esprit.ideca_client;

import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import contracts.EquityOptionRemote;
import contracts.FXMarketDataRemote;
import contracts.FXOptionsRemote;
import contracts.MarketDataRemote;
import ideca.entity.EquityOptions;
import ideca.entity.FXOptions;
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
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FxOptionController implements Initializable {
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
	private JFXButton eur_usdbtn;

	@FXML
	private JFXButton eur_Gbpbtn;

	@FXML
	private JFXButton eur_JPYbtn;

	@FXML
	private JFXButton aud_usdbtn;

	@FXML
	private JFXButton uds_jpybtn;

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
	private TextField spotRateInput;

	@FXML
	private TextField domesticRiskInput;

	@FXML
	private TextField foreignRiskInput;

	@FXML
	private JFXComboBox modelCombo;

	@FXML
	private JFXCheckBox call;

	@FXML
	private JFXCheckBox put;

	@FXML
	private DatePicker dateInput;

	@FXML
	private TextField nominalInput;

	@FXML
	private TextField strikePriceInput;

	@FXML
	private TextField fxVolatilityInput;

	@FXML
	private JFXButton btnSave;

	@FXML
	private JFXButton btnpersist;

	@FXML
	private JFXButton btnclear;

	@FXML
	private Label LBtype;

	@FXML
	private Label LBvalue;

	@FXML
	private Label PremiumPerShare;
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


	

	public void initialize(URL location, ResourceBundle resources) {
		modelCombo.getItems().addAll("Garman Kohlhagen model");
		spotRateInput.setDisable(true);

		eur_usdInput.setDisable(true);
		eur_GbpInput.setDisable(true);
		eur_JPYInput.setDisable(true);
		aud_usdInput.setDisable(true);
		uds_jpyInput.setDisable(true);

		try {

			eur_usdInput.setText(String.valueOf(GetSpotRate("EURUSD=X")));
			eur_GbpInput.setText(String.valueOf(GetSpotRate("EURGBP=X")));
			eur_JPYInput.setText(String.valueOf(GetSpotRate("EURJPY=X")));
			aud_usdInput.setText(String.valueOf(GetSpotRate("AUDUSD=X")));
			uds_jpyInput.setText(String.valueOf(GetSpotRate("USDJPY=X")));
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	Refresh();

		try {
			ProfileUser();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(Sign_inController.UserConnected);

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

	public Double GetSpotRate(String symbol) throws NamingException {
		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/FXMarketDataService!contracts.FXMarketDataRemote";
		Context context = new InitialContext();
		FXMarketDataRemote proxy = (FXMarketDataRemote) context.lookup(jndiName);

		double open = proxy.GetOpen(symbol).doubleValue();

		return open;
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

	}
    }
	
	@FXML
	void btnSaveAction(ActionEvent event) throws NamingException {
		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/FXOptionsService!contracts.FXOptionsRemote";
		Context context = new InitialContext();
		FXOptionsRemote proxy = (FXOptionsRemote) context.lookup(jndiName);
		FXOptions fxoption = new FXOptions();

		fxoption.setSpotRate(Double.parseDouble(spotRateInput.getText()));
		fxoption.setStrikePrice(Double.parseDouble(strikePriceInput.getText()));
		fxoption.setNominal((Integer.parseInt(nominalInput.getText())));
		fxoption.setName("fx Option name");
		fxoption.setVolatility(Double.parseDouble(fxVolatilityInput.getText()));
		fxoption.setInterestRateCurrency1(Double.parseDouble(domesticRiskInput.getText()));
		fxoption.setInterestRateCurrency2(Double.parseDouble(foreignRiskInput.getText()));
		fxoption.setTimeToMaturity(dateCalculator(String.valueOf(dateInput.getValue())));

		if (call.isSelected())

		{
			fxoption.setType("Call");

		}

		if (put.isSelected()) {
			fxoption.setType("Put");

		}

		if (fxoption.getType().equals("Call")) {
			fxoption.setPrice(fxoption.getNominal() * proxy.pricingCallFXOptions(fxoption));
		}

		if (fxoption.getType().equals("Put")) {
			fxoption.setPrice(fxoption.getNominal() * proxy.pricingPutFXOptions(fxoption));

		}
		LBtype.setText(fxoption.getType());
		LBvalue.setText(String.valueOf(fxoption.getPrice()));
		PremiumPerShare.setText(String.valueOf(fxoption.getPrice() / (double) fxoption.getNominal()));

	}

	@FXML
	void btnclearAction(ActionEvent event) {
		spotRateInput.clear();
		strikePriceInput.clear();
		nominalInput.clear();

		fxVolatilityInput.clear();
		domesticRiskInput.clear();
		foreignRiskInput.clear();
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

	@FXML
	void btnpersistAction(ActionEvent event) throws NamingException {
		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/FXOptionsService!contracts.FXOptionsRemote";
		Context context = new InitialContext();
		FXOptionsRemote proxy = (FXOptionsRemote) context.lookup(jndiName);
		FXOptions fxoption = new FXOptions();

		fxoption.setSpotRate(Double.parseDouble(spotRateInput.getText()));
		fxoption.setStrikePrice(Double.parseDouble(strikePriceInput.getText()));
		fxoption.setNominal((Integer.parseInt(nominalInput.getText())));
		fxoption.setName("fx Option name");
		fxoption.setVolatility(Double.parseDouble(fxVolatilityInput.getText()));
		fxoption.setInterestRateCurrency1(Double.parseDouble(domesticRiskInput.getText()));
		fxoption.setInterestRateCurrency2(Double.parseDouble(foreignRiskInput.getText()));
		fxoption.setTimeToMaturity(dateCalculator(String.valueOf(dateInput.getValue())));
		String jndiNameTrader = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";
		Context uds_jpybtn = new InitialContext();

		UtilisateurRemote proxyTrader = (UtilisateurRemote)uds_jpybtn.lookup(jndiNameTrader);
        Trader trader=proxyTrader.findByIdTrader(Sign_inController.userConnected);
        fxoption.setTrader(trader);


		if (call.isSelected())

		{
			fxoption.setType("Call");

		}

		if (put.isSelected()) {
			fxoption.setType("Put");

		}

		if (fxoption.getType().equals("Call")) {
			fxoption.setPrice(fxoption.getNominal() * proxy.pricingCallFXOptions(fxoption));
		}

		if (fxoption.getType().equals("Put")) {
			fxoption.setPrice(fxoption.getNominal() * proxy.pricingPutFXOptions(fxoption));

		}
		LBtype.setText(fxoption.getType());
		LBvalue.setText(String.valueOf(fxoption.getPrice()));
		PremiumPerShare.setText(String.valueOf(fxoption.getPrice() / (double) fxoption.getNominal()));

		proxy.addFXOption(fxoption);
		spotRateInput.clear();
		strikePriceInput.clear();
		nominalInput.clear();

		fxVolatilityInput.clear();
		domesticRiskInput.clear();
		foreignRiskInput.clear();
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

	@FXML
	void PutAction(ActionEvent event) {
		call.setSelected(false);
	}

	@FXML
	void callAction(ActionEvent event) {
		put.setSelected(false);

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
	void aud_usdAction(ActionEvent event) throws NamingException {
		spotRateInput.setText(String.valueOf(GetSpotRate("AUDUSD=X")));
	}

	@FXML
	void eur_GbpAction(ActionEvent event) throws NamingException {
		spotRateInput.setText(String.valueOf(GetSpotRate("EURGBP=X")));
	}

	@FXML
	void eur_JPYAction(ActionEvent event) throws NamingException {
		spotRateInput.setText(String.valueOf(GetSpotRate("EURJPY=X")));
	}

	@FXML
	void eur_usdAction(ActionEvent event) throws NamingException {
		spotRateInput.setText(String.valueOf(GetSpotRate("EURUSD=X")));
	}

	@FXML
	void uds_jpyAction(ActionEvent event) throws NamingException {
		spotRateInput.setText(String.valueOf(GetSpotRate("USDJPY=X")));
	}
	
	

	public Timer timer = new Timer();

	@FXML
	private Label timerLabel;
	@FXML
	private Label timerLabel1;

	
	

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