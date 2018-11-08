package tn.esprit.ideca_client;

import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.ListSelectionModel;

import org.apache.commons.lang.ObjectUtils.Null;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import contracts.UtilisateurRemote;
import ideca.entity.EnumGrade;
import ideca.entity.Trader;
import ideca.entity.Utilisateur;

public class DisplayTradersController implements Initializable {

	Logger logger = Logger.getAnonymousLogger();
	public static final Timer timer = new Timer();

	@FXML
	private Text labeltest;
	@FXML
	private Button acceptbtn;
	@FXML
	private Button refusebtn;

	@FXML
	private TableView<Trader> table;

	@FXML
	private TableColumn<Trader, String> firstName;

	@FXML
	private TableColumn<Trader, String> lastName;

	@FXML
	private TableColumn<Trader, String> email;

	@FXML
	private TableColumn<Trader, String> country;

	@FXML
	private TableColumn<Trader, String> nationality;

	@FXML
	private TableColumn<Trader, EnumGrade> grade;

	@FXML
	private TableColumn<Trader, String> etat;

	@FXML
	private TableColumn<Trader, String> phoneNumber;

	@FXML
	private TextField firstNameLabel;

	@FXML
	private TextField lastNameLabel;

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
	private TableColumn<?, ?> dateCapture;

	@FXML
	private TableView<?> tableTrade;

	@FXML
	private TableColumn<?, ?> traderSourceName;

	@FXML
	private TableColumn<?, ?> counterParty;

	@FXML
	private TableColumn<?, ?> status;

	@FXML
	private TableColumn<?, ?> option;
	@FXML
	private Label gradeVideLabel;
	String erreur = "r  an exception was thrown !!!";

	private int currentID;
	private EnumGrade currentGrade;

	Context context;

	String jnd = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";

	String jndiName = jnd;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		displayTraders();

	}

	public void displayTraders() {

		try {
			context = new InitialContext();
			UtilisateurRemote proxy = (UtilisateurRemote) context.lookup(jndiName);

			firstName.setCellValueFactory(new PropertyValueFactory<Trader, String>("firstName"));
			lastName.setCellValueFactory(new PropertyValueFactory<Trader, String>("lasttName"));
			email.setCellValueFactory(new PropertyValueFactory<Trader, String>("email"));
			country.setCellValueFactory(new PropertyValueFactory<Trader, String>("country"));
			nationality.setCellValueFactory(new PropertyValueFactory<Trader, String>("nationalite"));

			etat.setCellValueFactory(new PropertyValueFactory<Trader, String>("etat"));
			phoneNumber.setCellValueFactory(new PropertyValueFactory<Trader, String>("phoneNumber"));

			grade.setCellValueFactory(new PropertyValueFactory("Itemc"));

			grade.setCellFactory(ComboBoxTableCell.forTableColumn(EnumGrade.values()));
			table.setEditable(true);

			grade.setOnEditCommit((CellEditEvent<Trader, EnumGrade> event) -> {

				currentGrade = event.getNewValue();

			});
			table.getItems().clear();

			final ObservableList<Trader> listTrader = FXCollections.observableArrayList();

			for (Trader trader : proxy.listeTraderNonValide()) {
				listTrader.add(trader);
			}
			logger.log(Level.INFO, "size list trader", listTrader.size());
			table.setItems(listTrader);

			table.setRowFactory(tv -> {
				TableRow<Trader> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 1 && (!row.isEmpty())) {
						Trader rowData = row.getItem();
						currentID = rowData.getId();

					}
				});
				return row;
			});

		} catch (NamingException e) {
			logger.log(Level.SEVERE, "an exception was thrown   ", e);
		}

	}

	@FXML
	void validateTrader(ActionEvent event) {
		Double amount = 0.0;
		refresh();
		Boolean var = false;

		try {

			context = new InitialContext();
			UtilisateurRemote proxy = (UtilisateurRemote) context.lookup(jndiName);

			if (EnumGrade.First_Level.equals(currentGrade)) {
				amount = 1000.0;
			} else if (EnumGrade.Second_Level.equals(currentGrade)) {
				amount = 1500.0;
			} else if (EnumGrade.Third_Level.equals(currentGrade)) {
				amount = 2000.0;
			}

			proxy.validerTrader(currentID, Sign_inController.userConnected, currentGrade, amount);

		} catch (NamingException e) {
			logger.log(Level.SEVERE, erreur, e);
		}

	}

	@FXML
	void cancelTrader(ActionEvent event) {

		try {
			context = new InitialContext();
			UtilisateurRemote proxy = (UtilisateurRemote) context.lookup(jndiName);
			proxy.supprimerUtilisateur(currentID);

		} catch (NamingException e) {
			logger.log(Level.SEVERE, erreur, e);
		}

	}

	@FXML
	void rechercheBtnAction(ActionEvent event) {

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

	public void refresh() {// rafraichir l'heure tt 50s
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						try {
							displayTraders();

						} catch (Exception ex) {
							Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
						}
					}
				});
			}
		}, 0, 10000);
	}
}