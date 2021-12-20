package contact;

import com.google.gson.Gson;
import dto.ContactDto;
import dto.GetAllContactsDto;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class OkHttpGetAllContacts {
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imdvb2R3aW40OUBtYWlsLnJ1In0.d7BPB8XKJCzQScHguPqhaYvHrQpV1F-Qy_NXkSddg9s";
    OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();

    @Test
    public void getAllContactTest() throws IOException {

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .addHeader("Authorization",token)
                .build();

        Response responce = client.newCall(request).execute();

        Assert.assertTrue(responce.isSuccessful());

        GetAllContactsDto contacts = gson.fromJson(responce.body().string(), GetAllContactsDto.class);
        for(ContactDto contactDto:contacts.getContacts()){
            System.out.println(contactDto.getId());
            System.out.println(contactDto.getEmail());
            System.out.println("*********************");
        }

    }
}
