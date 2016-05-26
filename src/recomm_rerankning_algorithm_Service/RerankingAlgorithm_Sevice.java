package recomm_rerankning_algorithm_Service;

import java.util.Vector;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dataEntities.InputType;
import recomm_reranking_algorithm_logic_classes.RerankingAlgorithmLogic;

@Path("/")  
@Produces(MediaType.TEXT_PLAIN)
public class RerankingAlgorithm_Sevice {
	
	
	@POST
	@Path("/applyAlgorithmService")
	public String applyAlgorithmService()
	{
		RerankingAlgorithmLogic obj = new RerankingAlgorithmLogic();
		
		Vector<InputType> result = obj.applyAlgorithmService();
		return 	result.toString();
	}

}
