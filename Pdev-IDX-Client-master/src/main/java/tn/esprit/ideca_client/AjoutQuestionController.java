package tn.esprit.ideca_client;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import contracts.QuestionRemote;
import contracts.QuizRemote;

import ideca.entity.Question;
import ideca.entity.Quiz;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AjoutQuestionController implements Initializable {
	@FXML
	private Text labelError;
	@FXML
	private JFXButton quizBtn;

	@FXML
	private TextField rep2Input;

	@FXML
	private JFXButton lissUserBtn;

	@FXML
	private JFXButton btnpersist;

	@FXML
	private TextField rep1Input;

	@FXML
	private JFXButton marketDataBnt;

	@FXML
	private TextField questionInput;

	@FXML
	private JFXButton fxOptionBnt;

	@FXML
	private TextField rep3Input;

	@FXML
	private TextField vraiRepInput;

	@FXML
	private JFXButton statistiqueBtn;

	private String error = "r an exception was thrown !";

	Logger logger = Logger.getAnonymousLogger();

	@FXML
	private JFXComboBox<String> categorieCombo;
	ObservableList<String> categories = FXCollections.observableArrayList(

			"Trade", "Option", "genral culture");

	@FXML
	void btnpersistAction(ActionEvent event) throws NamingException {
		Question question = new Question();
		Quiz quiz = new Quiz();
		if (rep1Input.getText().isEmpty() && rep3Input.getText().isEmpty() && vraiRepInput.getText().isEmpty())

		{

			labelError.setText("field is empty");
		} else {
			labelError.setText("");
			if (rep1Input.getText().equals(rep2Input.getText()) || rep1Input.getText().equals(rep3Input.getText())
					|| rep3Input.getText().equals(rep2Input.getText())) {
				labelError.setText("reponces are similiar");
			} else {
				labelError.setText("");
			}
			if (vraiRepInput.getText().equals(rep1Input.getText()) || vraiRepInput.getText().equals(rep2Input.getText())
					|| vraiRepInput.getText().equals(rep3Input.getText())) {
				labelError.setText("cv");
				question.setContenu(questionInput.getText());
				question.setRep1(rep1Input.getText());
				question.setRep2(rep2Input.getText());
				question.setRep3(rep3Input.getText());
				question.setRepVrai(vraiRepInput.getText());

				List<Question> questions;
				questions = new ArrayList<>();
				questions.add(question);
				quiz.setCategorie(categorieCombo.getValue());

				String jndiNameQuiz = "Ideca_intelixx-ear/Ideca_intelixx-ejb/QuizService!contracts.QuizRemote";
				Context contextQuiz = new InitialContext();

				QuizRemote proxyQuiz = (QuizRemote) contextQuiz.lookup(jndiNameQuiz);

				int idQuiz = proxyQuiz.ajouterQuiz(quiz);
				String jndiNameQuestion = "Ideca_intelixx-ear/Ideca_intelixx-ejb/QuestionService!contracts.QuestionRemote";
				Context contextQuestion = new InitialContext();

				QuestionRemote proxyQuestion = (QuestionRemote) contextQuestion.lookup(jndiNameQuestion);

				int idQuestion = proxyQuestion.ajouterQuestion(question);
				quiz.setId(idQuiz);
				question.setQuiz(quiz);
				proxyQuestion.affecterQuestionQuiz(idQuestion, idQuiz);

			} else {
				labelError.setText("the correct responce must be one of the exisiten responce ");
			}

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		categorieCombo.setValue("Trader");
		categorieCombo.setItems(categories);

	}

}
