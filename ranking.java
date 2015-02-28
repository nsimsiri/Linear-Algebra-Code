import org.apache.commons.math3.linear.*;
class Ranking{
	public static void main(String args[]){
		System.out.print("Hello\n");
		// Create a real matrix with two rows and three columns, using a factory
		// method that selects the implementation class for us.
		double[][] matrixData = { {1d,2d,3d}, {2d,5d,3d}};
		RealMatrix m = MatrixUtils.createRealMatrix(matrixData);

		// One more with three rows, two columns, this time instantiating the
		// RealMatrix implementation class directly.
		double[][] matrixData2 = { {1d,2d}, {2d,5d}, {1d, 7d}};
		RealMatrix n = new Array2DRowRealMatrix(matrixData2);

		// Note: The constructor copies  the input double[][] array in both cases.

		// Now multiply m by n
		RealMatrix p = m.multiply(n);
		System.out.println(p.getRowDimension());    // 2
		System.out.println(p.getColumnDimension()); // 2

		// Invert p, using LU decomposition
		RealMatrix pInverse = new LUDecomposition(p).getSolver().getInverse();
	}
}