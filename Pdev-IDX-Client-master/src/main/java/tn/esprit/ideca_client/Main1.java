package tn.esprit.ideca_client;

import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import contracts.ChatServiceRemote;
import ideca.entity.Chat;
import ideca.entity.Trader;



public class Main1 {
	private static final Logger LOGGER = Logger.getLogger(Main1.class.getName());

	private Main1(){
		
	}
	public static void main(String[] args) throws NamingException {
		String jndiName ="Ideca_intelixx-ear/Ideca_intelixx-ejb/ChatService!contracts.ChatServiceRemote";
		Context context = new InitialContext();
		ChatServiceRemote proxy = (ChatServiceRemote)context.lookup(jndiName);
		Trader trader1 = new Trader();
		
		trader1.setFirstName("ahmed");
		trader1.setLasttName("selmi");
		Trader trader2 = new Trader();
		
		trader2.setFirstName("farah");
		trader2.setLasttName("lamouri");
		Trader trader3 = new Trader();
		
		trader3.setFirstName("ala");
		trader3.setLasttName("lamouri");
		//int idTrader1=proxy.ajouterTrader(trader1);
		//int idTrader2=proxy.ajouterTrader(trader2);
		//int idTrader3=proxy.ajouterTrader(trader3);
		
		
		
		proxy.ajouterChat(4, 1, "bye 1");
		//proxy.ajouterChat(2, 1, "bye 2");
		//proxy.ajouterChat(1, 3,  "bye 3");
		//proxy.ajouterChat(2, 3,  "bye 4");
		
		
		List<Chat> liste=proxy.afficherConversation(1, 2);
		for (Chat conv : liste) {
			
			Calendar cal=conv.getChatPK().getDate();
			  
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH)+1; 
			int day = cal.get(Calendar.DAY_OF_MONTH);
			int hour = cal.get(Calendar.HOUR_OF_DAY); 
			int minute = cal.get(Calendar.MINUTE); 
			
			String date=day+"/"+"0"+month+"/"+year;
			String heure=hour+":"+minute;
			
			LOGGER.info(date);
			LOGGER.info(heure);
			LOGGER.info(conv.getTraderE().getLasttName());
			LOGGER.info(conv.getTraderE().getFirstName());
			LOGGER.info(conv.getContenu());
		}
		 
		List<Trader> liste1 =proxy.afficherBoite(1);
		for(Trader tr:liste1){
			String nomPrenom=tr.getFirstName()+" "+tr.getLasttName();
			LOGGER.info(nomPrenom);
		}
		List<Trader> liste2 =proxy.afficherSent(1);
		for(Trader tr:liste2){
			String nomPrenom=tr.getFirstName()+" "+tr.getLasttName();
			LOGGER.info(nomPrenom);
		}
	}
}
