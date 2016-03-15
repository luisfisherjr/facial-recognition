package matrix_converter;

import image_processor.ImageData;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class MatrixConverter {
	
	private double[][] matrixRaw;
	private List<Double> isFaceList = new ArrayList<Double>();
	private RealMatrix matrix;
	
	public MatrixConverter(List<ImageData> imageDataIn, boolean bias){
		
		System.out.println(imageDataIn.get(0).getRGB().size() + " features detected.");
		System.out.println(imageDataIn.size() + " training examples detected.");
		
		if (!bias){
			matrixRaw = new double[imageDataIn.get(0).getRGB().size()][imageDataIn.size()];
			record(imageDataIn);
		}
		else{
			System.out.println("BIAS row will be generated.");
			matrixRaw = new double[imageDataIn.get(0).getRGB().size() + 1][imageDataIn.size()];
			recordBias(imageDataIn);
		}
		matrix = MatrixUtils.createRealMatrix(matrixRaw);
		recordBoolean(imageDataIn);
	}

	private void recordBoolean(List<ImageData> imageDataIn){
		for (ImageData image: imageDataIn){
			double value = image.getIsFace();
			isFaceList.add(value);
		}
	}
	
	private void recordBias(List<ImageData> imageDataIn){
		System.out.println("Converting process initiated...");
		for(int row = 0; row < imageDataIn.get(0).getRGB().size() + 1; row++){
			for(int col = 0; col < imageDataIn.size(); col++){
				double value;
				if (row == 0){
					value = 1.0;
				}
				else{
					value = (imageDataIn.get(col).getRGB().get(row - 1));
				}
				matrixRaw[row][col] = value;
			}
		}
	}
	
	private void record(List<ImageData> imageDataIn){
		System.out.println("Converting process initiated...");
		for(int row = 0; row < imageDataIn.get(0).getRGB().size(); row++){
			for(int col = 0; col < imageDataIn.size(); col++){
				double value = (imageDataIn.get(col).getRGB().get(row));
				matrixRaw[row][col] = value;
			}
		}
	}
	public double[][] getMatrixRaw() {
		return matrixRaw;
	}

	public void setMatrixRaw(double[][] matrixRaw) {
		this.matrixRaw = matrixRaw;
	}

	public List<Double> getIfFaceList() {
		return isFaceList;
	}

	public void setIfFaceList(List<Double> ifFaceList) {
		this.isFaceList = ifFaceList;
	}

	public RealMatrix getMatrix() {
		return matrix;
	}

	public void setMatrix(RealMatrix matrix) {
		this.matrix = matrix;
	}
}
