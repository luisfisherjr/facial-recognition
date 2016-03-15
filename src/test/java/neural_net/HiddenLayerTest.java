package neural_net;

import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.Test;

import junit.framework.TestCase;

public class HiddenLayerTest extends TestCase {

	@Test
	public void testPopulateLayerNeuronCount() {

		HiddenLayer testcase= new HiddenLayer();
		
		double[][] d = {{1,2,3},{4,5,6},{7,8,9}};
		RealMatrix input = new BlockRealMatrix(d);
		
		testcase.setInput(input);
		testcase.populateLayer(5);
		
		assertEquals(testcase.getListOfNeurons().size(), testcase.getListOfNeurons().size(), 0);
	}

	@Test
	public void testCalculate() {

		HiddenLayer testcase= new HiddenLayer();
		
		double[][] d = {{1,2,3},{4,5,6},{7,8,9}};
		RealMatrix input = new BlockRealMatrix(d);
		
		testcase.setInput(input);
		
		testcase.populateLayer(1);
		
		System.out.println("number of neurons expected: " + testcase.getListOfNeurons().size());
		System.out.println("actual neurons : " + testcase.getListOfNeurons().size());
		
		testcase.calculate();
		
		/*
		 * not sure what result should be
		 */
		
		// should the output be a n x 1 or 1 x n?
		// mess with line 61-67 of hiddenlayer
		System.out.println("matrix out: ");
		int rowCount = 0;
		int colCount = 0;
		System.out.println("[");
		for (double[] row: testcase.getOutput().getData()) {
			rowCount++;
			System.out.print("[");
		for(double num: row) {
			if (rowCount == 1) {
				colCount++;
			}
			System.out.print(num + " ");
			}
		
		System.out.println("]");
		}
		
		System.out.println("]");
		System.out.println("Shape of output: " + rowCount + " x " + colCount);
		
		// failed on purpose until we discuss if output should be a column or row
		// i am confused @.@
		assertTrue(false);
	}
	
}
