package neural_net;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

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
	
	/*
	 *Parameters:
	 *trainingX: Feature Matrix
	 *hiddenCount: Number of Hidden Layers
	 *neuronsPerHiddenLayer: Number of Neurons per hidden layer
	 *trainingY: Labels
	 */
	public NeuralNet(RealMatrix trainingX, int[] neuronsPerHiddenLayer, double[] trainingY, double lambda, double alpha, double tolerance) {
		
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
				layers.add(new Layer(trainingX, neuronsPerHiddenLayer[i], true));
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
	
	public void forwardPropagation() {
		
		for (Layer layer: layers){
			double[][] layerMatrix = layer.getThetas().getData();
			for(int i = 0; i < layer.getListOfNeurons().size(); i++){
				layer.getListOfNeurons().get(i).setWeights(layerMatrix[i]);
			}
		}
		
		
		
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
	public void train() {
		
		oldThetas = new ArrayList<RealMatrix>();
		boolean go = true;
		int counter = 0;
		while (go){
			
			for (int i = 0; i < layers.size(); i++){
				oldThetas.add(layers.get(i).getThetas().copy());
			}
			oldHyp = layers.get(layers.size() - 1).getA().copy();
			backwardPropagation();
		
			// just place holder
		
			gradientD();
			
			forwardPropagation();
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
	
	public List<Double> hypothesis() {
		Layer last = layers.get(layers.size()-1);
		List<Double> integers = new ArrayList<Double>();
		double[] aInside = last.getA().getRow(0);
		
		for(int i = 0; i < aInside.length; i++) {
			if (aInside[i] > 0) integers.add(1.0);
			else integers.add(0.0);
		}
		return integers;
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
			
			RealMatrix aPlus = l.getA().scalarAdd(epsilon);
			
			RealMatrix aMinus = l.getA().scalarAdd(-epsilon);
			
			RealMatrix gradApprox = (j(aPlus, l.getThetas())
										.subtract(j(aMinus, l.getThetas()))
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
	
	public RealMatrix j(RealMatrix a, RealMatrix thetas) {
		RealMatrix result = new BlockRealMatrix(thetas.getRowDimension(), thetas.getColumnDimension());
		
		RealMatrix x = layers.get(0).getX();
		RealMatrix y = trainingY;
		
		
		int m = a.getRowDimension();
		int k = y.getColumnDimension();
		
		System.out.println(m);
		System.out.println(k);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < k; j++){
				double yEntry = y.getEntry(j, 0);
				double xEntry = x.getEntry(j, i);
					
				double left = yEntry * Math.log(xEntry);
				
				double right = (1-yEntry) * Math.log(1 - xEntry);
				
				double resultEntry = left + right; // still needs regularization
				
				result.setEntry(j, i, resultEntry);
				
			}
		}
		
		return result;
	}
	
	public void regularizeBigDeltas() {
		for (int i = 1; i < layers.size(); i++) {
			double[][] tempMatrix = layers.get(i).getThetas().getData();
			for (int j = 0; j < layers.get(i).getThetas().getRowDimension(); j++){
				for (int k = 0; k < layers.get(i).getThetas().getColumnDimension(); k++){
					double value = tempMatrix[j][k];
					tempMatrix[j][k] = value * value;
				}
			}
			RealMatrix a = new BlockRealMatrix(tempMatrix);
			a.scalarMultiply(lambda/(2*layers.get(0).getThetas().getRowDimension()));
			RealMatrix lambdaThetas = a;
			lambdaThetas.setRow(0, layers.get(i).getThetas().getRow(0));
			bigDeltas.set(i, bigDeltas.get(i).add(lambdaThetas)); 
		}
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