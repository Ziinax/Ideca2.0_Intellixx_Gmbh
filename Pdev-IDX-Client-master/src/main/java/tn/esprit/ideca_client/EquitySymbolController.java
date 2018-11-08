package tn.esprit.ideca_client;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.hibernate.cache.spi.entry.ReferenceCacheEntryImpl;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import contracts.EquityOptionRemote;
import contracts.EquitySymbolRemote;
import ideca.entity.EquityOptions;
import ideca.entity.EquitySymbol;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class EquitySymbolController implements Initializable {
	Logger logger = Logger.getAnonymousLogger();
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
	private TableView table_symbol;

	@FXML
	private TableColumn col_symbol;

	@FXML
	private TableColumn col_name;

	@FXML
	private TableColumn col_exchange;

	@FXML
	private TableColumn col_country;

	@FXML
	private TableColumn col_categoryName;

	@FXML
	private TableColumn col_categoryNumbler;

	@FXML
	private TableColumn col_status;

	@FXML
	private Button btn_ajouter;

	@FXML
	private Button btn_retier;

	@FXML
	private TextField TF_recherche;
	@FXML
	private JFXTextField selectedRowSymbol;
	@FXML
	private Button btn_recherche;

	private ObservableList<Object> listEquitySymbols;
	public Timer timer = new Timer();

	
	public void initialize(URL location, ResourceBundle resources) {
		selectedRowSymbol.setDisable(true);
		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/EquitySymbolService!contracts.EquitySymbolRemote";

		Context context;

		try {
			context = new InitialContext();
			EquitySymbolRemote proxy = (EquitySymbolRemote) context.lookup(jndiName);

			col_symbol.setCellValueFactory(new PropertyValueFactory("Symbol"));
			col_name.setCellValueFactory(new PropertyValueFactory("Name"));
			col_exchange.setCellValueFactory(new PropertyValueFactory("Exchange"));
			col_country.setCellValueFactory(new PropertyValueFactory("Country"));
			col_categoryName.setCellValueFactory(new PropertyValueFactory("CategoryName"));
			col_categoryNumbler.setCellValueFactory(new PropertyValueFactory("CategoryNumber"));
			col_status.setCellValueFactory(new PropertyValueFactory("Status"));

			listEquitySymbols = FXCollections.observableArrayList();

			for (EquitySymbol equitysymbol : proxy.getAllSymbols()) {
				listEquitySymbols.add(equitysymbol);
			}
			table_symbol.setItems(listEquitySymbols);

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void rechercheName(KeyEvent event) throws NamingException {
		Affichage();
		Refresh();

	}

	@FXML
	void clickTable(MouseEvent event) {
		if (table_symbol != null) {

			List<EquitySymbol> tables = table_symbol.getSelectionModel().getSelectedItems();
			if (tables.size() == 1) {
				final EquitySymbol selectedDisc = tables.get(0);
				if (selectedDisc != null) {

					selectedRowSymbol.setText(String.valueOf(tables.get(0).getSymbol()));
				}
			}
		}
	}

	@FXML
	void addToMarketData(ActionEvent event) throws NamingException, IOException {
		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/EquitySymbolService!contracts.EquitySymbolRemote";

		Context context;
		context = new InitialContext();
		EquitySymbolRemote proxy = (EquitySymbolRemote) context.lookup(jndiName);
		proxy.addToMarketData(selectedRowSymbol.getText());
	}

	@FXML
	void removeFromMarketData(ActionEvent event) throws NamingException {
		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/EquitySymbolService!contracts.EquitySymbolRemote";

		Context context;
		context = new InitialContext();
		EquitySymbolRemote proxy = (EquitySymbolRemote) context.lookup(jndiName);
		proxy.removeFromMarketData(selectedRowSymbol.getText());
	}

	public void Refresh() {// rafraichir l'heure tt 50s

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					public void run() {
						try {
							Affichage();
						} catch (Exception ex) {
							Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
						}
					}
				});
			}
		}, 0, 10000);
	}

	void Affichage() {
		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/EquitySymbolService!contracts.EquitySymbolRemote";

		Context context;

		try {
			context = new InitialContext();
			EquitySymbolRemote proxy = (EquitySymbolRemote) context.lookup(jndiName);

			col_symbol.setCellValueFactory(new PropertyValueFactory("Symbol"));
			col_name.setCellValueFactory(new PropertyValueFactory("Name"));
			col_exchange.setCellValueFactory(new PropertyValueFactory("Exchange"));
			col_country.setCellValueFactory(new PropertyValueFactory("Country"));
			col_categoryName.setCellValueFactory(new PropertyValueFactory("CategoryName"));
			col_categoryNumbler.setCellValueFactory(new PropertyValueFactory("CategoryNumber"));
			col_status.setCellValueFactory(new PropertyValueFactory("Status"));

			listEquitySymbols = FXCollections.observableArrayList();

			for (EquitySymbol equitysymbol : proxy.reseachByName(TF_recherche.getText())) {
				listEquitySymbols.add(equitysymbol);
			}
			table_symbol.setItems(listEquitySymbols);

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
}
