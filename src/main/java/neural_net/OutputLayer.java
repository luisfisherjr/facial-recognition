package neural_net;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;


public class OutputLayer implements Layer {
	private List<Neuron> neurons;
	private BlockRealMatrix thetas;
	private BlockRealMatrix x;
	
	public OutputLayer(HiddenLayer hiddenLayer) {
		neurons = new ArrayList<Neuron>();
		
		List<Neuron> inputLayer = hiddenLayer.getListOfNeurons();
		
		thetas = new BlockRealMatrix(inputLayer.size()+1, 1);
		thetas.addToEntry(0, 0, 1);			// bias unit
		
		x = new BlockRealMatrix(inputLayer.size()+1, 1);
		x.addToEntry(0, 0, 1); 				// bias unit
		
		for (int i = 0; i < inputLayer.size(); i++){
			double[] weights = inputLayer.get(i).getWeights();
			double[] outputs = inputLayer.get(i).getOutput();
			
			thetas.addToEntry(i+1, 0, weights[i]);
			x.addToEntry(i+1, 0, outputs[i]);
		}
	}
	
	
	public List<Double> getHypothesis() {
		neurons.get(0);
		return new ArrayList<Double>();
	}

	protected double sigmoid(double z) {
		return 1/(1 + Math.pow(Math.E, z));
	}


	public List<Neuron> getNeurons() {
		return neurons;
	}


	public void setNeurons(List<Neuron> listOfNeurons) {
		neurons = listOfNeurons;
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
