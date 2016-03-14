package neural_net;
import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.ArrayList;

public class Neuron {

	// will eventually requires sigmoid function, sumation (rows * weights)
	
	// we need to decide on basic data structures
	private RealMatrix input;
	private ArrayList<Double> weights;
	private ArrayList<Double> output;

	// work in progress
	public Neuron() {
	}
	
	// work in progress
	public Neuron(double bias) {
	}

	public RealMatrix getInput() {
		return input;
	}
	
	public ArrayList<Double> getOutput() {
		return output;
	}
	
	public ArrayList<Double> getWeights() {
		return weights;
	}
	
	public void setInput(BlockRealMatrix input) {
		this.input = input;
	}
	
	public void setWeights(ArrayList<Double> weights) {
		this.weights = weights;
	}

	// populates output req's input matrix and weights to be initialized
	public void calculate() {
		
		output = new ArrayList<Double>();
		
		for(int row = 0; row < input.getRowDimension() ; row++) {
			 // output.add( sigmoid(sumation(row * weights)) )
		}
	}
}