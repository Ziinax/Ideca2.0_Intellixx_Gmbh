package tn.esprit.ideca_client;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.Label;
import javax.imageio.ImageIO;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import contracts.UtilisateurRemote;
import ideca.entity.Trader;
import ideca.entity.Utilisateur;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.chart.PieChart;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.paint.Color;


public class StatistiqueController implements Initializable {
	@FXML
	private Pane ChartOne;

	@FXML
	private Pane ChartTwo;
	@FXML
	private Button btnpie;

	@FXML
	private Button btnline;

	@FXML
	private JFXButton quizBtn;

	@FXML
	private JFXButton lissUserBtn;

	@FXML
	private JFXButton marketDataBnt;

	@FXML
	private JFXButton equityOptionBtn;

	@FXML
	private JFXButton fxOptionBnt;

	@FXML
	private Pane ProfilePane;

	@FXML
	private ImageView profile_pic;

	@FXML
	private JFXTextField eur_usdInput;

	@FXML
	private JFXTextField eur_GbpInput;

	@FXML
	private JFXTextField eur_JPYInput;

	@FXML
	private JFXTextField aud_usdInput;

	@FXML
	private JFXTextField uds_jpyInput;
	Logger logger = Logger.getAnonymousLogger();
	public void PieChart() throws NamingException {

		

		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";
		Context context = new InitialContext();

		UtilisateurRemote proxy = (UtilisateurRemote) context.lookup(jndiName);

		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data("Trader", proxy.findByType("Trader")),
				new PieChart.Data("BackOfficeUser", proxy.findByType("BackOfficeUser"))

		);

		final PieChart chart = new PieChart(pieChartData);
		chart.setTitle("Utilisateur par type");

		ChartOne.getChildren().add(chart);

		final Label caption = new Label("");
		caption.setTextFill(Color.DARKORANGE);
		caption.setStyle("-fx-font: 24 arial;");

		for (final PieChart.Data data : chart.getData()) {
			data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			
				public void handle(MouseEvent e) {
					caption.setTranslateX(e.getSceneX());
					caption.setTranslateY(e.getSceneY());
					caption.setText(String.valueOf(data.getPieValue()) + "%");
				}
			});
		}

		btnpie.setOnMouseClicked(new EventHandler<MouseEvent>() {

	
			public void handle(MouseEvent event) {

				WritableImage image = ChartOne.snapshot(new SnapshotParameters(), null);

				File file = new File("charpie.png");

				try {
					ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
				} catch (IOException e) {
					logger.log(Level.SEVERE, "r an exception was thrown !!!", e);
				}

			}

		});

	}

	public void LineChart() throws NamingException {
	
		Set<Trader> treeset = new TreeSet <Trader>();

		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";
		Context context = new InitialContext();

		UtilisateurRemote proxy = (UtilisateurRemote) context.lookup(jndiName);
		treeset = proxy.findAllTreeSet();

			

		final String valide = "Valide";
		final String nonvALIDE = "Non Valide";

		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		final BarChart<String, Number> bc = new BarChart<String,Number>(xAxis, yAxis);

		bc.setTitle("Trader/Etat");
		xAxis.setLabel("Etat");
		yAxis.setLabel("Trader identifiant");

		XYChart.Series series1 = new XYChart.Series();
		series1.setName("Nombre Des Produits");
		XYChart.Series seriest = new XYChart.Series();
		seriest.setName("2003");
		XYChart.Series seriestt = new XYChart.Series();
		seriestt.setName("2003");

		for (Trader e : treeset) {

			series1.getData().add(new XYChart.Data(e.getEtat(), e.getId()));

		}
		

		XYChart.Series series2 = new XYChart.Series();
		series2.setName("2004");
		series2.getData().add(new XYChart.Data(valide, 50));
		series2.getData().add(new XYChart.Data(nonvALIDE, 40));
		
		XYChart.Series series3 = new XYChart.Series();
		series3.setName("2005");
		series3.getData().add(new XYChart.Data(valide, 31));
		series3.getData().add(new XYChart.Data(nonvALIDE, 43));
		

		bc.getData().addAll(series1);
		ChartTwo.getChildren().add(bc);

		btnline.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event) {

				WritableImage image = ChartTwo.snapshot(new SnapshotParameters(), null);

				File file = new File("chartline.png");

				try {
					ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
				} catch (IOException e) {
					logger.log(Level.SEVERE, "an exception was thrown", e);
				}

			}
		});

	}

	
	public void initialize(URL location, ResourceBundle resources) {

		try {
			PieChart();
			LineChart() ;
		} catch (NamingException e) {
			logger.log(Level.SEVERE, "an exception was thrown   ", e);
		}
		
	}
	
	  @FXML
	    void impeAction(ActionEvent event) {

	    
	 PrinterJob job = PrinterJob.getPrinterJob();
     HashPrintRequestAttributeSet printRequestSet = new HashPrintRequestAttributeSet();
     
     printRequestSet.add(OrientationRequested.LANDSCAPE);
     
     job.setPrintable(new PrintRectangle());
     if (job.printDialog(printRequestSet)){
        try {
           job.print();
        } catch (PrinterException ex) {
           ex.printStackTrace();
        }}
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
}