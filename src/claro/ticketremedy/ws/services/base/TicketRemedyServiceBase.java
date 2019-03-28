package claro.ticketremedy.ws.services.base;

import claro.ticketremedy.ws.util.TicketRemedyLogger;
import claro.ticketremedy.ws.util.TicketRemedyTiming;

public class TicketRemedyServiceBase {

	private static TicketRemedyLogger appLogger = TicketRemedyLogger.getInstance();

	protected TicketRemedyTiming appTiming;

	public TicketRemedyServiceBase() {

		// Setea el classloader del Thread si este esta en null
		if (Thread.currentThread().getContextClassLoader() == null)
			Thread.currentThread().setContextClassLoader(TicketRemedyServiceBase.class.getClassLoader());

		appTiming = new TicketRemedyTiming(appLogger.getTiming());
	}
}