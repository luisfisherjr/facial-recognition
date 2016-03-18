package neural_net;

import java.util.ArrayList;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class NeuralNet {
	
	// do not need trainingX it is already
	// in the first layer once net is created
	// private RealMatrix trainingX;
	
	private RealMatrix trainingY;
	
	// accumulator
	private RealMatrix bigDelta;
	
	// hidden layers and output layer
	private ArrayList<Layer> hiddenLayers;
	
	// must have atleast 1 hidden layer
	// later take in a int[] of length[hiddenCount] with values = number of neurons per layer
	public NeuralNet(RealMatrix trainingX, int hiddenCount, double[] trainingY) {
		
		// this.trainingX = trainingX;
		double[][] in = new double[1][trainingY.length];
		in[0] = trainingY;

		this.trainingY = MatrixUtils.createRealMatrix(in);
		
		hiddenLayers = new ArrayList<Layer>();
		
		int i = 0;
		for (; i < hiddenCount; i++ ) {
			if (i == 0) hiddenLayers.add(new Layer(trainingX, 5, true));
			else hiddenLayers.add(new Layer(hiddenLayers.get(i - 1).getA(), 5, true));
		}
		hiddenLayers.add(new Layer(hiddenLayers.get(i - 1).getA(), 1, false));
		
		train();
	}
	
	public ArrayList<Layer> getHiddenLayers() {
		return hiddenLayers;
	}

	public void setHiddenLayers(ArrayList<Layer> hiddenLayers) {
		this.hiddenLayers = hiddenLayers;
	}
	
	public void forwardPropagation() {
		
		hiddenLayers.get(0).calculate();
			
		for (int i = 1; i < hiddenLayers.size(); i++) {
			hiddenLayers.get(i).setX(hiddenLayers.get(i - 1).getA());
			hiddenLayers.get(i).calculate();
		}
	}
	
	public void backwardPropagation() {
		
		int layerSize = hiddenLayers.size() - 1;
		
		double[][] delta;
		
		for(int index = layerSize; index >= 0; index--) {
			
			if (index == layerSize ) {
				                                                 // break point trying to tranpose        @.@
				hiddenLayers.get(layerSize).setDelta(hiddenLayers.get(index).getA().subtract(trainingY).transpose());
			}
			else {
				printThetas(index);
				printD(index + 1);
				                                         
				delta = hiddenLayers.get(index).getThetas().transpose().
						multiply(hiddenLayers.get(index + 1).getDelta()).getData();
				                                          
				double[][] a = hiddenLayers.get(index).getA().getData();
				
				for(int i = 0; i < delta[0].length; i++) {
					for(int j = 0; j < delta.length; j++) {
						delta[j][i] = delta[j][i] * a[j][i] * (1 - a[j][i]);
					}
				}
				
				hiddenLayers.get(index).setDelta(MatrixUtils.createRealMatrix(delta));
				
				if (index == (layerSize - 1)) {                       // transposed @.@
					bigDelta = hiddenLayers.get(index + 1).getDelta().transpose().
							multiply(hiddenLayers.get(index).getA().transpose());
							
							
				}
				if (index < (layerSize - 1)) {
					bigDelta = bigDelta.add(hiddenLayers.get(index + 1).getDelta().
							multiply(hiddenLayers.get(index).getA().transpose()));				
				}
				
			}	
		}
	}

	public void train() {	
	}

	public void predict(RealMatrix x) {
	}
	
	public void printA(int index) {
		System.out.println("matrix out: ");
		int rowCount = 0;
		int colCount = 0;
		System.out.println("[");
		for (double[] row: this.getHiddenLayers().
								get(index).getA().getData()) {
			rowCount++;
			System.out.print("[");
		for(double num: row) {
			if (rowCount == 1) {
				colCount++;
			}
			System.out.print(num + " ");
			}
		
		System.out.println("]");
		}
		
		System.out.println("]");
		System.out.println("Shape of output: " + rowCount + " x " + colCount);

	}
	
	public void printD(int index) {
		System.out.println("matrix out: ");
		int rowCount = 0;
		int colCount = 0;
		System.out.println("[");
		for (double[] row: this.getHiddenLayers().
								get(index).getDelta().getData()) {
			rowCount++;
			System.out.print("[");
		for(double num: row) {
			if (rowCount == 1) {
				colCount++;
			}
			System.out.print(num + " ");
			}
		
		System.out.println("]");
		}
		
		System.out.println("]");
		System.out.println("Shape of output: " + rowCount + " x " + colCount);

	}
	
	public void printThetas(int index) {
		System.out.println("matrix out: ");
		int rowCount = 0;
		int colCount = 0;
		System.out.println("[");
		for (double[] row: this.getHiddenLayers().
								get(index).getThetas().getData()) {
			rowCount++;
			System.out.print("[");
		for(double num: row) {
			if (rowCount == 1) {
				colCount++;
			}
			System.out.print(num + " ");
			}
		
		System.out.println("]");
		}
		
		System.out.println("]");
		System.out.println("Shape of output: " + rowCount + " x " + colCount);

	}
}