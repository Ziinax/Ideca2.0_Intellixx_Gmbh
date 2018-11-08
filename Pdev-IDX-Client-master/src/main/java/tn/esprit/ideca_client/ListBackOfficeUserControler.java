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
import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import javafx.scene.text.Text;
import javafx.stage.Stage;
import contracts.UtilisateurRemote;
import ideca.entity.Utilisateur;

public class ListBackOfficeUserControler implements Initializable {
	Logger logger = Logger.getAnonymousLogger();
	@FXML
    private Text labeltest;
	@FXML
	private Button acceptbtn;
	@FXML
	private Button refusebtn;
	
	@FXML
	private TableView<Utilisateur> table;

	@FXML
	private TableColumn firstName;

	@FXML
	private TableColumn lastName;

	@FXML
	private TableColumn email;

	@FXML
	private TableColumn country;

	@FXML
	private TableColumn nationality;

	@FXML
	private TableColumn grade;

	@FXML
	private TableColumn etat;

	@FXML
	private TableColumn cin;

	
	@FXML
	private TextField emailLabel;

	@FXML
	private TextField countryLabel;
    @FXML
    private JFXButton rechercheBtn;
    @FXML
    private JFXTextField rechercheinput;
    @FXML
    private JFXButton statistiqueBtn;

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

	private ObservableList<Object> listTrader;
	public int currentID;

	

	public void DisplayTraders(){
		
		Context context;

		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";

		try {
			context = new InitialContext();
			UtilisateurRemote proxy = (UtilisateurRemote) context.lookup(jndiName);

			firstName.setCellValueFactory(new PropertyValueFactory("firstName"));
			lastName.setCellValueFactory(new PropertyValueFactory("lasttName"));
			email.setCellValueFactory(new PropertyValueFactory("email"));
			country.setCellValueFactory(new PropertyValueFactory("country"));
			nationality.setCellValueFactory(new PropertyValueFactory("nationalite"));
			grade.setCellValueFactory(new PropertyValueFactory("grade"));
			
			
			final ObservableList<Utilisateur> listTrader = FXCollections.observableArrayList();

			for (Utilisateur trader : proxy.listeUserEtatType("BackOfficeUser")) {
				listTrader.add(trader);
			}
			
			table.setItems(listTrader);
			
			table.setRowFactory( tv -> {
			    TableRow<Utilisateur> row = new TableRow<>();
			    row.setOnMouseClicked(event -> {
			        if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
			        	Utilisateur rowData = row.getItem();
			         currentID=rowData.getId();
			    	
			      
			        }
			    });
			    return row ;
			});
			
	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			logger.log(Level.SEVERE, "an exception was thrown", e);
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
	void PricingofFXOptionVtn(ActionEvent event) {
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
	void PricingofEquityOptionAction(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/fxml/EquityOption.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Pricing of EquityOption Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "  an exception was thrown", e);
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
	void EditProfileAction(ActionEvent event) {

		try {
			timer.cancel();
			/*
			 * mediaPlayer.stop(); mediaPlayer1.stop();
			 */
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
			logger.log(Level.SEVERE, "an exception was thrown  ", e);
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

	
	@FXML
    void rechercheBtnAction(ActionEvent event) {

    }
    

	public void initialize(URL location, ResourceBundle resources) {

		DisplayTraders();
		Refresh();

		/*try {
		//	ProfileUser();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
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
	

/*	public void ProfileUser() throws NamingException {

		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";
		Context context = new InitialContext();
		UtilisateurRemote proxy = (UtilisateurRemote) context.lookup(jndiName);

		System.out.println(proxy.findById(Sign_inController.UserConnected));
		Utilisateur trader = proxy.findById(Sign_inController.UserConnected);
		firstNameLabel.setText(trader.getFirstName());
		lastNameLabel.setText(trader.getLasttName());
		EmailLabel.setText(trader.getEmail());
		firstNameLabel1.setText(trader.getFirstName());
		lastNameLabel1.setText(trader.getLasttName());

	}*/

	

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
	void Deconnect(ActionEvent event) {

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
	void DeconnexAction(ActionEvent event) {

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