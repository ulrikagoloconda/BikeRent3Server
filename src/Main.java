import Interfaces.DBAccess;
import Model.*;
import com.google.gson.Gson;

import REST.RestRoot;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.util.ArrayList;

//Denna klass ska bara anv�ndas f�r att testk�ra metoder
public class Main {
	private static DBAccess dbAccess = new DBAccessImpl();

	public static void main(String[] args) {
		System.out.println("Obs, k�rs fr�n main och inte som server ");
		ArrayList<Bike> availableBikes = dbAccess.selectAvailableBikes();
		System.out.println(" avilable " + availableBikes);
		RestRoot rr = new RestRoot();
		Gson gson = new Gson();
		System.out.println("K�rs detta ");
		BikeUser user = new BikeUser();
		user.setUserName("GoloGolo");
		user.setPassw("GoloGolo");
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
	}
}
