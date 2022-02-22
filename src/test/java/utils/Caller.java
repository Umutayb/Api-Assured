package utils;

import org.junit.Assert;
import retrofit2.Call;
import retrofit2.Response;
import java.io.IOException;

public class Caller {
    static Printer log = new Printer(Caller.class);

    protected static <T> T perform(Call<T> call, Boolean strict, String serviceName){
        try {
            Response<T> response = call.execute();

            if (response.isSuccessful()){
                log.new Important(response.message());
                log.new Success("The response code is: " + response.code());
                return response.body();
            }
            else{
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