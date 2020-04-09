import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import javax.swing.JOptionPane;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MainFrame extends Application {
	
	private Stage window;
	private BorderPane layout;
	private FileChooser fileChooser;
	private Menu fileMenu;
	private Menu aboutMenu;
	private Menu helpMenu;
	private MenuBar menuBar;
	private File file;
	private ImageView imgView;
	private FaceDetection faceDetection;
	private ImageFrame imgFrame;
	private CameraDetector detector;
	private ImgDrawerForCamera drawer;
	
	
	
	public MenuBar createMenuBar()
	{
		file = new File("C:\\Users\\Sky\\Downloads");
		
		//Menu Build
		fileMenu = new Menu("File");
		aboutMenu = new Menu("About");
		helpMenu = new Menu("Help");
		
				
		//This opens up the file chooser,allowing us to load the images from a given directory
		//The functionality of the "Load image" button is actually creating the file chooser and thus allowing 
		//us to load images.
		MenuItem loadImageItem = new MenuItem("Load Image");
		loadImageItem.setOnAction(e -> {
			fileChooser.setTitle("Choose File");
			fileChooser.setInitialDirectory(file);
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JPG files","*.jpg"),new ExtensionFilter("PDF Files","*.pdf"));
			
			MainFrame.this.file = fileChooser.showOpenDialog(window);
			imgView.setImage(imgFrame.loadImage(file));
			
		});
				
		//Here we create the exit menu item with its appropriate functionality
		MenuItem exitMenuItem = new MenuItem("Exit");
		exitMenuItem.setOnAction(e -> {
		int action = JOptionPane.showConfirmDialog(null,Alert.AlertType.WARNING,"Do you wish to Exit?",JOptionPane.YES_NO_OPTION);
			if(action == JOptionPane.YES_OPTION)
			{
				System.gc();
				System.exit(0);
			}	
		});
		
		MenuItem detectFacesMenuItem = new MenuItem("Detect Faces");
		detectFacesMenuItem.setOnAction(e -> {
			//Image imgLoaded = imgFrame.loadImage(file);
			Image detectedImage = MainFrame.this.faceDetection.detectFaces(MainFrame.this.file);
			imgView.setImage(detectedImage);
		});
		
		MenuItem launchVideoMenuItem = new MenuItem("Launch Live Detection");
		launchVideoMenuItem.setOnAction(e -> {
			launchCamera();
			
		});
		
		//Individual menu items assigned to the menu button "File"
		fileMenu.getItems().add(loadImageItem);
		fileMenu.getItems().add(detectFacesMenuItem);
		fileMenu.getItems().add(launchVideoMenuItem);
		fileMenu.getItems().add(exitMenuItem);
				
				
		//Now that we have menu items, we need a menu bar to add them.
		menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu,aboutMenu,helpMenu);	
		
		return menuBar;
	}
	
	public void launchCamera()
	{
		Canvas canvas;
		GraphicsContext g;
		Mat webCam = new Mat();
		VideoCapture videoCapture = new VideoCapture(0);
		
		if(videoCapture.isOpened())
		{
			System.out.println("Camera Launched Successfully!");
			while(true)
			{
				videoCapture.read(webCam);
				
				if(!webCam.empty())
				{
					webCam = detector.detect(webCam);
					Image webCamImg = drawer.convertMat2Image(webCam);
					
					canvas = new Canvas(640,700);
					g = canvas.getGraphicsContext2D();
					g.setStroke(Color.GREEN);
					drawer.drawComponent(g);
					g.drawImage(webCamImg, webCamImg.getWidth(), webCamImg.getHeight());
					
					Group group = new Group(canvas);
					Scene newScene = new Scene(group,640,700);
					Stage videoStage = new Stage();
					videoStage.setScene(newScene);
					videoStage.show();
					
				}
			}
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		window = primaryStage;
		window.setTitle(Finals.APP_NAME);
		
		layout = new BorderPane();
		layout.setTop(createMenuBar());
		this.fileChooser = new FileChooser();
		this.imgFrame = new ImageFrame();
		this.faceDetection = new FaceDetection();
		this.detector = new CameraDetector();
		this.drawer = new ImgDrawerForCamera();
		
		imgView = new ImageView();
		
		layout.setCenter(imgView);
		
		imgView.setFitHeight(Finals.WINDOW_HEIGHT);
		imgView.setFitWidth(Finals.WINDOW_WIDTH);
		
		Scene scene = new Scene(layout,Finals.WINDOW_WIDTH,Finals.WINDOW_HEIGHT);
		window.setScene(scene);
		
		window.show();
	}
	
	public static void main (String[] args) {
		launch(args);
	}

}