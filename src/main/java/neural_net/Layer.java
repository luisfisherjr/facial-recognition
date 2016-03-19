
package neural_net;

import java.util.ArrayList;

import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;


// to create output layer set hidden to false
// to create hidden layer with bias neuron set hidden to true

public class Layer {
	
	private ArrayList<Neuron> neurons;

	// input F x T
	private RealMatrix x;

	private RealMatrix thetas;
	
	// ouput g(z) ; N x T
	private RealMatrix a;
	
	private RealMatrix delta; 
	
	private boolean isOutputLayer;

	// input:x ,neuronCount: neurons hidden: ishiddenlayer
	public Layer(RealMatrix x, int neruons, boolean hidden){
		this.x = x;
		this.isOutputLayer = !hidden;
		populateLayer(neruons);
		calculate();
	}

	public void populateLayer(int n) {

		int featureCount = getX().getRowDimension();

		Neuron tempNeuron;
		double[] tempWeights;
		ArrayList<Neuron> neurons = new ArrayList<Neuron>();

		int i = 0;
		if (isOutputLayer) i++;
		
		for(; i <= n; i++) {

			tempNeuron = new Neuron();
			tempWeights = new double[featureCount];
			
			for (int j = 0; j < featureCount; j++) {
		
				if (i == 0) tempWeights[j] = 1;
				else tempWeights[j] = Math.random() / 100;
			}

			tempNeuron.setWeights(tempWeights);
			neurons.add(tempNeuron);	
		}
		setListOfNeurons(neurons);
	}

	public void calculate() {

		int rows = getListOfNeurons().size();
		int cols = getListOfNeurons().get(0).getWeights().length;

		BlockRealMatrix thetas = new BlockRealMatrix(rows, cols);

		for(int i = 0; i < rows; i++) {

			thetas.setRow(i, getListOfNeurons().get(i).getWeights());
		}
		
		this.thetas = thetas;
		setA(sigmoid(thetas.multiply(x)));		
	}

	public RealMatrix sigmoid(RealMatrix matrix) {

		for(int i = 0; i < matrix.getRowDimension(); i++) {

			double[] row = matrix.getRow(i);

			for(double d: row) {
				
				d = 1 / ( 1 + Math.exp(d));	
			}

			matrix.setRow(i, row);
		}
		return matrix;
	}

	public RealMatrix getX() {
		return x;
	}
	
	public void setX(RealMatrix x) {
		this.x = x;
	}

	public RealMatrix getA() {
		return a;
	}

	public void setA(RealMatrix a) {
		this.a = a;

	}
	
	public ArrayList<Neuron> getListOfNeurons() {
		return neurons;
	}

	public void setListOfNeurons(ArrayList<Neuron> listOfNeurons) {
		this.neurons = listOfNeurons;

	}

	public RealMatrix getThetas() {
		return thetas;
	}

	public void setThetas(RealMatrix thetas) {
		this.thetas = thetas;
	}
	
	public void setDelta(RealMatrix delta) {
//		if ((delta.getColumnDimension() > 1) == false)
//			delta = delta.transpose();
//		
		this.delta = delta;
	}
	
	public RealMatrix getDelta() {
//		if ((delta.getColumnDimension() > 1) == false)
//			delta = delta.transpose();
//		
		return delta;
	}
	
	
	// used to set the final learned weights
	public void setNeuronWeights() {
		
		int col = thetas.getColumnDimension();
		
		for (int i = 0 ;i < col;)	
			neurons.get(i).setWeights(thetas.getColumn(i));
	}
}