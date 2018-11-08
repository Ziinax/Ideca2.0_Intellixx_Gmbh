package tn.esprit.ideca_client;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class TestVideoController implements Initializable{

	
	
	
	
	// video
	@FXML
	private MediaView video;
	
	
	

	String s = new File("/Users/Ghada/git/ideca client/ideca_client/src/main/resources/images/v1.mp4").getAbsolutePath();
	final File file = new File(s);
	final Media media = new Media(file.toURI().toString());
	final MediaPlayer mediaPlayer = new MediaPlayer(media);
	
	
	
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
