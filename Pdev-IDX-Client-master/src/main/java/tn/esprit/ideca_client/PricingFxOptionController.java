package tn.esprit.ideca_client;

import java.awt.Label;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sun.glass.events.MouseEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class PricingFxOptionController implements Initializable{

    @FXML
    private JFXButton EurUsdrate;

    @FXML
    private JFXButton EurGbprate;

    @FXML
    private JFXButton EurJpyrate;

    @FXML
    private JFXButton AudUsdrate;

    @FXML
    private JFXButton UsdJpyrate;
    @FXML
    private JFXTextField eurusdcurrent;

    @FXML
    private JFXTextField eurgbpcurrent;

    @FXML
    private JFXTextField eurjpycurrent;

    @FXML
    private JFXTextField audusdcurrent;

    @FXML
    private JFXTextField usdjpycurrent;

    @FXML
    private Label DateInterface;
    @FXML
    private JFXComboBox pricingModel;


    
	public void initialize(URL urlBase, ResourceBundle resources) {
		

		
		
		
	
	}
	@FXML
    void OnAudUsdrateClicked(MouseEvent event) {

    }

    @FXML
    void OnEurGbprateClicked(MouseEvent event) {

    }

    @FXML
    void OnEurJpyrateClicked(MouseEvent event) {

    }

    @FXML
    void OnEurUsdrateClicked(MouseEvent event) {

    }

    @FXML
    void OnUsdJpyrateClicked(MouseEvent event) {

    }
}
