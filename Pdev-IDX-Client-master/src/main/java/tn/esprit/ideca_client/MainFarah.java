package tn.esprit.ideca_client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXDecorator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class MainFarah extends Application{
	public static Stage stage;
	public static String urlhome;
	public static String urlBase;
	
	public MainFarah(){
		urlhome="/fxml/chat.fxml";
		urlBase="/fxml/chat.fxml";
		
	}
	
	@Override
	public void start(Stage primaryStage) {
		afficher(urlBase, "Home", true);
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public static void afficher(String url, String title, boolean resizable) {
        try {
            if (stage!=null)
            {
                stage.close();
            }
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(url));
            AnchorPane content = loader.load();            
            //Scene sc = new Scene(content);
            stage=new Stage();
            Scene sc = new Scene(new JFXDecorator(stage, content));
            
            sc.getStylesheets().add("/ressources/jfoenix-main-demo.css");
            
            stage.setScene(sc);
            stage.setTitle(title);
            stage.setResizable(resizable);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

	public static void initBase(AnchorPane base, String url) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource(url));
        try {
            base.getChildren().clear();
            AnchorPane content = loader.load();
            base.getChildren().add(content);   
            AnchorPane.setTopAnchor(content,0.0);
            AnchorPane.setBottomAnchor(content,0.0);
            AnchorPane.setLeftAnchor(content,0.0);
            AnchorPane.setRightAnchor(content,0.0);
            
            
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
