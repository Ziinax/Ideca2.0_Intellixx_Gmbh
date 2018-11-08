package tn.esprit.ideca_client;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import contracts.ChatServiceRemote;
import contracts.UtilisateurRemote;
import ideca.entity.Chat;
import ideca.entity.Trader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class ChatController implements Initializable{
	int idTraderE;
	 @FXML
	    private JFXButton bRecieved;

	    @FXML
	    private JFXButton bSent;
	    @FXML
	    private JFXListView<String> sent;
	    
	@FXML
    private JFXTextField nomPrenomL;
	@FXML
    private JFXListView<String> boite;

    @FXML
    private JFXTextField message;

    @FXML
    private JFXButton send;

    @FXML
    private JFXListView<Object> conversation;

    

    private ObservableList<Object> listmsg;
    private ObservableList<String> listboite;
	public int currentID;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idTraderE=ListTraderUserControler.tables;
		if(idTraderE!=Sign_inController.userConnected)
		{
		
		//System.out.println(traderE.getFirstName());
			Context context;

			String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";
			
			try {
				context = new InitialContext();
				UtilisateurRemote proxy = (UtilisateurRemote) context.lookup(jndiName);
				Trader traderE =proxy.findByIdTrader(idTraderE);
				nomPrenomL.setText("                 "+traderE.getFirstName()+" "+traderE.getLasttName());
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		nomPrenomL.setDisable(true);
		boite.setVisible(true);
		sent.setVisible(false);
		displayChat(idTraderE);
		displayBoite();}
		
	}
	
public void displayChat(int idExp){
	
		Context context;

		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/ChatService!contracts.ChatServiceRemote";

		try {
			context = new InitialContext();
			ChatServiceRemote proxy = (ChatServiceRemote) context.lookup(jndiName);

			

			
			final ObservableList<Object> listmsg = FXCollections.observableArrayList();
				String dateact="";
			for (Chat chat : proxy.afficherConversation(Sign_inController.userConnected, idExp)) {
				Calendar cal=chat.getChatPK().getDate();
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH)+1; 
				int day = cal.get(Calendar.DAY_OF_MONTH);
				int hour = cal.get(Calendar.HOUR_OF_DAY); 
				int minute = cal.get(Calendar.MINUTE); 
				
				String date="                                                "+day+"/"+"0"+month+"/"+year;
				String heure=hour+":"+minute;
				if(!date.equals(dateact))
				{dateact=date;
				listmsg.add(date);
				}
				//listmsg.add(heure);
				String nomPrenom=heure+" : "+chat.getTraderE().getLasttName()+" "+chat.getTraderE().getFirstName();
				listmsg.add(nomPrenom);
				listmsg.add(chat.getContenu());
			}
			System.out.println(listmsg.size());
			conversation.setItems(listmsg);
			
			
		
			 
			
	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
    public void displaySent()
    {
    	Context context;

		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/ChatService!contracts.ChatServiceRemote";

		try {
			context = new InitialContext();
			ChatServiceRemote proxy = (ChatServiceRemote) context.lookup(jndiName);

			

			
			final ObservableList<String> listSent = FXCollections.observableArrayList();
				
			for (Trader tr : proxy.afficherSent(Sign_inController.userConnected)) {
				String nomP=tr.getFirstName()+" "+tr.getLasttName();
				listSent.add(nomP);
				
			}
			System.out.println(listSent.size());
			sent.setItems(listSent);
			
			
			      
			 
			
	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
	public void displayBoite(){
		Context context;

		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/ChatService!contracts.ChatServiceRemote";

		try {
			context = new InitialContext();
			ChatServiceRemote proxy = (ChatServiceRemote) context.lookup(jndiName);

			

			
			final ObservableList<String> listboite = FXCollections.observableArrayList();
				
			for (Trader tr : proxy.afficherBoite(Sign_inController.userConnected)) {
				String nomP=tr.getFirstName()+" "+tr.getLasttName();
				listboite.add(nomP);
				
			}
			System.out.println(listboite.size());
			boite.setItems(listboite);
			
			
			      
			 
			
	
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML
    void btnSaveAction(ActionEvent event) {
		Context context;

		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/ChatService!contracts.ChatServiceRemote";
		try {
			context = new InitialContext();
			ChatServiceRemote proxy = (ChatServiceRemote) context.lookup(jndiName);
			
			String contenu=message.getText();
			proxy.ajouterChat(idTraderE, Sign_inController.userConnected, contenu);
			message.clear();
			displayChat(idTraderE);
			/*if(!sent.contains(traderE.getFirstName+" "+traderE.getLastName)&&!boite.contains(traderE.getFirstName+" "+traderE.getLastName))
			{
				sent.add(traderE.getFirstName+" "+traderE.getLastName);
				displaySent();
			}*/
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }

    @FXML
    void e8702a(ActionEvent event) {

    }
    @FXML
    void clickList(MouseEvent event) {
    	Context context;
    	
		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/ChatService!contracts.ChatServiceRemote";
	
		
			String tables = boite.getSelectionModel().getSelectedItem();
			nomPrenomL.setText("                 "+tables);
			if(tables!=null)
			{
				try {
					context = new InitialContext();
					ChatServiceRemote proxy = (ChatServiceRemote) context.lookup(jndiName);
					for (Trader tr: proxy.afficherTousTrader())
					{
						String s=tr.getFirstName()+" "+tr.getLasttName();
						if(tables.equals(s))
						{
							
							 idTraderE=tr.getId();
							displayChat(idTraderE);
						}
					}
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
			
		
    }
    @FXML
    void receivedEvent(ActionEvent event) {
    	sent.setVisible(false);
    	boite.setVisible(true);
    	displayBoite();
    }

    @FXML
    void sentEvent(ActionEvent event) {
    	sent.setVisible(true);
    	boite.setVisible(false);
    	displaySent();
    }
    @FXML
    void Deconnect(ActionEvent event) {
    	try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxml/Accueil.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Acceuil Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {

		}
    }
    @FXML
    void clickListS(MouseEvent event) {
Context context;
    	
		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/ChatService!contracts.ChatServiceRemote";
	
		
			String tables = sent.getSelectionModel().getSelectedItem();
			nomPrenomL.setText("                 "+tables);
			if(tables!=null)
			{
				try {
					context = new InitialContext();
					ChatServiceRemote proxy = (ChatServiceRemote) context.lookup(jndiName);
					for (Trader tr: proxy.afficherTousTrader())
					{
						String s=tr.getFirstName()+" "+tr.getLasttName();
						if(tables.equals(s))
						{
							
							 idTraderE=tr.getId();
							displayChat(idTraderE);
						}
					}
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
    }
}

