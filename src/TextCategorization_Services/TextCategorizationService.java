package TextCategorization_Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.sun.org.apache.xpath.internal.operations.Bool;

import TextCategorization_logic_classes.TextCategorization;

import javax.ws.rs.Path;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)

public class TextCategorizationService {

	
	
	
	@POST
	@Path("/categorizeText")
	public String categorizeText(@FormParam("text") String text) throws JSONException, ParseException {
		
		TextCategorization textCategorization = new TextCategorization();
		String textCategory = textCategorization.callTextCategoryAPI(text);
		
		return textCategory;
	}
	

}
