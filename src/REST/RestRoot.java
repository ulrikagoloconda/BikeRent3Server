
package REST;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
	@Produces(MediaType.APPLICATION_XML)
	public String getTest(){
		System.out.println("I getmetoden ");
		String s = "hejsan";
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