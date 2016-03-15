package matrix_converter;

import java.util.List;

import image_processor.ImageData;
import image_processor.ImageParser;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class Matrixdemo {
	double[][] matrix = {{1,1}, {2.2}};
	RealMatrix n = MatrixUtils.createRealMatrix(matrix);
	
	@SuppressWarnings("unused")
	public static void main( String[] args )
    {
		//see ImageDemo on usage of ImageParser & ImageData
		
		String filePath = "C:/Users/ImageFolder/faces";
		ImageParser parser = new ImageParser(filePath, true);
		List<ImageData> imageDataList = parser.getImageDataList();
	
		//normalizer will normalize all RGB values.
		Normalizer normalizer = new Normalizer(imageDataList);
		
		//this will convert the 2D array into a RealMatrix
		MatrixConverter convert = new MatrixConverter(imageDataList, true);
		
		//Just if you're curious to see what the values are at which node. MatrixRaw is a 2D array.
		double [][] matrix2 = convert.getMatrixRaw();
		System.out.println(matrix2[40000][0]);
		
		//This is the real matrix.
		RealMatrix matrix = convert.getMatrix();
		
		//this returns an array of 1.0 and 0.0. 1 for the image is a face, 0 for not face.
		System.out.println(convert.getIfFaceList());
    }
}
