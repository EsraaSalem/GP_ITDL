package recomm_reranking_algorithm_logic_classes;

import java.sql.Timestamp;
import java.util.Vector;

import org.json.simple.parser.ParseException;

import com.google.appengine.labs.repackaged.org.json.JSONException;

import Model.CategoryCountModel;
import Model.DefinedCategories;
import Model.RankingInputsModel;
import dataEntities.CategoryCountOfSources;
import dataEntities.InputType;
import dataEntities.PairComparison;
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

		if (sourcesSignificance.size() > 0 && userInitialWeights.size() > 0) {
			UpdatePreferenceLastDate lastDate = new UpdatePreferenceLastDate();
			Timestamp lastUpdateDate = lastDate.getLastUpdatePreferneceDate(userID);
			Vector<InputType> inputsNotes = new Vector<InputType>();
			Vector<InputType> inputsTweets = new Vector<InputType>();
			Vector<InputType> inputsFacebook = new Vector<InputType>();

			if (!userID.equals("") || userID != null) {

				// get user notes as inputs
				NoteComponent noteComponent = new NoteComponent();
				inputsNotes = noteComponent.extractNotes(userID, lastUpdateDate);
				// get user posts as inputs

				FacebookComponent facebookComponent = new FacebookComponent();
				inputsFacebook = facebookComponent.extractFacebook(userID, lastUpdateDate);

			}

			if (!twitterID.equals("") || twitterID != null) {
				// get user tweets as inputs
				TwitterComponent twitterComponent = new TwitterComponent();
				inputsTweets = twitterComponent.extractTweets(twitterID, lastUpdateDate);
			}
			Vector<CategoryCountOfSources> allCategories = new Vector<CategoryCountOfSources>();
			DefinedCategories dc = new DefinedCategories();
			allCategories = dc.selectAllCategory();

			if (inputsNotes!= null && inputsNotes.size() > 0) {
				allCategories = handleNotesCategoriesCount(allCategories, inputsNotes);

			}
			if (inputsTweets !=null && inputsTweets.size() > 0) {
				allCategories = handleTweetsCategoriesCount(allCategories, inputsTweets);
			}

			if (inputsFacebook!= null && inputsFacebook.size() > 0) {
				allCategories = handleFacebookCategoriesCount(allCategories, inputsFacebook);
			}

//			if(inputsTweets !=null && inputsNotes!=null && inputsFacebookinputsNotes.size() > 0 || inputsTweets.size() > 0||inputsFacebook.size() > 0)
//			{
//			
			if((inputsTweets !=null && inputsTweets.size() > 0)||(inputsNotes !=null && inputsNotes.size() > 0)||
					(inputsFacebook !=null && inputsFacebook.size() > 0))
			{
			
				AnalyticalHierarchicalProcess a = new AnalyticalHierarchicalProcess();
				finalResult = a.run(userInitialWeights, sourcesSignificance, allCategories, userID);
				rankModel.updateUserInterestAfterRunAlgo(finalResult);
				System.out.println("NO UPDATE");
			}
				
			lastDate.addLastUpdatePreferencesDate(userID);

		}

		// rankModel.updateUserInterestAfterRunAlgo(finalResult);

		return finalResult;

	}

	Vector<CategoryCountOfSources> handleNotesCategoriesCount(Vector<CategoryCountOfSources> allDefinedCategories,
			Vector<InputType> notes) {

		for (int i = 0; i < notes.size(); i++) {
			String noteCategory = notes.get(i).getTextCategory();
			for (int j = 0; j < allDefinedCategories.size(); j++) {
				String definedCategory = allDefinedCategories.get(j).getCategoryName();
				if (noteCategory.equals(definedCategory)) {
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
			String category = tweets.get(i).getTextCategory();
			for (int j = 0; j < allDefinedCategories.size(); j++) {
				String definedCategory = allDefinedCategories.get(j).getCategoryName();
				if (category.equals(definedCategory)) {
					allDefinedCategories.get(j).increamentCategoryCount("tweet");
				}
			}

		}
		return allDefinedCategories;
	}

	Vector<CategoryCountOfSources> handleFacebookCategoriesCount(Vector<CategoryCountOfSources> allDefinedCategories,
			Vector<InputType> posts) {

		for (int i = 0; i < posts.size(); i++) {
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