package tn.esprit.ideca_client;

import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import contracts.UtilisateurRemote;
import ideca.entity.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class PwdForgottenController implements Initializable {

	@FXML
	private Label ErreurInput;

	@FXML
	private JFXTextField tf_cin;

	@FXML
	private JFXButton btn_validate;

	@FXML
	void SaveFPW(ActionEvent event) throws NamingException {

		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";
		Context context = new InitialContext();
		UtilisateurRemote proxy = (UtilisateurRemote) context.lookup(jndiName);

		Utilisateur user = proxy.pwdForgotten(tf_cin.getText());
		if (user != null) {
			btn_validate.setDisable(false);
			user.setPwd(tf_cin.getText());
			proxy.updateUtilisateur(user);

		} else {

			ErreurInput.setText("There's no account associated with this CIN !");
			btn_validate.setDisable(true);

		}

	}

	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
