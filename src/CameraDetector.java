import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class CameraDetector {
	private CascadeClassifier classifier;
	private Mat colImage;
	private Mat grayImage;
	private MatOfRect detectedFaces;
	private Scalar scalarValues = new Scalar(192,56,22);
	
	public CameraDetector()
	{
		this.classifier = new CascadeClassifier(Finals.CASCADE_CLASSIFIER_EXTENDED);
		this.detectedFaces = new MatOfRect();
		this.colImage = new Mat();
		this.grayImage = new Mat();
	}
	
	public Mat detect(Mat source)
	{
		source.copyTo(grayImage);
		source.copyTo(colImage);
		
		Imgproc.cvtColor(colImage, grayImage, Imgproc.COLOR_BGR2GRAY);
		Imgproc.equalizeHist(grayImage, grayImage);
		
		classifier.detectMultiScale(grayImage, detectedFaces);
		
		displayFaces();
		
		return colImage;
	}
	
	private void displayFaces()
	{
		for(Rect rect: detectedFaces.toArray())
		{
			Imgproc.rectangle(colImage, new Point(rect.x, rect.y), 
					new Point(rect.x + rect.width, rect.y + rect.height), scalarValues,5);
		}
	}
}
