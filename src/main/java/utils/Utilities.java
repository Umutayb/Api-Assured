package utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import static resources.Colors.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import resources.Database;

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
        this.url = url;
        this.uri = uri;
        try {properties.load(new FileReader("src/test/resources/test.properties"));}
        catch (IOException e) {e.printStackTrace();}
    }

    String url;
    String uri;



    public Response uploadFile(String url, String uri, Object file, String fileUrl){

        Response response = null;

        RequestSpecification request;

        String requestUrl = "https://"+url+uri;

        log.new Info("Performing "+BLUE+"post"+GRAY+" request at: \""+BLUE+requestUrl+GRAY+"\""+RESET);

        request = RestAssured.given().multiPart("file", new File(fileUrl), "image/jpeg");

        if (file!=null && file!="")
            request.body(file);
        else
            Assert.fail(YELLOW+"The input cannot be null"+RESET);

        response = request.post(requestUrl);

        printStatusCode(response);

        Database.serverResponse = jsonUtilities.str2json(response.asString());

        return response;

    }

    public void printStatusCode(Response response){
        if (response.getStatusCode()==200)
            log.new Info("Server response: "+GREEN+response.getStatusCode()+RESET);

        else if (response.getStatusCode()==500)
            log.new Info("Server response: "+RED+response.getStatusCode()+RESET);

        else if (response.getStatusCode()==404)
            log.new Info("Server response: "+YELLOW+response.getStatusCode()+RESET);

        else
            log.new Info("Server response: "+BLUE_BACKGROUND+response.getStatusCode()+RESET);
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
