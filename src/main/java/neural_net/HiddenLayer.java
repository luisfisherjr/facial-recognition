
package neural_net;

import java.util.ArrayList;

import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;


public class HiddenLayer implements Layer {
	private ArrayList<Neuron> neurons;
	
	// alter
	public HiddenLayer() {
		
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
		
		// populates thetas from neuron weights list
		for(int i = 0; i < rows;i++) {
			
			thetas.setRow(i, getListOfNeurons().get(i).getWeights());
		}
		
		// confused on what output shape of layer should be
		
		// shape of output matrix is :  neuronInLayer x columns matrix 
		setOutput(sigmoid(thetas).multiply(getInput()));
		
		// shape of output matrix is : columns x neuronInLayer
		// setOutput(sigmoid(getInput().multiply(thetas.transpose())));
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
	
	// alter
	public void printLayer(ArrayList<HiddenLayer> listOfHiddenLayer) {
	}

	public ArrayList<Neuron> getNeurons() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setNeurons(ArrayList<Neuron> listOfNeurons) {
		// TODO Auto-generated method stub
		
	}

	public OutputLayer generateOutputLayer() {
		// TODO Auto-generated method stub
		return null;
	}

	public RealMatrix getInput() {
		// TODO Auto-generated method stub
		return null;
	}

	public RealMatrix getOutput() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Neuron> getListOfNeurons() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setInput(RealMatrix input) {
		// TODO Auto-generated method stub
		
	}

	public void setOutput(RealMatrix output) {
		// TODO Auto-generated method stub
		
	}

	public void setListOfNeurons(ArrayList<Neuron> listOfNeurons) {
		// TODO Auto-generated method stub
		
	}
}
