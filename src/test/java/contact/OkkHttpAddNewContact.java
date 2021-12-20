package contact;

import com.google.gson.Gson;
import dto.ContactDto;
import dto.ErrorDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class OkkHttpAddNewContact {

    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");


    @Test
    public void addNewContact() throws IOException {
        ContactDto contactDto = ContactDto.builder()
                .address("Haifa")
                .description("Friend")
                .email("ooop123@gmail.com")
                .id(14689)
                .lastName("Petr")
                .name("Ivanof")
                .phone("0534583")
                .build();

        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imdvb2R3aW40OUBtYWlsLnJ1In0.d7BPB8XKJCzQScHguPqhaYvHrQpV1F-Qy_NXkSddg9s";

        RequestBody requestBody = RequestBody.create(gson.toJson(contactDto), JSON);
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .addHeader("Authorization", token)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        String responceJson = response.body().string();
        ContactDto contactDto1 = gson.fromJson(responceJson, ContactDto.class);
        ErrorDto errorDto = gson.fromJson(responceJson, ErrorDto.class);
        System.out.println(errorDto.getMessage());
        System.out.println(errorDto.getDetails());

        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals("Ivan", contactDto1.getName());

    }

    @Test
    public void addNewContactNegative400() throws IOException {
        ContactDto contactDto = ContactDto.builder()
                .address("Haifa")
                .description("Friend")
                .email("aaaa123@gmail.com")
                .id(123456)
                .lastName("")
                .name("Ivan")
                .phone("0537234566")
                .build();

        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imdvb2R3aW40OUBtYWlsLnJ1In0.d7BPB8XKJCzQScHguPqhaYvHrQpV1F-Qy_NXkSddg9s";

        RequestBody requestBody = RequestBody.create(gson.toJson(contactDto), JSON);
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .addHeader("Authorization", token)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        String responceJson = response.body().string();
        ErrorDto errorDto = gson.fromJson(responceJson, ErrorDto.class);

        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(400, errorDto.getCode());
        Assert.assertTrue(errorDto.getMessage().contains("Wrong contact format"));

    }

    @Test
    public void addNewContactNegative401() throws IOException {
        ContactDto contactDto = ContactDto.builder()
                .address("Haifa")
                .description("Friend")
                .email("aaaa123@gmail.com")
                .id(123456)
                .lastName("Petrov")
                .name("Ivan")
                .phone("0537234566")
                .build();

        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();
        String token = "";

        RequestBody requestBody = RequestBody.create(gson.toJson(contactDto), JSON);
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .addHeader("Authorization", token)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        String responceJson = response.body().string();
        ErrorDto errorDto = gson.fromJson(responceJson, ErrorDto.class);

        System.out.println(errorDto.getMessage());
        System.out.println(errorDto.getDetails());
        System.out.println(errorDto.getCode());

        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(401, errorDto.getCode());
        Assert.assertTrue(errorDto.getMessage().contains("Wrong authorization"));

    }
}
