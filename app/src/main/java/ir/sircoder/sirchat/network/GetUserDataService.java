package ir.sircoder.sirchat.network;

import ir.sircoder.sirchat.model.UserList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sr_hosseini on 7/28/18.
 */
public interface GetUserDataService {
    @GET("users")
//    Call<UserList> getUserData(@Query("company_no") int companyNo);
    Call<UserList> getUserData();
}
