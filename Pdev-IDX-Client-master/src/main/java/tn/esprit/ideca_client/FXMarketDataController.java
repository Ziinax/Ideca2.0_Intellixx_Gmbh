package tn.esprit.ideca_client;

import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import contracts.EquityOptionRemote;
import contracts.FXOptionsRemote;
import ideca.entity.EquityOptions;
import ideca.entity.FXOptions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FXMarketDataController implements Initializable {
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
	    private TableView Table_Equity;

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
	    private ObservableList<Object> listFXOptions;
	 @FXML
	    void equityOptionBtnAction(ActionEvent event) {
	    	try {

	            ((Node) event.getSource()).getScene().getWindow().hide();
	            Stage primaryStage = new Stage();
	            FXMLLoader loader = new FXMLLoader();
	            Pane root = loader.load(getClass().getResource("/fxml/EquityOption.fxml"));
	            Scene scene = new Scene(root);
	            primaryStage.setScene(scene);
	            primaryStage.setTitle("Inscription Ideca2.0");
	            primaryStage.show();
	        } catch (Exception e) {

	        }
	    }

	    @FXML
	    void fxOptionBntAction(ActionEvent event) {
	    	try {

	            ((Node) event.getSource()).getScene().getWindow().hide();
	            Stage primaryStage = new Stage();
	            FXMLLoader loader = new FXMLLoader();
	            Pane root = loader.load(getClass().getResource("/fxml/FxOption.fxml"));
	            Scene scene = new Scene(root);
	            primaryStage.setScene(scene);
	            primaryStage.setTitle(" Fx Option Ideca2.0");
	            primaryStage.show();
	        } catch (Exception e) {

	        }
	    }

	    @FXML
	    void lissUserBtnAction(ActionEvent event) {
	    	try {

	            ((Node) event.getSource()).getScene().getWindow().hide();
	            Stage primaryStage = new Stage();
	            FXMLLoader loader = new FXMLLoader();
	            Pane root = loader.load(getClass().getResource("/fxml/AccueilBackOfiiceUser.fxml"));
	            Scene scene = new Scene(root);
	            primaryStage.setScene(scene);
	            primaryStage.setTitle("List of Users Ideca2.0");
	            primaryStage.show();
	        } catch (Exception e) {

	        }
	    }

	    @FXML
	    void fxOptionQuotesBtn(ActionEvent event) {
			try {

				((Node) event.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxml/FxoptionQuotes.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle(" Fx Option Ideca2.0");
				primaryStage.show();
			} catch (Exception e) {

			}
	    }
	    
	    
	    @FXML
	    void marketDataBntAction(ActionEvent event) {
	    	try {

	            ((Node) event.getSource()).getScene().getWindow().hide();
	            Stage primaryStage = new Stage();
	            FXMLLoader loader = new FXMLLoader();
	            Pane root = loader.load(getClass().getResource("/fxml/MarketData.fxml"));
	            Scene scene = new Scene(root);
	            primaryStage.setScene(scene);
	            primaryStage.setTitle("Market data Ideca2.0");
	            primaryStage.show();
	        } catch (Exception e) {

	        }
	    }
	public void initialize(URL location, ResourceBundle resources) {
		String jndiName ="Ideca_intelixx-ear/Ideca_intelixx-ejb/FXOptionsService!contracts.FXOptionsRemote";

		Context context;
		
		
			try {
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
			
			listFXOptions =FXCollections.observableArrayList();
			
			for (FXOptions equityoption : proxy.findAllFXOption())
			{
				listFXOptions.add(equityoption);
			}
			System.out.println(listFXOptions.size());
		 Table_Equity.setItems(listFXOptions);
		
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
	}

}
