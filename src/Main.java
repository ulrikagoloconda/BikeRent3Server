;import Interfaces.DBAccess;
import Model.*;

import java.io.ByteArrayInputStream;
import java.time.Year;
import java.util.Random;

//Denna klass ska bara anv�ndas f�r att testk�ra metoder
public class Main {
	private static DBAccess dbAccess = new DBAccessImpl();
private static ByteArrayInputStream stream;
	public static void main(String[] args) {
		System.out.println("Obs, k�rs fr�n main och inte som server ");
		PrestandaMeasurement pm = new PrestandaMeasurement();
		//Integer measuramentId, LocalDateTime dateTime, double totalTimeSec, double perceivedTimeAvailableBikesSec,
		// double dbProcedureSec, double readFromDbJdbcSec, double gsonToJsonSec, double executeSec,
		// double gsonFromJsonSec, double readOneBike, String comment, double totalSizeDataMb) {

		pm.setDateTime(null);
		pm.setTotalTimeSec(2.433333333);
		pm.setPerceivedTimeAvailableBikesSec(3.4);
		pm.setDbProcedureSec(4.4);
		pm.setReadFromDbJdbcSec(5.4);
		pm.setGsonToJsonSec(6.4);
		pm.setExecuteSec(7.4);
		pm.setGsonFromJsonSec(8.4);
		pm.setComment("Testar");
		pm.setTotalSizeDataMb(10.4);

		double d = 28786.079999999998;
		String str = String.format("%1.2f", d);
		Year year = Year.now();

		int j = 37889;
		int k = 37889 +700;
		for(int i = j; i<k; i++ ) {
			//deletBikes(i);
		}

		//AccessUser.UpdateUser("DBUser", "DBUser", 10,"dbuser@hotmail.com",07000000,"DBUser", "Female","DBUser");
		//AccessUser.insertNewUser("DBUser", "DBUser", 10,year,"dbuser@hotmail.com",07000000,"DBUser", "Annat","DBUser");
		//AccessBike.selectAvailableBikes();
		//AcccesPrestandaMesaurment.insertMesaurment(pm);

		/*double bd = new BigDecimal(1.490514650355E12).setScale(4, RoundingMode.HALF_UP).doubleValue();
		String ds = Double.toString(bd);
		ds = ds.replace("E","");
		bd = new BigDecimal(ds).setScale(4, RoundingMode.HALF_UP).doubleValue();


		/*d = Double.valueOf(str);
		System.out.println(d);
		//AcccesPrestandaMesaurment.insertMesaurment(pm);
		//AccessBike.executeBikeLoan(21,19);
	/*	RestRoot restRoot = new RestRoot();
		BikeUser user = new BikeUser();
		user.setUserName("Demo123");
		user.setPassw("Demo123");
		Gson g = new Gson();
		long millisStart = Calendar.getInstance().getTimeInMillis();
		System.out.println("start " + millisStart);
		AccessBike.selectAvailableBikes();
		long millisStop = Calendar.getInstance().getTimeInMillis();
		System.out.println("läsa från databas available bikes " + (millisStop-millisStart));
		//restRoot.loginBikeUser(g.toJson(user));
		//String json = "{"sessionToken":"9tact15mmvehr8t6g0mhblh580","userID":19}"
		/*BikeUser user = new BikeUser();
		user.setUserID(19);
		user.setSessionToken("9tact15mmvehr8t6g0mhblh580");
		Gson g = new Gson();
		String s = g.toJson(user);
		long millisStart = Calendar.getInstance().getTimeInMillis();
		restRoot.getAvailableBikes(s);
		long millisStop = Calendar.getInstance().getTimeInMillis();
		System.out.println("Tidsåtgång: " + (millisStop - millisStart));
		//AccessUser.UpdateUser("golo","golo",10,"gologologolo@golo.com",400,"Ulrika", "Golo");
	//	Bike b = AccessBike.getBikeByID(17);
		//stream = b.getImageStream();

	/*	for(int i = 0; i < 50; i++) {
			addBikesChild();
		}*/

	/*	for(int i = 37887; i < 37887+2; i++){
			deletBikes(i);
			/*
			27256
			18756

			18741  st = 69223 millisekunder

		}*/
		//System.out.println("Tidsåtgång: " + (millisStop - millisStart) + " millisekunder" );
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
