package utils;

import retrofit2.Call;
import org.junit.Assert;
import retrofit2.Response;
import java.io.IOException;

public class Caller {

    static Printer log = new Printer(Caller.class);

    protected static <T> T perform(Call<T> call, Boolean strict, Boolean body, String serviceName) {
        log.new Info("Performing an api call to " + call.request().url());
        try {
            Response<T> response = call.execute();

            if (response.isSuccessful()){
                if (response.message().length()>0)
                    log.new Info(response.message());
                log.new Success("The response code is: " + response.code());
                if (body)
                    return (T) response;
                else
                    return response.body();
            }
            else{
                if (response.message().length()>0)
                    log.new Warning(response.message());
                log.new Warning("The response code is: " + response.code());
                if (strict)
                    Assert.fail("The strict call performed for " + serviceName + " service returned response code " + response.code());
            }
        }
        catch (IOException e) {
            log.new Error(e.getStackTrace());
            Assert.fail("The call performed for " + serviceName + " failed for an unknown reason.");
        }
        return null;
    }
}