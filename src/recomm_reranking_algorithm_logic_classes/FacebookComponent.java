package recomm_reranking_algorithm_logic_classes;

import java.sql.Timestamp;
import java.util.Vector;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.sun.corba.se.spi.orbutil.fsm.Input;

import HTTPConnection.Connection;
import TextCategorization_logic_classes.TextCategorization;
import dataEntities.FacebookPost;
import dataEntities.InputType;

public class FacebookComponent {

	private UpdatePreferenceLastDate lastDate;

	public FacebookComponent() {

		lastDate = new UpdatePreferenceLastDate();
	}

	public Vector<FacebookPost> callGetUserFBPostWebService(String userID) {

		/***
		 * Here call the web service which will fetch the facebook post(yasmin
		 * will make it)
		 **/
		Vector<FacebookPost> posts = new Vector<FacebookPost>();
			String serviceUrl = "http://8-dot-itdloffers.appspot.com/rest/GetPostsService";
			String urlParameters = "userID=" + userID;
		System.out.println(urlParameters);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			
			
			if (object.get("Status").equals("OK")) {
				JSONArray jposts = (JSONArray) parser.parse(object.get("AllUserPosts").toString());
				for (int i = 0; i < jposts.size(); i++) {
					JSONObject jpost;
					jpost = (JSONObject) jposts.get(i);
					posts.add(convertJsonObjToPostObj(jpost));
				}
				
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i <posts.size(); i++) {
			System.out.println("-------------    "+posts.get(i).toString());
		}
		return posts;

	}

public FacebookPost convertJsonObjToPostObj(JSONObject jsonObj) {
	return new FacebookPost(jsonObj.get("userID").toString(), jsonObj.get("postID").toString(),
			jsonObj.get("postContent").toString(), jsonObj.get("creationDate").toString(), 1);
}
	public Vector<InputType> getPostWithinRange(Vector<FacebookPost> userPosts, Timestamp lastUpdateDate) {
		TextCategorization textCategorization = new TextCategorization();
		java.util.Date date = new java.util.Date();
		Timestamp todayDate = new Timestamp(date.getTime());
		Vector<InputType> allInputs = new Vector<InputType>();
		for (int i = 0; i < userPosts.size(); i++) {
			InputType input = new InputType();
			//java.util.Date postDate = userPosts.get(i).getCreationDate();
			
			Timestamp postCreationDate = Timestamp.valueOf(userPosts.get(i).getCreationDate());

			if (lastDate.isWithinRange(todayDate, lastUpdateDate, postCreationDate)) {

				input.setSourcetype("facebook");
				input.setText(userPosts.get(i).getPostContent());
				try {
					String textCategory = textCategorization.callTextCategoryAPI(userPosts.get(i).getPostContent());
					input.setTextCategory(textCategory);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
				input.setCreationDate(Timestamp.valueOf(userPosts.get(i).getCreationDate()));

				System.out.println(input.toString());
				allInputs.add(input);
			}
		}
		return allInputs;
	}

	public Vector<InputType> getAllPost(Vector<FacebookPost> userPosts) {
		TextCategorization textCategorization = new TextCategorization();
		Vector<InputType> allInput = new Vector<InputType>();
		for (int i = 0; i < allInput.size(); i++) {
			InputType input = new InputType();
			input.setSourcetype("facebook");
			input.setText(userPosts.get(i).getPostContent());
			try {
				String textCategory = textCategorization.callTextCategoryAPI(userPosts.get(i).getPostContent());
				input.setTextCategory(textCategory);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			input.setCreationDate(Timestamp.valueOf(userPosts.get(i).getCreationDate()));

			allInput.add(input);
		}
		return allInput;
	}

	public Vector<InputType> extractFacebook(String userID, Timestamp lastUpdateDate) {
		Vector<InputType> allInput = new Vector<InputType>();
		Vector<FacebookPost> userPosts = new Vector<FacebookPost>();
		userPosts = callGetUserFBPostWebService(userID);

		if (userPosts.size() > 0) {
			if (lastUpdateDate == null) {
				allInput = getAllPost(userPosts);
			} else {
				allInput = getPostWithinRange(userPosts, lastUpdateDate);
			}
			if (allInput.size() == 0) {
				System.out.println("There is NO Posts");
			}
		}
		else
		{
			System.out.println("no posts you did not call the api");
		}
		return allInput;

	}

}
