package utils;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Caller {

    protected static <T> T perform(Call<T> call) throws IOException {

        call.timeout().timeout(1, TimeUnit.SECONDS).throwIfReached();

        Response<T> response = call.execute();

        if (response.isSuccessful()){
            return response.body();}
        else {call.cancel();
            return null;}
    }

}