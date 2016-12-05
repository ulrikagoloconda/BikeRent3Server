
package REST;

import java.io.File;
import java.io.StringReader;
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
import com.google.gson.stream.JsonReader;
import com.sun.jersey.multipart.MultiPart;
import helpers.AuthHelper;
import jdk.nashorn.internal.parser.JSONParser;

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
    public String loginBikeUser(String json) {
        System.out.println("I postmetoden " + json);
        Gson gson = new Gson();
        BikeUser user;
        user = gson.fromJson(json, BikeUser.class);
        user.setUserID(0);
        try {
            currentUser = dbAccess.logIn(user.getUserName(), user.getPassw());
            if (currentUser.getUserID() > 0) {
                ArrayList<Integer> currentBikesID = dbAccess.getUsersCurrentBikes(currentUser.getUserID());
                ArrayList<Bike> bikes = new ArrayList<>();
                for (Integer i : currentBikesID) {
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
       if(dbAccess.isSessionOpen(currentUser.getUserID())){
           mvi.getCurrentUser().setSessionToken(dbAccess.readSessionToken(currentUser.getUserID()));
       } else {
           String s = AuthHelper.generateValidationToken();
           dbAccess.startSession(s, currentUser.getUserID());
           mvi.getCurrentUser().setSessionToken(s);
       }
        String jsonUser = gson.toJson(mvi);
        System.out.println(jsonUser);
        return jsonUser;
    }

    @POST
    @Path("/availableBikes")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public String getAvailableBikes(String json) {
        try {
            System.out.println("körs detta i availableBikes");
            Gson gson = new Gson();
            BikeUser user = gson.fromJson(json, BikeUser.class);
            String clientToken = dbAccess.readSessionToken(user.getUserID());
            System.out.println(user.getSessionToken() + " sessionTOken " + clientToken);
            if (user.getSessionToken().equals(clientToken)) {
                System.out.println(" I ifsatesn, det stämde");
                ArrayList<Bike> availableBikes = dbAccess.selectAvailableBikes();
                Bikes bikeCollection = new Bikes();
                bikeCollection.setBikes(availableBikes);
                String returnJson = gson.toJson(bikeCollection);
                System.out.println(returnJson);
                return returnJson;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @POST
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public String getSearchResults(String json) {
        System.out.println("körs detta i searchResults ");
        Gson gson = new Gson();
        MainViewInformaiton mvi = gson.fromJson(json, MainViewInformaiton.class);
        String clientToken = dbAccess.readSessionToken(mvi.getCurrentUser().getUserID());
        System.out.println(mvi.getCurrentUser().getSessionToken() + " sessionTOken " + clientToken);
        if (mvi.getCurrentUser().getSessionToken().equals(clientToken)) {
            System.out.println(" I ifsatesn, det stämde " + mvi.getSearchValue());
            Map<String, Integer> searchMap = dbAccess.getSearchValue(mvi.getSearchValue());
            Bikes bikes = new Bikes();
            bikes.setSearchResults(searchMap);
            Gson gson1 = new Gson();
            String returnJson = gson1.toJson(bikes);
            System.out.println(returnJson);
            return returnJson;
        } else {
            return null;
        }
    }

    @POST
    @Path("/closeSession")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public String closeSession(String json) {
        try {
            System.out.println("körs detta i closeSession");
            Gson gson = new Gson();
            BikeUser user = gson.fromJson(json, BikeUser.class);
            String clientToken = dbAccess.readSessionToken(user.getUserID());
            System.out.println(user.getSessionToken() + " sessionTOken " + clientToken);
            if (user.getSessionToken().equals(clientToken)) {
                System.out.println(" I ifsatesn, det stämde");
                dbAccess.closeSession(user.getUserID());
                user.setSessionToken("-1");
                Gson gson1 = new Gson();
               String returnUser = gson1.toJson(user);
                return returnUser;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @POST
    @Path("/getBike")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public String getSingleBike(String json) {
        try {
            System.out.println("körs detta i getSingleBIke ");
            Gson gson = new Gson();
            MainViewInformaiton mvi = gson.fromJson(json, MainViewInformaiton.class);
            String clientToken = dbAccess.readSessionToken(mvi.getCurrentUser().getUserID());
            System.out.println(mvi.getCurrentUser().getSessionToken() + " sessionTOken " + clientToken);
            if (mvi.getCurrentUser().getSessionToken().equals(clientToken)) {
                System.out.println(" I ifsatesn, det stämde " + mvi.getSingleBikeID());
                Bike returnBike = dbAccess.getBikeByID(mvi.getSingleBikeID());
                Gson gson1 = new Gson();
                String returnJson = gson1.toJson(returnBike);return returnJson;

            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
