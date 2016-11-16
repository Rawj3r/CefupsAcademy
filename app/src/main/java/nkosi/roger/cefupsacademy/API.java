package nkosi.roger.cefupsacademy;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by lulu on 10/24/2016.
 */

public interface API {

    @FormUrlEncoded
    @POST("/index.php")
    void getPosts(@FieldMap Map<String, String> map, Callback<String> callback);

    @FormUrlEncoded
    @POST("/index.php")
    void getSlides(@FieldMap Map<String, String> map, Callback<String> callback);

    @FormUrlEncoded
    @POST("/index.php")
    void getProfiles(@FieldMap Map<String, String> map, Callback<String> callback);
}
