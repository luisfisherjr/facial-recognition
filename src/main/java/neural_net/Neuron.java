package neural_net;
import org.apache.commons.math3.linear.RealMatrix;

public class Neuron {



	// work in progress
	public Neuron() {
	}
	
	// work in progress
	public Neuron(double bias) {
	}
	
	// will eventually requires sigmoid function, sumation (rows * weights)
	
	// we need to decide on basic data structures
	private RealMatrix input;
	private double[] weights;
	private double[] output;

	
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

}