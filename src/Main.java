import Interfaces.DBAccess;
import Model.DBAccessImpl;
import REST.RestRoot;
import com.google.gson.JsonObject;

//Denna klass ska bara anv�ndas f�r att testk�ra metoder
public class Main {
	private static DBAccess dbAccess = new DBAccessImpl();

	public static void main(String[] args) {
		System.out.println("Obs, k�rs fr�n main och inte som server ");
    RestRoot rr = new RestRoot();
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("userName", "ost1");
    jsonObject.addProperty("passw", "ost1");
    String valuePair = jsonObject.toString();
    rr.loginBikeUser(valuePair);
   // String fname, String lname, int memberlevel, String email, int phone, String username, String passw) {
  // boolean b = AccessUser.insertNewUser("ost1", "ost1",10,"ost@ost.com", 1010343433, "ost1","ost1");
    //System.out.println(b);
  /*	ArrayList<Bike> availableBikes = dbAccess.selectAvailableBikes();
		System.out.println(" avilable " + availableBikes);
		RestRoot rr = new RestRoot();
		Gson gson = new Gson();
		System.out.println("K�rs detta ");
		BikeUser user = new BikeUser();
//		user.setUserName("GoloGolo");
//		user.setPassw("GoloGolo");
    user.setUserName("cykeltur");
    user.setPassw("12345");

    Bike bike = new Bike();
		bike.setAvailable(true);
		bike.setBikeID(1);
		bike.setBrandName("MockMonark");
		bike.setColor("Grön");
//bike.setBufferedImage(availableBikes.get(0).getBufferedImage());
		bike.setDayOfRent(LocalDate.now());
		String json = gson.toJson(user);
		System.out.println("null eller inte " + json );
        ArrayList<Integer> currentBikesID = dbAccess.getUsersCurrentBikes(1);
        ArrayList<Bike> bikes = new ArrayList<>();
        System.out.println(currentBikesID.size());
        for(Integer i : currentBikesID) {
            System.out.println(i);
            Bike temp = dbAccess.getBikeByID(i);
            System.out.println(temp);
            Gson gson1 = new Gson();
            String s =  gson1.toJson(temp);
            System.out.println(bike);
        }
		//System.out.println(rr.loginBikeUser(json));
		//rr.getTest();
		*/
	}
}
