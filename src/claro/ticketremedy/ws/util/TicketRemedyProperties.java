package claro.ticketremedy.ws.util;

import java.io.InputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;

import claro.ticketremedy.ws.exception.TicketRemedyRuntimeException;

import java.util.HashSet;
import java.util.Iterator;

public class TicketRemedyProperties {

  private static int MONITOR_SLEEP = 60000;
  private static String APP_CONFIG_FILE = "claro" + File.separator +
                                          "ticketremedy" + File.separator +
                                          "ws" + File.separator +                                          
                                          "ticketremedy_config.properties";
  private static TicketRemedyProperties appProperties = new TicketRemedyProperties();
                                           
  private Properties properties = null;
  private HashSet propertiesToMonitor = new HashSet();
  private HashSet objectsToCallByMonitor = new HashSet();
  private PropertiesMonitor propertiesMonitor = null;
  
  private class PropertiesMonitor extends Thread {
  
    public void run() {

      TicketRemedyLogger logger = null;
      while (true) {
        try {
          if (logger == null) {
            logger = TicketRemedyLogger.getInstance();
            if (logger != null)
              logger.log("PropertiesMonitor -> iniciado OK");
          }

          sleep(MONITOR_SLEEP);
      
          if (!propertiesToMonitor.isEmpty() && !objectsToCallByMonitor.isEmpty()) {
            boolean callToObjects = false;
            Properties auxProperties = getProperties();
            
            Iterator iterator = propertiesToMonitor.iterator(); 
            String auxPropertyKey = null;
            String auxPropertyValue = null;
            String currPropertyValue = null;
            while (iterator.hasNext()) {
              auxPropertyKey = (String)iterator.next(); 
              auxPropertyValue = auxProperties.getProperty(auxPropertyKey);
              currPropertyValue = properties.getProperty(auxPropertyKey); 
              if ((auxPropertyValue != null && currPropertyValue == null) ||
                  (auxPropertyValue != null && currPropertyValue != null && !currPropertyValue.equals(auxPropertyValue))) {               
                properties.setProperty(auxPropertyKey, auxPropertyValue);
                callToObjects = true;
              } else if (auxPropertyValue == null && currPropertyValue != null) {
                properties.remove(auxPropertyKey);
                callToObjects = true;
              }
            }              

            if (callToObjects) {
              iterator = objectsToCallByMonitor.iterator(); 
              TicketRemedyPropertiesMonitor auxAppPropertiesMonitor = null;
              while (iterator.hasNext()) {
                auxAppPropertiesMonitor = (TicketRemedyPropertiesMonitor)iterator.next(); 
                try {
                  auxAppPropertiesMonitor.callByPropertiesMonitor();
                } catch (Exception e) {
                  if (logger != null) 
                    logger.warning("PropertiesMonitor -> Exception: " + e.toString());
                }                
              }
            }              
          }                      

          if (logger != null) 
            logger.debug("PropertiesMonitor -> validacion de cambios en \"" + APP_CONFIG_FILE + "\" OK");
        } catch (Exception e) {
          if (logger != null) 
            logger.warning("PropertiesMonitor -> Exception: " + e.toString());
        }          
      }     
    }
  }
  
  private TicketRemedyProperties() {

    properties = getProperties();
    propertiesMonitor = new PropertiesMonitor(); 
    propertiesMonitor.setName("PropertiesMonitor");
    propertiesMonitor.setDaemon(true);
    propertiesMonitor.start();
  }    

  private static Properties getProperties() {

    InputStream inputStream = null;
    Properties auxProperties = null;
    try {
      inputStream = TicketRemedyProperties.class.getClassLoader().getResourceAsStream(APP_CONFIG_FILE);
      if (inputStream == null) throw new FileNotFoundException(APP_CONFIG_FILE);

      auxProperties = new Properties();
      auxProperties.load(inputStream);
      
      return auxProperties;
    } catch (Exception e) {
      throw new TicketRemedyRuntimeException(e.toString());        
    } finally {
      try {
        if (inputStream != null) inputStream.close();
      } catch (Exception e) {}
    }        
  }
  
  public static TicketRemedyProperties getInstance() {
      
    return appProperties;      
  }
  
  public String getProperty(String prefix, String property) {
    
    String aux = properties.getProperty(prefix + "." + property);  
    return ((aux == null) ? aux : aux.trim());   
  }

  public String getProperty(String prefix, String property, boolean isRequired) { 

    String value = getProperty(prefix, property);
    if (value == null && isRequired)
      throw new TicketRemedyRuntimeException("Propiedad \"" + prefix + "." + property + "\" es obligatoria en el archivo de propiedades \"" + APP_CONFIG_FILE + "\"");

    return value;      
  }
  
  public String getPropertyKey(String prefix, String property) {
  
    return ((prefix != null && property != null) ? prefix + "." + property : null);          
  }
  
  public void registerToPropertiesMonitor(TicketRemedyPropertiesMonitor appPropertiesMonitor, HashSet propertiesToMonitor) {
      
    if (appPropertiesMonitor != null && propertiesToMonitor != null && !propertiesToMonitor.isEmpty()) {
      objectsToCallByMonitor.add(appPropertiesMonitor);

      Iterator iterator = propertiesToMonitor.iterator();
      String auxPropertyKey = null;
      while (iterator.hasNext()) {
        auxPropertyKey = (String)iterator.next();
        if (auxPropertyKey != null)
          this.propertiesToMonitor.add(auxPropertyKey);
      }        
    }
  }
}



