package claro.ticketremedy.ws.services;

public class TicketRemedyResponse {

	private String sistema;
	private String forma;
	private String columnas;
	private String condiciones;
	private String adicionales;
	private Resultado resultado;	
	private String error;
	private String nuevo;
	private String update;
	private String idUp;
	private String orden;

	
	public String getSistema() {
		return sistema;
	}

	public void setSistema(String sistema) {
		this.sistema = sistema;
	}

	public String getForma() {
		return forma;
	}

	public void setForma(String forma) {
		this.forma = forma;
	}

	public String getColumnas() {
		return columnas;
	}

	public void setColumnas(String columnas) {
		this.columnas = columnas;
	}

	public String getCondiciones() {
		return condiciones;
	}

	public void setCondiciones(String condiciones) {
		this.condiciones = condiciones;
	}


	public String getAdicionales() {
		return adicionales;
	}

	public void setAdicionales(String adicionales) {
		this.adicionales = adicionales;
	}

	public Resultado getResultado() {
		return resultado;
	}

	public void setResultado(Resultado resultado) {
		this.resultado = resultado;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	public String getNuevo() {
		return nuevo;
	}

	public void setNuevo(String nuevo) {
		this.nuevo = nuevo;
	}
	
	public String getUpdate() {
		return nuevo;
	}

	public void setUpdate(String update) {
		this.update = update;
	}
	
	public String getIdUp() {
		return idUp;
	}

	public void setIdUp(String idUp) {
		this.idUp = idUp;
	}
	
	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("TicketResponse[sistema=" + sistema + ", forma=" + forma);
		if (idUp != null) 
			sb.append(", id=" + idUp);
		if (columnas != null) 
			sb.append(", columnas=" + columnas);
		if (condiciones != null)
			sb.append(", condiciones=" + condiciones);
		if (adicionales != null)
			sb.append(", adicionales=" + adicionales);
		if (orden != null)
			sb.append(", orden=" + orden);
		if (nuevo != null)
			sb.append(", nuevo=" + nuevo);
		if (update != null)
			sb.append(", update=" + update);
		if (error != null)
			sb.append(", error=" + error);
		if (resultado != null)
			sb.append(", "+ resultado.toString());
		
		sb.append("]");
		return sb.toString();

	}
}
