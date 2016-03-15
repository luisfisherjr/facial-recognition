package machine_learning.facial_recognition;

import org.apache.commons.math3.linear.AbstractFieldMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.RealVector;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import neural_net.Neuron;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {

    	
    	
    	
    
    }
    
    public void testBlockMatrixExperiments() {
    	double[][] thetasData = new double[10][1];
    	for (int i = 0; i < 10; i++)
    		thetasData[i][0] = i;
    	
    	
    	BlockRealMatrix thetas = new BlockRealMatrix(thetasData);
    	
    	System.out.println(thetas.toString());
    	System.out.println(thetas.getRowDimension());
    	System.out.println(thetas.getColumnDimension());
    	
    	BlockRealMatrix thetaTranpose = thetas.transpose();
    	System.out.println(thetas);
    	System.out.println(thetaTranpose);
    	BlockRealMatrix z = thetaTranpose.multiply(thetas);
    	
    	System.out.println(mapSigmoid(z));
    	System.out.println(mapSigmoid(thetas));
    	
    	
    }
    
    
    //
    protected BlockRealMatrix mapSigmoid(BlockRealMatrix matrix) {
    	for (int row = 0; row < matrix.getRowDimension(); row++) {
    		for (int col = 0; col < matrix.getColumnDimension(); col++) {
    			double newValue = sigmoid(matrix.getEntry(row, col));
    			matrix.setEntry(row, col, newValue);
    		}
    	}
    	return matrix;
    }
    
	protected double sigmoid(double z) {
		return 1/(1 + Math.pow(Math.E, z));
	}
    

    
    
}
