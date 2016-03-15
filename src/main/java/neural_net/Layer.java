package neural_net;

import java.util.ArrayList;
import org.apache.commons.math3.linear.RealMatrix;

public interface Layer {
	
	public RealMatrix getInput();
	public RealMatrix getOutput();
	public ArrayList<Neuron> getListOfNeurons();
	
	public void setInput(RealMatrix input);
	public void setOutput(RealMatrix output);
	public void setListOfNeurons(ArrayList<Neuron> listOfNeurons);
}
