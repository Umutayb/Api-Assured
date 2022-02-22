package utils;

import exceptions.FailedCallException;
import retrofit2.Call;
import retrofit2.Response;
import java.io.IOException;

public class Caller {

    static Printer log = new Printer(Caller.class);

    protected static <T> T perform(Call<T> call, Boolean strict, String serviceName) throws FailedCallException {
        try {
            Response<T> response = call.execute();

            if (response.isSuccessful()){
                if (response.message().length()>0)
                    log.new Info(response.message());
                log.new Success("The response code is: " + response.code());
                return response.body();
            }
            else{
                log.new Warning(response.message());
                log.new Warning("The response code is: " + response.code());
                if (response.errorBody()!=null)
                    log.new Warning(response.errorBody());
                if (strict)
                    throw new FailedCallException("The strict call performed for " + serviceName + " service returned response code " + response.code());
                return null;
            }
        }
        catch (IOException e) {
            log.new Error(e.getStackTrace());
            throw new FailedCallException("The call performed for " + serviceName + " failed for an unknown reason.");
        }
    }
}