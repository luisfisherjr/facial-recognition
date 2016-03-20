package neural_net;

import java.security.InvalidParameterException;
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
	
	private double lambda;
	
	private double alpha;
	
	private double tolerance;
	
	private ArrayList<RealMatrix> oldThetas;
	
	private RealMatrix oldHyp;
	
	private RealMatrix[] trainCol;
	
	double[] trainingYAlt;
	
	/*
	 *Parameters:
	 *trainingX: Feature Matrix
	 *hiddenCount: Number of Hidden Layers
	 *neuronsPerHiddenLayer: Number of Neurons per hidden layer
	 *trainingY: Labels
	 */
	public NeuralNet(RealMatrix trainingX, int[] neuronsPerHiddenLayer, double[] trainingY, double lambda, double alpha, double tolerance) {
		
		trainCol = new RealMatrix[trainingX.getColumnDimension()];
		trainingYAlt = trainingY;
		
		RealMatrix colTemp;
		for(int i = 0; i < trainingX.getColumnDimension(); i++) {
			colTemp = new BlockRealMatrix(trainingX.getRowDimension(), 1);
			colTemp.setColumn(0, trainingX.getColumn(i));
			trainCol[i] = colTemp;
		}
		
		this.tolerance = tolerance;
		this.alpha = alpha;
		this.lambda = lambda;
		if (neuronsPerHiddenLayer.length != neuronsPerHiddenLayer.length) System.exit(1);
		
		double[][] in = new double[1][trainingY.length];
		in[0] = trainingY;

		this.trainingY = MatrixUtils.createRealMatrix(in);
		
		layers = new ArrayList<Layer>();
		bigDeltas = new ArrayList<RealMatrix>();
	
		int rows;
		int cols;
		
		for (int i = 0; i <= neuronsPerHiddenLayer.length; i++ ) {
			if (i == 0) {
				layers.add(new Layer(trainCol[0], neuronsPerHiddenLayer[i], true));
			}
			else if ((i > 0)&&( i < neuronsPerHiddenLayer.length)) {
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
	
	public void forwardPropagation(int colIndex) {
		
		for (Layer layer: layers){
			double[][] layerMatrix = layer.getThetas().getData();
			for(int i = 0; i < layer.getListOfNeurons().size(); i++){
				layer.getListOfNeurons().get(i).setWeights(layerMatrix[i]);
			}
		}
		
		
		layers.get(0).setX(trainCol[colIndex]);
		layers.get(0).calculate();
			
		for (int i = 1; i < layers.size(); i++) {
			layers.get(i).setX(layers.get(i - 1).getA());
			layers.get(i).calculate();
		}
	}
	
	public void backwardPropagation(int colIndex) {
		
		int L = layers.size() - 1;
		
		double[][] delta;
		
		// used for testing
		// int count = 0;

		for(int index = L; index >= 0; index--) {
			
			if (index == L ) {                                                
				layers.get(index).setDelta(layers.get(index).getA().scalarAdd(trainingYAlt[colIndex] * - 1));
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
	public void train() {
		
		oldThetas = new ArrayList<RealMatrix>();
		boolean go = true;
		int counter = 0;
		while (go){
			
			for (int i = 0; i < layers.size(); i++){
				oldThetas.add(layers.get(i).getThetas().copy());
			}
			oldHyp = layers.get(layers.size() - 1).getA().copy();
			
			for(RealMatrix m: bigDeltas) {
				m.scalarMultiply(0);
			}
			
			for(int i = 0; i < trainCol.length; i++) {
				
				forwardPropagation(i);
				backwardPropagation(i);
			}
			
			gradientD();
			
			
			go = converged();
			counter++;
		}
		System.out.println("Iterations -  " + counter);
		
	}

	public boolean converged(){
		RealMatrix m = oldHyp.subtract(layers.get(layers.size() - 1).getA());
		double[][] matrixForm = m.getData();
		for (int i = 0; i < m.getRowDimension(); i++){
			for (int j = 0; j < m.getColumnDimension(); j++){
				Math.pow(matrixForm[i][j], 2);
			}
		}
		m = new BlockRealMatrix(matrixForm);
		double divider = (2*layers.get(layers.size() - 1).getA().getColumnDimension());
		m.scalarMultiply(divider);
		double[] sumSquared = m.getRow(0);
		double sum= 0.0;
		for (double value: sumSquared){
			sum += value;
		}
		
		return sum > tolerance;
		
	}
	
	
	public void regularizeBigDeltas() {
		for (int i = 1; i < layers.size(); i++) {
			RealMatrix lambdaThetas = layers.get(i).getThetas().scalarMultiply(lambda);
			lambdaThetas.setRow(0, layers.get(i).getThetas().getRow(0));
			bigDeltas.set(i, bigDeltas.get(i).add(lambdaThetas)); 
		}
	}
	
	
	public void gradientD(){
		
		regularizeBigDeltas();
		for (int i = 1; i < layers.size(); i++){
			//System.out.println("Thetas before...");
			//printThetas(i);
			RealMatrix originalT = layers.get(i).getThetas();
			RealMatrix bigDelta = bigDeltas.get(i);
			RealMatrix newThetas = originalT.subtract(bigDelta.scalarMultiply(alpha));
			
			if (i == layers.size()-1){
				continue;
			}
			else{
				double[] rowOnes = newThetas.getRow(0);
				for(int j = 0; j < rowOnes.length; j++){
					rowOnes[j] = 1;
				}
				
				newThetas.setRow(0, rowOnes);
				layers.get(i).setThetas(newThetas);
			}
		}
		/*
		for (int i = 0; i < layers.size(); i++){
			printThetas(i);
		}
		*/
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
		//forwardPropagation();
		
		double[] labels = layers.get(layers.size() - 1).getA().getRow(0);
		
		for(double d: labels) {
			if (d > .5) d = 1;
			else d = 0;
		}
		return labels;
	}
	
	public double[] precisionScore(double[] yTraining, double[] yPredicted) {
		if (yTraining.length != yPredicted.length) {
			System.out.println("NeuralNet#precisionScore bad double arrays given");
			System.out.println("yTraining: " + yTraining.length);
			System.out.println("yPredicted: " + yPredicted.length);
			throw new InvalidParameterException();
		}
		
		double[] result  = new double[yTraining.length];
		for (int i = 0; i < yTraining.length; i++)
			result[i] = yTraining[i] - yPredicted[i];
		return result;
	}	
	
	public void gradientCheck(double epsilon) {
		for (int i = 0; i < layers.size(); i++) {
			Layer l = layers.get(i);
			
			RealMatrix thetaPlus = l.getThetas().scalarAdd(epsilon);
			
			RealMatrix thetaMinus = l.getThetas().scalarAdd(-epsilon);
			
			RealMatrix gradApprox = (j(thetaPlus)
										.subtract(j(thetaMinus))
										.scalarMultiply(1/2*epsilon));
			
			RealMatrix bigDelta = bigDeltas.get(i);
			
			if (gradApprox.getRowDimension() != bigDelta.getRowDimension()
					|| gradApprox.getColumnDimension() != bigDelta.getColumnDimension()) {
				System.out.println("gradApprox(" 
					+ gradApprox.getRowDimension() + ","
					+ gradApprox.getColumnDimension() + ")");
				System.out.println("bigDelta(" 
						+ bigDelta.getRowDimension() + ","
						+ bigDelta.getColumnDimension() + ")");
				System.out.println("Gradient Check dimension mismatch");
				throw new InvalidParameterException();
			}
			
			
			//testing printing
//			System.out.println("gradApprox(" 
//					+ gradApprox.getRowDimension() + ","
//					+ gradApprox.getColumnDimension() + ")");
//			System.out.println("bigDelta(" 
//						+ bigDelta.getRowDimension() + ","
//						+ bigDelta.getColumnDimension() + ")");			
//			System.out.println(gradApprox);
//			System.out.println(bigDelta);
			
			//result printing
			System.out.println(gradApprox.equals(bigDelta) ? 
					"gradient checking pass" : "gradient checking fail");
		}
		
		
	}
	
	public RealMatrix j(RealMatrix x) {
		RealMatrix term1 = (log(sigmoid(x)));
//		System.out.println(term1);
		
		
		return x;
	}
	
	public RealMatrix sigmoid(RealMatrix matrix) {
		for(int i = 0; i < matrix.getRowDimension(); i++) {
			double[] row = matrix.getRow(i);
			for(double d: row)
				d = 1 / ( 1 + Math.exp(d));	
			matrix.setRow(i, row);
		}
		return matrix;
	}
	
	public RealMatrix log(RealMatrix matrix) {
		for(int i = 0; i < matrix.getRowDimension(); i++) {
			double[] row = matrix.getRow(i);
			for(double d: row)
				d = Math.log10(d);	
			matrix.setRow(i, row);
		}
		return matrix;
	}
	
	public RealMatrix generateMatrixOfOnes(int row, int col) {
		double[][] array = new double[row][col];
		for (int r = 0; r < row; r++)
			for (int c = 0; c < col; c++)
				array[r][c] = 1;
		return new BlockRealMatrix(array);
	}
	
	// below functions used for testing
	
	
	public void printMatrix(RealMatrix matrix) {
		
		System.out.println("matrix: ");
		int rowCount = matrix.getRowDimension();
		int colCount = matrix.getColumnDimension();
		System.out.println("[");
		for (int i = 0; i < rowCount; i++) {
			System.out.print("[");
			for (double num: matrix.getRow(i)) {
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
	
	public ArrayList<Layer> getLayers() {
		return layers;
	}
}