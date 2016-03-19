package neural_net;

import java.util.ArrayList;

import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class NeuralNet {
	
	private RealMatrix trainingY;
	
	// accumulator to be used in cost function?
	private ArrayList<RealMatrix> bigDeltas;
	
	// hidden layers and output layer
	private ArrayList<Layer> layers;
	
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
		
		layers = new ArrayList<Layer>();
		bigDeltas = new ArrayList<RealMatrix>();
	
		int rows;
		int cols;
		
		for (int i = 0; i <= hiddenCount; i++ ) {
			if (i == 0) {
				layers.add(new Layer(trainingX, neuronsPerHiddenLayer[i], true));
			}
			else if ((i > 0)&&( i < hiddenCount)) {
				layers.add(new Layer(layers.get(i - 1).getA(), neuronsPerHiddenLayer[i], true));
			}
			else {
				layers.add(new Layer(layers.get(i - 1).getA(), 1, false));
			}
			
			
			// Big D are offest by 1 first bigD is empty, skip it!!
			// it is input layer does not had value to set
			
			rows = layers.get(i).getThetas().getRowDimension();
			cols = layers.get(i).getThetas().getColumnDimension();
			bigDeltas.add(new BlockRealMatrix(rows, cols));
		}	
	}
	
	public void forwardPropagation() {
		
		layers.get(0).calculate();
			
		for (int i = 1; i < layers.size(); i++) {
			layers.get(i).setX(layers.get(i - 1).getA());
			layers.get(i).calculate();
		}
	}
	
	public void backwardPropagation() {
		
		int L = layers.size() - 1;
		
		double[][] delta;
		
		// used for testing
		// int count = 0;

		for(int index = L; index >= 0; index--) {
			
			if (index == L ) {                                                
				layers.get(index).setDelta(layers.get(index).getA().subtract(trainingY));
				/*
				 
				// for testing
				count++
				System.out.println("L");
				printDimensionA(index);
				printDimensionY();
				printDimensionThetas(index);
				printDimensionDelta(index);
				printDimensionBigDelta(index);
				*/
				
			}
			else {
				
				/*
				// for testing
				System.out.println();
				System.out.println("L - " + count);
				printDimensionA(index);
				printDimensionDelta(index + 1);
				System.out.println();
				printDimensionThetas(index);
				printDimensionBigDelta(index);
				*/
			
				delta = (layers.get(index + 1).getThetas()).transpose().
						multiply(layers.get(index + 1).getDelta()).getData();

				double[][] a = layers.get(index).getA().getData();
				
				for(int i = 0; i < delta.length; i++) {
					for(int j = 0; j < delta[0].length; j++) {
						delta[i][j] = delta[i][j] * a[i][j] * (1 - a[i][j]);
					}
				}
				
				layers.get(index).setDelta(MatrixUtils.createRealMatrix(delta));
				
				if (index <= (L - 1)) {
				
					RealMatrix temp = layers.get(index + 1).getDelta().
							multiply(layers.get(index).getA().transpose());
					
					
					// used for testing
					// System.out.print("testing: " + temp.getRowDimension() +","+ temp.getColumnDimension());
					
					bigDeltas.get(index + 1).add(temp);
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
		
		layers.get(0).setX(x);
		forwardPropagation();
		
		double[] labels = layers.get(layers.size() - 1).getA().getRow(0);
		
		for(double d: labels) {
			if (d > .5) d = 1;
			else d = 0;
		}
		return labels;
	}
	
	public void precisionScore(double[] yTraining, double[] yPredicted) {
		// basically just compare the two
	}	
	
	// below functions used for testing
	
	
	public void printA(int index) {
		System.out.println("matrix out: ");
		int rowCount = 0;
		int colCount = 0;
		System.out.println("[");
		for (double[] row: layers.get(index).getA().getData()) {
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
		for (double[] row: layers.get(index).getDelta().getData()) {
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
		for (double[] row: layers.get(index).getThetas().getData()) {
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
		System.out.println("a^" + index + " : (" + layers.get(index).getA().getRowDimension() + 
				", " + layers.get(index).getA().getColumnDimension() + ")");
	}
	
	public void printDimensionThetas(int index) {
		System.out.println("t^" + index + " : (" + layers.get(index).getThetas().getRowDimension() + 
				", " + layers.get(index).getThetas().getColumnDimension() + ")");
	}
	
	public void printDimensionBigDelta(int index) {
		System.out.println("D^" + index + " : (" + bigDeltas.get(index).getRowDimension() + 
				", " + bigDeltas.get(index).getColumnDimension() + ")");
	}
	
	public void printDimensionDelta(int index) {
		System.out.println("d^" + index + " : (" + layers.get(index).getDelta().getRowDimension() + 
				", " + layers.get(index).getDelta().getColumnDimension() + ")");
	}
	
	public void printDimensionX(int index) {
		System.out.println("x^" + index + " : (" + layers.get(index).getX().getRowDimension() + 
				", " + layers.get(index).getX().getColumnDimension() + ")");
	}
	
	public void printDimensionY() {
		System.out.println("y   : (" + trainingY.getRowDimension() + 
				", " + trainingY.getColumnDimension() + ")");
	}
}