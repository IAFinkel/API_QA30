package contact;

import com.google.gson.Gson;
import dto.AuthRequestDto;
import dto.AuthResponceDto;
import dto.ErrorDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class OkkHttpLoginTest {

    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");//Parameter content type + кодировка. только в запросах где есть budy

    @Test
    public void loginTest() throws IOException {

        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("goodwin49@mail.ru")
                .password("Gor12345$")
                .build();

        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDto), JSON); //превращение объекта в JSON (body запроса)
        Request request = new Request.Builder() //построение запроса
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response responce = client.newCall(request).execute();

        if(responce.isSuccessful()){
            String responceJson = responce.body().string();
            AuthResponceDto responceDto = gson.fromJson(responceJson,AuthResponceDto.class);//берем body из ответа и парсим в объект
            System.out.println(responceDto.getToken());
            System.out.println(responce.code());
            Assert.assertTrue(responce.isSuccessful());

        }
        else {
            System.out.println("Responce code--->"+responce.code());
            ErrorDto errorDto = gson.fromJson(responce.body().string(),ErrorDto.class);
            System.out.println(errorDto.getCode()+"*******"+errorDto.getMessage()+"*********"+errorDto.getDetails());
            Assert.assertFalse(responce.isSuccessful());

        }


    }
}
