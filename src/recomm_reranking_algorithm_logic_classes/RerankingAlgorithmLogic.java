package recomm_reranking_algorithm_logic_classes;

import java.util.Vector;

import Model.CategoryCountModel;
import dataEntities.InputType;
import dataEntities.UserEntity;

public class RerankingAlgorithmLogic {

	
	public boolean updateUserCategoriesCounts(String userID, Vector<InputType> allInputs)
	{
		CategoryCountModel categoryModel = new CategoryCountModel();
		
		for (int i = 0; i < allInputs.size(); i++) {
			InputType obj = allInputs.get(i);
			categoryModel.updateCategoryCount(obj.getTextCategory(), userID);
		}
		return true;
		
	}
	
	public Vector<InputType> applyAlgorithmService() {

		Vector<InputType> inputs = new Vector<InputType>();

		UserEntity currentUser = UserEntity.getUserInstance();
		currentUser.setUserTwitterAccount(currentUser.getUserTwitterAccount());
		InputSources inputSources = new TwitterComponent();
		inputSources.setCurrentUser(currentUser);
		inputSources.extractInput();
		for (int i = 0; i < inputSources.getAllInputs().size(); i++) {
			inputs.add(inputSources.getAllInputs().get(i));
		}
		inputSources.clearvec();
		inputSources = new NoteComponent();
		inputSources.setCurrentUser(currentUser);

		inputSources.extractInput();

		for (int i = 0; i < inputSources.getAllInputs().size(); i++) {
			inputs.add(inputSources.getAllInputs().get(i));
		}
		
		System.out.println("SIZE   "+inputs.size());
		updateUserCategoriesCounts(currentUser.getUserDB_ID(),inputs);
		
		return inputs;

	}

}
