package neural_net;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.Before;
import org.junit.Test;


public class NeuralNetTest {
	@Before
	public void setUp() {
		//TODO
	}
	
	/*
	 * Writing some tests from "Examples and intuitions I" from course era slides
	 * 
	 */
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
	}
}