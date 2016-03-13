package neural_net;

import java.util.ArrayList;

public class NeuralNet {

	
	private InputLayer inputLayer;
	private HiddenLayer hiddenLayer;
	private ArrayList<HiddenLayer> listOfHiddenLayer;
	private OutputLayer outputLayer;
	private int numberOfHiddenLayers;
	
	public void initNet() {
		
		// input layer
		inputLayer = new InputLayer();
		
		Neuron n1 = new Neuron();
		Neuron n2 = new Neuron();
		n1.initNeuron();
		n2.initNeuron();
		
		ArrayList<Neuron> nur1 = new ArrayList<Neuron>();
		nur1.add(n1);
		nur1.add(n2);
		
		inputLayer.setNumberOfNeuronsInLayer(2);
		inputLayer.setListOfNeurons(nur1);
		
		
		// hidden layer 1
		HiddenLayer h1 = new HiddenLayer();	
		
		Neuron n3 = new Neuron();
		Neuron n4 = new Neuron();
		Neuron n5 = new Neuron();
		n3.initNeuron();
		n4.initNeuron();
		n5.initNeuron();
		
		ArrayList<Neuron> nur2 = new ArrayList<Neuron>();
		nur2.add(n3);
		nur2.add(n4);
		nur2.add(n5);
		
		h1.setNumberOfNeuronsInLayer(3);
		h1.setListOfNeurons(nur2);
		
		// hidden layer 2
		HiddenLayer h2 = new HiddenLayer();

		Neuron n6 = new Neuron();
		Neuron n7 = new Neuron();
		Neuron n8 = new Neuron();
		n6.initNeuron();
		n7.initNeuron();
		n8.initNeuron();
		
		ArrayList<Neuron> nur3 = new ArrayList<Neuron>();
		nur3.add(n6);
		nur3.add(n7);
		nur3.add(n8);
		
		h2.setNumberOfNeuronsInLayer(3);
		h2.setListOfNeurons(nur3);
		
		listOfHiddenLayer = new ArrayList<HiddenLayer>();
		listOfHiddenLayer.add(h1);
		listOfHiddenLayer.add(h2);
		
		// output layer
		outputLayer = new OutputLayer();
		
		Neuron n9 = new Neuron();
		n9.initNeuron();
		
		ArrayList<Neuron> nur4 = new ArrayList<Neuron>();
		nur4.add(n9);
		
		outputLayer.setNumberOfNeuronsInLayer(1);
		outputLayer.setListOfNeurons(nur4);
	}
	
	public void printNet() {
		
	}
}
