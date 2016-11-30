import Interfaces.DBAccess;
import Model.*;
import com.google.gson.Gson;

import REST.RestRoot;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.util.ArrayList;

//Denna klass ska bara anvndas fr att testkra metoder
public class Main {
	private static DBAccess dbAccess = new DBAccessImpl();

	public static void main(String[] args) {
		System.out.println("Obs, körs från main och inte som server ");

		ArrayList<Bike> availableBikes = dbAccess.selectAvailableBikes();
		System.out.println(" avilable " + availableBikes);
		RestRoot rr = new RestRoot();
		Gson gson = new Gson();

	System.out.println("Körs detta ");
		BikeUser user = new BikeUser();
    user.setUserName("cykeltur");
		user.setPassw("12345");
//		String json = gson.toJson(user);
//	System.out.println(rr.loginBikeUser(json));
/*
		System.out.println("Körs detta ");
		BikeUser user = new BikeUser();
		user.setUserName("GoloGolo");
		user.setPassw("GoloGolo");
		*/
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
        }
		//System.out.println(rr.loginBikeUser(json));
		//rr.getTest();
	}
}
