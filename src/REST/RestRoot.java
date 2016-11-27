
package REST;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import Model.Bike;
import com.google.gson.Gson;

import Interfaces.DBAccess;
import Model.BikeUser;
import Model.DBAccessImpl;
import Model.JDBCConnection;

@Path("/resources")
public class RestRoot{
	 	private JDBCConnection jdbcConnection;
	    private DBAccess dbAccess = new DBAccessImpl();
	    private BikeUser currentUser;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getTest(){
		System.out.println("I getmetoden ");
       Gson gson = new Gson();
      ArrayList<Bike> availableBikes = dbAccess.selectAvailableBikes();

       System.out.println(availableBikes);
        Bike b = availableBikes.get(0);

       /* try {
            System.out.println("Körs detta i try");
            String json = gson.toJson(b);
            System.out.println("utskrift av json" + json);
        }catch (Exception e){
            System.out.println("Eller körs chatch? ");
            e.printStackTrace();
        }*/
        String s = "hejsan ";
		return s;
		
	}	


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String loginBikeUser(String json) {
		System.out.println("I postMetoden ");
		Gson gson = new Gson();
		BikeUser user = new BikeUser();
		user = gson.fromJson(json, BikeUser.class);
	user.setUserID(0);
		try {
			currentUser = dbAccess.logIn(user.getUserName(), user.getPassw());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return gson.toJson(currentUser);
	}

}