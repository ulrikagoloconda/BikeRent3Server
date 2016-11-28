import Interfaces.DBAccess;
import Model.Bike;
import Model.DBAccessImpl;
import com.google.gson.Gson;

import Model.BikeUser;
import REST.RestRoot;

import java.util.ArrayList;

//Denna klass ska bara anvndas fr att testkra metoder
public class Main {
	private static DBAccess dbAccess = new DBAccessImpl();
	public static void main(String [] args){
		System.out.println("Obs, körs från main och inte som server ");
		ArrayList<Bike> availableBikes = dbAccess.selectAvailableBikes();
		System.out.println(" avilable " + availableBikes);
		RestRoot rr = new RestRoot();
		Gson gson = new Gson();
	System.out.println("Körs detta ");
		BikeUser user = new BikeUser();
    user.setUserName("cykeltur");
		user.setPassw("12345");
		String json = gson.toJson(user);
	System.out.println(rr.loginBikeUser(json));
		//rr.getTest();
	}
}
