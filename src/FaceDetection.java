import java.awt.image.BufferedImage;
import java.io.File;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FaceDetection {
	private CascadeClassifier classifier;
	private Scalar colourScalar = new Scalar(192,56,22);
	
	public FaceDetection()
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		//this.classifier = new CascadeClassifier(Finals.CASCADE_CLASSIFIER);
		this.classifier = new CascadeClassifier(Finals.CASCADE_CLASSIFIER_EXTENDED);
	}
	
	public Image detectFaces(File file)
	{
		Mat imageMat = Imgcodecs.imread(file.getAbsolutePath(), Imgcodecs.IMREAD_COLOR);
		
		MatOfRect detectedFaces = new MatOfRect();
		classifier.detectMultiScale(imageMat, detectedFaces,1.23,3,10,new Size(50,50),new Size(800,800));

		for(Rect faces: detectedFaces.toArray())
		{
			Imgproc.rectangle(imageMat, new Point(faces.x,faces.y), 
					new Point(faces.x + faces.width,faces.y + faces.height), colourScalar, 5);
		}
		Image img = convertMatToImage(imageMat);
		return img;
		
	}
	
	private Image convertMatToImage(Mat image)
	{
		BufferedImage imageToConvert = ImageConverter.toImage(image);
		Image imgRet = SwingFXUtils.toFXImage(imageToConvert, null);
		return imgRet;
	}
}
