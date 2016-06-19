
package CRUD_oprations_Serivces;

import java.sql.Timestamp;
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

import Model.RankingInputsModel;
import Model.NoteModel;
import dataEntities.DeadlineNoteEntity;
import dataEntities.MeetingNoteEntity;
import dataEntities.NoteEntity;
import dataEntities.OrdinaryNoteEntity;
import dataEntities.ShoppingNoteEntity;
import dataEntities.UserInialWeights;
import note_crud_operation_logic_classes.NoteCRUDOperations;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)

public class RankingInputs {

	
	/***
	 * add pairwise comparison of input sources
	 * */
	@POST
	@Path("/addInputSourcesSignificanceService")
	public String getAllNotes() {

		RankingInputsModel iss = new RankingInputsModel();
		iss.readFile();
		return "ADDEDService";
	}

	@POST
	@Path("/getInputSourcesSignificanceService")
	public String getInputSourcesSignificanceService() {
		
		RankingInputsModel iss = new RankingInputsModel();
		String result = iss.getInputSourcesSignificanceSTR();
		return result;
	}

	/***
	 * 
	 * add initial Weights of a user
	 * @throws ParseException 
	 * @throws JSONException 
	 * **/
	@POST
	@Path("/enterInitialWeightsForOneUserService")
	public String enterInitialWeightsForOneUser(@FormParam("userID") String userID,
			@FormParam("userInitialWeightsSTR") String userInitialWeightsSTR ) throws ParseException, JSONException {
		
		RankingInputsModel rim = new RankingInputsModel();

		boolean result = rim.addUserInitialWeights(userID, userInitialWeightsSTR);
		return "added";
	}
	
	
	public Vector<UserInialWeights> getParsedUserInitialWeights(String str) throws ParseException {
		JSONParser parser = new JSONParser();
		Vector<UserInialWeights> res = new Vector<UserInialWeights>();
		System.out.println("AAAAAAA  " + str);
		JSONArray jsonArray = (JSONArray) parser.parse(str.toString());

		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject obj = new JSONObject();
			obj = (JSONObject) jsonArray.get(i);
			UserInialWeights uiw = new UserInialWeights();
			uiw.setCategoryID(obj.get("categoryID").toString());
			uiw.setCategoryName(obj.get("categoryName").toString());
			uiw.setInialWeight(Double.parseDouble(obj.get("inialWeight").toString()));
			uiw.setUserID(obj.get("userID").toString());
			uiw.setCategoryRecordID(obj.get("categoryRecordID").toString());

			res.add(uiw);
		}

		return res;
	}
	
	
	@POST
	@Path("/updatePrefAfterConfirmationService")
	public String updatePrefAfterConfirmation(@FormParam("userID") String userID,
			@FormParam("userInitialWeightsSTR") String userInitialWeightsSTR ) throws ParseException, JSONException {
		
		Vector<UserInialWeights> parsedVec = getParsedUserInitialWeights(userInitialWeightsSTR);
		RankingInputsModel rim = new RankingInputsModel();
		rim.updateUserInterestAfterRunAlgo(parsedVec);
		
		JSONObject result = new JSONObject();
		result.put("Status", "OK");
		return result.toString();
	}
	
	
	
	
	

}
