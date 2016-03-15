package neural_net;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

/*
 * 
 * 
 */

public class OutputLayer implements Layer {
	private ArrayList<Neuron> neurons;

	private RealMatrix input;
	private RealMatrix output;
	
	private RealMatrix x;
	private RealMatrix thetas;
	
	public OutputLayer(RealMatrix realMatrix) {
		
	}
	
	
	public List<Double> getHypothesis() {
		neurons.get(0);
		return new ArrayList<Double>();
	}

	protected double sigmoid(double z) {
		return 1/(1 + Math.pow(Math.E, z));
	}

	public RealMatrix getInput() {
		return input;
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
		// TODO Auto-generated method stub
		
	}


}
