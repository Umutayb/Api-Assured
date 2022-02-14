package utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import static resources.Colors.*;
import org.junit.Assert;

public abstract class Utilities {

    Printer log = new Printer(Utilities.class);

    public TextParser parser = new TextParser();
    public Properties properties = new Properties();
    public EmailUtilities email = new EmailUtilities();
    public FileUtilities fileUtils = new FileUtilities();
    public JsonUtilities jsonUtilities = new JsonUtilities();
    public XPathUtilities xpathUtils = new XPathUtilities();
    public StringUtilities strUtils = new StringUtilities();
    public NumericUtilities numeric = new NumericUtilities();
    public TerminalUtilities terminal = new TerminalUtilities();

    public Utilities(String url, String uri){
        try {properties.load(new FileReader("src/test/resources/test.properties"));}
        catch (IOException e) {e.printStackTrace();}
    }

    //This method makes the thread wait for a certain while
    public void waitFor(double seconds){
        if (seconds > 1)
            log.new Info("Waiting for "+BLUE+seconds+GRAY+" seconds");
        try {
            Thread.sleep((long) (seconds* 1000L));
        }
        catch (InterruptedException exception){
            Assert.fail(GRAY+exception.getLocalizedMessage()+RESET);
        }
    }

}
