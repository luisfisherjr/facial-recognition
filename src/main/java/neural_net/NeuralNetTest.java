package neural_net;
import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class NeuralNetTest {

	public static void main(String[] args) {
		int m = 3;
		int n = 3;
		
		// testing out matrix class
		double[][] data = {{1,2,3},{4,5,6},{7,8,9}};
		
		RealMatrix test1 = new BlockRealMatrix(data);
		System.out.println(test1.getEntry(1, 1));
		
		RealMatrix test2 = new BlockRealMatrix(m,n);
		System.out.println(test2.getEntry(1, 1));

		NeuralNet nn = new NeuralNet();
		nn.initNet();
		nn.printNet();
	}
	
}
