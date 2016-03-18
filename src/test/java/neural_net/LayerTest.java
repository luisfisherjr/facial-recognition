package neural_net;

import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.Test;

import junit.framework.TestCase;

public class LayerTest extends TestCase {

	@Test
	public void testPopulateLayerNeuronCount() {

		double[][] d = {{1,2,3},{4,5,6},{7,8,9}};
		RealMatrix x = new BlockRealMatrix(d);
		
		Layer testcase= new Layer(x, 3, true);

		assertEquals(4, testcase.getListOfNeurons().size(), 0);
		assertEquals(3, testcase.getListOfNeurons().get(0).getWeights().length, 0);
		
	}

	@Test
	public void testCalculateHiddenLayer() {

		double[][] d = {{1,2,3},{4,5,6},{7,8,9}};
		RealMatrix input = MatrixUtils.createRealMatrix(d);
		Layer testcase= new Layer(input, 5, true);

		testcase.calculate();
		
		System.out.println("matrix a: ");
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
		System.out.println("Shape (" + rowCount + ", " + colCount+ ")");
		
		// if a row is an image, the number of outputs is the same as number of rows
		assertTrue(testcase.getListOfNeurons().get(0).getWeights().length == testcase.getA().getColumnDimension());
		assertTrue(testcase.getListOfNeurons().size() == testcase.getA().getRowDimension());
	}
	
	@Test
	public void testCalculateOutputLayer() {

		double[][] d = {{1,2,3},{4,5,6},{7,8,9}};
		RealMatrix input = MatrixUtils.createRealMatrix(d);
		Layer testcase= new Layer(input, 1, false);

		testcase.calculate();
		
		System.out.println("matrix a: ");
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
		System.out.println("Shape (" + rowCount + ", " + colCount+ ")");
		
		// if a row is an image, the number of outputs is the same as number of rows
		assertTrue(testcase.getListOfNeurons().get(0).getWeights().length == testcase.getA().getColumnDimension());
		assertTrue(testcase.getListOfNeurons().size() == testcase.getA().getRowDimension());
	}
}