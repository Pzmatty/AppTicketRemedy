# AppTicketRemedy

Estos ejemplos se encuentran dentro de src en la clase TicketRemedyServices.

public static void main(String[] args) {		 
		
	// Ejemplo para realizar un insert en Remedy, devuelve un número de ID de Remedy como <nuevo></nuevo> en el XML
	/*	TicketRemedyRequest request = new TicketRemedyRequest(
				"RMDInsert?", // Servicio
				"TEMIPARG", // Sistema
				"HPD:Help Desk", // Forma
				"'1000000019'='CUENTA GENERICA' '1000000151'='------óóóóóóóóó------ '1000000215'='5000' '536890043'='CPD' '304306840'='STL PROBLEMAS DE COBERTURA' '1000000099'='0' '536890008'='0' '301572100'='NPAR' '1000000163'='3000' '1000000162'='4000' '1000000000'='STL PROBLEMAS DE COBERTURA'",
				null, // Condiciones
				null, // Adicionales
				null, // Id (Update),
				null // orden
				);*/
		
	// Ejemplo para realizar un select en Remedy, devuelve varios incidentes en el XML
//	TicketRemedyRequest request = new TicketRemedyRequest(
//			"RMDSelect?", // Servicio
//			"STEALTHARG", // Sistema
//			"HPD:Help Desk", // Forma
//			"1000000161", // Columnas
//			"'7'=' (5) ' '1000000019'='CUENTA GENERICA' '1000000018'='STEALTH ARG' '1000000251'='CLARO ARGENTINA' '301572100'='NPAR'", // Condiciones
//			null, // Adicionales
//			null, // Id (Update),
//			null // orden
//			);

		// Esta consulta estática devuelve las plantillas de Remedy (sigue siendo una URL para hacer select pero devuelve otros datos).	
		/*TicketRemedyRequest request = new TicketRemedyRequest(
				"RMDSelect?", // Servicio
				"STEALTHARG", // Sistema
				"HPD:Template", // Forma
				"1000001437&20303165800 303165900 303174700", // Columnas
				"'1000000580'='1'", // Condiciones
				null, // Adicionales
				null, // Id (Update),
				null // orden
				);*/
	
		TicketRemedyResponse response = consultarTickets(request);
		System.out.println("Respuesta:     " + response.toString());
	}
