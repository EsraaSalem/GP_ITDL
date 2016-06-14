package recomm_rerankning_algorithm_Service;

import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.parser.ParseException;

import com.google.appengine.labs.repackaged.org.json.JSONException;

import dataEntities.InputType;
import recomm_reranking_algorithm_logic_classes.RerankingAlgorithmLogic;

@Path("/")  
@Produces(MediaType.TEXT_PLAIN)
public class RerankingAlgorithm_Sevice {
	
	
	@POST
	@Path("/updateUserPreferenceService")
	public String applyAlgorithmService(@FormParam("userID") String userID,@FormParam("twitterID") String twitterID)
	{
		String result = "empty";
		RerankingAlgorithmLogic obj = new RerankingAlgorithmLogic();
		
		try {
			obj.applyAlgorithmService(userID,twitterID);
			
			System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
			System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
			System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
			System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
		} catch (ParseException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		return 	result.toString();
		
		return result;
	}

}
