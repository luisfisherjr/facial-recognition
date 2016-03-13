package neural_net;

import java.util.ArrayList;

public class Neuron {

	private ArrayList<Double> listOfWeightIn;
	private ArrayList<Double> listOfWeightOut;

	// may need to edit
	public double initNeuron() {
		double randDouble  = Math.random();
		
		listOfWeightIn  = new ArrayList<Double>();
		listOfWeightIn.add(randDouble);
		
		listOfWeightOut  = new ArrayList<Double>();
		listOfWeightOut.add(randDouble);
		
		return randDouble;
	}	

	// may need to add more methods

	public void setListOfWeightIn(ArrayList<Double> listOfWeightIn) {
		this.listOfWeightIn = listOfWeightIn;
	}

	public void setListOfWeightOut(ArrayList<Double> listOfWeightOut) {
		this.listOfWeightOut = listOfWeightOut;
	}

	public ArrayList<Double> getListOfWeightIn() {
		return listOfWeightIn;
	}

	public ArrayList<Double> getListOfWeightOut() {
		return listOfWeightOut;
	}
}