package utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import retrofit2.Call;
import org.junit.Assert;
import retrofit2.Response;
import java.io.IOException;

public class Caller {

    static Printer log = new Printer(Caller.class);
    static ObjectMapper objectMapper = new ObjectMapper();

    public Caller(){
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    protected static <T> T perform(Call<T> call, Boolean strict, Boolean printBody, String serviceName) {
        log.new Info("Performing an api call to " + call.request().url());
        try {
            Response<T> response = call.execute();

            if (printBody && response.body() != null)
                log.new Info("The response body is: " + "\n" + objectMapper.valueToTree(response.body()).toPrettyString());

            if (response.isSuccessful()){
                if (response.message().length()>0)
                    log.new Info(response.message());
                log.new Success("The response code is: " + response.code());
            }
            else{
                if (response.message().length()>0)
                    log.new Warning(response.message());
                log.new Warning("The response code is: " + response.code());
                log.new Warning(response.raw());

                if (strict)
                    Assert.fail("The strict call performed for " + serviceName + " service returned response code " + response.code());
            }
            return response.body();
        }
        catch (IOException e) {
            log.new Error(e.getStackTrace());
            Assert.fail("The call performed for " + serviceName + " failed for an unknown reason.");
        }
        return null;
    }

    protected static <T> Response<T> getResponse(Call<T> call, Boolean strict, Boolean printBody, String serviceName) {
        log.new Info("Performing an api call to " + call.request().url());
        try {
            Response<T> response = call.execute();

            if (printBody && response.body() != null)
                log.new Info("The response body is: " + "\n" + objectMapper.valueToTree(response.body()).toPrettyString());

            if (response.isSuccessful()){
                if (response.message().length()>0)
                    log.new Info(response.message());
                log.new Success("The response code is: " + response.code());
            }
            else{
                if (response.message().length()>0)
                    log.new Warning(response.message());
                log.new Warning("The response code is: " + response.code());
                log.new Warning(response.raw());

                if (strict)
                    Assert.fail("The strict call performed for " + serviceName + " service returned response code " + response.code());

            }
            return response;
        }
        catch (IOException e) {
            log.new Error(e.getStackTrace());
            log.new Error(e.getCause());
            Assert.fail("The call performed for " + serviceName + " failed for an unknown reason.");
        }
        return null;
    }
}