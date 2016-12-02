
package REST;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Model.*;
import com.google.gson.Gson;

import Interfaces.DBAccess;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.sun.jersey.multipart.MultiPart;
import helpers.AuthHelper;

@Path("/resources")
public class RestRoot {
    private JDBCConnection jdbcConnection;
    private DBAccess dbAccess = new DBAccessImpl();
    private BikeUser currentUser;

    @GET
    @Produces(MediaType.APPLICATION_JSON)//(MediaType.TEXT_PLAIN)
    public String getTest() {
        System.out.println("I getmetoden ");
        Gson gson = new Gson();
        ArrayList<Bike> availableBikes = dbAccess.selectAvailableBikes();

        System.out.println(availableBikes);
        Bike b = availableBikes.get(0);

        System.out.println("test the bike objeckt: " + b.getBrandName());
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
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public String loginBikeUser(String json,@Context HttpServletRequest req) {
        System.out.println("I postmetoden " + json);
        Gson gson = new Gson();
        BikeUser user;
        user = gson.fromJson(json, BikeUser.class);
        user.setUserID(0);
        try {
            currentUser = dbAccess.logIn(user.getUserName(), user.getPassw());
            if(currentUser.getUserID()>0) {
                ArrayList<Integer> currentBikesID = dbAccess.getUsersCurrentBikes(currentUser.getUserID());
                ArrayList<Bike> bikes = new ArrayList<>();
                for(Integer i : currentBikesID){
                    Bike temp = dbAccess.getBikeByID(i);
                    bikes.add(temp);
                }
                currentUser.setCurrentBikeLoans(bikes);
              currentUser.setTotalBikeLoans(dbAccess.getUsersTotalLoan(currentUser.getUserID()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        MainViewInformaiton mvi = new MainViewInformaiton();
        mvi.setCurrentUser(currentUser);
        //TODO hårdkodad lösning av statisti, ändra detta
        mvi.setTotalBikes(100);
        mvi.setRentedBikes(20);
        String s = AuthHelper.generateValidationToken();
        mvi.setAuthToken(s);
        System.out.println("token "+s);
        String jsonUser = gson.toJson(mvi);

        System.out.println(jsonUser);
        return jsonUser;
    }
    @GET
    @Path("/availableBikes")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAvailableBikes(@Context HttpServletRequest req){
       try {
           System.out.println("körs detta i availableBikes");
           Gson gson = new Gson();
           ArrayList<Bike> availableBikes = dbAccess.selectAvailableBikes();
           Bikes bikeCollection = new Bikes();
           bikeCollection.setBikes(availableBikes);
           String json = "tillgängliga cyklar ";
           json = gson.toJson(bikeCollection);
           System.out.println(json);
           return json;
       }catch (Exception e){
           e.printStackTrace();
           return null;
       }
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public String getSearchResults(String search,@Context HttpServletRequest req){
        System.out.println("körs detta i searchResults ");
        Gson gson = new Gson();
       Map<String,Integer> searchMap = dbAccess.getSearchValue(search);

        String json = "tillgängliga cyklar ";
     //   json = gson.toJson(bikeCollection);
        System.out.println(json);
        return json;
    }

}

