package neural_net;

import java.util.ArrayList;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class NeuralNet {
	
	// do not need trainingX it is already
	// in the first layer once net is created
	private RealMatrix trainingX;
	
	private RealMatrix trainingY;
	
	// hidden layers and output layer
	private ArrayList<Layer> hiddenLayers;
	
	// must have atleast 1 hidden layer
	// later take in a int[] of length[hiddenCount] with values = number of neurons per layer
	public NeuralNet(RealMatrix trainingX, int hiddenCount, double[] trainingY) {
		
		this.trainingX = trainingX;
		double[][] in = new double[1][trainingY.length];
		in[0] = trainingY;

		this.trainingY = MatrixUtils.createRealMatrix(in);
		
		hiddenLayers = new ArrayList<Layer>();
		
		int i = 0;
		for (; i < hiddenCount; i++ ) {
			if (i == 0) hiddenLayers.add(new Layer(trainingX, 3, true));
			else hiddenLayers.add(new Layer(hiddenLayers.get(i - 1).getA(), 3, true));
		}
		hiddenLayers.add(new Layer(hiddenLayers.get(i - 1).getA(), 1, false));
		
		train();
	}
	
	public ArrayList<Layer> getHiddenLayers() {
		return hiddenLayers;
	}

	public void setHiddenLayers(ArrayList<Layer> hiddenLayers) {
		this.hiddenLayers = hiddenLayers;
	}
	
	public void forwardPropagation() {
		
		hiddenLayers.get(0).calculate();
			
		for (int i = 1; i < hiddenLayers.size(); i++) {
			hiddenLayers.get(i).setX(hiddenLayers.get(i - 1).getA());
			hiddenLayers.get(i).calculate();
		}
	}
	
	public void backwardPropagation() {
		
		int layerSize = hiddenLayers.size() - 1;
		
		//RealMatrix deltaAccum = null;
		double[][] delta;
		
		for(int index = layerSize; index >= 0; index--) {
			
			if (index == layerSize ) {
				hiddenLayers.get(layerSize).setDelta(hiddenLayers.get(index).getA().subtract(trainingY));
			}
			else {
				
				delta = hiddenLayers.get(index).getThetas().transpose().
						multiply(hiddenLayers.get(layerSize + 1).getDelta()).getData();
				
				double[][] a = hiddenLayers.get(index).getA().getData();
				
				for(int i = 0; i < delta[0].length; i++) {
					for(int j = 0; j < delta.length; j++) {
						delta[i][j] += delta[i][j] * a[i][j] * (1- a[i][j]);
					}
				}
				
				hiddenLayers.get(index).setDelta(MatrixUtils.createRealMatrix(delta));
			}	
		}
	}

	public void train() {	
	}

	public void predict(RealMatrix x) {
	}
}