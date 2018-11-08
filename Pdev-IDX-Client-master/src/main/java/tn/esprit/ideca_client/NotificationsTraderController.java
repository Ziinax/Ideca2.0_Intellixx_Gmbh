package tn.esprit.ideca_client;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import contracts.NotificationsRemote;
import contracts.UtilisateurRemote;
import ideca.entity.Notification;
import ideca.entity.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class NotificationsTraderController implements Initializable {

	private ObservableList<Object> listNotification;

	@FXML
	private GridPane sidebar;

	@FXML
	private JFXButton goToHome;

	@FXML
	private Button deconnex;

	@FXML
	private JFXTextField notification_id;

	@FXML
	private Label Notification_message;

	@FXML
	private Label Message_notification;

	@FXML
	private Label refresh_list;

	@FXML
	private Label Not_seen;

	@FXML
	private Label demands;

	@FXML
	private TableView Table_Notifications;

	@FXML
	private TableColumn col_Subject;

	@FXML
	private TableColumn col_Date;

	@FXML
	private TableColumn col_State;

	@FXML
	private TableColumn col_Type;
	private String jndiNameNotification = "Ideca_intelixx-ear/Ideca_intelixx-ejb/NotificationsService!contracts.NotificationsRemote";
	private String jndiNameUser = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";
	private final String notSeen = "notSeen";
	Logger logger = Logger.getAnonymousLogger();

	@FXML
	void Deconnect(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Pane root;
		root = FXMLLoader.load(getClass().getResource("/fxml/Sign_in.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Deconnect Ideca2.0");
		Notifications notif = Notifications.create().title("Session Closed").text("You are Disconected")
				.hideCloseButton().position(Pos.BOTTOM_RIGHT).onAction(new EventHandler<ActionEvent>() {

					public void handle(ActionEvent arg0) {
						// la methode auto generated

					}
				});

		notif.showConfirm();

		primaryStage.show();
	}

	@FXML
	void DeconnexAction(ActionEvent event) throws IOException {

		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Pane root;
		root = FXMLLoader.load(getClass().getResource("/fxml/Accueil.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Deconnect Ideca2.0");
		primaryStage.show();

	}


	
    @FXML
    void goToHome(ActionEvent event) throws IOException {
    	((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/fxml/Accueil.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Notifications Ideca2.0");
		primaryStage.show();
    }

	@FXML
	void Delete(MouseEvent event) throws NamingException {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText("Would You Like To Proceed Your Choice?");
		alert.setContentText("Please choose an option.");

		ButtonType yesButton = new ButtonType("Yes");
		ButtonType noButton = new ButtonType("No");
		ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(yesButton, noButton, cancelButton);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == yesButton) {
			if (notification_id.getText() != null) {
				Context context;
				context = new InitialContext();
				NotificationsRemote proxy = (NotificationsRemote) context.lookup(jndiNameNotification);

				proxy.supprimerNotification(Integer.parseInt(notification_id.getText()));
			}
			affiche();
		} else {
			event.consume();
		}
	}

	@FXML
	void Demands_notifications(MouseEvent event) throws NamingException {
		Table_Notifications.refresh();

		Context context;

		context = new InitialContext();
		NotificationsRemote proxy = (NotificationsRemote) context.lookup(jndiNameNotification);

		UtilisateurRemote proxyUser = (UtilisateurRemote) context.lookup(jndiNameUser);

		Utilisateur user = proxyUser.findById(Sign_inController.userConnected);

		col_Subject.setCellValueFactory(new PropertyValueFactory("Text"));
		col_Date.setCellValueFactory(new PropertyValueFactory("Date"));
		col_State.setCellValueFactory(new PropertyValueFactory("etat"));
		col_Type.setCellValueFactory(new PropertyValueFactory("Type"));

		listNotification = FXCollections.observableArrayList();

		for (Notification notification : proxy.listeNotificatioForTrader(user.getId())) {
			if (("tradeResponse").equals(notification.getType()) || ("tradeRequest").equals(notification.getType())) {
				listNotification.add(notification);
			}
		}
		Table_Notifications.setItems(listNotification);

	}

	@FXML
	void not_seen_notifications(MouseEvent event) throws NamingException {
		Table_Notifications.refresh();

		Context context;

		context = new InitialContext();
		NotificationsRemote proxy = (NotificationsRemote) context.lookup(jndiNameNotification);

		UtilisateurRemote proxyUser = (UtilisateurRemote) context.lookup(jndiNameUser);

		Utilisateur user = proxyUser.findById(Sign_inController.userConnected);

		col_Subject.setCellValueFactory(new PropertyValueFactory("Text"));
		col_Date.setCellValueFactory(new PropertyValueFactory("Date"));
		col_State.setCellValueFactory(new PropertyValueFactory("etat"));
		col_Type.setCellValueFactory(new PropertyValueFactory("Type"));

		listNotification = FXCollections.observableArrayList();

		for (Notification notification : proxy.listeNotificatioForTrader(user.getId())) {
			if (notSeen.equals(notification.getEtat())) {
				listNotification.add(notification);
			}
		}
		Table_Notifications.setItems(listNotification);

	}

	

	@FXML
	void refresh_notification(MouseEvent event) throws NamingException {
		affiche();
	}

	@FXML
	void rowSelection(MouseEvent event) throws NamingException {
		if (Table_Notifications != null) {

			List<Notification> tables = Table_Notifications.getSelectionModel().getSelectedItems();
			if (tables.size() == 1) {
				final Notification selectedDisc = tables.get(0);
				if (selectedDisc != null) {

					notification_id.setText(String.valueOf(tables.get(0).getId()));
					if (notification_id.getText() != null) {
						Context context;
						context = new InitialContext();
						NotificationsRemote proxy = (NotificationsRemote) context.lookup(jndiNameNotification);
						Notification notification = proxy.findById(Integer.parseInt(notification_id.getText()));
						if (notSeen.equals(notification.getEtat())) {
							notification.setEtat("seen");
							proxy.updateNotification(notification);
							affiche();
						}
					}

				}
			}
		}
	}

	void affiche() throws NamingException {
		Table_Notifications.refresh();

		Context context;

		context = new InitialContext();
		NotificationsRemote proxy = (NotificationsRemote) context.lookup(jndiNameNotification);

		UtilisateurRemote proxyUser = (UtilisateurRemote) context.lookup(jndiNameUser);

		Utilisateur user = proxyUser.findById(Sign_inController.userConnected);

		col_Subject.setCellValueFactory(new PropertyValueFactory("Text"));
		col_Date.setCellValueFactory(new PropertyValueFactory("Date"));
		col_State.setCellValueFactory(new PropertyValueFactory("etat"));
		col_Type.setCellValueFactory(new PropertyValueFactory("Type"));

		int compteur = 0;
		listNotification = FXCollections.observableArrayList();

		for (Notification notification : proxy.listeNotificatioForTrader(user.getId())) {// get
																							// a
																							// remplir
			if (("tradeRequest").equals(notification.getType()) || ("tradeResponse").equals(notification.getType())
					|| ("information").equals(notification.getType())) {
				if (notSeen.equals(notification.getEtat())) {
					compteur++;
				}
				listNotification.add(notification);
			}
		}
		Table_Notifications.setItems(listNotification);
		Notification_message.setText("you Have " + compteur + " new notifacation(s)");

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Auto-generated method stub
		try {
			affiche();
		} catch (NamingException e) {
			// Auto-generated catch block
			logger.log(Level.SEVERE, "an exception was throwne", e);
		}

	}

}
