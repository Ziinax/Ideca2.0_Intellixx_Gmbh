package tn.ideca_intellixx_client.FXOptionsManagement;

import java.net.Proxy;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import contracts.EquityOptionRemote;
import contracts.FXOptionsRemote;
import contracts.MarketDataRemote;
import ideca.entity.EquityOptions;
import ideca.entity.FXOptions;


public class FXOptionManag {

	public static void main(String[] args)throws NamingException{
		/*String jndiName ="Ideca_intelixx-ear/Ideca_intelixx-ejb/FXOptionsService!contracts.FXOptionsRemote";
		Context context = new InitialContext();
		FXOptionsRemote proxy = (FXOptionsRemote) context.lookup(jndiName);
		
		//proxy.addFXOption(new FXOptions("FirstOption", 18, 19)) ;
		//System.out.println(proxy.findById(1));
		//System.out.println(proxy.deleteFXOption(5)) ; 
		//FXOptions fxoption=proxy.findById(1);
		//fxoption.setPutPremium(50);
		//System.out.println(proxy.updateFXOption(fxoption)) ; 
		/*List<FXOptions> listeFxOptions =proxy.findAllFXOption() ;
		  for (int i=0; i<=listeFxOptions.size(); i++)
		  
	      {
			  System.out.println(listeFxOptions.get(i));
	      }
			FXOptions fxoption=proxy.findById(1);
			System.out.println(fxoption);
			System.out.println(proxy.pricingCallFXOptions(fxoption));
			System.out.print(proxy.pricingPutFXOptions(fxoption));
			*/
		String jndiName ="Ideca_intelixx-ear/Ideca_intelixx-ejb/EquityOptionService!contracts.EquityOptionRemote";
		 Context context = new InitialContext(); 
		 EquityOptionRemote proxy =(EquityOptionRemote) context.lookup(jndiName);
		 EquityOptions equityoption=new EquityOptions("hamdi","Call",0.0,1,136.66,170,0.5,0.1,0.0131);
		 System.out.println(proxy.pricingCallEquityOptions(equityoption));
		 System.out.println(proxy.pricingPutEquityOptions(equityoption));
		 
		 java.util.Date utilDate = new java.util.Date();
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	        String da1="09/02/2017";
	        String da2=String.valueOf(sqlDate); 
	        int year1=Integer.valueOf(da1.substring(6,10)) ; 
	        int mounth1=Integer.valueOf(da1.substring(3,5)); 
	        int day1=Integer.valueOf(da1.substring(0,2)) ; 
	        
	        int year2=Integer.valueOf(da2.substring(0,4)) ; 
	        int mounth2=Integer.valueOf(da2.substring(5,7)) ; 
	        int day2=Integer.valueOf(da2.substring(8,10)) ; 
	        
	        int date1=year1*365+mounth1*30+day1 ;
	        int date2=year2*365+mounth2*30+day2;
	        System.out.println(date1-date2);
		}

	}


