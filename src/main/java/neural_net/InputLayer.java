package neural_net;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class InputLayer implements Layer {
	private List<Neuron> neurons;
	
	
	// alter
	public InputLayer (RealMatrix x) {
	}
	
	// alter
	public void printLayer(InputLayer inputLayer) {
	}

	public List<Neuron> getNeurons() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setNeurons(List<Neuron> listOfNeurons) {
		// TODO Auto-generated method stub
		
	}



	public HiddenLayer generateHiddenLayer() {
		// TODO Auto-generated method stub
		return null;
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
