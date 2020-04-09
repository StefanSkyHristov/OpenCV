import java.awt.image.BufferedImage;

import org.opencv.core.Mat;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ImgDrawerForCamera {
	
	private Image convertedImage;
	
	public boolean convMat2Image(Mat source)
	{
		BufferedImage img2Convert = ImageConverter.convertToImageForCamera(source);
		convertedImage = SwingFXUtils.toFXImage(img2Convert, null);
		return true;
	}
	
	public Image convertMat2Image(Mat source)
	{
		BufferedImage img2Convert = ImageConverter.convertToImageForCamera(source);
		convertedImage = SwingFXUtils.toFXImage(img2Convert, null);
		return convertedImage;
	}
	
	public void drawComponent(GraphicsContext g)
	{
		if(this.convertedImage == null)
		{
			return;
		}
		g.drawImage(this.convertedImage, 10, 10, this.convertedImage.getWidth(), this.convertedImage.getHeight());
		
	}
}
