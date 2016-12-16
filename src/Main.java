import Interfaces.DBAccess;
import Model.AccessBike;
import Model.AccessUser;
import Model.DBAccessImpl;
import REST.RestRoot;
import com.google.gson.JsonObject;

//Denna klass ska bara anv�ndas f�r att testk�ra metoder
public class Main {
	private static DBAccess dbAccess = new DBAccessImpl();

	public static void main(String[] args) {
		System.out.println("Obs, k�rs fr�n main och inte som server ");
		AccessBike.getBikeByID(2);
	}
}
