package claro.ticketremedy.ws.util;

import java.util.Calendar;
import java.util.HashSet;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketRemedyLogger implements TicketRemedyPropertiesMonitor {

	private static String PREFIX_PROPERTIES = "logger";
	private static String PROPERTY_FILE = "file";
	private static String PROPERTY_LIMIT = "limit";
	private static String PROPERTY_COUNT = "count";
	private static String PROPERTY_LEVEL = "level";
	private static String PROPERTY_TIMING = "timing";
	private static TicketRemedyProperties appProperties = TicketRemedyProperties.getInstance();
	private static TicketRemedyLogger appLogger = new TicketRemedyLogger();

	private Logger logger = null;
	private Level level = Level.INFO;
	private int timing = 0;
	private boolean isLogger = false;

	private TicketRemedyLogger() {

		logConsole("Iniciando Logger de la aplicacion Ticket Remedy");

		logger = Logger.getLogger("amx.claro.ticketremedy");
		logger.setUseParentHandlers(false);
		logger.setLevel(level);
		String applicationLogfile = null;
		int limit = 10000000;
		int count = 30;

		try {
			applicationLogfile = appProperties.getProperty(PREFIX_PROPERTIES, PROPERTY_FILE, true);

			try {
				limit = Integer.parseInt(appProperties.getProperty(PREFIX_PROPERTIES, PROPERTY_LIMIT, false));
			} catch (Exception e) {
			}

			try {
				count = Integer.parseInt(appProperties.getProperty(PREFIX_PROPERTIES, PROPERTY_COUNT, false));
			} catch (Exception e) {
			}

			FileHandler fileHandler = new FileHandler(applicationLogfile, limit, count, true);
			fileHandler.setLevel(Level.ALL);
			fileHandler.setFormatter(new TicketRemedyLoggingFormatter());
			logger.addHandler(fileHandler);
			isLogger = true;
			logConsole("Log en \"" + applicationLogfile + "\"");
		} catch (Exception exception) {
			logConsole("Error creando el archivo de log "
					+ ((applicationLogfile != null) ? "\"" + applicationLogfile + "\"" : "") + ": "
					+ exception.toString());
			logConsole("Log en consola");
		}

		try {
			setLevel(appProperties.getProperty(PREFIX_PROPERTIES, PROPERTY_LEVEL, false));
		} catch (Exception e) {
		}
		try {
			timing = Integer.parseInt(appProperties.getProperty(PREFIX_PROPERTIES, PROPERTY_TIMING, false));
		} catch (Exception e) {
		}

		logConsole("Logger iniciado en nivel " + level.getName() + ", timing " + timing);

		if (isLogger) {
			log("--------------------------------------------------------------------------------");
			log("Logger iniciado OK [Nivel " + level.getName() + ", Limite " + limit + ", Cantidad " + count
					+ ", Timing " + timing + "]");
		}

		HashSet propertiesToMonitor = new HashSet();
		propertiesToMonitor.add(appProperties.getPropertyKey(PREFIX_PROPERTIES, PROPERTY_LEVEL));
		propertiesToMonitor.add(appProperties.getPropertyKey(PREFIX_PROPERTIES, PROPERTY_TIMING));
		appProperties.registerToPropertiesMonitor(this, propertiesToMonitor);
	}

	public static TicketRemedyLogger getInstance() {

		return appLogger;
	}

	public void setLevel(String level) {

		try {
			if (level != null)
				this.level = Level.parse(level.toUpperCase());
		} catch (Exception exception) {
			log("Nivel de log \"" + level + "\" invalido");
		}

		if (isLogger)
			logger.setLevel(this.level);
	}

	public boolean isDebug() {

		return (level.intValue() == Level.FINE.intValue());
	}

	public int getTiming() {

		return timing;
	}

	public void log(String text) {

		log(Level.INFO, text);
	}

	public void debug(String text) {

		log(Level.FINE, text);
	}

	public void warning(String text) {

		log(Level.WARNING, text);
	}

	private void log(Level level, String text) {

		try {
			if (isLogger)
				logger.log(level, text);
			else if (level.intValue() >= this.level.intValue())
				logConsole(level.getName() + " " + text);
		} catch (Exception exception) {
			try {
				logConsole("Error escribiendo al log: " + exception.toString());
			} catch (Exception exception1) {
			}
		}
	}

	private static void logConsole(String text) {
		System.out.println(TicketRemedyLoggingFormatter.simpleDateFormat.format(Calendar.getInstance().getTime()) + " " + text);
	}

	public void callByPropertiesMonitor() {

		String auxString;
		int auxInt;

		try {
			auxString = appProperties.getProperty(PREFIX_PROPERTIES, PROPERTY_LEVEL, false);
			if (auxString == null)
				auxString = Level.INFO.getName();
			if (!level.getName().equalsIgnoreCase(auxString)) {
				setLevel(auxString);
				log("Logger cambio Nivel a " + level.getName());
			}
		} catch (Exception e) {
		}

		try {
			auxInt = 0;
			auxString = appProperties.getProperty(PREFIX_PROPERTIES, PROPERTY_TIMING, false);
			try {
				auxInt = (auxString == null) ? 0 : Integer.parseInt(auxString);
				if (auxInt != 0)
					auxInt = 1;
			} catch (Exception e) {
			}

			if (timing != auxInt) {
				timing = auxInt;
				log("Logger cambio Timing a " + timing);
			}
		} catch (Exception e) {
		}
	}
}
