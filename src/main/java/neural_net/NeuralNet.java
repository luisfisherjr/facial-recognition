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
	
	/*
	 *Parameters:
	 *trainingX: Feature Matrix
	 *hiddenCount: Number of Hidden Layers
	 *neuronsPerHiddenLayer: Number of Neurons per hidden layer
	 *trainingY: Labels
	 */
	public NeuralNet(RealMatrix trainingX, int hiddenCount, int[] neuronsPerHiddenLayer, double[] trainingY) {
		
		if (neuronsPerHiddenLayer.length != hiddenCount) System.exit(1);
		
		double[][] in = new double[1][trainingY.length];
		in[0] = trainingY;

		this.trainingY = MatrixUtils.createRealMatrix(in);
		
		hiddenLayers = new ArrayList<Layer>();
		
		int i = 0;
		
	
		for (; i < hiddenCount; i++ ) {
			if (i == 0) hiddenLayers.add(new Layer(trainingX, neuronsPerHiddenLayer[i], true));
			else hiddenLayers.add(new Layer(hiddenLayers.get(i - 1).getA(), neuronsPerHiddenLayer[i], true));
		}
		hiddenLayers.add(new Layer(hiddenLayers.get(i - 1).getA(), 1, false));		
		
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
		
		int L = hiddenLayers.size() - 1;
		
		double[][] delta;

		for(int index = L, count = 0; index >= 0; index--, count++) {
			
			if (index == L ) {                                                
				hiddenLayers.get(index).setDelta(hiddenLayers.get(index).getA().subtract(trainingY));
				
				// for testing
				System.out.println("L");
				printDimensionA(index);
				printDimensionY();
				printDimensionDelta(index);
			}
			else {
				
				// for testing
				System.out.println();
				System.out.println("L - " + count);
				printDimensionThetas(index + 1);
				printDimensionDelta(index + 1);
				printDimensionA(index);
				
				
				/*changing getThetas.tranpose() to getThetas() fixed it @.@ not sure why and I'm not sure
				 * it is a legit fix*/
				delta = (hiddenLayers.get(index + 1).getThetas()).
						multiply(hiddenLayers.get(index + 1).getDelta()).getData();
				                     
				
				
				double[][] a = hiddenLayers.get(index).getA().getData();
				
				for(int i = 0; i < delta.length; i++) {
					for(int j = 0; j < delta[0].length; j++) {
						delta[i][j] = delta[i][j] * a[i][j] * (1 - a[i][j]);
					}
				}
				
				hiddenLayers.get(index).setDelta(MatrixUtils.createRealMatrix(delta));
				
				// for testing
				printDimensionDelta(index);
				
				// I am not sure how bigDelta is suppose to accumulate need help
				
				if (index == (L - 1)) {
					bigDelta = hiddenLayers.get(index + 1).getDelta().
							multiply(hiddenLayers.get(index).getA().transpose());			
				}
				if (index < (L - 1)) {
				//	bigDelta = bigDelta.add(hiddenLayers.get(index + 1).getDelta().
				//			multiply(hiddenLayers.get(index).getA().transpose()));				
				}
			}	
		}
	}

	
	
	/*
	 *Trains Neural Net with given Features & Labels
	 *Parameters:
	 *passes: Max number of loops
	 *tolerance: How far difference is from actual results
	 *Note: work in progress
	 */
	public void train(int passes, double tolerance) {
		
		double totalDifference = 1;
		
		for(int count = 0; (count < passes) && (tolerance <= totalDifference); count++) {
		
		backwardPropagation();
		
		// just place holder
		totalDifference -= .001;
		
		forwardPropagation();
		}
	}

	/*
	 *Trains Neural Net with given Features & Labels
	 *Parameters: 
	 *x: test set of features
	 *Return: double[] of predicted Labels
	 *Note: current threshold is .5
	 */
	public double[] predict(RealMatrix x) {
		
		getHiddenLayers().get(0).setX(x);
		forwardPropagation();
		
		double[] labels = getHiddenLayers().get(getHiddenLayers().size() - 1).getA().getRow(0);
		
		for(double d: labels) {
			if (d > .5) d = 1;
			else d = 0;
		}
		return labels;
	}
	
	public void precisionScore(double[] yTraining, double[] yPredicted) {
		// basically just compare the two
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
	
	public void printDimensionA(int index) {
		System.out.println("a^" + index + " : (" + hiddenLayers.get(index).getA().getRowDimension() + 
				", " + hiddenLayers.get(index).getA().getColumnDimension() + ")");
	}
	
	public void printDimensionThetas(int index) {
		System.out.println("t^" + index + " : (" + hiddenLayers.get(index).getThetas().getRowDimension() + 
				", " + hiddenLayers.get(index).getThetas().getColumnDimension() + ")");
	}
	
	public void printDimensionDelta(int index) {
		System.out.println("d^" + index + " : (" + hiddenLayers.get(index).getDelta().getRowDimension() + 
				", " + hiddenLayers.get(index).getDelta().getColumnDimension() + ")");
	}
	
	public void printDimensionX(int index) {
		System.out.println("x^" + index + " : (" + hiddenLayers.get(index).getX().getRowDimension() + 
				", " + hiddenLayers.get(index).getX().getColumnDimension() + ")");
	}
	
	public void printDimensionY() {
		System.out.println("y   : (" + trainingY.getRowDimension() + 
				", " + trainingY.getColumnDimension() + ")");
	}
}