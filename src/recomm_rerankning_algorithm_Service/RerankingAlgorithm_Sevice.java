package recomm_rerankning_algorithm_Service;

import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.labs.repackaged.org.json.JSONException;

import dataEntities.InputType;
import dataEntities.Offer;
import dataEntities.UserInialWeights;
import recomm_reranking_algorithm_logic_classes.RerankingAlgorithmLogic;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class RerankingAlgorithm_Sevice {

	@POST
	@Path("/updateUserPreferenceService")
	public String applyAlgorithmService(@FormParam("userID") String userID, @FormParam("twitterID") String twitterID) {
		String result = "empty";
		RerankingAlgorithmLogic obj = new RerankingAlgorithmLogic();
		Vector<UserInialWeights> r = new Vector<UserInialWeights>();
		try {
			r = obj.applyAlgorithmService(userID, twitterID);

			if (r.size() > 0)
				result = convertUserUpdatedPreferencesToJsonArrSTTR(r);

		} catch (ParseException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public String convertUserUpdatedPreferencesToJsonArrSTTR(Vector<UserInialWeights> u) {

		JSONArray jsonArr = new JSONArray();
		for (int i = 0; i < u.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("inialWeight", u.get(i).getInialWeight());
			obj.put("categoryID", u.get(i).getCategoryID());
			obj.put("categoryName", u.get(i).getCategoryName());
			obj.put("userID", u.get(i).getUserID());
			obj.put("categoryRecordID", u.get(i).getCategoryRecordID());

			jsonArr.add(obj);
		}

		return jsonArr.toString();
	}

}
