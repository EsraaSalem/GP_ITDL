package recomm_reranking_algorithm_logic_classes;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Vector;

import org.json.simple.parser.ParseException;

import com.google.api.server.spi.auth.common.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.labs.repackaged.org.json.JSONException;

import CRUD_oprations_Serivces.RankingInputs;
import Model.CategoryCountModel;
import Model.DefinedCategories;
import Model.RankingInputsModel;
import dataEntities.CategoryCountOfSources;
import dataEntities.InputType;
import dataEntities.PairComparison;
import dataEntities.UserEntity;
import dataEntities.UserInialWeights;

public class RerankingAlgorithmLogic {

	public boolean updateUserCategoriesCounts(String userID, Vector<InputType> allInputs) {
		CategoryCountModel categoryModel = new CategoryCountModel();

		for (int i = 0; i < allInputs.size(); i++) {
			InputType obj = allInputs.get(i);
			categoryModel.updateCategoryCount(obj.getTextCategory(), userID);
		}
		return true;

	}

	public Vector<UserInialWeights> applyAlgorithmService(String userID, String twitterID)
			throws ParseException, JSONException {

		RankingInputsModel rankModel = new RankingInputsModel();
		Vector<UserInialWeights> finalResult = new Vector<UserInialWeights>();
		Vector<UserInialWeights> userInitialWeights = new Vector<UserInialWeights>();
		Vector<PairComparison> sourcesSignificance = new Vector<PairComparison>();
		
		sourcesSignificance = rankModel.getInputSourcesSignificance();
		userInitialWeights = rankModel.getUserInitialWeightsByUserID(userID);
		if(sourcesSignificance.size() > 0 && userInitialWeights.size()>0)
		{
			UpdatePreferenceLastDate lastDate = new UpdatePreferenceLastDate();
			Timestamp lastUpdateDate = lastDate.getLastUpdatePreferneceDate(userID);
			//System.out.println("********************    "+lastUpdateDate);
			Vector<InputType> inputsNotes = new Vector<InputType>();
			Vector<InputType> inputsTweets = new Vector<InputType>();
			Vector<InputType> inputsFacebook = new Vector<InputType>();

			if (!userID.equals("") || userID != null) {

				// get user notes as inputs
				NoteComponent noteComponent = new NoteComponent();
				inputsNotes = noteComponent.extractNotes(userID, lastUpdateDate);
				// get user posts as inputs

//				FacebookComponent facebookComponent = new FacebookComponent();
//				inputsFacebook = facebookComponent.extractFacebook(userID, lastUpdateDate);

			}
			
			if (!twitterID.equals("") || twitterID != null) {
				// get user tweets as inputs
				TwitterComponent twitterComponent = new TwitterComponent();
				inputsTweets = twitterComponent.extractTweets(twitterID, lastUpdateDate);
			}
			Vector<CategoryCountOfSources> allCategories = new Vector<CategoryCountOfSources>();
			DefinedCategories dc = new DefinedCategories();
			allCategories = dc.selectAllCategory();

			
			System.out.println("allCategories size = "+allCategories.size());
			System.out.println("tweets  = "+inputsTweets.size());
			System.out.println("notes = "+inputsNotes.size());
			System.out.println("facebook = "+inputsFacebook.size());
			
			if(inputsNotes.size() > 0)
			{
				allCategories = handleNotesCategoriesCount(allCategories, inputsNotes);
				
			}
			if(inputsTweets.size() > 0)
			{
				allCategories = handleTweetsCategoriesCount(allCategories, inputsTweets);
			}
			
			
//			if(inputsFacebook.size() > 0)
//			{
//				allCategories = handleFacebookCategoriesCount(allCategories, inputsFacebook);
//			}
			
			for(int i = 0 ; i < allCategories.size();i++)
			{
				
				System.out.println(allCategories.get(i).toString());	
			}
			AnalyticalHierarchicalProcess a = new AnalyticalHierarchicalProcess();
			a.run(userInitialWeights, sourcesSignificance, allCategories);
			
			
		}
//		System.out.println("HHHHHHHHHHHHHHHsourcesSignificanceHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
//		for (int i = 0; i <sourcesSignificance.size(); i++) {
//			System.out.println(sourcesSignificance.get(i).toString());
//		}
//		
//
//		System.out.println("HHHHHHHHHHHHuserInitialWeightsHHHHHHHH    "+userInitialWeights.size());
//		for (int i = 0; i <userInitialWeights.size(); i++) {
//			System.out.println(userInitialWeights.get(i).toString());
//		}
		/*
		UpdatePreferenceLastDate lastDate = new UpdatePreferenceLastDate();
		Timestamp lastUpdateDate = lastDate.getLastUpdatePreferneceDate();
		Vector<InputType> inputsNotes = new Vector<InputType>();
		Vector<InputType> inputsTweets = new Vector<InputType>();
		Vector<InputType> inputsFacebook = new Vector<InputType>();

		if (!userID.equals("") || userID != null) {

			// get user notes as inputs
			NoteComponent noteComponent = new NoteComponent();
			inputsNotes = noteComponent.extractNotes(userID, lastUpdateDate);
			// get user posts as inputs

//			FacebookComponent facebookComponent = new FacebookComponent();
//			inputsFacebook = facebookComponent.extractFacebook(userID, lastUpdateDate);

		}

		if (!twitterID.equals("") || twitterID != null) {
			// get user tweets as inputs
			TwitterComponent twitterComponent = new TwitterComponent();
			inputsTweets = twitterComponent.extractTweets(twitterID, lastUpdateDate);
		}

		Vector<CategoryCountOfSources> allCategories = new Vector<CategoryCountOfSources>();
		DefinedCategories dc = new DefinedCategories();
		allCategories = dc.selectAllCategory();

		if(inputsNotes.size() > 0)
		{
			allCategories = handleNotesCategoriesCount(allCategories, inputsNotes);
			
		}
		if(inputsTweets.size() > 0)
		{
			allCategories = handleTweetsCategoriesCount(allCategories, inputsTweets);
		}
//		if(inputsFacebook.size() > 0)
//		{
//			allCategories = handleFacebookCategoriesCount(allCategories, inputsFacebook);
//		}
		AnalyticalHierarchicalProcess a = new AnalyticalHierarchicalProcess();
		a.run(userInitialWeights, sourcesSignificance, allCategories);
		
		lastDate.addLastUpdatePreferencesDate();

		/*
		 * currentUser.setUserTwitterAccount(currentUser.getUserTwitterAccount()
		 * ); TwitterComponent inputSources = new TwitterComponent();
		 * inputSources.setCurrentUser(currentUser);
		 * inputSources.extractInput(); for (int i = 0; i <
		 * inputSources.getAllInputs().size(); i++) {
		 * inputs.add(inputSources.getAllInputs().get(i)); }
		 * inputSources.clearvec(); inputSources = new NoteComponent();
		 * inputSources.setCurrentUser(currentUser);
		 * 
		 * inputSources.extractInput();
		 * 
		 * for (int i = 0; i < inputSources.getAllInputs().size(); i++) {
		 * inputs.add(inputSources.getAllInputs().get(i)); }
		 * 
		 * System.out.println("SIZE   "+inputs.size());
		 * updateUserCategoriesCounts(currentUser.getUserDB_ID(),inputs);
		 *
		
		*/
		return null;

	}

	Vector<CategoryCountOfSources> handleNotesCategoriesCount(Vector<CategoryCountOfSources> allDefinedCategories,
			Vector<InputType> notes) {

		for (int i = 0; i < notes.size(); i++) {
			System.out.println(notes.get(i).getText());
			System.out.println(notes.get(i).getTextCategory());
			System.out.println("_______________________________________________________________________________");
			String category = notes.get(i).getTextCategory();
			for (int j = 0; j < allDefinedCategories.size(); j++) {
				String definedCategory = allDefinedCategories.get(j).getCategoryName();
				if (category.equals(definedCategory)) {
					allDefinedCategories.get(j).increamentCategoryCount("note");
					break;
				}
				
			}

		}
		return allDefinedCategories;
	}
	Vector<CategoryCountOfSources> handleTweetsCategoriesCount(Vector<CategoryCountOfSources> allDefinedCategories,
			Vector<InputType> tweets) {

		for (int i = 0; i < tweets.size(); i++) {
			System.out.println(tweets.get(i).getText());
			System.out.println(tweets.get(i).getTextCategory());
			String category = tweets.get(i).getTextCategory();
			for (int j = 0; j < allDefinedCategories.size(); j++) {
				String definedCategory = allDefinedCategories.get(j).getCategoryName();
				if (category.equals(definedCategory)) {
					allDefinedCategories.get(j).increamentCategoryCount("tweet");
					
				
				System.out.println("KKKKKKKKKKKK  tweet count =  "+allDefinedCategories.get(j).getTwitterSrcCount());
				System.out.println("KKKKKKKKKKKK  note count =  "+allDefinedCategories.get(j).getNoteSrcCount());
				System.out.println("KKKKKKKKKKKK  facebook count =  "+allDefinedCategories.get(j).getFacebookSrcCount());
				System.out.println("#####################################################################################");
				}
			}

		}
		return allDefinedCategories;
	}
	Vector<CategoryCountOfSources> handleFacebookCategoriesCount(Vector<CategoryCountOfSources> allDefinedCategories,
			Vector<InputType> posts) {

		for (int i = 0; i < posts.size(); i++) {
			System.out.println(posts.get(i).getText());
			System.out.println(posts.get(i).getTextCategory());
			System.out.println("_______________________________________________________________________________");
			
			String category = posts.get(i).getTextCategory();
			for (int j = 0; j < allDefinedCategories.size(); j++) {
				String definedCategory = allDefinedCategories.get(j).getCategoryName();
				if (category.equals(definedCategory)) {
					allDefinedCategories.get(j).increamentCategoryCount("facebook");
					break;
				}
				
			}

		}
		return allDefinedCategories;
	}
}
