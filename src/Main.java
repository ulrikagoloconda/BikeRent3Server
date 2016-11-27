import com.google.gson.Gson;

import Model.BikeUser;
import REST.RestRoot;

//Denna klass ska bara anv�ndas f�r att testk�ra metoder
public class Main {
	public static void main(String [] args){
		System.out.println("Obs, k�rs fr�n main och inte som server ");
		RestRoot rr = new RestRoot();
		Gson gson = new Gson();
	System.out.println("K�rs detta ");
		BikeUser user = new BikeUser();
		user.setUserName("GoloGolo");
		user.setPassw("GoloGolo");
		String json = gson.toJson(user);
	System.out.println(rr.loginBikeUser(json));
		rr.getTest();
	}
}
