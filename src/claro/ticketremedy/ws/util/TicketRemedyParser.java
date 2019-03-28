package claro.ticketremedy.ws.util;

import java.io.PrintStream;
import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import claro.ticketremedy.ws.services.Entry;
import claro.ticketremedy.ws.services.Field;
import claro.ticketremedy.ws.services.Resultado;
import claro.ticketremedy.ws.services.TicketRemedyResponse;

public class TicketRemedyParser extends DefaultHandler {
	
	private static TicketRemedyLogger logger = TicketRemedyLogger.getInstance();

	private static TicketRemedyResponse response;
	private Resultado resultado;
	private Entry entry;
	private Field field;
	private boolean bSistema = false;
	private boolean bForma = false;
	private boolean bColumnas = false;
	private boolean bCondiciones = false;
	private boolean bAdicionales = false;
	private boolean bResultado = false;
	private boolean bEntry = false;
	private boolean bField = false;
	private boolean bId = false;
	private boolean bValue = false;
	private boolean bNuevo = false;
	private boolean bError = false;
	private boolean bUpdate = false;
	private boolean bIdUp = false;
	private boolean bOrden = false;
    private String data = null;
    private String estoy = null;
    
	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		
		if (qName.equalsIgnoreCase("RMDINSERT") || qName.equalsIgnoreCase("RMDSELECT") || qName.equalsIgnoreCase("RMDUPDATE")) {
			response = new TicketRemedyResponse();
		} else if (qName.equals("SISTEMA")) {
			estoy = "SISTEMA";
			bSistema = true;
		} else if (qName.equals("FORMA")) {
			estoy = "FORMA";
			bForma = true;
		} else if (qName.equals("COLUMNAS")) {
			estoy = "COLUMNAS";
			bColumnas = true;
		} else if (qName.equals("ID")) {
			estoy = "ID";
			bIdUp = true;
		} else if (qName.equals("UPDATED")) {
			estoy = "UPDATED";
			bUpdate = true;
		} else if (qName.equals("ERROR")) {
			estoy = "ERROR";			
			bError = true;
		} else if (qName.equals("NUEVO")) {
			estoy = "NUEVO";			
			bNuevo = true;
		} else if (qName.equals("CONDICIONES")) {
			estoy = "CONDICIONES";			
			bCondiciones = true;
		} else if (qName.equals("ADICIONALES")) {
			estoy = "ADICIONALES";			
			bAdicionales = true;
		} else if (qName.equals("RESULTADO")) {
			estoy = "RESULTADO";			
			resultado = new Resultado();
			bResultado = true;
		} else if (qName.equals("Entry")) {
			estoy = "Entry";			
			entry = new Entry();
			bEntry = true;
		} else if (qName.equals("Field")) {
			estoy = "Field";			
			field = new Field();
			bField = true;
		} else if (qName.equals("id")) {
			estoy = "id";			
			bId = true;
		} else if (qName.equals("value")) {
			estoy = "value";			
			bValue = true;
		} else if (qName.equals("orden")) {
			estoy = "orden";
			bOrden = true;
		}

		data = "";
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		if (bSistema) {
			response.setSistema(data);
			bSistema = false;
		} else if (bForma) {
			response.setForma(data);
			bForma = false;
		} else if (bColumnas) {
			response.setColumnas(data);
			bColumnas = false;
		} else if (bIdUp) {
			response.setIdUp(data);
			bIdUp = false;
		} else if (bUpdate) {
			response.setUpdate(data);
			bUpdate = false;
		} else if (bError) {
			response.setError(data);
			bError = false;
		} else if (bNuevo) {
			response.setNuevo(data);
			bNuevo = false;
		} else if (bOrden) {
			response.setOrden(data);
			bOrden = false;
		} else if (bCondiciones) {
			response.setCondiciones(data);
			bCondiciones = false;
		} else if (bAdicionales) {
			response.setAdicionales(data);
			bAdicionales = false;
		} else if (bId) {
			field.setId(data);
			bId = false;
		} else if (bValue) {
			field.setValue(data);
			bValue = false;
		} else if (bField) {
			entry.addField(field);
			bField = false;
		} else if (bEntry) {
			resultado.addEntry(entry);
			bEntry = false;
		}  else if(bResultado) {
			response.setResultado(resultado);
			bResultado = false;
		} 
		
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {				
		data += new String(ch, start, length).trim();		
	}

	static public TicketRemedyResponse parser(String xml) throws Exception {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		SAXParser saxParser = spf.newSAXParser();
		XMLReader xmlReader = saxParser.getXMLReader();
		xmlReader.setContentHandler(new TicketRemedyParser());
		xmlReader.setErrorHandler(new MyErrorHandler(System.err));
		xmlReader.parse(new InputSource(new StringReader(xml)));
		return response;
	}

	private static class MyErrorHandler implements ErrorHandler {
		private PrintStream out;

		MyErrorHandler(PrintStream out) {
			this.out = out;
		}

		private String getParseExceptionInfo(SAXParseException spe) {
			String systemId = spe.getSystemId();
			if (systemId == null) {
				systemId = "null";
			}
			String info = "URI=" + systemId + " Line=" + spe.getLineNumber() + ": " + spe.getMessage();
			return info;
		}

		public void warning(SAXParseException spe) throws SAXException {
			out.println("Warning: " + getParseExceptionInfo(spe));
		}

		public void error(SAXParseException spe) throws SAXException {
			String message = "Error: " + getParseExceptionInfo(spe);
			throw new SAXException(message);
		}

		public void fatalError(SAXParseException spe) throws SAXException {
			String message = "Fatal Error: " + getParseExceptionInfo(spe);
			throw new SAXException(message);
		}
	}
}