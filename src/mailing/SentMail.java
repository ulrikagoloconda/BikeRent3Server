package mailing;
/*
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

//70 send email: http://www.rgagnon.com/javadetails/java-0083.html
//http://www.codejava.net/java-ee/javamail/send-e-mail-with-attachment-in-java

/**
 * @author Niklas Karlsson
 * @version 1.0
 * @since 2016-09-15
 */

public interface SentMail extends OnlyOne {

/*  String AdminEmail = "cykeltur@gmail.com";
  //   String AdminEmail = "flagrans@hotmail.com";

  static boolean sendDelRQ(String userNamn, String email) {

    //_____________________________________________________________________


    String host = "smtp.gmail.com";
    String port = "587";
    String mailFrom = "bike4u2Rent@gmail.com";
    String password = "Just4Fun#";

    //String mailTo = "cykeltur@gmail.com";  //
    String mailTo = AdminEmail;
    String mailinfo = email;
    String subject = "AnvÃ¤ndare " + userNamn + " Ã¶nskar del";

    /*   String message = makeNiceEMailtextDel(mailTo, userNamn, mailinfo);

    String[] attachFiles = new String[1];
    attachFiles[0] = "img/bikelogo.png";

    try {
      sendEmailWithAttachments(host, port, mailFrom, password, mailTo, subject, message, attachFiles);
      System.out.println("mailTo " + mailTo);
      System.out.println("subject " + subject);
      System.out.println("message " + message);

//    		
    } catch (Exception ex) {
      ex.printStackTrace();
      return false; //inte ett fullskaligt test..
    }

    return true;

  }


  static boolean sendNewUser(String userNamn, String email) {

    //_____________________________________________________________________


    String host = "smtp.gmail.com";
    String port = "587";
    String mailFrom = "bike4u2Rent@gmail.com";
    String password = "Just4Fun#";

    //String mailTo = "cykeltur@gmail.com";  //
    String mailTo = email;
    String mailinfo = email;
    String subject = "AnvÃ¤ndare " + userNamn + ", konto Ã¤r nu registrerat hos bike4u2rent";

    String message = makeNiceEMailtextNewUser(mailTo, userNamn, mailinfo);

    String[] attachFiles = new String[1];
    attachFiles[0] = "img/bikelogo.png";

    try {
      sendEmailWithAttachments(host, port, mailFrom, password, mailTo, subject, message, attachFiles);
      System.out.println("mailTo " + mailTo);
      System.out.println("subject " + subject);
      System.out.println("message " + message);

//
    } catch (Exception ex) {
      ex.printStackTrace();
      return false; //inte ett fullskaligt test..
    }

    return true;

  }


  static String makeNiceEMailtextDel(String mailTo, String userNamn, String mailinfo) {
    System.out.println("make a mail to anmin for DEL");
    System.out.println(mailTo);
    System.out.println(userNamn);
    System.out.println(mailinfo);
    System.out.println("----");
    //String cid = generateCID();
    String mailToNice = changeSpecialCharForHTML(mailTo);
    String userNamnNice = changeSpecialCharForHTML(userNamn);
    String siteNamnNice = changeSpecialCharForHTML(mailinfo);
    String message =
        "<table border=\"0\" cellpadding=\"0\" class=\"MsoNormalTable\" height=\"58\" style=\"background: none repeat scroll 0% 0% rgb(0, 219, 200);\" width=\"100%\">" +
            "<tbody> <tr> " +
            "<th><img src=\"cid:image\"></th>" +
            "<th><big><big><big><big>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
            + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
            + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </big></big></big></big></th>"
            + " <th>&nbsp; "
            + "</th> 		</tr> 			<tr> 			</tr>		</tbody>	</table>"
            + "<p><strong>Hej&nbsp;Admin, uppdrag Ã¥t anv:  "
            + userNamn
            + "</strong><br />	H&auml;r kommer uppdraget: "
            +
            "<br />	denna anvÃ¤ndare vill radera sitt konto:&nbsp; "
            + mailinfo
            + "</p>	<p>&nbsp;</p>	<p>&nbsp;</p>"//;
            + "<br /> <a href=\"http://www.bike4u2Rent.app4solutions.com\"> 	<img src=\"cid:imageFoot\"> </a>";


    return message;
  }

  static String makeNiceEMailtextNewUser(String mailTo, String userNamn, String mailinfo) {
    System.out.println("make a mail to new user");
    System.out.println(mailTo);
    System.out.println(userNamn);
    System.out.println(mailinfo);
    System.out.println("----");
    //String cid = generateCID();
    String mailToNice = changeSpecialCharForHTML(mailTo);
    String userNamnNice = changeSpecialCharForHTML(userNamn);
    String siteNamnNice = changeSpecialCharForHTML(mailinfo);
    String message =
        "<table border=\"0\" cellpadding=\"0\" class=\"MsoNormalTable\" height=\"58\" style=\"background: none repeat scroll 0% 0% rgb(0, 219, 200);\" width=\"100%\">" +
            "<tbody> <tr> " +
            "<th><img src=\"cid:image\"></th>" +
            "<th><big><big><big><big>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
            + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
            + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </big></big></big></big></th>"
            + " <th>&nbsp; "
            + "</th> 		</tr> 			<tr> 			</tr>		</tbody>	</table>"
            + "<p><strong>Hej&nbsp;:  "
            + userNamn
            + "</strong><br />	V&auml;lkommen till bike4u2rent "
            +
            "<br />	ni angav Email:&nbsp; "
            + mailinfo
            + "</p>	<p>&nbsp;</p>	<p>&nbsp;</p>"//;
            + "<br /> <a href=\"http://www.bike4u2Rent.app4solutions.com\"> 	<img src=\"cid:imageFoot\"> </a>";


    return message;
  }


  static String changeSpecialCharForHTML(String textString) {
    String old = textString;
    try {
      textString = textString.replaceAll("&", "&amp;");
      textString = textString.replaceAll("Â¤", "&curren;");
      textString = textString.replaceAll("Ã‘", "&Ntilde;");
      textString = textString.replaceAll("Ãœ", "&uuml;");
      textString = textString.replaceAll("ÃŸ", "&szlig;");
      textString = textString.replaceAll("â‚¬", "&euro;");
      textString = textString.replaceAll("Ã†", "&AElig;");
      textString = textString.replaceAll("Ã†", "&aelig;");
      textString = textString.replaceAll("Ã€", "&agrave;");
      textString = textString.replaceAll("Ãˆ", "&egrave;");
      textString = textString.replaceAll("Ã‰", "&eacute;");
      textString = textString.replaceAll("Â§", "&sect;");
      textString = textString.replaceAll("Â°", "&deg;");
      textString = textString.replaceAll("Â£", "&pound;");
      textString = textString.replaceAll("Â´", "&acute;");
      textString = textString.replaceAll(">", "&gt;");
      textString = textString.replaceAll("<", "&lt;");
      textString = textString.replaceAll("â€�", "&rdquo;");
      textString = textString.replaceAll("Ã¥", "&aring;");
      textString = textString.replaceAll("Ã¤", "&auml;");//&auml;
      textString = textString.replaceAll("Ã¶", "&ouml;");
      textString = textString.replaceAll("Ã…", "&Aring;");
      textString = textString.replaceAll("Ã„", "&Auml;");
      textString = textString.replaceAll("Ã–", "&Ouml;");
      textString = textString.replaceAll("&", "&amp;");
      textString = textString.replaceAll("~", "&rsquo;");
      textString = textString.replaceAll("Ã¡", "&aacute;");
      textString = textString.replaceAll("Ã ", "&agrave;");
      textString = textString.replaceAll("Ã�", "&Aacute;");
      textString = textString.replaceAll("Ã€", "&Agrave;");
      textString = textString.replaceAll("Ã©", "&eacute;");
      textString = textString.replaceAll("Ã¨", "&egrave;");
      textString = textString.replaceAll("Ãˆ", "&Egrave;");
      textString = textString.replaceAll("Ã‰", "&Eacute;");
      textString = textString.replaceAll("@", "AT");

      System.out.println("del av mailtext; " + textString);
      return textString;
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return old;
    }


  }

  static void sendEmailWithAttachments(String host, String port,
                                       final String userName, final String password, String toAddress,
                                       String subject, String message, String[] attachFiles)
      throws MessagingException {
    // sets SMTP server properties
    Properties properties = new Properties();
    properties.put("mail.smtp.host", host);
    properties.put("mail.smtp.port", port);
    properties.put("mail.smtp.auth", "true");

    //properties.put("mail.smtp.starttls.enable", "true"); //google
    properties.put("mail.smtp.starttls.enable", "false"); //old

    properties.put("mail.smtp.starttls.enable", "true"); //google


    properties.put("mail.user", userName);
    properties.put("mail.password", password);
    // creates a new session with an authenticator
    Authenticator auth = new Authenticator() {
      @Override
      public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
      }
    };
    Session session = Session.getInstance(properties, auth);

    // creates a new e-mail message
    Message msg = new MimeMessage(session);

    msg.setFrom(new InternetAddress(userName));
    InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
    msg.setRecipients(Message.RecipientType.TO, toAddresses);
    msg.setSubject(subject);
    msg.setSentDate(new Date());

    // creates message part
    MimeBodyPart messageBodyPart = new MimeBodyPart();
    //messageBodyPart.setContent(message, "text/html");
    messageBodyPart.setContent(message, "text/html; charset=UTF-8");

    // creates multi-part
    Multipart multipart = new MimeMultipart();
    multipart.addBodyPart(messageBodyPart);


    // second part (the image)

    messageBodyPart = new MimeBodyPart();
    DataSource fds = new FileDataSource
        ("img/bikelogo-mail.png");
    messageBodyPart.setDataHandler(new DataHandler(fds));
    messageBodyPart.setHeader("Content-ID", "<image>");
    // add it
    multipart.addBodyPart(messageBodyPart);


    messageBodyPart = new MimeBodyPart();
    DataSource fdsFot = new FileDataSource
        ("img/bikelogo-mailfot.png");
    messageBodyPart.setDataHandler(new DataHandler(fdsFot));
    messageBodyPart.setHeader("Content-ID", "<imageFoot>");
    // add it
    multipart.addBodyPart(messageBodyPart);


    if (attachFiles != null && attachFiles.length > 0) {
      for (String filePath : attachFiles) {
        MimeBodyPart attachPart = new MimeBodyPart();

        String newfilepath = filePath;

        try {
          attachPart.attachFile(newfilepath);
        } catch (IOException ex) {
          ex.printStackTrace();
        }

        multipart.addBodyPart(attachPart);
      }
    }

    // sets the multi-part as e-mail's content
    msg.setContent(multipart);

    // sends the e-mail
    Transport.send(msg);
    System.out.println("ot sent : " + message);

  }

*/
}