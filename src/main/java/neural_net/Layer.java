package neural_net;

import java.util.ArrayList;

public abstract class Layer {

	// should we pass the matrix to the layer?
	// that is basically the way it is done in class instead of making a bunch of copies..
	
	private ArrayList<Neuron> listOfNeurons;
	private int numberOfNeuronsInLayer;
	
	public ArrayList<Neuron> getListOfNeurons() {
		return listOfNeurons;
	}
	
	public void setListOfNeurons(ArrayList<Neuron> listOfNeurons) {
		this.listOfNeurons = listOfNeurons;
	}
	
	public int getNumberOfNeuronsInLayer() {
		return numberOfNeuronsInLayer;
	}
	
	public void setNumberOfNeuronsInLayer(int numberOfNeuronsInLayer) {
		this.numberOfNeuronsInLayer = numberOfNeuronsInLayer;
	}
}
