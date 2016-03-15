package neural_net;
import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.ArrayList;

public class Neuron {

	// thetas.setRow requires double[] so saved weights as double[]
	// currently the input and output is stored in the layer
	// the weights "thetas" are the only things being used at the Neuron level
	// calculation is done at layer level with layer storing the input and output
	
	private RealMatrix input;
	private double[] weights;
	private double[] output;

	// work in progress
	public Neuron() {
	}
	
	// work in progress
	public Neuron(double bias) {
	}

	public RealMatrix getInput() {
		return input;
	}
	
	public double[] getOutput() {
		return output;
	}
	
	public double[] getWeights() {
		return weights;
	}
	
	public void setInput(BlockRealMatrix input) {
		this.input = input;
	}
	
	public void setWeights(double[] weights) {
		this.weights = weights;
	}
	
	public void setOutput(double[] output) {
		this.output = output;
	}
}