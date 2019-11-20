package fr.insat.om2m.tp2.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import fr.insat.om2m.tp2.mapper.Mapper;

import org.eclipse.om2m.commons.resource.*;
import fr.insat.om2m.tp2.util.*;
import fr.insat.om2m.tp2.util.NotificationUtil.NotObixContentException;
import obix.Obj;

public class HttpServer {

	private static Mapper mapper = new Mapper();
	private static Notification notif;
	private static org.eclipse.om2m.commons.resource.Subscription sub ;
	private static int PORT = 1400;
	private static String CONTEXT = "/monitor";

	/**
	 * Get the payload as string
	 * 
	 * @param bufferedReader
	 * @return payload as string
	 */
	public static String getPayload(BufferedReader bufferedReader) {
		Scanner sc = new Scanner(bufferedReader);
		String payload = "";
		while (sc.hasNextLine()) {
			payload += sc.nextLine() + "\n";
		}
		sc.close();
		return payload;
	}

	public static class MonitorServlet extends HttpServlet {

		private static final long serialVersionUID = 2302036096330714914L;

		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
 			String payload = getPayload(req.getReader());
			System.out.println("Subscription received with payload:\n" + payload);
 			// unmarshalling the notification

			/*
			 * jaxbContext = JAXBContext.newInstance(Notification.class);
			 * Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			 * StringReader reader = new StringReader(payload); Notification
			 * notification =(Notification) jaxbUnmarshaller.unmarshal(reader);
			 * 
			 * Obj notifUtils = NotificationUtil.getObixFromNotification(
			 * (Notification)
			 * notification.getNotificationEvent().getRepresentation());
			 * System.out.println(notifUtils.toString()); // String m =
			 * ObixEncoder.toString(notifUtils.get("con"));
			 */

		    notif =(Notification)  mapper.unmarshal(payload);
		    //sub =  (Subscription) mapper.unmarshal(payload);
		    System.out.println(notif.isVerificationRequest());
		   
		    
		  	
			resp.setStatus(HttpServletResponse.SC_OK);

		}

	}

	public static void main(String[] args) throws Exception {

		// start the server
		Server server = new Server(PORT);
		ServletHandler servletHandler = new ServletHandler();

		// add servlet and context
		servletHandler.addServletWithMapping(MonitorServlet.class, CONTEXT);
		server.setHandler(servletHandler);
		server.start();
		server.join();

	}

}
