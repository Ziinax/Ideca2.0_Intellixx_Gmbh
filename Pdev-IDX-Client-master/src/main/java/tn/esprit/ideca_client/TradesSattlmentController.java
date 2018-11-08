package tn.esprit.ideca_client;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import contracts.NotificationsRemote;
import contracts.TradeRemote;
import contracts.UtilisateurRemote;
import ideca.entity.BackOfficeUser;
import ideca.entity.EnumEtatTrade;

import ideca.entity.Notification;
import ideca.entity.Trade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TradesSattlmentController implements Initializable {
	@FXML
	private JFXButton quizBtn;

	@FXML
	private JFXButton lissUserBtn;

	@FXML
	private TextField idTraderSource;

	@FXML
	private TextField idTraderDestination;
	@FXML
	private JFXButton marketDataBnt;

	@FXML
	private JFXButton statistiqueBtn;
	@FXML
	private TableView tableTrade;
	@FXML
	private JFXButton fxOptionBnt;

	@FXML
	private Pane ProfilePane;

	@FXML
	private ImageView profile_pic;

	@FXML
	private JFXTextField eur_usdInput;

	@FXML
	private JFXTextField eur_GbpInput;

	@FXML
	private JFXTextField eur_JPYInput;

	@FXML
	private JFXTextField aud_usdInput;
	@FXML
	private TableColumn traderSourceName;

	@FXML
	private TableColumn counterParty;
    @FXML
    private Button btnAcceptTrade;

    @FXML
    private Button btnRefusedTrade;

	@FXML
	private TableColumn status;
    @FXML
    private TableColumn SettlementDate;
	@FXML
	private TableColumn option;

	@FXML
	private TableColumn dateCapture;
	@FXML
	private JFXTextField uds_jpyInput;
	private String jndiNameTrade = "Ideca_intelixx-ear/Ideca_intelixx-ejb/TradeService!contracts.TradeRemote";
	
    private String tradeResponse="tradeResponse";
	ObservableList listTrades;
	Logger logger = Logger.getAnonymousLogger();


	@FXML
	void clickTableTrade(MouseEvent event) throws IOException {
		if (tableTrade != null) {

			List<Trade> tables = tableTrade.getSelectionModel().getSelectedItems();
			if (tables.size() == 1) {
				final Trade selectedDisc = tables.get(0);
				if (selectedDisc != null) {
					MainApp.popupSource=tables.get(0).getTrader_source().getId() ;
					MainApp.popupDestination=tables.get(0).getTrader_destination().getId();
					idTraderSource.setText(String.valueOf(tables.get(0).getTrader_source().getId()));
					idTraderDestination.setText(String.valueOf(tables.get(0).getTrader_destination().getId()));
					
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/OptionInformationBackOffice.fxml"));
					Parent root1 = (Parent) fxmlLoader.load();
					Stage stage = new Stage();
					stage.setTitle("Trade Informations ") ;
					stage.setScene(new Scene(root1)); 
					stage.show();
					if (selectedDisc.getEtat()==EnumEtatTrade.SETTLED ||selectedDisc.getEtat()==EnumEtatTrade.UNSETTLED )
					{
						btnAcceptTrade.setDisable(true);
						btnRefusedTrade.setDisable(true);
						
					}
					else 
					{
						btnAcceptTrade.setDisable(false);
						btnRefusedTrade.setDisable(false);
						
					}
				}
			}
		}

	}

	@FXML
	void AcceptTrade(ActionEvent event) throws NamingException {
		Context context = new InitialContext();

		TradeRemote proxy = (TradeRemote) context.lookup(jndiNameTrade);

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
			if (idTraderSource.getText() != null && idTraderDestination.getText() != null) {
				proxy.validateTrade(proxy.findByIdSourceIdDestination(Integer.parseInt(idTraderSource.getText()),
						Integer.parseInt(idTraderDestination.getText())));
	
				Notif_with_action("Trade","The Trade Was Confirmed",tradeResponse,Integer.parseInt(idTraderSource.getText()));
			}
			affichage();
		} else  {
			event.consume();
		}

		
	}

	public void Notif_with_action(String title, String text,String type, int trader) throws NamingException{
		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/NotificationsService!contracts.NotificationsRemote";
		Context context = new InitialContext();
		NotificationsRemote proxy = (NotificationsRemote) context.lookup(jndiName);
		String jndiNameUser = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";
		UtilisateurRemote proxyUser = (UtilisateurRemote) context.lookup(jndiNameUser);
		BackOfficeUser user =  (BackOfficeUser) proxyUser.findById(Sign_inController.userConnected);
		
		Date today = new Date();
		Notification notification = new Notification();
		notification.setId_trader(trader);
		notification.setId_BackofficeUser(user.getId());
		notification.setDate(today);
		notification.setText(text);
		notification.setEtat("notSeen");
		notification.setType(tradeResponse);
		
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
	void RefuseTrade(ActionEvent event) throws NamingException {
		Context context = new InitialContext();

		TradeRemote proxy = (TradeRemote) context.lookup(jndiNameTrade);

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
			if (idTraderSource.getText() != null && idTraderDestination.getText() != null) {
				proxy.refuserTrade(proxy.findByIdSourceIdDestination(Integer.parseInt(idTraderSource.getText()),
						Integer.parseInt(idTraderDestination.getText())));
				Notif_with_action("Trade","The Trade Was Declined",tradeResponse,Integer.parseInt(idTraderSource.getText()));

			}
			affichage();
		} else  {
			event.consume();
		}

	}

	public void initialize(URL location, ResourceBundle resources) {
		idTraderSource.setVisible(false);
		idTraderDestination.setVisible(false);
		try {
			affichage();
		} catch (NamingException e) {
			//  Auto-generated catch block
			logger.log(Level.SEVERE, "an exception   was thrown", e);
		}

	}

	void affichage() throws NamingException {

		Context context;
	
			context = new InitialContext();
			String jndiNameUser = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";

			UtilisateurRemote proxy = (UtilisateurRemote) context.lookup(jndiNameUser);
			BackOfficeUser user = (BackOfficeUser) proxy.findById(Sign_inController.userConnected);

			TradeRemote proxyTrade = (TradeRemote) context.lookup(jndiNameTrade);

			traderSourceName.setCellValueFactory(new PropertyValueFactory("trader_source"));
			counterParty.setCellValueFactory(new PropertyValueFactory("trader_destination"));
			status.setCellValueFactory(new PropertyValueFactory("etat"));
			option.setCellValueFactory(new PropertyValueFactory("equityOption"));
			dateCapture.setCellValueFactory(new PropertyValueFactory("dateDemand"));
			SettlementDate.setCellValueFactory(new PropertyValueFactory("dateSettle"));

			listTrades = FXCollections.observableArrayList();

			for (Trade trade : proxyTrade.getAllTradesByBackOfficeUser(user.getId())) {
				if(trade.getDateSettle()==null){
					Date settlement = new java.sql.Date(0,0,0);
					trade.setDateSettle(settlement);
				}
				
				listTrades.add(trade);
				
			}
			
			tableTrade.setItems(listTrades);

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
			logger.log(Level.SEVERE, "an exception was thrown", e);

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
			logger.log(Level.SEVERE, "r  an exception was thrownaa !!!", e);

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
			logger.log(Level.SEVERE, "r  an exception was throwndd !!!", e);

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
			logger.log(Level.SEVERE, "r  an exception was thrown cc!!!", e);

		}
	}
	
		}

	
		
		
		

