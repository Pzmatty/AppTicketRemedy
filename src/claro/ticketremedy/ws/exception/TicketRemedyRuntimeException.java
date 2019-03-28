package claro.ticketremedy.ws.exception;

import claro.ticketremedy.ws.util.TicketRemedyLogger;

public class TicketRemedyRuntimeException extends RuntimeException {

  public TicketRemedyRuntimeException(String message) {
    this(message, null);
  }
  
  public TicketRemedyRuntimeException(String message, Throwable throwable) {
    super(message, throwable);  
    TicketRemedyLogger.getInstance().warning(message);
  }
}
