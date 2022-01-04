package contact;

import com.google.gson.Gson;
import dto.ContactDto;
import dto.DeleteContactResponce;
import dto.ErrorDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;


public class OkkHttpDeleteContact {

    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imdvb2R3aW40OUBtYWlsLnJ1In0.d7BPB8XKJCzQScHguPqhaYvHrQpV1F-Qy_NXkSddg9s";
    int id;

    @BeforeMethod
    public void preRequest() throws IOException {
        ContactDto contactDto = ContactDto.builder()
                .address("Haifa")
                .description("Friend")
                .email("poedx12@gmail.com")
                .lastName("Petr")
                .name("Ivanof")
                .phone("0534582382")
                .build();


        RequestBody requestBody = RequestBody.create(gson.toJson(contactDto), JSON);
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .addHeader("Authorization", token)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        String responceJson = response.body().string();
        ContactDto contactDto1 = gson.fromJson(responceJson, ContactDto.class);
        id = contactDto1.getId();
        System.out.println(id);
    }


    @Test
    public void deleteContactTest() throws IOException {

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
