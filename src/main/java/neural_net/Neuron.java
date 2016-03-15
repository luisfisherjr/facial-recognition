package neural_net;
import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.ArrayList;

public class Neuron {

	// will eventually requires sigmoid function, sumation (rows * weights)
	
	// we need to decide on basic data structures
	private RealMatrix input;
	public RealMatrix getInput() {
		return input;
	}

	public void setInput(RealMatrix input) {
		this.input = input;
	}

	public double[] getWeights() {
		return weights;
	}

	public void setWeights(double[] weights) {
		this.weights = weights;
	}

	public double[] getOutput() {
		return output;
	}

	public void setOutput(double[] output) {
		this.output = output;
	}

	private double[] weights;
	private double[] output;

	// work in progress
	public Neuron() {
	}
	
	// work in progress
	public Neuron(double bias) {
	}

	
}