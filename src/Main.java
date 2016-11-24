import com.google.gson.Gson;

import Model.BikeUser;
import REST.RestRoot;

//Denna klass ska bara användas för att testköra metoder
public class Main {
	public static void main(String [] args){
		System.out.println("Obs, körs från main och inte som server ");
		RestRoot rr = new RestRoot();
		Gson gson = new Gson();
	System.out.println("Körs detta ");
		BikeUser user = new BikeUser();
		user.setUserName("GoloGolo");
		user.setPassw("GoloGolo");
		String json = gson.toJson(user);
	System.out.println(rr.loginBikeUser(json));
	}
}
