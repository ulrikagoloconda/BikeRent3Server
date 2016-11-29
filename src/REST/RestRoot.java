
package REST;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Model.*;
import com.google.gson.Gson;

import Interfaces.DBAccess;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.sun.jersey.multipart.MultiPart;

@Path("/resources")
public class RestRoot {
    private JDBCConnection jdbcConnection;
    private DBAccess dbAccess = new DBAccessImpl();
    private BikeUser currentUser;

    @GET
    @Produces(MediaType.APPLICATION_JSON)//(MediaType.TEXT_PLAIN)
    public String getTest() {
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
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public String loginBikeUser(String json) {
        System.out.println("I postmetoden " + json);
        Gson gson = new Gson();
        BikeUser user;
        user = gson.fromJson(json, BikeUser.class);
        user.setUserID(0);
        System.out.println(user.getUserName());
        try {
            currentUser = dbAccess.logIn(user.getUserName(), user.getPassw());
           /* ArrayList<Integer> currentBikesID = dbAccess.getUsersCurrentBikes(currentUser.getUserID());
            Bike currentBIke = dbAccess.getBikeByID(currentBikesID.get(0));
            //currentUser.setRentedBIke(currentBIke);
            currentBIke.setBufferedImage(null);
            System.out.println(currentBIke);*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        String jsonUser = gson.toJson(user);
        System.out.println(jsonUser);
        return jsonUser;
    }


    @Path("downloadfile")
    @Produces("multipart/mixed")
    @GET
    public Response getFile() {
        System.out.println("gi getfile ");

        MultiPart objMultiPart = new MultiPart();
        try {
            ArrayList<Integer> currentBikesID = dbAccess.getUsersCurrentBikes(currentUser.getUserID());
            Bike currentBIke = dbAccess.getBikeByID(currentBikesID.get(0));

            File outputfile = new File("image.jpg");
            ImageIO.write(currentBIke.getBufferedImage(), "jpg", outputfile);

            objMultiPart.type(new MediaType("multipart", "mixed"));
            objMultiPart
                    .bodyPart(outputfile.getName(), new MediaType("text", "plain"));
            objMultiPart.bodyPart("" + outputfile.length(), new MediaType("text",
                    "plain"));
            objMultiPart.bodyPart(outputfile, new MediaType("multipart", "mixed"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return Response.ok(objMultiPart).build();

    }

}

