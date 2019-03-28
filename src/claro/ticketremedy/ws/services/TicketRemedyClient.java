package claro.ticketremedy.ws.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import claro.ticketremedy.ws.exception.TicketRemedyRuntimeException;
import claro.ticketremedy.ws.services.base.TicketRemedyServiceBase;
import claro.ticketremedy.ws.services.base.TicketRemedyServiceProperties;
import claro.ticketremedy.ws.util.TicketRemedyLogger;
import claro.ticketremedy.ws.util.TicketRemedyParser;

public final class TicketRemedyClient extends TicketRemedyServiceBase {

	private String request;

	private static String WS_CONFIG_NAME = "TicketRemedy";
	private static TicketRemedyServiceProperties ticketRemedyProperties = new TicketRemedyServiceProperties(WS_CONFIG_NAME);

	private static TicketRemedyLogger logger = TicketRemedyLogger.getInstance();

	public TicketRemedyResponse sendRequest(TicketRemedyRequest request) {
		this.request = request.generateEndpoint();
		TicketRemedyResponse response = invokeWS();
		return response;
	}

	private TicketRemedyResponse invokeWS() {

		OutputStreamWriter out = null;
		BufferedReader in = null;
		
		StringBuffer responseConn = new StringBuffer();
		URL url = null;
		HttpURLConnection urlConn = null;
		TicketRemedyResponse ticketResponse = null;

		try {
			
			logger.debug("Request inicial----: " + this.request);
			String encodeXML = (this.request.replaceAll(" ", "%20")).replaceAll("'", "%27");
			url = new URL(ticketRemedyProperties.getEndpointAddress() + encodeXML);
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setUseCaches(false);
			urlConn.setDoOutput(true);
			urlConn.setRequestMethod("GET");
			urlConn.setRequestProperty("accept-charset", "UTF-8");
			urlConn.setRequestProperty("Accept", "application/xml");
			urlConn.setConnectTimeout(Integer.parseInt(ticketRemedyProperties.getHttpReadTimeout()));
			urlConn.setReadTimeout(Integer.parseInt(ticketRemedyProperties.getHttpReadTimeout()));

			logger.debug("Request: " + this.request);
			logger.debug("Sending request to " + ticketRemedyProperties.getEndpointAddress());

			InputStream is = null;

			try {
				is = urlConn.getInputStream();
			} catch (IOException e) {
				if (urlConn instanceof HttpURLConnection) {
					HttpURLConnection httpConn = (HttpURLConnection) urlConn;
					int statusCode = httpConn.getResponseCode();
					if (statusCode != 200) {
						is = httpConn.getErrorStream();
					}
				}
			}

			InputStreamReader ios = new InputStreamReader(is, "UTF-8");
			in = new BufferedReader(ios);
			logger.debug("Getting response...");

			int content;
			
            while ((content = in.read()) != -1) {
                            responseConn.append((char)content);
            }

		} catch (MalformedURLException e) {
			throw new TicketRemedyRuntimeException("Exception -> [Message: Malformed URL]");
		} catch (IOException e) {
			throw new TicketRemedyRuntimeException(
					"Exception -> [Message: ERROR connecting to Ticket Remedy Server -->" + e.getMessage() + "]", e);
		} catch (Throwable t) {
			throw new TicketRemedyRuntimeException(
					"Exception -> [Message: Unexpected exception -->" + t.getMessage() + "]", t);
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
			}

			try {
				if (in != null) {
					in.close();
					logger.debug("Connection close to Ticket Remedy Server");
				}

			} catch (IOException e) {
			}

		}

		logger.debug("Response: " + responseConn.toString());
		try {
			ticketResponse = TicketRemedyParser.parser(responseConn.toString());
			
		} catch (Exception e) {
			throw new TicketRemedyRuntimeException(
					"Exception -> [Message: ERROR parsing response -->" + e.getMessage() + "]", e);
		}
		
		return ticketResponse;

	}


}
