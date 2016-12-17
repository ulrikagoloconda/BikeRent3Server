
BIKERENT3
	
	SYNOPSIS
	Projektet är examinerande I kursen Java Enterprise Edition och är utfört med ett grafiskt gränssnitt I JavaFX.
	I programmet BikeRent3 kan man, som administratör, lägga upp cyklar för uthyrning likväl som man kan radera dem. Som användare kan man låna cyklar, söka efter lediga cyklar, söka med wild card, överblicka sina lån och hantera sina lån och sina användaruppgifter. I programmet finns även en statistikvy.
	
	KODEXEMPEL
	Detta projekt är en vidarutveckling av ett tidigare projekt, där vårt främsta fokus var att bygga en välutvecklad databas. I denna version har vi separerat klient och server som nu kommunicerar med rest-anrop. I detta exempel illustreras hur vi har valt att använda sessions för att säkerställa att icke inloggad obehörig kan få tillgång till information från databasen. Session hanteras genom att, vid korrekt inloggning, ett session_token sparas i databasen och skickas som svar till klienten. Vid förfrågan från klienten efterfrågas användarens id och ett tillhörande session_token innan något rest-förfrågan besvaras. I detta exempel stängs ett aktuellt session. 

@POST
  @Path("/closeSession")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.TEXT_PLAIN)
  public String closeSession(String json) {
      try {
          Gson gson = new Gson();
          BikeUser user = gson.fromJson(json, BikeUser.class);
          String clientToken = dbAccess.readSessionToken(user.getUserID());
          if (user.getSessionToken().equals(clientToken)) {
              dbAccess.closeSession(user.getUserID());
              user.setSessionToken("-1");
              Gson gson1 = new Gson();
             String returnUser = gson1.toJson(user);
              return returnUser;
          } else {
              return null;
          }
      } catch (Exception e) {
          e.printStackTrace();
          return null;
      }
}

	MOTIVATION
	Vi har utforskat hur REST kan navändas till kommunikation mellan en applikation och en server, vilket har varit en fråga vi ställt oss många gånger under utbildningen. 
	
	INSTALLATION
	För att köra programmet krävs en databas och en driver. Script till databasen finns under fliken SQL-settings. 
	Klassen DBUtil läser användarnamn, lösenord och databasnamn från en fil. 
	För att koppla upp sig mot databasen krävs att korrekta uppgifter införs i dokumentet.
    Vi har använt många bibliotek vid sidan av javaEE. För att hantera RESTanropen på klientsidan har vi använt Apache HTTPClient.
    Andra bibliotek att nämna är Gson, Jersey, Appache commons.
    En komplett lib-katalog för att köra programmet finns inkluderat i både Server-repot och Client-repot.

	
	Contributors
	Niklas Karlson, cykeltur, och Ulrika Goloconda Fahlén är de som författat programmet. 
	
	

