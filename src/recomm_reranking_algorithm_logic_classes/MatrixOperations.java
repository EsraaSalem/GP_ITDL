package recomm_reranking_algorithm_logic_classes;

import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

import javax.jws.soap.SOAPBinding.Use;

import Jama.Matrix;
import dataEntities.CategoryCountOfSources;
import dataEntities.PairComparison;
import dataEntities.UserInialWeights;

public class MatrixOperations {

	private int nRows;
	private int nCols;

	public boolean isequal(Matrix previous, Matrix current, int numOfRows) {

		for (int i = 0; i < numOfRows; i++) {

			String num1Str = String.format("%.5g%n", previous.get(i, 0));

			double num1 = Double.parseDouble(num1Str);
			String num2Str = String.format("%.5g%n", current.get(i, 0));

			double num2 = Double.parseDouble(num2Str);
			if (num1 != num2)

				return false;
		}
		return true;
	}

	public Matrix getEignVector(Matrix firstSquaredMatrix, Matrix inialVector, int r, int c) {
		int i = 0;
		Matrix vector = new Matrix(r, 1);
		while (i < 3) {

			firstSquaredMatrix = getMatrixSquared(firstSquaredMatrix);
			vector = getOneDMatrixOfRowSum(firstSquaredMatrix,r,c);

			double totalSum = getTotalSumOfColumn(vector); 

			Matrix inialMatrix = getOneDWeightMatrix(vector, totalSum);

			if (isequal(inialVector, inialMatrix, nRows)) {
				inialVector = vector;
				return inialVector;
			} else {
				inialVector = vector;
			}
			i++;

		}
		return inialVector;

	}

	public MatrixOperations(int nRows, int nCols) {

		this.nRows = nRows;
		this.nCols = nCols;
	}

	public Matrix getInialWeightMatrix(Vector<PairComparison> pc, int r, int c) {
		
	
		Matrix matrix = new Matrix(r, c);
		int k = 0;
		for (int i = 0; i < r; i++) {
			for (int j = i; j <c; j++) {
				if (i == j) {
					matrix.set(i, j, 1.0);
				} else {

					PairComparison p = new PairComparison();
					p = pc.get(k);
					matrix.set(p.getCategoryID1(), p.getCategoryID2(), p.getCategory1WeightVsCategory2());
					matrix.set(p.getCategoryID2(), p.getCategoryID1(), (1.0 / p.getCategory1WeightVsCategory2()));
					k++;
				}

			}
		}

		return matrix;
	}

	public Matrix getMatrixOfCountCriteria(Vector<CategoryCountOfSources> categoryCounts) {
		int numOfSources = 3;
		Matrix matrix = new Matrix(categoryCounts.size(), numOfSources);
		
		for (int i = 0; i < categoryCounts.size(); i++) {
			matrix.set(i, 0, (double) categoryCounts.get(i).getNoteSrcCount());
			matrix.set(i, 1, (double) categoryCounts.get(i).getTwitterSrcCount());
			matrix.set(i, 2, (double) categoryCounts.get(i).getFacebookSrcCount());
		}
		double sum = 0;

		//this is matrix normalization
		for (int i = 0; i < categoryCounts.size(); i++) {

			for (int j = 0; j < numOfSources; j++) {

				sum += matrix.get(i, j);
			}

		}
		for (int i = 0; i < categoryCounts.size(); i++) {

			for (int j = 0; j < numOfSources; j++) {
				if (sum == 0)
					sum = 1;
				double ratio = matrix.get(i, j) / sum;
				matrix.set(i, j, ratio);

			}

		}

		return matrix;
	}

	public Matrix getMatrixSquared(Matrix matrix) {
		return matrix.times(matrix);
	}

	public Matrix getOneDMatrixOfRowSum(Matrix matrix, int r, int c) {

		Matrix sumRowsMatrix = new Matrix(r, 1);

		for (int i = 0; i < r; i++) {
			double sum = 0.0;
			for (int j = 0; j < c; j++) {
				sum += matrix.get(i, j);

			}
			sumRowsMatrix.set(i, 0, sum);
		}

		return sumRowsMatrix;
	}

	public Matrix getOneDMatrixOfRowSum(Matrix matrix, int r) {

		Matrix sumRowsMatrix = new Matrix(r, 1);

		for (int i = 0; i < r; i++) {
			double sum = 0.0;
			for (int j = 0; j < 3; j++) {
				sum += matrix.get(i, j);
			}
			sumRowsMatrix.set(i, 0, sum);
		}

		return sumRowsMatrix;
	}

	public double getTotalSumOfColumn(Matrix matrix) {
		double sum = 0.0;
		for (int i = 0; i < nRows; i++) {
			sum += matrix.get(i, 0);
		}
		String sumStr = String.format("%.5g%n", sum);

		sum = Double.parseDouble(sumStr);

		return sum;
	}

	public Matrix getOneDWeightMatrix(Matrix matrix, double totalSum) {
		if (totalSum != 0)
			for (int i = 0; i < nRows; i++) {
				matrix.set(i, 0, (matrix.get(i, 0) / totalSum));
			}
		return matrix;
	}

	public Matrix userInialWeights(Vector<UserInialWeights> u) {
		Matrix matrix = new Matrix(u.size(), 1);
		for (int i = 0; i < u.size(); i++) {
			matrix.set(i, 0, u.get(i).getInialWeight());
		}
		return matrix;
	}

	public Matrix getFianlWeightMatrixCriteria(Matrix sourceWeights, Matrix categoiesWeights, Matrix userInialWeights,int limit) {

		Matrix result = new Matrix(limit,1);
		for (int i = 0; i < limit; i++) {
			for (int j = 0; j < nCols; j++) {

				String num1Str = String.format("%.5g%n", sourceWeights.get(j, 0));
				double num1 = Double.parseDouble(num1Str);

				String num2Str = String.format("%.5g%n", categoiesWeights.get(i, j));
				double num2 = Double.parseDouble(num2Str);

				String num3Str = String.format("%.5g%n", userInialWeights.get(i, 0));
				double num3 = Double.parseDouble(num3Str);

				double newRatio = (num1 + num2)* num3;
				categoiesWeights.set(i, j, newRatio);
			}
		}

		return categoiesWeights;
	}

	public MatrixOperations() {
		super();
	}

	public int getnRows() {
		return nRows;
	}

	public void setnRows(int nRows) {
		this.nRows = nRows;
	}

	public int getnCols() {
		return nCols;
	}

	public void setnCols(int nCols) {
		this.nCols = nCols;
	}


}
