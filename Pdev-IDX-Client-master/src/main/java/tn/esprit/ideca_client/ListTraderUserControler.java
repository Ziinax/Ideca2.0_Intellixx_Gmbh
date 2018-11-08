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
import com.sun.glass.events.KeyEvent;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import contracts.ChatServiceRemote;
import contracts.UtilisateurRemote;
import ideca.entity.Trader;
import ideca.entity.EnumGrade;
import ideca.entity.Trader;
import ideca.entity.Utilisateur;

public class ListTraderUserControler implements Initializable {
	static int tables;
	Logger logger = Logger.getAnonymousLogger();
	@FXML
	private Text labeltest;
	@FXML
	private Button acceptbtn;
	@FXML
	private Button refusebtn;

	@FXML
	private TableView<Trader> table;

	@FXML
	private TableColumn<Utilisateur, String> firstName;

	@FXML
	private TableColumn<Utilisateur, String> lastName;
    @FXML
    private TableColumn chat;
	@FXML
	private TableColumn<Utilisateur, String> email;

	@FXML
	private TableColumn<Utilisateur, String> country;

	@FXML
	private TableColumn<Utilisateur, String> nationality;

	@FXML
	private TableColumn<Trader, EnumGrade> grade;

	@FXML
	private TableColumn<Trader, EnumGrade> etat;

	@FXML
	private TableColumn<Utilisateur, String> cin;

	@FXML
	private TextField emailLabel;

	@FXML
	private TextField countryLabel;
	@FXML
	private JFXButton rechercheBtn;
	
	@FXML
	private TextField rechercheinput1;
	@FXML
	private JFXButton statistiqueBtn;

	@FXML
	private JFXButton quizBtn;

	@FXML
	private Label firstNameLabel;
    @FXML
    private Label lastNameLabel11;
	@FXML
	private Label lastNameLabel;
	@FXML
	private Button deconnex;
	@FXML
	private Label firstNameLabel1;

	@FXML
	private Label lastNameLabel1;

	@FXML
	private JFXButton editProfile;

	public Timer timer = new Timer();

	@FXML
	private Label timerLabel;
	@FXML
	private Label timerLabel1;

	public void displayTraders() {

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
			
			 Callback<TableColumn<Trader, String>, TableCell<Trader, String>> cellFactory;
             cellFactory = new Callback<TableColumn<Trader, String>, TableCell<Trader, String>>() {
                 @Override
                 public TableCell call(final TableColumn<Trader, String> param) {
                     final TableCell<Trader, String> cell;
                     cell = new TableCell<Trader, String>() {

                         final Button plus = new Button("Contact");

                         @Override
                         public void updateItem(String item, boolean empty) {
                             super.updateItem(item, empty);
                             if (empty) {
                                 setGraphic(null);
                                 setText(null);
                             } else {

                                 plus.setOnAction((ActionEvent event) 
                                         -> {
                                        	 try {

                                 				((Node) event.getSource()).getScene().getWindow().hide();
                                 				Stage primaryStage = new Stage();
                                 				FXMLLoader loader = new FXMLLoader();
                                 				Pane root = loader.load(getClass().getResource("/fxml/Chat.fxml"));
                                 				Scene scene = new Scene(root);
                                 				primaryStage.setScene(scene);
                                 				primaryStage.setTitle("Chat Ideca2.0");
                                 				primaryStage.show();
                                 			} catch (Exception e) {

                                 			}
                                            
                                 });

                                 setGraphic(plus);
			firstName.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("firstName"));
			lastName.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("lasttName"));
			email.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("email"));
			country.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("country"));
			nationality.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("nationalite"));
			grade.setCellValueFactory(new PropertyValueFactory<Trader, EnumGrade>("grade"));

                                 setText(null);
                             }
                         }
                     };
                     return cell;
                 }
             };
             chat.setCellFactory(cellFactory);
			final ObservableList<Trader> listTrader = FXCollections.observableArrayList();

			for (Trader trader : proxy.listeUserEtatType("Trader")) {
				listTrader.add(trader);
			}

			table.setItems(listTrader);

			table.setRowFactory(tv -> {
				TableRow<Trader> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 1 && (!row.isEmpty())) {
						Trader rowData = row.getItem();
						rowData.getId();
					}
				});
				return row;
			});

		} catch (NamingException e) {
			logger.log(Level.SEVERE, "no erreur ", e);
		}

	}
	  @FXML
	    void clickTable(MouseEvent event) throws NamingException {
		  Context context;
	    	
			String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/ChatService!contracts.ChatServiceRemote";
		
			
				 Trader tabless = (Trader) table.getSelectionModel().getSelectedItem();
				tables=tabless.getId();
				 //System.out.println(tables.getFirstName());
				/*if(tables!=null)
				{
					try {
						context = new InitialContext();
						ChatServiceRemote proxy = (ChatServiceRemote) context.lookup(jndiName);
						for (Trader tr: proxy.afficherTousTrader())
						{
							String s=tr.getFirstName()+" "+tr.getLasttName();
							if(tables.equals(s))
							{
								
								 idTraderE=tr.getId();
								displayChat(idTraderE);
							}
						}
					} catch (NamingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}*/
	    }

	@FXML
	void rechercheBtnAction(ActionEvent event) {
		// a faire
	}
	@FXML
	void rechercheBtnAction1(KeyEvent event) {
		// a faire
	}


	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		 chat.setStyle(" -fx-alignment : center;");

		
		displayTraders();

		refresh();

	}

	public void profileUser() throws NamingException {

		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";
		Context context = new InitialContext();
		UtilisateurRemote proxy = (UtilisateurRemote) context.lookup(jndiName);

		Utilisateur trader = proxy.findById(Sign_inController.userConnected);
		firstNameLabel1.setText(trader.getFirstName());
		lastNameLabel1.setText(trader.getLasttName());

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
	void deconnect(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root;
			root = FXMLLoader.load(getClass().getResource("/fxml/Sign_in.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Deconnect Ideca2.0");
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
			primaryStage.show();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "an exception was thrown  ", e);
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
	void editProfileAction(ActionEvent event) {
		// on sait pas oue est e boutton
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
			primaryStage.setTitle("Pricing of EquityOption Ideca2.0");
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
	void affichage() throws NamingException {
		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";

		Context context;

		context = new InitialContext();
		UtilisateurRemote proxy = (UtilisateurRemote) context.lookup(jndiName);

		firstName.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("firstName"));
		lastName.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("lasttName"));
		email.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("email"));
		country.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("country"));
		nationality.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("nationalite"));
		grade.setCellValueFactory(new PropertyValueFactory<Trader, EnumGrade>("grade"));

		final ObservableList<Trader> listTrader = FXCollections.observableArrayList();

		for (Trader object : proxy.rechercheUser(rechercheinput1.getText())) {
			listTrader.add(object);
		}
		table.setItems(listTrader);

	}
	
	
    @FXML
    void rechercheAction1(KeyEvent event) throws NamingException {
    	affichage();
    }
	
}