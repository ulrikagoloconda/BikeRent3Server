
package REST;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import Model.Bike;
import com.google.gson.Gson;

import Interfaces.DBAccess;
import Model.BikeUser;
import Model.DBAccessImpl;
import Model.JDBCConnection;
import com.google.gson.JsonObject;

@Path("/resources")
public class RestRoot{
	 	private JDBCConnection jdbcConnection;
	    private DBAccess dbAccess = new DBAccessImpl();
	    private BikeUser currentUser;

    @GET
    @Produces(MediaType.APPLICATION_JSON)//(MediaType.TEXT_PLAIN)
    public String getTest(){
        System.out.println("I getmetoden ");
        //String indataString = convertStreamToString(incomingData);
        //System.out.println(indataString);
        Gson gson = new Gson();
        ArrayList<Bike> availableBikes = dbAccess.selectAvailableBikes();

        System.out.println(availableBikes);
        Bike b = availableBikes.get(0);

        System.out.println("test the bike objeckt: " + b.getBrandName());

        /*try {
            System.out.println("Körs detta i try");
            String json = gson.toJson(b);
            System.out.println("utskrift av json" + json);
        }catch (Exception e){
            System.out.println("Eller körs chatch? ");
            e.printStackTrace();
        }*/
        BikeUser user = new BikeUser();
        user.setUserName("cykeltur");
        user.setPassw("12345");
        String json = gson.toJson(user);
        //System.out.println(loginBikeUser(json));
        System.out.println("");
        String s = "HHejsan från restRoot ";
        return json;//s;
		
	}	


	@POST
   // @Path("{inputJsonObj}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	public String loginBikeUser(String json) {
        //@PathParam("userName") String userName, @PathParam("passw") String passw
        //@Path("{userName}/ {passw}")
        System.out.println("I postmetoden " + json);
       // System.out.println( json.toString());
		Gson gson = new Gson();
		BikeUser user = new BikeUser();
	    user.setUserID(0);
		try {
			currentUser = dbAccess.logIn("GoloGolo", "GoloGolo");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return gson.toJson(currentUser);
	}

}