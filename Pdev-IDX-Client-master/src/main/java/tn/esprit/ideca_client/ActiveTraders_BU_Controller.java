package tn.esprit.ideca_client;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.jfoenix.controls.JFXButton;

import contracts.UtilisateurRemote;
import ideca.entity.EnumGrade;
import ideca.entity.Trader;
import ideca.entity.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ActiveTraders_BU_Controller implements Initializable {

	Logger logger = Logger.getAnonymousLogger();
	@FXML
	private TableView<Utilisateur> tableActiveTraders;
	@FXML
	private TableView<Utilisateur> tableBackOfiiceUsers;

	@FXML
	private TableColumn<Utilisateur, String> firstNameT;

	@FXML
	private TableColumn<Utilisateur, String> emailT;

	@FXML
	private TableColumn<Utilisateur, String> countryT;

	@FXML
	private TableColumn<Trader,EnumGrade> gradeT;
	@FXML
	private TableColumn<Utilisateur, String> firstNameB;

	@FXML
	private TableColumn<Utilisateur, String> emailB;

	@FXML
	private TableColumn<Utilisateur, String> countryB;

	@FXML
	private TableColumn<Utilisateur, String> nationalityB;

	@FXML
	private JFXButton quizBtn;

	@FXML
	private JFXButton lissUserBtn;

	@FXML
	private JFXButton marketDataBnt;

	int currentID;
	int currentID1;

	Context context;

	String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";

	// afficher liste des Traders by backOffice User.
	public void displayTradersParCurrentBackOfficeUser() {

		try {
			context = new InitialContext();
			UtilisateurRemote proxy = (UtilisateurRemote) context.lookup(jndiName);

			firstNameT.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("firstName"));
			emailT.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("email"));
			countryT.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("country"));
			gradeT.setCellValueFactory(new PropertyValueFactory<Trader,EnumGrade>("grade"));

			final ObservableList<Utilisateur> listTrader = FXCollections.observableArrayList();

			for (Utilisateur trader : proxy.listeTraderParBU(Sign_inController.userConnected)) {
				listTrader.add(trader);
			}
			tableActiveTraders.setItems(listTrader);

			tableActiveTraders.setRowFactory(tv -> {
				TableRow<Utilisateur> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 1 && (!row.isEmpty())) {
						Utilisateur rowData = row.getItem();
						currentID = rowData.getId();
					}
				});
				return row;
			});

		} catch (NamingException e) {
			logger.log(Level.SEVERE, "an exception was thrown   ", e);
		}

	}

	// afficher liste backOffice Users.
	public void displayBackOfficeUser() {

		try {
			context = new InitialContext();
			UtilisateurRemote proxy = (UtilisateurRemote) context.lookup(jndiName);

			firstNameB.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("firstName"));
			emailB.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("email"));
			countryB.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("country"));
			nationalityB.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("nationalite"));

			final ObservableList<Utilisateur> listBackOffice = FXCollections.observableArrayList();

			for (Utilisateur BackOffice : proxy.listeUser("BackOfficeUser"))
				listBackOffice.add(BackOffice);
			tableBackOfiiceUsers.setItems(listBackOffice);

			tableBackOfiiceUsers.setRowFactory(tv -> {
				TableRow<Utilisateur> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 1 && (!row.isEmpty())) {
						Utilisateur rowData = row.getItem();
						currentID1 = rowData.getId();
					}
				});
				return row;
			});

		} catch (NamingException e) {
			logger.log(Level.SEVERE, "an exception was thrown  ", e);
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
			logger.log(Level.SEVERE, "exception was thrown   ", e);
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
			logger.log(Level.SEVERE, "an exception was thrown", e);
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
			logger.log(Level.SEVERE, "an exception was thrown ", e);
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
			logger.log(Level.SEVERE, "exception was thrown ", e);
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
			logger.log(Level.SEVERE, "an exception was thrown", e);
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
			logger.log(Level.SEVERE, "  an exception was thrown !!!", e);

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

		}}
	

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
			logger.log(Level.SEVERE, "an exception  was thrown", e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		displayTradersParCurrentBackOfficeUser();
		displayBackOfficeUser();
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
			logger.log(Level.SEVERE, "erreur", e);

		}
	}

}
