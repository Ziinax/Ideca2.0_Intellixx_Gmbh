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

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;


import contracts.NotificationsRemote;
import contracts.UtilisateurRemote;
import ideca.entity.Notification;
import ideca.entity.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class NotificationsController implements Initializable {

	
	 @FXML
	    private JFXButton homeBtn;

	    @FXML
	    private Pane ProfilePane;

	    @FXML
	    private ImageView profile_pic;

	    @FXML
	    private TableView Table_Notifications;

	    @FXML
	    private TableColumn col_Trader;
	    
	    @FXML
	    private TableColumn col_Id;

	    @FXML
	    private TableColumn col_Subject;

	    @FXML
	    private TableColumn col_Date;

	    @FXML
	    private TableColumn col_State;

	    @FXML
	    private TableColumn col_Type;

	    @FXML
	    private Label Notification_message;

	    @FXML
	    private JFXTextField notification_id;
	    
	    @FXML
	    private Label All_notification;

	    @FXML
	    private Label demands;

	    @FXML
	    private Label Not_seen;

	    @FXML
	    private Label refresh_list;

	    @FXML
	    private Label Message_notification;
	    
	    private String idTraderDuplicate="id_trader";
	    
	    private ObservableList<Object> listNotification;
		Logger logger = Logger.getAnonymousLogger();


	   private String jndiNameNotification = "Ideca_intelixx-ear/Ideca_intelixx-ejb/NotificationsService!contracts.NotificationsRemote";
	   
	   private String jndiNameUser = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";
	   private final String notSeen="notSeen";

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
			} else  {
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
	    		
	    		
	    		col_Trader.setCellValueFactory(new PropertyValueFactory(idTraderDuplicate));
	    		col_Subject.setCellValueFactory(new PropertyValueFactory("Text"));
	    		col_Date.setCellValueFactory(new PropertyValueFactory("Date"));
	    		col_State.setCellValueFactory(new PropertyValueFactory("etat"));
	    		col_Type.setCellValueFactory(new PropertyValueFactory("Type"));
	    		col_Id.setCellValueFactory(new PropertyValueFactory("id"));
	    		

	    		listNotification = FXCollections.observableArrayList();

	    		for (Notification notification : proxy.listeNotificatioForBackOffUser(user.getId())) {
	    			if(("registration").equals(notification.getType()) || ("forBackoffice").equals(notification.getType()))
	    			{listNotification.add(notification);}
	    		}
	    		Table_Notifications.setItems(listNotification);

	    	

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
				    		if(notSeen.equals(notification.getEtat()))
				    		{notification.setEtat("seen");
				    		proxy.updateNotification(notification);
				    		affiche();
				    		}
						}
						
					}
				}
			}
	    }

	    @FXML
	    void homeBtnAction(ActionEvent event) throws IOException {

	    	

				((Node) event.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxml/AccueilBackOfiiceUser.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Notifications Ideca2.0");
				primaryStage.show();
			
	    }

	    @FXML
	    void not_seen_notifications(MouseEvent event) throws NamingException {
Table_Notifications.refresh();
	    	
	    	
	    	Context context;

	    	
	    		context = new InitialContext();
	    		NotificationsRemote proxy = (NotificationsRemote) context.lookup(jndiNameNotification);
	    		
	    		
	    		UtilisateurRemote proxyUser = (UtilisateurRemote) context.lookup(jndiNameUser);

	    		Utilisateur user = proxyUser.findById(Sign_inController.userConnected);
	    		
	    		
	    		col_Trader.setCellValueFactory(new PropertyValueFactory(idTraderDuplicate));
	    		col_Subject.setCellValueFactory(new PropertyValueFactory("Text"));
	    		col_Date.setCellValueFactory(new PropertyValueFactory("Date"));
	    		col_State.setCellValueFactory(new PropertyValueFactory("etat"));
	    		col_Type.setCellValueFactory(new PropertyValueFactory("Type"));
	    		col_Id.setCellValueFactory(new PropertyValueFactory("id"));
	    		

	    		listNotification = FXCollections.observableArrayList();

	    		for (Notification notification : proxy.listeNotificatioForBackOffUser(user.getId())) {
	    			if(notSeen.equals(notification.getEtat()))
	    			{listNotification.add(notification);}
	    		}
	    		Table_Notifications.setItems(listNotification);

	    	
	    }

	    @FXML
	    void refresh_notification(MouseEvent event) throws NamingException {
	    	affiche();
	    }
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			affiche();
		} catch (NamingException e) {
			//  Auto-generated catch block
			logger.log(Level.SEVERE, "an exception was throwne", e);
		}
		
	
	}
	
void affiche() throws NamingException{
	Table_Notifications.refresh();
	
	Context context;

	
		context = new InitialContext();
		NotificationsRemote proxy = (NotificationsRemote) context.lookup(jndiNameNotification);
		
		
		UtilisateurRemote proxyUser = (UtilisateurRemote) context.lookup(jndiNameUser);

		Utilisateur user = proxyUser.findById(Sign_inController.userConnected);
		
		
		col_Trader.setCellValueFactory(new PropertyValueFactory(idTraderDuplicate));
		col_Subject.setCellValueFactory(new PropertyValueFactory("Text"));
		col_Date.setCellValueFactory(new PropertyValueFactory("Date"));
		col_State.setCellValueFactory(new PropertyValueFactory("etat"));
		col_Type.setCellValueFactory(new PropertyValueFactory("Type"));
		col_Id.setCellValueFactory(new PropertyValueFactory("id"));
		
		int compteur=0;
		listNotification = FXCollections.observableArrayList();

		for (Notification notification : proxy.listeNotificatioForBackOffUser(user.getId())) {// get a remplir
			if(("forBackoffice").equals(notification.getType()) || ("connexion").equals(notification.getType())
					|| ("registration").equals(notification.getType())|| ("information").equals(notification.getType()))
			{
			if(notSeen.equals(notification.getEtat()))
			{compteur++;}
			listNotification.add(notification);
			}
		}
		Table_Notifications.setItems(listNotification);
		Notification_message.setText("you Have "+compteur+" new notifacation(s)");
	
}	
}


