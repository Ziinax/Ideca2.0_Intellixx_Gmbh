package tn.esprit.ideca_client;

import javax.naming.NamingException;

import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import contracts.NotificationsRemote;
import contracts.QuestionRemote;
import contracts.UtilisateurRemote;
import ideca.entity.EnumGrade;
import ideca.entity.Notification;
import ideca.entity.Trader;
import ideca.entity.Utilisateur;

public class MainGhada {

	public static void main(String[] args) throws NamingException {
	
		String jndiName = "Ideca_intelixx-ear/Ideca_intelixx-ejb/UtilisateurService!contracts.UtilisateurRemote";
		Context context = new InitialContext();

		UtilisateurRemote proxy = (UtilisateurRemote) context.lookup(jndiName);

		// Date t = new Date(1992,05,17);

		// Utilisateur user = proxy.pwdForgotten("user2");
		// user.setPwd("user2");
		// proxy.updateUtilisateur(user);
		// Trader user = (Trader) proxy.findById(1);
		//System.out.println(proxy.verifConnexion("user2", "user2"));
//proxy.supprimerUtilisateur(2);
		// System.out.println(proxy.pwdForgotten("user2"));
		// System.out.println(user);
		// ajouter
		// proxy.ajouterUtilisateur(user);

		//proxy.listeUser("Trader");
		//System.out.println(proxy.getAllUsers());

		// user.setFirstName("lllll");

		// updaate
		// proxy.updateUtilisateur(user);
		// System.out.println(proxy.getAllUsers());

		// findByName
		// System.out.println(proxy.findById(1));

		// login Verif conn
		// proxy.verifConnexion("user2", "user2");
		// System.out.println(proxy.verifConnexion("user2", "user2"));

		
//			context = new InitialContext();
//			//NotificationsRemote proxy = (NotificationsRemote) context.lookup(jndiName);
//			List<Notification> li=proxy.listeNotificatioForTrader(22);
//			System.out.println(li);
			
			
			
		//String jndiNameQuestion = "Ideca_intelixx-ear/Ideca_intelixx-ejb/QuestionService!contracts.QuestionRemote";
		//Context contextQuestion = new InitialContext();

		//QuestionRemote proxyQuestion = (QuestionRemote) contextQuestion.lookup(jndiNameQuestion);

		//proxyQuestion.getAllQuestions();
		//SELECT id
		//FROM utilisateur
		//ORDER BY RAND( ) 
		//LIMIT 0 , 30
	//System.out.println(EnumGrade.First_Level);
		
		//proxy.validerTrader(31,3,EnumGrade.First_Level, 1000.0); 
	}
}