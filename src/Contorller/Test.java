package Contorller;

import java.sql.Time;
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
import com.sun.org.apache.bcel.internal.generic.RET;

import HTTPConnection.Connection;
import Model.DefinedCategories;
import Model.NoteParser;
import Model.RankingInputsModel;
import TextCategorization_logic_classes.TextCategorization;
import dataEntities.CategoryCountOfSources;
import dataEntities.NearestStore;
import dataEntities.NoteEntity;
import dataEntities.Offer;
import dataEntities.UserInialWeights;
import recomm_reranking_algorithm_logic_classes.UpdatePreferenceLastDate;;

@Path("/")
@Produces("text/html")
public class Test {

	
}
