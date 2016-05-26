package recomm_reranking_algorithm_logic_classes;

import java.util.List;
import java.util.Vector;

import org.json.simple.parser.ParseException;

import com.google.appengine.labs.repackaged.org.json.JSONException;

import Model.NoteModel;
import TextCategorization_logic_classes.TextCategorization;
import dataEntities.InputType;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterComponent extends InputSources {

	
	
	public TwitterComponent()
	{
		
	}
	@Override
	public void extractInput() {
		
		// TODO Auto-generated method stub
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey("0ZMKyv8tBJloZgG64VkgAJxf9")
				.setOAuthConsumerSecret("tDg624UeSnFP3Bd2SzdiyFJ93Eex4qhZHG0dvJgjcLIJLSVInu")
				.setOAuthAccessToken("4076988922-3YcGb97K3r05FJOI8lgCLvgA068uPhAYfQNU2fm")
				.setOAuthAccessTokenSecret("GMXr7lBrSaYgUVggKIqTfEycOu5PJOwr9OD1Owyh1T0Ay");
		
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		
		try {
			
			
			User user = twitter.verifyCredentials();
			List<Status> statuses = twitter.getUserTimeline(currentUser.getUserTwitterAccount().trim().toLowerCase());

			for (int i = 0; i < statuses.size(); i++) {

				TextCategorization textCategorization = new TextCategorization();				
				String tweetText = statuses.get(i).getText().trim().toLowerCase();
				String textCategory = textCategorization.callTextCategoryAPI(tweetText);
				
				allInputs.add(new InputType(tweetText, "tweet", statuses.get(i).getCreatedAt(),textCategory));
				
				
			}
			

		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
			System.exit(-1);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
