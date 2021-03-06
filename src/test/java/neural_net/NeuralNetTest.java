package neural_net;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.Before;
import org.junit.Test;


public class NeuralNetTest {
	double[][] training_x;
	double[] training_y;
	double[][] test_x;
	ArrayList<Double> result;
	
@Before
  public void setUp() {

    int m = 40000;
    int n = 200;

    training_x = new double[m][n];
    training_y = new double[n];
    test_x = new double[m][1];
    
    

    result = new ArrayList<Double>();
    for (int j = 0; j < n; j++) {
      for (int i = 0; i < m; i++) {
        training_x[i][j] = Math.random() - .5;
      }

      if (Math.random() > .5) training_y[j] = 1;
      else training_y[j] = 0;

      result.add(training_y[j]);	
    }	
    
    for (int i = 0; i < m; i++)
    	test_x[i][0] = Math.random() - .5;
  }
	
	@Test
	public void testForwardPropagation() {

		int[] neurons = {25,5};
		NeuralNet net = new NeuralNet(new BlockRealMatrix(training_x), neurons, training_y, 1, 2000, .001);
		net.train();
		System.out.println(net.hypothesis());
		System.out.println(result);
		
		double correct_sum = 0;
		for (int i = 0; i < result.size(); i++) {
			if ((net.hypothesis().get(i) == 1.0 && result.get(i) == 1.0)
					|| (net.hypothesis().get(i) == 0.0 && result.get(i) == 0.0)) {
				correct_sum += 1;
			}
			
		}
		
		System.out.println("Accuracy: " + correct_sum/result.size()*1.0);
		
		
//		System.out.println();
//		System.out.println("test_x: " + twoDimArrayToString(test_x));
//		System.out.println("prediction: " + oneDimArrayToString(net.predict(new BlockRealMatrix(test_x))));
//		
//		
//		
		

		//net.gradientCheck(0.001);
		
		
		//System.out.println(result);
		//net.backwardPropagation();
		
		//net.gradientD();
		
		//net.forwardPropagation();
	}
	
	private String oneDimArrayToString(double[] array) {
		String result = "[";
		for (int i = 0; i < array.length; i++) {
			result += (Double.toString(array[i]));
			if (i == array.length - 1) continue;
			else result += ",";
		}
		result += "]";
		return result;
	}
	
	private String twoDimArrayToString(double[][] two) {
		String result = "[";
		for (int i = 0; i < two.length; i++) {
			result += "[";
			for (int j = 0; j < two[0].length; j++) {
				result += (Double.toString(two[i][j]));
				if (j == two[0].length - 1) continue;
				else result += ",";
				
			}
			result += "]";
			if (i == two.length - 1) continue;
			else result += ",";
		}
		result += "]";
		return result;
	}
	
	/*
	@Test
	public void testGradient() {

		int[] neurons = {2,5,10,3,8,2};
		NeuralNet net = new NeuralNet(new BlockRealMatrix(training_x), neurons, training_y, 0.0001, 0.02, 0.00000001);

		net.train();
		
		
		net.gradientCheck(0.001);
		
		//net.train(100, .001);
	}*/
	

	
	/*
	@Test
	public void testTrain() {

		int[] neurons = {2,5,10,3,8,2};
		NeuralNet net = new NeuralNet(new BlockRealMatrix(training_x), 6, neurons, training_y);
		
		//net.train(100, .001);
	}*/
	

	/*
	 * Writing some tests from "Examples and intuitions I" from course era slides
	 * 
	 */
	/*
	@Test
	public void andTest1() {
		double[][] xArray = new double[3][1];
		xArray[0][0] = 1;
		xArray[1][0] = 0;
		xArray[2][0] = 0;
		
		double[][] thetasArray = new double[3][1];
		thetasArray[0][0] = -30;
		thetasArray[1][0] = 20;
		thetasArray[2][0] = 20;

		RealMatrix x = new BlockRealMatrix(xArray);
		RealMatrix thetas = new BlockRealMatrix(thetasArray);

		NeuralNet net = new NeuralNet(x);
		net.getOutputLayer().setThetas(thetas);		//hacky way of manually setting thetas
		
		assertEquals(net.getHypothesis(), 0);
	}
	
	@Test
	public void andTest2() {
		double[][] xArray = new double[3][1];
		xArray[0][0] = 1;
		xArray[1][0] = 0;
		xArray[2][0] = 1;
		
		double[][] thetasArray = new double[3][1];
		thetasArray[0][0] = -30;
		thetasArray[1][0] = 20;
		thetasArray[2][0] = 20;

		RealMatrix x = new BlockRealMatrix(xArray);
		RealMatrix thetas = new BlockRealMatrix(thetasArray);

		NeuralNet net = new NeuralNet(x);
		net.getOutputLayer().setThetas(thetas);		//hacky way of manually setting thetas
		
		assertEquals(net.getHypothesis(), 0);
	}
	
	@Test
	public void andTest3() {
		double[][] xArray = new double[3][1];
		xArray[0][0] = 1;
		xArray[1][0] = 1;
		xArray[2][0] = 0;
		
		double[][] thetasArray = new double[3][1];
		thetasArray[0][0] = -30;
		thetasArray[1][0] = 20;
		thetasArray[2][0] = 20;

		RealMatrix x = new BlockRealMatrix(xArray);
		RealMatrix thetas = new BlockRealMatrix(thetasArray);

		NeuralNet net = new NeuralNet(x);
		net.getOutputLayer().setThetas(thetas);		//hacky way of manually setting thetas
		
		assertEquals(net.getHypothesis(), 0);
	}
	
	@Test
	public void andTest4() {		
		double[][] xArray = new double[3][1];
		xArray[0][0] = 1;
		xArray[1][0] = 1;
		xArray[2][0] = 1;
		
		double[][] thetasArray = new double[3][1];
		thetasArray[0][0] = -30;
		thetasArray[1][0] = 20;
		thetasArray[2][0] = 20;

		RealMatrix x = new BlockRealMatrix(xArray);
		RealMatrix thetas = new BlockRealMatrix(thetasArray);

		NeuralNet net = new NeuralNet(x);
		net.getOutputLayer().setThetas(thetas);		//hacky way of manually setting thetas
		
		assertEquals(net.getHypothesis(), 1);
	}

	@Test
	public void orTest1() {		
		double[][] xArray = new double[3][1];
		xArray[0][0] = 1;
		xArray[1][0] = 0;
		xArray[2][0] = 0;
		
		double[][] thetasArray = new double[3][1];
		thetasArray[0][0] = -10;
		thetasArray[1][0] = 20;
		thetasArray[2][0] = 20;

		RealMatrix x = new BlockRealMatrix(xArray);
		RealMatrix thetas = new BlockRealMatrix(thetasArray);

		NeuralNet net = new NeuralNet(x);
		net.getOutputLayer().setThetas(thetas);		//hacky way of manually setting thetas
		
		assertEquals(net.getHypothesis(), 0);
	}
	
	@Test
	public void orTest2() {		
		double[][] xArray = new double[3][1];
		xArray[0][0] = 1;
		xArray[1][0] = 0;
		xArray[2][0] = 1;
		
		double[][] thetasArray = new double[3][1];
		thetasArray[0][0] = -10;
		thetasArray[1][0] = 20;
		thetasArray[2][0] = 20;

		RealMatrix x = new BlockRealMatrix(xArray);
		RealMatrix thetas = new BlockRealMatrix(thetasArray);

		NeuralNet net = new NeuralNet(x);
		net.getOutputLayer().setThetas(thetas);		//hacky way of manually setting thetas
		
		assertEquals(net.getHypothesis(), 1);
	}
	
	@Test
	public void orTest3() {		
		double[][] xArray = new double[3][1];
		xArray[0][0] = 1;
		xArray[1][0] = 1;
		xArray[2][0] = 0;
		
		double[][] thetasArray = new double[3][1];
		thetasArray[0][0] = -10;
		thetasArray[1][0] = 20;
		thetasArray[2][0] = 20;

		RealMatrix x = new BlockRealMatrix(xArray);
		RealMatrix thetas = new BlockRealMatrix(thetasArray);

		NeuralNet net = new NeuralNet(x);
		net.getOutputLayer().setThetas(thetas);		//hacky way of manually setting thetas
		
		assertEquals(net.getHypothesis(), 1);
	}
	
	@Test
	public void orTest4() {		
		double[][] xArray = new double[3][1];
		xArray[0][0] = 1;
		xArray[1][0] = 1;
		xArray[2][0] = 1;
		
		double[][] thetasArray = new double[3][1];
		thetasArray[0][0] = -10;
		thetasArray[1][0] = 20;
		thetasArray[2][0] = 20;

		RealMatrix x = new BlockRealMatrix(xArray);
		RealMatrix thetas = new BlockRealMatrix(thetasArray);

		NeuralNet net = new NeuralNet(x);
		net.getOutputLayer().setThetas(thetas);		//hacky way of manually setting thetas
		
		assertEquals(net.getHypothesis(), 1);
	}
	
	@Test
	public void xnorTest1() {		
		double[][] xArray = new double[2][1];
		xArray[0][0] = 0;
		xArray[1][0] = 0;
		
		double[][] andThetasArray = new double[3][1];
		andThetasArray[0][0] = -30;
		andThetasArray[1][0] = 20;
		andThetasArray[2][0] = 20;
		
		double[][] notAndNotThetasArray = new double[3][1];
		notAndNotThetasArray[0][0] = 10;
		notAndNotThetasArray[0][0] = -20;
		notAndNotThetasArray[0][0] = -20;
		
		double[][] orThetasArray = new double[3][1];
		orThetasArray[0][0] = -10;
		orThetasArray[1][0] = 20;
		orThetasArray[2][0] = 20;		

		RealMatrix x = new BlockRealMatrix(xArray);
		RealMatrix andThetas = new BlockRealMatrix(andThetasArray);
		RealMatrix notAndNotThetas = new BlockRealMatrix(notAndNotThetasArray);
		RealMatrix orThetas = new BlockRealMatrix(orThetasArray);

		HiddenLayer and = new HiddenLayer(x);
		HiddenLayer notAndNot = new HiddenLayer(x);
		OutputLayer or = new OutputLayer(x);
		
		// Again, hacky way to manually setting thetas
		and.setThetas(andThetas);
		notAndNot.setThetas(notAndNotThetas);
		or.setThetas(orThetas);
		
		
		
		List<HiddenLayer> hiddenLayer = new ArrayList<HiddenLayer>();
		hiddenLayer.add(and);
		hiddenLayer.add(notAndNot);
		
		
		
		
		
		NeuralNet net = new NeuralNet(x);
		// assertEquals(net.getHypothesis(), 1);
		// uhhh I give up for now.
	}*/
}
