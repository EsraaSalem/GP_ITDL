package recomm_reranking_algorithm_logic_classes;

import java.util.Vector;

import Jama.Matrix;
import dataEntities.CategoryCountOfSources;
import dataEntities.PairComparison;
import dataEntities.UserInialWeights;

public class AnalyticalHierarchicalProcess {
	
	public Vector<UserInialWeights> run(Vector<UserInialWeights> u, Vector<PairComparison> pc, Vector<CategoryCountOfSources> coCategoryCounts)
	{
		Vector<UserInialWeights> result = new Vector<UserInialWeights>();

		int numOfColumns = 3; // num of sources(twitter, facebook , notes)
		int numOfRows = 3; // num of defined categories
		int pairwiseRows = 3;
		int pairwiseCol = 3;
		
		MatrixOperations mo = new MatrixOperations(numOfRows, numOfColumns);
		Matrix m = mo.getInialWeightMatrix(pc,pairwiseRows,pairwiseCol);
		
		System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
		m.print(3, 2);
		
		/*Matrix matrixSqr = mo.getMatrixSquared(m);
		Matrix sumRowsMatrix = mo.getOneDMatrixOfRowSum(matrixSqr); 
		double totalSum = mo.getTotalSumOfColumn(sumRowsMatrix); 
		Matrix inialMatrix = mo.getOneDWeightMatrix(sumRowsMatrix, totalSum);
		// first input eigenVector
		Matrix eigenVector = mo.getEignVector(matrixSqr, sumRowsMatrix);// vector holds the weight of each input source
		//second input
		Matrix categoriesMatrix = mo.getMatrixOfCountCriteria(coCategoryCounts);
		//third input
		Matrix userInialWeights = mo.userInialWeights(u);
		Matrix finalNormalizedMatrix = mo.getFianlWeightMatrixCriteria(eigenVector, categoriesMatrix, userInialWeights); // normalize
		Matrix finalCategoryWeights = mo.getOneDMatrixOfRowSum(finalNormalizedMatrix, numOfRows);
		
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		for (int i = 0; i < numOfRows; i++) {
			for (int j = 0; j < 1; j++) {
				String num1Str = String.format("%.4g%n", finalCategoryWeights.get(i, 0));
				double num1 = Double.parseDouble(num1Str);

				System.out.print(num1 + "  ");
			}
			System.out.println();
		}

		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		*/
		return result;
		
	}
}
