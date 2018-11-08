package tn.esprit.ideca_client;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.jfoenix.controls.JFXButton;

import contracts.UtilisateurRemote;
import ideca.entity.Trader;
import ideca.entity.Utilisateur;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelpDataController implements Initializable {
	@FXML
	private JFXButton quizBtn;

	@FXML
	private Label firstNameLabel;

	@FXML
	private Label lastNameLabel;
	@FXML
	private Button deconnex;
	@FXML
	private Label firstNameLabel1;

	@FXML
	private Label lastNameLabel1;

	@FXML
	private Label EmailLabel;
	@FXML
	private JFXButton editProfile;

	@FXML
	private Pane ProfileUser;

	@FXML
	private Button ProfilePic;

	// video
	@FXML
	private MediaView video;

	@FXML
	private MediaView video1;

	@FXML
	private MediaView video2;

	public Timer timer = new Timer();

	@FXML
	private Label timerLabel;
	@FXML
	private Label timerLabel1;

	/*
	 * String s = new File(
	 * "/Users/Ghada/git/ideca client/ideca_client/src/main/resources/images/v7.mp4"
	 * ) .getAbsolutePath(); final File file = new File(s); final Media media =
	 * new Media(file.toURI().toString()); final MediaPlayer mediaPlayer = new
	 * MediaPlayer(media);
	 * 
	 * String s1 = new File(
	 * "/Users/Ghada/git/ideca client/ideca_client/src/main/resources/images/v4.mp4"
	 * ).getAbsolutePath(); final File file1 = new File(s1); final Media media1
	 * = new Media(file1.toURI().toString()); final MediaPlayer mediaPlayer1 =
	 * new MediaPlayer(media1);
	 * 
	 * String s2 = new File(
	 * "/Users/Ghada/git/ideca client/ideca_client/src/main/resources/images/v2.mp4"
	 * ).getAbsolutePath(); final File file2 = new File(s2); final Media media2
	 * = new Media(file2.toURI().toString()); final MediaPlayer mediaPlayer2 =
	 * new MediaPlayer(media2);
	 */

	public void initialize(URL location, ResourceBundle resources) {

		// play();
		Refresh();

		try {
			ProfileUser();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(Sign_inController.UserConnected);

		ProfileUser.setVisible(false);
		ProfilePic.setOnMouseEntered(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				ProfileUser.setVisible(true);
				ProfilePic.setOnMouseClicked(new EventHandler<MouseEvent>() {

					public void handle(MouseEvent event) {
						ProfileUser.setVisible(false);

					}
				});

			}

		});
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
	public void ProfileUser() throws NamingException {

		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";
		Context context = new InitialContext();
		UtilisateurRemote proxy = (UtilisateurRemote) context.lookup(jndiName);

		System.out.println(proxy.findById(Sign_inController.userConnected));
		Utilisateur trader = proxy.findById(Sign_inController.userConnected);
		firstNameLabel.setText(trader.getFirstName());
		lastNameLabel.setText(trader.getLasttName());
		EmailLabel.setText(trader.getEmail());
		firstNameLabel1.setText(trader.getFirstName());
		lastNameLabel1.setText(trader.getLasttName());

	}

	@FXML
	void EditProfileAction(ActionEvent event) {

		try {
			timer.cancel();
			/*
			 * mediaPlayer.stop(); mediaPlayer1.stop();
			 */
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxml/Profile.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Acceuil Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {

		}
	}

	///////// video

	/*
	 * public void play() {// play video
	 * 
	 * video.setMediaPlayer(mediaPlayer); mediaPlayer.play();
	 * mediaPlayer.setCycleCount(100);
	 * 
	 * video1.setMediaPlayer(mediaPlayer1); mediaPlayer1.play();
	 * mediaPlayer1.setCycleCount(100);
	 * 
	 * video2.setMediaPlayer(mediaPlayer2); mediaPlayer2.play();
	 * mediaPlayer2.setCycleCount(100); }
	 */

	public void heureactu() {// affichage heure

		Date dNow = new Date();
		SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
		SimpleDateFormat mdy = new SimpleDateFormat("MM/dd/yyyy");
		timerLabel1.setText(hm.format(dNow));
		timerLabel.setText(mdy.format(dNow));

	}

	public void Refresh() {// rafraichir l'heure tt 50s
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					public void run() {
						try {
							heureactu();

						} catch (Exception ex) {
							Logger.getLogger(HelpDataController.class.getName()).log(Level.SEVERE, null, ex);
						}
					}
				});
			}
		}, 0, 50000);
	}

	//////////////////////////////////////////

	@FXML
	void Deconnect(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxml/Sign_in.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Inscription Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {

		}

	}

	@FXML
	void DeconnexAction(ActionEvent event) {
		try {

			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/fxml/Sign_in.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Sign_in Ideca2.0");
			primaryStage.show();
		} catch (Exception e) {

		}
	}
	 @FXML
	    void quizBtn(ActionEvent event) {
		 try {

				((Node) event.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/fxml/QuizTrade.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Sign_in Ideca2.0");
				primaryStage.show();
			} catch (Exception e) {

			}
	    }
	  @FXML
	    void go(ActionEvent event) {
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
				Pane root = loader.load(getClass().getResource("/fxml/EquityOption.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Sign_in Ideca2.0");
				primaryStage.show();
			} catch (Exception e) {

			}
	    }

	    @FXML
	    void listBackOfficeUserbtnActtion(ActionEvent event) {

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
