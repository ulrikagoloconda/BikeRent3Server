
package REST;

import Interfaces.DBAccess;
import Model.*;
import com.google.gson.Gson;
import helpers.AuthHelper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Map;

//import com.sun.jersey.multipart.MultiPart;

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
        Gson gson = new Gson();
        BikeUser user;
        user = gson.fromJson(json, BikeUser.class);
        user.setUserID(0);
        try {
            currentUser = dbAccess.logIn(user.getUserName(), user.getPassw());
          System.out.println("I restroot login " + currentUser.getUserID());
            if (currentUser.getUserID() > 0) {

                ArrayList<Integer> currentBikesID = dbAccess.getUsersCurrentBikes(currentUser.getUserID());
                ArrayList<Bike> bikes = new ArrayList<>();
                for (Integer i : currentBikesID) {
                    Bike temp = dbAccess.getBikeByID(i);
                    bikes.add(temp);
                }
                currentUser.setCurrentBikeLoans(bikes);
                currentUser.setTotalBikeLoans(dbAccess.getUsersTotalLoan(currentUser.getUserID()));
                System.out.println("getottalloans: " + currentUser.getTotalBikeLoans() +
                "get c. bikeloans: " + currentUser.getCurrentBikeLoans());
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
            Gson gson = new Gson();
            BikeUser user = gson.fromJson(json, BikeUser.class);
            String clientToken = dbAccess.readSessionToken(user.getUserID());
            if (user.getSessionToken().equals(clientToken)) {
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
        Gson gson = new Gson();
        MainViewInformaiton mvi = gson.fromJson(json, MainViewInformaiton.class);
        String clientToken = dbAccess.readSessionToken(mvi.getCurrentUser().getUserID());
        if (mvi.getCurrentUser().getSessionToken().equals(clientToken)) {
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
            Gson gson = new Gson();
            BikeUser user = gson.fromJson(json, BikeUser.class);
            String clientToken = dbAccess.readSessionToken(user.getUserID());
            if (user.getSessionToken().equals(clientToken)) {
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
            Gson gson = new Gson();
            MainViewInformaiton mvi = gson.fromJson(json, MainViewInformaiton.class);
            String clientToken = dbAccess.readSessionToken(mvi.getCurrentUser().getUserID());
            if (mvi.getCurrentUser().getSessionToken().equals(clientToken)) {
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


    @POST
    @Path("/executeRental")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public String executeBikeRent(String json) {
        Gson gson = new Gson();
        MainViewInformaiton mvi = gson.fromJson(json, MainViewInformaiton.class);
        String clientToken = dbAccess.readSessionToken(mvi.getCurrentUser().getUserID());
        if (mvi.getCurrentUser().getSessionToken().equals(clientToken)) {
            Bike returnBike = dbAccess.executeBikeLoan(mvi.getBikeToRentID(), mvi.getCurrentUser().getUserID());
            Gson gson1 = new Gson();
            String returnJson = gson1.toJson(returnBike);
            return returnJson;
        } else {
            return null;
        }
    }

        @GET
        @Path("/removeBike/{userID}/{sessionToken}/{bikeID}")
        @Produces(MediaType.TEXT_PLAIN)
       public String removeBike(@PathParam("userID") String userID, @PathParam("sessionToken") String token, @PathParam("bikeID") String bikeID) {
           String returnString = "";
            try {
               int userIDInt = Integer.parseInt(userID);
               int bikeIDInt = Integer.parseInt(bikeID);
               String clientToken = dbAccess.readSessionToken(userIDInt);
               if (token.equals(clientToken)) {
                    dbAccess.deleteBike(bikeIDInt);
                  if(dbAccess.deleteBike(bikeIDInt)){
                      returnString = "Cykel med cykelID " + bikeID + " har raderats från databasen";
                  }
               } else {
                   return returnString;
               }
           }catch (Exception e){
               e.printStackTrace();
           }
          return returnString;
    }


    @POST
    @Path("/newBike")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public String addBikeToDB(String json) {
        Gson gson = new Gson();
        MainViewInformaiton mvi = gson.fromJson(json, MainViewInformaiton.class);
        String clientToken = dbAccess.readSessionToken(mvi.getCurrentUser().getUserID());
        if (mvi.getCurrentUser().getSessionToken().equals(clientToken)) {
            Bike returnBike =  dbAccess.insertNewBike(mvi.getNewBike());
            Gson gson1 = new Gson();
            String returnJson = gson1.toJson(returnBike);
            return returnJson;
        } else {
            return null;
        }

    }

    @POST
    @Path("/getAllBikes")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public String getAllBikes(String json) {
        Gson gson = new Gson();
        BikeUser user = gson.fromJson(json, BikeUser.class);
        String clientToken = dbAccess.readSessionToken(user.getUserID());
        System.out.println(clientToken);
        System.out.println(user.getMemberLevel());
        if (user.getSessionToken().equals(clientToken) && user.getMemberLevel()==10) {
            Bikes bikes = new Bikes();
            bikes.setBikes(dbAccess.getAllBikes());
            System.out.println(bikes.getBikes());
            Gson gson1 = new Gson();
            String returnJson = gson1.toJson(bikes);
            System.out.println(returnJson);
            return returnJson;
        } else {
            System.out.println("Är det null här, varför det? ");
            return null;
        }

    }
}
