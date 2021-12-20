package contact;

import com.google.gson.Gson;
import dto.DeleteContactResponce;
import dto.ErrorDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class OkkHttpDeleteContact {

    @Test
    public void deleteContactTest() throws IOException {

        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imdvb2R3aW40OUBtYWlsLnJ1In0.d7BPB8XKJCzQScHguPqhaYvHrQpV1F-Qy_NXkSddg9s";
        String id = "18723";

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact/" + id)
                .addHeader("Authorization", token)
                .delete()
                .build();

        Response response = client.newCall(request).execute();
        String responceString = response.body().string();
        DeleteContactResponce deleteContactResponce = gson.fromJson(responceString, DeleteContactResponce.class);

        Assert.assertTrue(response.isSuccessful());
        Assert.assertTrue(deleteContactResponce.getStatus().contains("Contact was deleted"));

    }

    @Test
    public void deleteContactNegativeTest404() throws IOException {

        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imdvb2R3aW40OUBtYWlsLnJ1In0.d7BPB8XKJCzQScHguPqhaYvHrQpV1F-Qy_NXkSddg9s";
        String id = "123";

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact/" + id)
                .addHeader("Authorization", token)
                .delete()
                .build();

        Response response = client.newCall(request).execute();
        String responceString = response.body().string();
        ErrorDto errorDto = gson.fromJson(responceString, ErrorDto.class);

        Assert.assertFalse(response.isSuccessful());
        Assert.assertTrue(errorDto.getMessage().contains("not found"));


    }

    @Test
    public void deleteContactNegativeTest401() throws IOException {

        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();
        String token = "";
        String id = "123";

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact/" + id)
                .addHeader("Authorization", token)
                .delete()
                .build();

        Response response = client.newCall(request).execute();
        String responceString = response.body().string();
        ErrorDto errorDto = gson.fromJson(responceString, ErrorDto.class);

        Assert.assertFalse(response.isSuccessful());
        Assert.assertTrue(errorDto.getMessage().contains("Wrong authorization"));


    }


}
