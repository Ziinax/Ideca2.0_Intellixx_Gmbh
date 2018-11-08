package tn.esprit.ideca_client;

import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import contracts.TradeRemote;
import contracts.UtilisateurRemote;
import ideca.entity.EquityOptions;
import ideca.entity.Trader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class OptionInformationController implements Initializable {
	
	@FXML
    private Label traderName;

    @FXML
    private Label grade;

    @FXML
    private Label email;

    @FXML
    private Label optionName;

    @FXML
    private Label spotRate;

    @FXML
    private Label strikePrice;

    @FXML
    private Label nominal;

    @FXML
    private Label volatility;

    @FXML
    private Label maturity;
    
    @FXML
    private Label premium;

	public void initialize(URL location, ResourceBundle resources) {

		try {
			affichage();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	void affichage() throws NamingException
	{
		String jndiNameTrade = "Ideca_intelixx-ear/Ideca_intelixx-ejb/TradeService!contracts.TradeRemote";
		String jndiNameUser = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";
		Context context;
		context = new InitialContext();
		TradeRemote proxyTrade = (TradeRemote) context.lookup(jndiNameTrade);
		UtilisateurRemote proxyUser = (UtilisateurRemote) context.lookup(jndiNameUser);
		EquityOptions equityOption=proxyTrade.findByIdSourceIdDestination(MainApp.popupSource, MainApp.popupDestination).getEquityOption() ; 
		Trader user = (Trader) proxyUser.findById(MainApp.popupSource);
		
		
		
		traderName.setText(user.getFirstName()+" "+user.getLasttName());
		grade.setText(String.valueOf(user.getGrade()));
		email.setText(user.getEmail()) ;
		optionName.setText(equityOption.getName());
		spotRate.setText(String.valueOf(equityOption.getCurrentStockPrice()));
		strikePrice.setText(String.valueOf(equityOption.getOptionStrikePrice()));
		nominal.setText(String.valueOf(equityOption.getNumberOfShares()));
		volatility.setText(String.valueOf(equityOption.getVolatility()));
		premium.setText(String.valueOf(equityOption.getPrice()));
		
	}
}
