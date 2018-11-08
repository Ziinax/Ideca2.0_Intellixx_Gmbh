package tn.esprit.ideca_client;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.w3c.dom.ls.LSInput;

import com.jfoenix.controls.JFXListView;

import contracts.QuestionRemote;
import contracts.UtilisateurRemote;
import ideca.entity.Question;
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
import javafx.scene.control.ListView;

import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class QuizTradeController implements Initializable {
	@FXML
	private ListView listQuiz;
	public static int UserConnected;

	public int GetUserConnected() throws Exception {
		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";
		Context context = new InitialContext();
		UtilisateurRemote proxy = (UtilisateurRemote) context.lookup(jndiName);
		int userCo = proxy.findById(Sign_inController.userConnected).getId();
		return userCo;
	}

	public List<Question> getAllQuestions() throws Exception {

		String jndiNameQuestion = "Ideca_intelixx-ear/Ideca_intelixx-ejb/QuestionService!contracts.QuestionRemote";
		Context contextQuestion = new InitialContext();

		QuestionRemote proxyQuestion = (QuestionRemote) contextQuestion.lookup(jndiNameQuestion);

		return proxyQuestion.getAllQuestions();

	}

	public Question getQuestion(int id) throws Exception {

		String jndiNameQuestion = "Ideca_intelixx-ear/Ideca_intelixx-ejb/QuestionService!contracts.QuestionRemote";
		Context contextQuestion = new InitialContext();

		QuestionRemote proxyQuestion = (QuestionRemote) contextQuestion.lookup(jndiNameQuestion);

		return proxyQuestion.ListQuestionByUser(id);

	}

	public void initialize(URL location, ResourceBundle resources) {

		ObservableList<Pane> panes = FXCollections.observableArrayList();

		List<Question> listQuestions = new ArrayList<Question>();

		int idUtilisateur = 0;
		try {
			idUtilisateur = GetUserConnected();
		} catch (Exception e) {

			e.printStackTrace();
		} //
		try {
			listQuestions = getAllQuestions();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < listQuestions.size(); i++) {

			int ids = listQuestions.get(i).getId();
			System.out.println(ids + "\n");
			try {
				panes.add(LoadServiceFromDB(ids));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// listHotel.get(i).getIdRestaurant(),
																// listHotel.get(i).getNomRestaurant()));

			listQuiz.setItems(panes);

		}

	}

	private Pane LoadServiceFromDB(int ids)throws Exception

	{
		Question question = new Question();

		int idQuizSelectionne = ids;

		question = getQuestion(idQuizSelectionne);// id utilisateur
									

		Pane pane = new Pane();

		pane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		pane.setPrefWidth(422);
		pane.setPrefHeight(184);
		String style = "-fx-padding: 8 15 15 15;\n" + "    -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;\n"
				+ "    -fx-background-radius: 8;\n" + "    -fx-font-weight: bold;\n" + "    -fx-font-size: 1.1em;";
		pane.setStyle(style);
		pane.setId("pane_Service");

		// ImageView Proprieties
		
		String style_btn = "-fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 0 0;\n" + "    -fx-background-radius: 8;\n"
				+ " fx-border-color: white;\n" + "    -fx-font-size: 0.8em;";

		Label Snom = new Label("Question :" + question.getContenu());
		
		Button btn = new Button("validé");
		btn.getStyleClass().add("btn-danger");

		// Labels Proprieties
		// Label NOM
		Snom.setLayoutX(39);
		Snom.setLayoutY(30);
		Snom.setId("Snom");
		// Label SAdresse
		

		// Button Position
		btn.setLayoutX(266);
		btn.setLayoutY(104);
		btn.setId("btnSupprimer");
		btn.setStyle(style_btn);
		btn.setStyle("-fx-background-radius: 40;-fx-border-color: white;-fx-font-size: 0.8em;");
		
		
		pane.getChildren().add(Snom);
		

		pane.getChildren().add(btn);

		return pane;

	}
	@FXML
    void personalDataBtnAction(ActionEvent event) {
    	 try {

				((Node) event.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxml/Profile.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Sign_in Ideca2.0");
				primaryStage.show();
			} catch (Exception e) {

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
	    void portfolioBtnAction(ActionEvent event) {
	    	try {

				((Node) event.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxml/Portfolio.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Sign_in Ideca2.0");
				primaryStage.show();
			} catch (Exception e) {

			}
	    }

	    @FXML
	    void listTradersAction(ActionEvent event) {
	    	try {

				((Node) event.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxml/ListTraderUser.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Sign_in Ideca2.0");
				primaryStage.show();
			} catch (Exception e) {

			}
	    }

	    @FXML
	    void helpDataBtnAction(ActionEvent event) {
	    	try {

				((Node) event.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxml/HelpData.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Sign_in Ideca2.0");
				primaryStage.show();
			} catch (Exception e) {

			}
	    }

	    @FXML
	    void listBackOfficeUserbtnActtion(ActionEvent event) {
	    	try {

				((Node) event.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxml/ListBackOfficeUser.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Sign_in Ideca2.0");
				primaryStage.show();
			} catch (Exception e) {

			}
	    }
	   @FXML
	    void PricingofFXOptionVtn(ActionEvent event) {
		   try {

				((Node) event.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxml/FxOption.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Sign_in Ideca2.0");
				primaryStage.show();
			} catch (Exception e) {

			}
	    }

	    @FXML
	    void PricingofEquityOptionAction(ActionEvent event) {
	    	 try {

					((Node) event.getSource()).getScene().getWindow().hide();
					Stage primaryStage = new Stage();
					FXMLLoader loader = new FXMLLoader();
					Pane root = loader.load(getClass().getResource("/fxml/EquityOption.fxml"));
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.setTitle("Sign_in Ideca2.0");
					primaryStage.show();
				} catch (Exception e) {

				}
	    }

	    @FXML
	    void tradeBtnAction(ActionEvent event) {
	    	try {

				((Node) event.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxml/trade.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Pricing of EquityOption Ideca2.0");
				primaryStage.show();
			} catch (Exception e) {

			}
	    }

}
