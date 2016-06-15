package recomm_reranking_algorithm_logic_classes;

import java.util.Vector;

import Jama.Matrix;
import dataEntities.CategoryCountOfSources;
import dataEntities.PairComparison;
import dataEntities.UserInialWeights;

public class AnalyticalHierarchicalProcess {
	
	public Vector<UserInialWeights> run(Vector<UserInialWeights> u, Vector<PairComparison> pc, Vector<CategoryCountOfSources> coCategoryCounts, String userID)
	{
		Vector<UserInialWeights> result = new Vector<UserInialWeights>();

		
		
	
		int numOfColumns = 3; // num of sources(twitter, facebook , notes)
		int numOfRows = 3; // num of defined categories
		int pairwiseRows = 3;
		int pairwiseCol = 3;
		
		MatrixOperations mo = new MatrixOperations(numOfRows, numOfColumns);
		Matrix m = mo.getInialWeightMatrix(pc,pairwiseRows,pairwiseCol);
		//m.print(3, 4);
		
		Matrix matrixSqr = mo.getMatrixSquared(m);
		Matrix sumRowsMatrix = mo.getOneDMatrixOfRowSum(matrixSqr,pairwiseRows, pairwiseCol);
		
		double totalSum = mo.getTotalSumOfColumn(sumRowsMatrix); // just for testing 
		Matrix inialMatrix = mo.getOneDWeightMatrix(sumRowsMatrix, totalSum);//just for testing  this is the step of getting the ration of sources
		
		
		// first input eigenVector
		Matrix eigenVector = mo.getEignVector(matrixSqr, sumRowsMatrix, pairwiseRows, pairwiseCol);// final first input --->vector holds the weight of each input source
		
		//second input
		Matrix categoriesMatrix = mo.getMatrixOfCountCriteria(coCategoryCounts);
		
		Vector<UserInialWeights> newU = new Vector<UserInialWeights>();
		
		//third input
		Matrix userInialWeights = mo.userInialWeights(u);
		//userInialWeights.print(u.size(), 5);
		
		
		
		
		/**
		 * format user inial weights for the algorithms
		 * 
		 * **/
		for (int i = 0; i < coCategoryCounts.size(); i++) {
			String category = coCategoryCounts.get(i).getCategoryName();
			for (int j = 0; j < u.size(); j++) {
				if(category.equals(u.get(j).getCategoryName()))
				{
					
					newU.add(u.get(j));
					break;
				}
				
			}
		}
		
		Matrix userInialWeights222 = mo.userInialWeights(newU);
		//userInialWeights222.print(u.size(), 5);
		
		
		 int limit = 10;
		Matrix finalNormalizedMatrix = mo.getFianlWeightMatrixCriteria(eigenVector, categoriesMatrix, userInialWeights222,limit); // normalize
		Matrix finalCategoryWeights = mo.getOneDMatrixOfRowSum(finalNormalizedMatrix, coCategoryCounts.size());
	//	finalCategoryWeights.print(coCategoryCounts.size(), 5);
		 
		
		for(int i = 0 ; i < limit ; i++)
		{
			UserInialWeights uiw = new UserInialWeights();
			uiw.setUserID(userID);
			uiw.setCategoryName(coCategoryCounts.get(i).getCategoryName());
			uiw.setInialWeight(finalCategoryWeights.get(i, 0));
			uiw.setCategoryID(coCategoryCounts.get(i).getCategoryID());
			uiw.setCategoryRecordID(newU.get(i).getCategoryRecordID());
			result.add(uiw);
		//	System.out.println(uiw.toString());
			
		}
		System.out.println("Algorith END");
		return result;
		
	}
}
