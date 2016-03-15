
package neural_net;

import java.util.ArrayList;

import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;


public class HiddenLayer implements Layer {
	private ArrayList<Neuron> neurons;
	private RealMatrix input;
	private RealMatrix output;

	// X is the same as input matrix, this is a redundancy
	private RealMatrix x;
	private RealMatrix thetas;
	
	// must set input matrix before populateLayer
	public HiddenLayer() {
		
	}
	
	// can use populateLayer input layer is set as matrix
	public HiddenLayer(RealMatrix matrix){
		input = matrix;
	}
	
	/*
	 * creates a hidden layer with n neurons with f random weights
	 * were f is the number of columns in the input matrix
	 */
	public void populateLayer(int n) {
		
		int featureCount = getInput().getColumnDimension();
		
		Neuron tempNeuron;
		double[] tempWeights;
		ArrayList<Neuron> neurons = new ArrayList<Neuron>();
		
		
		for(int i = 0; i < n; i++) {
			
			tempNeuron = new Neuron();
			tempWeights = new double[featureCount];
			
			// randomize weight
			for (int j = 0; j < featureCount; j++) {
				tempWeights[j] = Math.random();
			}
			
			tempNeuron.setWeights(tempWeights);
			neurons.add(tempNeuron);	
		}
		setListOfNeurons(neurons);
	}
	
	/*
	 * need to test should do     sigmoid ( ( m x n ) dot (n x neuronCOunt) )
	 * i am not 100% sure i know what im doing
	 */
	public void calculate() {
		
		int rows = getListOfNeurons().size();
		int cols = getInput().getColumnDimension();
		
		BlockRealMatrix thetas = new BlockRealMatrix(rows, cols);
		
		// populates thetas matrix from neuron weights list
		for(int i = 0; i < rows;i++) {
			
			thetas.setRow(i, getListOfNeurons().get(i).getWeights());
		}
		
		// confused on what output shape of layer should be
		
		// shape of output matrix is :  neuronInLayer x columns matrix 
		// setOutput(sigmoid(thetas).multiply(getInput()));
		
		// shape of output matrix is : columns x neuronInLayer
		 setOutput(sigmoid(getInput().multiply(thetas.transpose())));
	}
	
	public RealMatrix sigmoid(RealMatrix matrix) {
		
		for(int i = 0; i < matrix.getRowDimension(); i++) {
		
			double[] row = matrix.getRow(i);
			
		for(double d: row) {
			d = 1 / ( 1 + Math.exp(d));
		}
		matrix.setRow(i, row);
		}
		return matrix;
	}

	public RealMatrix getInput() {
		return input;
	}

	public RealMatrix getOutput() {
		return output;
	}

	public ArrayList<Neuron> getListOfNeurons() {
		return neurons;
	}

	public void setInput(RealMatrix input) {
		this.input = input;
		
	}

	public void setOutput(RealMatrix output) {
		this.output = output;
		
	}

	public void setListOfNeurons(ArrayList<Neuron> listOfNeurons) {
		this.neurons = listOfNeurons;
		
	}
	
	public RealMatrix getX() {
		return x;
	}

	public void setX(RealMatrix x) {
		this.x = x;
	}

	public RealMatrix getThetas() {
		return thetas;
	}

	public void setThetas(RealMatrix thetas) {
		this.thetas = thetas;
	}
	
}