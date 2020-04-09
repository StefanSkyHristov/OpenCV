import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import org.opencv.core.Mat;

public class ImageConverter {
	
	public static BufferedImage toImage(Mat source)
	{
		int type = BufferedImage.TYPE_BYTE_GRAY;
		
		if(source.channels() > 1)
		{
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		
		int bufferSize = source.channels()*source.cols()*source.rows();
		
		System.out.println("Heere " + source.cols() + " " + source.rows());
		byte[] bytes = new byte[bufferSize];
		source.get(0, 0, bytes);
		BufferedImage newImage = new BufferedImage(source.cols(), source.rows(), type);
		final byte[]targetPixels = ((DataBufferByte)newImage.getRaster().getDataBuffer()).getData();
		System.arraycopy(bytes, 0, targetPixels, 0 ,targetPixels.length);
		return newImage;
	}
	
	
	public static BufferedImage convertToImageForCamera(Mat src)
	{
		int width = src.width();
		int height = src.height();
		int channels = src.channels();
		
		byte[]srcPixels = new byte[width*height*channels];
		src.get(0, 0, srcPixels);
		
		BufferedImage camImage = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
		final byte[]targetPixels = ((DataBufferByte)camImage.getRaster().getDataBuffer()).getData();
		System.arraycopy(srcPixels, 0, targetPixels, 0 ,srcPixels.length);
		
		return camImage;
	}
	
}
