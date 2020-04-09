import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;

public class ImageFrame {
	
	private Image image;
	private ImageView img;
	private BorderPane borderLayout;
	
	public ImageFrame()
	{
		this.img = new ImageView(this.image);
		borderLayout = new BorderPane();
		borderLayout.setBorder(Border.EMPTY);
		borderLayout.setCenter(img);
	}
	
	public ImageView updateImage(Image image)
	{
		return new ImageView(image);
	}
	
	/*private ImageView scaleImage(Image image)
	{	
		this.img = new ImageView(image);
		img.setFitHeight(Finals.WINDOW_HEIGHT);
		img.setFitWidth(Finals.WINDOW_WIDTH);
		return img;
	}*/
	
	public Image loadImage(File file)//previously Image
	{
		return new Image("file:///" + file.getAbsolutePath());
	}
}
