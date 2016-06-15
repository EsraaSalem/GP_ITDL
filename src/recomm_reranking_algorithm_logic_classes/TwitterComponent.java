package recomm_reranking_algorithm_logic_classes;

import java.sql.Timestamp;
import java.util.List;
import java.util.Vector;

import org.json.simple.parser.ParseException;

import com.google.appengine.labs.repackaged.org.json.JSONException;

import TextCategorization_logic_classes.TextCategorization;
import dataEntities.InputType;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterComponent {
	UpdatePreferenceLastDate lastDate;
	TextCategorization textcategorization;

	public TwitterComponent() {
		lastDate = new UpdatePreferenceLastDate();
		textcategorization = new TextCategorization();
	}

	public Vector<InputType> getAllTweets(List<Status> statuses) throws JSONException, ParseException {

		Vector<InputType> allInputs = new Vector<InputType>();
		 int limit = statuses.size();
		//int limit = 10;
		for (int i = 0; i < limit; i++) {
			InputType input = new InputType();
			java.util.Date tweetDate = statuses.get(i).getCreatedAt();
			Timestamp tweetCreationDate = new Timestamp(tweetDate.getTime());
			input.setCreationDate(tweetCreationDate);
			input.setSourcetype("tweet");
			input.setText(statuses.get(i).getText());

			try {
				input.setTextCategory(textcategorization.callTextCategoryAPI(statuses.get(i).getText()));
			} catch (JSONException | ParseException e) {
				// TODO Auto-generated catch block

			}
			allInputs.add(input);

		}
		return allInputs;

	}

	public Vector<InputType> getTweetsWithin(List<Status> statuses, Timestamp lastUpdateDate) {
		java.util.Date date = new java.util.Date();
		Timestamp todayDate = new Timestamp(date.getTime());
		Vector<InputType> allInputs = new Vector<InputType>();
		for (int i = 0; i < statuses.size(); i++) {
			InputType input = new InputType();
			java.util.Date tweetDate = statuses.get(i).getCreatedAt();
			Timestamp tweetCreationDate = new Timestamp(tweetDate.getTime());

			if (lastDate.isWithinRange(todayDate, lastUpdateDate, tweetCreationDate)) {

				input.setCreationDate(tweetCreationDate);
				input.setSourcetype("tweet");
				input.setText(statuses.get(i).getText());
				try {
					input.setTextCategory(textcategorization.callTextCategoryAPI(statuses.get(i).getText()));
				} catch (JSONException | ParseException e) {
					// TODO Auto-generated catch block

				}
				allInputs.add(input);
			}
		}
		return allInputs;

	}

	public Vector<InputType> extractTweets(String twitterID, Timestamp lastUpdateDate)
			throws JSONException, ParseException {

		// Timestamp lastUpdateDate = lastDate.getLastUpdatePreferneceDate();

		Vector<InputType> allInputs = new Vector<InputType>();

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey("0ZMKyv8tBJloZgG64VkgAJxf9")
				.setOAuthConsumerSecret("tDg624UeSnFP3Bd2SzdiyFJ93Eex4qhZHG0dvJgjcLIJLSVInu")
				.setOAuthAccessToken("4076988922-3YcGb97K3r05FJOI8lgCLvgA068uPhAYfQNU2fm")
				.setOAuthAccessTokenSecret("GMXr7lBrSaYgUVggKIqTfEycOu5PJOwr9OD1Owyh1T0Ay");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();

		java.util.Date date = new java.util.Date();
		Timestamp todayDate = new Timestamp(date.getTime());

		try {

			User user = twitter.verifyCredentials();
			List<Status> statuses = twitter.getUserTimeline(twitterID);

			if (statuses.size() > 0) {
				if (lastUpdateDate == null) {
					allInputs = getAllTweets(statuses);
				} else {
					
					allInputs = getTweetsWithin(statuses, lastUpdateDate);
				}

				if (allInputs.size() == 0) {
					System.out.println("there is No tweets");
				}
			}

		} catch (TwitterException te) {
			
			System.out.println("Error ocurred");
			return null;
		}

		return allInputs;
	}

}
