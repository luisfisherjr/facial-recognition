package neural_net;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

/*
 * InputLayer:  # of neurons in inputLayer is # of columns in trainingData
 * OutputLayer:  # of neurons in outputLayer is # of distinct values in labels
 */

public class NeuralNet {
	private InputLayer inputLayer;
	private List<HiddenLayer> hiddenLayers;
	private OutputLayer outputLayer;

	public NeuralNet(RealMatrix trainingData, RealMatrix labels) {
		inputLayer = new InputLayer(trainingData);
		
		//Currently only one hidden layer.  subject to change?
		hiddenLayers = new ArrayList<HiddenLayer>();
		hiddenLayers.add(new HiddenLayer(inputLayer.getOutput())); 
		
		HiddenLayer lastHiddenLayer = hiddenLayers.get(hiddenLayers.size()-1);
		outputLayer = new OutputLayer(lastHiddenLayer.getOutput());
	}
	
	public void printResult() {
		System.out.println(outputLayer.getHypothesis());
	}
}
