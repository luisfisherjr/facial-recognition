package neural_net;

import java.util.ArrayList;

import org.apache.commons.math3.linear.RealMatrix;

public abstract class Layer {

	
	private RealMatrix input;
	private RealMatrix output;
	private ArrayList<Neuron> listOfNeurons;
	private int numberOfNeuronsInLayer = 0;
	
	public RealMatrix getInput() {
		return input;
	}
	
	public RealMatrix getOutput() {
		return output;
	}
	
	public ArrayList<Neuron> getListOfNeurons() {
		return listOfNeurons;
	}
	
	public int getNumberOfNeuronsInLayer() {
		return numberOfNeuronsInLayer;
	}
	
	public void setInput(RealMatrix input) {
		this.input = input;
	}
	
	public void setOutput(RealMatrix output) {
		this.output = output;
	}
	
	public void setListOfNeurons(ArrayList<Neuron> listOfNeurons) {
		this.listOfNeurons = listOfNeurons;
		setNumberOfNeuronsInLayer(listOfNeurons.size());
		}
	
	public void setNumberOfNeuronsInLayer(int numberOfNeuronsInLayer) {
		this.numberOfNeuronsInLayer = numberOfNeuronsInLayer;
	}
}