package neural_net;

import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.Test;

import junit.framework.TestCase;

public class HiddenLayerTest extends TestCase {

	@Test
	public void testPopulateLayerNeuronCount() {

		double[][] d = {{1,2,3},{4,5,6},{7,8,9}};
		RealMatrix x = new BlockRealMatrix(d);
		
		Layer testcase= new Layer(x, 3, true);

		assertEquals(4, testcase.getListOfNeurons().size(), 0);
		assertEquals(3, testcase.getListOfNeurons().get(0).getWeights().length, 0);
		
		//3 x 6
		
	}

	@Test
	public void testCalculate() {

		double[][] d = {{1,2,3},{4,5,6},{7,8,9}};
		RealMatrix input = MatrixUtils.createRealMatrix(d);
		Layer testcase= new Layer(input, 1, true);
		
		System.out.println("number of neurons expected: " + testcase.getListOfNeurons().size());
		System.out.println("actual neurons : " + testcase.getListOfNeurons().size());
		
		testcase.calculate();
		
		
		// should the output be a n x 1 or 1 x n?
		// mess with line 61-67 of hiddenlayer
		System.out.println("matrix out: ");
		int rowCount = 0;
		int colCount = 0;
		System.out.println("[");
		for (double[] row: testcase.getA().getData()) {
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
		
		// if a row is an image, the number of outputs is the same as number of rows
		assertTrue(testcase.getListOfNeurons().get(0).getWeights().length == testcase.getX().getRowDimension());
	}
}


