;import Interfaces.DBAccess;
import Model.AccessBike;
import Model.AccessUser;
import Model.Bike;
import Model.DBAccessImpl;
import REST.RestRoot;
import com.google.gson.JsonObject;

import java.io.ByteArrayInputStream;
import java.util.Calendar;
import java.util.Random;

//Denna klass ska bara anv�ndas f�r att testk�ra metoder
public class Main {
	private static DBAccess dbAccess = new DBAccessImpl();
private static ByteArrayInputStream stream;
	public static void main(String[] args) {
		System.out.println("Obs, k�rs fr�n main och inte som server ");
		//AccessUser.UpdateUser("golo","golo",10,"gologologolo@golo.com",400,"Ulrika", "Golo");
		Bike b = AccessBike.getBikeByID(17);
		stream = b.getImageStream();

		long millisStart = Calendar.getInstance().getTimeInMillis();
		/*for(int i = 0; i < 10000; i++) {
			addBikesChild();
		}*/

		for(int i = 28742; i < 28742+5000; i++){
			deletBikes(i);
			/*
			27256
			18756

			18741  st = 69223 millisekunder
			*/
		}
		long millisStop = Calendar.getInstance().getTimeInMillis();
		System.out.println("Tidsåtgång: " + (millisStop - millisStart) + " millisekunder" );
		/*
		17729st
		18730
		28731st
		1000st = 7421 millisec
		10 000st = 48813 millisec

		10 000 = 87347 millisekunder med korrekt bild
		com.mysql.jdbc.exceptions.jdbc4.CommunicationsException: The driver was unable to create a connection due to an inability to establish the client portion of a socket.

This is usually caused by a limit on the number of sockets imposed by the operating system. This limit is usually configurable.

For Unix-based platforms, see the manual page for the 'ulimit' command. Kernel or system reconfiguration may also be required.
		 */

	}

	public static void addBikesChild(){

		Random random = new Random();
		int randomNumber = random.nextInt(6 - 1) + 1;
		Bike bike = new Bike();
		bike.setBrandName("Dbs");
		bike.setType("Barn");
		bike.setModelYear(2014);
		bike.setSize(20);
		bike.setState(randomNumber);
		bike.setColor("Rosa");
		stream.reset();
		bike.setImageStream(stream);

		AccessBike.insertNewBike(bike);
	}

	public static void deletBikes(int i){
		AccessBike.deleteBike(i);
	}
}
