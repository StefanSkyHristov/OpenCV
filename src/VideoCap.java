import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

public class VideoCap {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		VideoCapture camera = new VideoCapture(0);
		
		if(!camera.isOpened()) {
			System.out.println("Error");
		}
		else {
			Mat frame = new Mat();
			while(true) {
				if(camera.read(frame)) {
					System.out.println("Frame Obtained");
					System.out.println("Captured Frame width " + frame.width() + " Height " + frame.height());
					Imgcodecs.imwrite("C:\\Users\\Sky\\Desktop\\data\\me.jpg",frame);
					System.out.println("Ok");
					break;
				}
			}
		}
		camera.release();
	}

}
