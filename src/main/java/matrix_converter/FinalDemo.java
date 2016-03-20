package matrix_converter;

import image_processor.ImageData;
import image_processor.ImageParser;

import java.util.List;

import neural_net.NeuralNet;

import org.apache.commons.math3.linear.RealMatrix;

public class FinalDemo {
	
	
	public static void main( String[] args )
    {
		MatrixConverter convert = initialize();
		RealMatrix matrix = convert.getMatrix();
		List<Double> yRaw = convert.getIfFaceList();
		double[]y = new double[yRaw.size()];
		for(int i = 0; i < yRaw.size(); i++){
			y[i] = yRaw.get(i);
		}
		int[] neurons = {25,5};
		NeuralNet n = new NeuralNet(matrix, neurons, y, 0.01, 0.02, 0.01);
		n.train();
		n.printMatrix(n.getLayers().get(2).getA());
		System.out.println(yRaw);
		
    }
	
	private static MatrixConverter initialize(){
		String filePath = "C:/Users/Kuo-Cheng/Desktop/test images/face/TestImages";
		ImageParser parser = new ImageParser(filePath, true);
		List<ImageData> imageDataList = parser.getImageDataList();
		
		String filePath2 = "C:/Users/Kuo-Cheng/Desktop/test images/notface";
		ImageParser parser2 = new ImageParser(filePath2, false);
		List<ImageData> imageDataList2 = parser2.getImageDataList();
		
		imageDataList.addAll(imageDataList2);
		Normalizer normalizer = new Normalizer(imageDataList);
		MatrixConverter convert = new MatrixConverter(imageDataList, true);
		return convert;
	}
}
