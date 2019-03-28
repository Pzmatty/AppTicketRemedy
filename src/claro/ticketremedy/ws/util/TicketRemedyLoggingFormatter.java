package claro.ticketremedy.ws.util;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.text.SimpleDateFormat;

public class TicketRemedyLoggingFormatter extends Formatter {

  public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM HH:mm:ss");

  public TicketRemedyLoggingFormatter() {
  }

  public String format(LogRecord logRecord) {

    String level = (logRecord.getLevel()).getLocalizedName();
    String myDate = simpleDateFormat.format(new Date(logRecord.getMillis()));
    return myDate + " " +
           level  + " " +
           "[" + Thread.currentThread().getName() + "]: " +
           logRecord.getMessage() + '\n';
  }
}
