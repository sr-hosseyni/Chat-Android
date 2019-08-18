package ir.sircoder.sirchat.network;

import ir.sircoder.sirchat.service.Singleton;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sr_hosseini on 7/28/18.
 */
public class RetrofitInstance {

    private static Retrofit retrofit;
//    private static final String BASE_URL = "http://10.0.2.2:7777/";
    private static final String BASE_URL = Singleton.getInstance().getServerInformation().getHttpApiBaseURI();

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}