
package REST;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

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
    public String loginBikeUser(String json,@Context HttpServletRequest req) {
        System.out.println("I postmetoden " + json);
        HttpSession session= req.getSession(true);
        Gson gson = new Gson();
        BikeUser user;
        user = gson.fromJson(json, BikeUser.class);
        user.setUserID(0);
        System.out.println(user.getUserName());
        try {
            currentUser = dbAccess.logIn(user.getUserName(), user.getPassw());
            if(currentUser.getUserID()>0) {
                session.setAttribute("currentUser", currentUser);
                System.out.println("Currnetuser passw " + currentUser.getPassw());
                ArrayList<Integer> currentBikesID = dbAccess.getUsersCurrentBikes(currentUser.getUserID());
                ArrayList<Bike> bikes = new ArrayList<>();
                for(Integer i : currentBikesID){
                    Bike temp = dbAccess.getBikeByID(i);
                   // temp.setBufferedImage(null);
                    bikes.add(temp);
                }
                currentUser.setCurrentBikeLoans(bikes);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        String jsonUser = gson.toJson(currentUser);
        System.out.println(jsonUser);
        return jsonUser;
       /* public String hello(@Context HttpServletRequest req) {

            HttpSession session= req.getSession(true);
            Object foo = session.getAttribute("foo");
            if (foo!=null) {
                System.out.println(foo.toString());
            } else {
                foo = "bar";
                session.setAttribute("foo", "bar");
            }
            return foo.toString();


        }*/
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
            //ImageIO.write(currentBIke.getBufferedImage(), "jpg", outputfile);

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

