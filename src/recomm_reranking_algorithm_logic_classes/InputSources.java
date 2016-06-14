package recomm_reranking_algorithm_logic_classes;
 

import java.util.Vector;

import dataEntities.InputType;
import dataEntities.UserEntity;


public abstract class InputSources {

	protected static Vector<InputType> allInputs;
	protected static UserEntity currentUser;
	
	public void clearvec()
	{
		allInputs.clear();
		allInputs = new Vector<InputType>();
	}
	public static UserEntity getCurrentUser() {
		return currentUser;
	}
  
	public static void setCurrentUser(UserEntity currentUser) {
		InputSources.currentUser = currentUser;
	}

	public InputSources() {
		
		allInputs = new Vector<InputType>();
		// TODO get the current active user
	}

	public static Vector<InputType> getAllInputs() {
		return allInputs;
	}

	public static void setAllInputs(Vector<InputType> allInputs) {
		InputSources.allInputs = allInputs;
	}
	
	
	public abstract String extractInput();
	
	
	
}
