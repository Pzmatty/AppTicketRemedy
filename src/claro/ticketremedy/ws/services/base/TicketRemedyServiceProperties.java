package claro.ticketremedy.ws.services.base;

import claro.ticketremedy.ws.util.TicketRemedyLogger;
import claro.ticketremedy.ws.util.TicketRemedyProperties;

public class TicketRemedyServiceProperties {

	private static String PREFIX_DEFAULT_PROPERTIES = "wsDefault";
	private static String PROPERTY_USERNAME = "username";
	private static String PROPERTY_PASSWORD = "password";
	private static String PROPERTY_HTTP_READ_TIMEOUT = "httpReadTimeout";
	private static String PROPERTY_ENDPOINT_ADDRESS = "endpointAddress";
	private static TicketRemedyProperties appProperties = TicketRemedyProperties.getInstance();
	private static TicketRemedyLogger appLogger = TicketRemedyLogger.getInstance();

	private String endpointAddress;
	private String username;
	private String password;
	private String httpReadTimeout;
	private String allProperties;

	public TicketRemedyServiceProperties(String wsConfigName) {

		endpointAddress = appProperties.getProperty(wsConfigName, PROPERTY_ENDPOINT_ADDRESS, true);

		username = appProperties.getProperty(wsConfigName, PROPERTY_USERNAME);
		if (username == null)
			username = appProperties.getProperty(PREFIX_DEFAULT_PROPERTIES, PROPERTY_USERNAME, true);

		password = appProperties.getProperty(wsConfigName, PROPERTY_PASSWORD);
		if (password == null)
			password = appProperties.getProperty(PREFIX_DEFAULT_PROPERTIES, PROPERTY_PASSWORD, true);

		httpReadTimeout = appProperties.getProperty(wsConfigName, PROPERTY_HTTP_READ_TIMEOUT);
		if (httpReadTimeout == null)
			httpReadTimeout = appProperties.getProperty(PREFIX_DEFAULT_PROPERTIES, PROPERTY_HTTP_READ_TIMEOUT, true);

		allProperties = wsConfigName + " -> Propiedades [Username " + username + ", HttpReadTimeout " + httpReadTimeout
				+ ", EndpointAddress " + endpointAddress + "]";
		appLogger.log(allProperties);
	}

	public String getEndpointAddress() {

		return endpointAddress;
	}

	public String getUsername() {

		return username;
	}

	public String getPassword() {

		return password;
	}

	public String getHttpReadTimeout() {

		return httpReadTimeout;
	}

	public String getAllProperties() {

		return allProperties;
	}
}
