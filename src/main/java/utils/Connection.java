package utils;

import java.io.File;
import resources.Database;
import static resources.Colors.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Connection {

    public JsonUtilities jsonUtilities = new JsonUtilities();
    Printer log = new Printer(Connection.class);
    RequestSpecification request;
    Response response;
    String requestUrl;
    String url;
    String uri;

    public Connection(String url, String uri){
        this.url = url;
        this.uri = uri;
    }

    public class Get{
        Response response;
        public Get(Object input){
            requestUrl = "https://"+url+uri;

            log.new Info("Performing "+BLUE+"GET"+GRAY+" call at: \""+BLUE+requestUrl+GRAY+"\""+RESET);

            response = RestAssured.get(requestUrl);

            printStatusCode(response);

            if(response.getStatusCode()==200){
                Database.contextJSON = jsonUtilities.str2json(response.asString());
                Database.serverResponse = jsonUtilities.str2json(response.asString());

            }
        }

        public Response response(){return response;}
    }

    public class Delete{
        Response response;
        public Delete(Object input){
            requestUrl = "https://"+url+uri;

            log.new Info("Performing "+BLUE+"DELETE"+GRAY+" call at: \""+BLUE+requestUrl+GRAY+"\""+RESET);

            request = RestAssured.given().header("Accept","application/json");

            response = request.delete(requestUrl);

            printStatusCode(response);

            Database.serverResponse = jsonUtilities.str2json(response.asString());
        }

        public Response response(){return response;}
    }

    public class Post{
        Response response;
        public Post(Object input){
            requestUrl = "https://"+url+uri;

            log.new Info("Performing "+BLUE+"POST"+GRAY+" call at: \""+BLUE+requestUrl+GRAY+"\""+RESET);

            request = RestAssured.given().header("Content-Type","application/json").header("Accept","application/json");

            if (input!=null && input!="")
                request.body(input);

            response = request.post(requestUrl);

            printStatusCode(response);

            Database.serverResponse = jsonUtilities.str2json(response.asString());
        }

        public Response response(){return response;}
    }

    public class Put{
        Response response;
        public Put(Object input){
            requestUrl = "https://"+url+uri;

            log.new Info("Performing "+BLUE+"PUT"+GRAY+" call at: \""+BLUE+requestUrl+GRAY+"\""+RESET);

            request = RestAssured.given().header("Content-Type","application/json").header("Accept","application/json");

            if (input!=null && input!="")
                request.body(input);

            response = request.put(requestUrl);

            printStatusCode(response);

            Database.serverResponse = jsonUtilities.str2json(response.asString());

        }

        public Response response(){return response;}
    }

    public class Upload{
        Response response;
        public Upload(String url, String uri, Object file, String fileUrl){
            Response response;

            RequestSpecification request;

            String requestUrl = "https://"+url+uri;

            log.new Info("Performing "+BLUE+"post"+GRAY+" call at: \""+BLUE+requestUrl+GRAY+"\""+RESET);

            request = RestAssured.given().multiPart("file", new File(fileUrl), "image/jpeg");

            if (file!=null && file!="")
                request.body(file);

            response = request.post(requestUrl);

            printStatusCode(response);

            Database.serverResponse = jsonUtilities.str2json(response.asString());

        }

        public Response response(){return response;}
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
}
