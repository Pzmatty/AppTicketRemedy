package claro.ticketremedy.ws.services;

import claro.ticketremedy.ws.exception.TicketRemedyRuntimeException;

public class TicketRemedyRequest {

	private String sistema;
	private String forma;
	private String columnas;
	private String condiciones;
	private String adicionales;
	private String service;
	private String id;
	private String orden;

	public TicketRemedyRequest(String service,String sistema, String forma, String columnas, String condiciones, String adicionales,String id,String orden) {
		if (sistema == null || forma == null || columnas == null || (service.equals("RMDSelect?") && condiciones == null )|| (service.equals("RMDUpdate?") && id == null )) {
			throw new TicketRemedyRuntimeException("Exception -> [Message: ERROR --> Missing Required Parameters ]");
		}
		this.service = service;
		this.sistema = sistema;
		this.forma = forma;
		this.columnas = columnas;
		this.condiciones = condiciones;
		this.adicionales = adicionales;
		this.id = id;
		this.orden = orden;
		
	}

	public String generateEndpoint() {

		StringBuilder sbEndpoint = new StringBuilder();
		sbEndpoint.append(service);
		sbEndpoint.append("cSistema=" + sistema);
		sbEndpoint.append("&cForma=" + forma);
		sbEndpoint.append("&cColumnas=" + columnas);
		
		if (id != null) {
			sbEndpoint.append("&cID=" + id);
		}
		if (condiciones != null) {
			sbEndpoint.append("&cCondiciones=" + condiciones);
		}
		if (adicionales != null) {
			sbEndpoint.append("&cAdicionales=" + adicionales);
		}
		if (orden != null) {
			sbEndpoint.append("&cOrden=" + orden);
		}
		return sbEndpoint.toString();
	}
	
	
}
