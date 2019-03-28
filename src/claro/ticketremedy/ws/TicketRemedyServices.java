package claro.ticketremedy.ws;

import claro.ticketremedy.ws.exception.TicketRemedyRuntimeException;
import claro.ticketremedy.ws.services.TicketRemedyClient;
import claro.ticketremedy.ws.services.TicketRemedyRequest;
import claro.ticketremedy.ws.services.TicketRemedyResponse;

public class TicketRemedyServices {

	public static TicketRemedyResponse consultarTickets(TicketRemedyRequest ticket) {
		return ((new TicketRemedyClient()).sendRequest(ticket));
	}

	public static String getThrowableText(Throwable throwable) {
		String text = null;
		if (throwable != null) {
			text = throwable.toString();
			if (throwable instanceof TicketRemedyRuntimeException)
				text = text.substring(TicketRemedyRuntimeException.class.getName().length() + 1);
			if (text.length() > 500)
				text = text.substring(0, 500);
		}
		return text;
	}	

	
	
	public static void main(String[] args) {		 
		//TicketRemedyRequest request = new TicketRemedyRequest("RMDInsert?","TEMIPARG","HPD:Help Desk","'1000000000'='NOMBRE RESUMEN CPD'", null,null,null);
		//System.out.println("salida Respuesta:     " + request.toString());
		
		//'1000000018'='SAFE ARG' '1000000019'='CUENTA GENERICA' '1000000251'='CLARO ARGENTINA' '1000000076'='CREATE' '1000000014'='NOC' '1000000217'='ARG-MV-SOPORTE DE CONMUTACION' '1000000000'='TEST3 FRAN BORRAR AHORA' '1000000151'='øEs PP Reg o Cta Seg? Cta SeguraøPP o Reg? Loc Pcia y ALM en la cual emite ALM a la que pertenece Copiar Mercado de Stealth MIN y Billing del Cliente DescripciÛn del Problema Mensaje recibido Fecha y hora de intentos øEst· en Roaming?SI/NO MIN /DN: 5470441011 Tipo de Negocio: PP Mercado: AMBA Tipo de equipo: CPP' '303497400'='CO008U100MB2' '1000000163'='3000' '1000000162'='4000' '1000000099'='0' '1000000251'='CLARO ARGENTINA' '1000000215'='5000' '301572100'='TESTFIELD'	
		/*	TicketRemedyRequest(String service,
		String sistema,
		String forma,
		String columnas, 
		String condiciones, 
		String adicionales,
		String id,
		String orden)*/
		
		
		TicketRemedyRequest request = new TicketRemedyRequest(
				"RMDInsert?", // Servicio
				"TEMIPARG", // Sistema
				"HPD:Help Desk", // Forma
				"'1000000019'='CUENTA GENERICA' '1000000151'='···········aaaaaaa '1000000215'='5000' '536890043'='CPD' '304306840'='STL PROBLEMAS DE COBERTURA' '1000000099'='0' '536890008'='0' '301572100'='NPAR' '1000000163'='3000' '1000000162'='4000' '1000000000'='STL PROBLEMAS DE COBERTURA'",
				null, // Condiciones
				null, // Adicionales
				null, // Id (Update),
				null // orden
				);
		
	//	'1000000018'='STEALTH ARG' '1000000019'='CUENTA GENERICA' '1000000151'='PRUEBA ERROR ARGENTINA 11' '1000000215'='5000' '536890043'='CPD' '304306840'='GPON CORTE DE SERVICIO' '1000000099'='0' '536890008'='0' '301572100'='ARG' '1000000000'='Desciption'
		
	/*	TicketRemedyRequest request = new TicketRemedyRequest(
				"RMDSelect?", // Servicio
				"STEALTHARG", // Sistema
				"HPD:Help Desk", // Forma
				"1000000161", // Columnas
				"'7'=' (5) ' '1000000019'='CUENTA GENERICA' '1000000018'='STEALTH ARG' '1000000251'='CLARO ARGENTINA' '301572100'='NPAR'", // Condiciones
				null, // Adicionales
				null, // Id (Update),
				null // orden
				);*/
		

	
		TicketRemedyResponse response = consultarTickets(request);
		System.out.println("Respuesta:     " + response.toString());
	}
	
	
}
