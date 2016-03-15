package neural_net;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.linear.BlockRealMatrix;

/*
 * InputLayer:  # of neurons in inputLayer is # of columns in trainingData
 * OutputLayer:  # of neurons in outputLayer is # of distinct values in labels
 */

public class NeuralNet {
	private InputLayer inputLayer;
	private List<HiddenLayer> hiddenLayers;
	private OutputLayer outputLayer;

	public NeuralNet(BlockRealMatrix trainingData, BlockRealMatrix labels) {
		inputLayer = new InputLayer(trainingData);
		
		
		hiddenLayers = new ArrayList<HiddenLayer>();
		
		//Currently only one hidden layer.  subject to change?
		hiddenLayers.add(inputLayer.generateHiddenLayer()); 
		HiddenLayer lastHiddenLayer = hiddenLayers.get(hiddenLayers.size()-1);
		
		
		outputLayer = lastHiddenLayer.generateOutputLayer();
		
		
	}
	
	public void printNet() {
		
	}
}
