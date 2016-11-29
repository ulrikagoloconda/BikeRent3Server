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
		BikeMock mock = new BikeMock();
	mock.setAvailable(true);
		mock.setBikeID(1);
		mock.setBrandName("MockMonark");
		mock.setColor("Grön");
//mock.setBufferedImage(availableBikes.get(0).getBufferedImage());
		mock.setDayOfRent(LocalDate.now());
		user.setRentedBIke(mock);
		String json = gson.toJson(user);
		System.out.println("null eller inte " + json);
		System.out.println(rr.loginBikeUser(json));
		rr.getTest();
	}
}
