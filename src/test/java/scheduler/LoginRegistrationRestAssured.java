package scheduler;

import com.jayway.restassured.RestAssured;

import com.jayway.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import schedulerdto.AuthRequestDto;
import schedulerdto.AuthResponceDto;
import schedulerdto.ErrorDto;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;


public class LoginRegistrationRestAssured {
    @BeforeMethod
    public void precondition() {
        RestAssured.baseURI = "https://super-scheduler-app.herokuapp.com/";
        RestAssured.basePath = "api";
    }

    @Test
    public void loginSuccess() {
        AuthRequestDto auth = AuthRequestDto.builder()
                .email("noa@gmail.com")
                .password("Nnoa12345$")
                .build();

        AuthResponceDto authResponceDto =
                given()   //отправка запроса сразу с преобразованием объекта в JSON
                        .body(auth)
                        .contentType("application/json")
                        .when().post("login")   //собирает адрес из baseURI+basePath+"login"
                        .then().assertThat().statusCode(200)
                        .extract().response().as(AuthResponceDto.class);//извлечение ответа как объект класса AuthResponceDto

        System.out.println(authResponceDto.getToken());
        System.out.println(authResponceDto.isRegistration());
        System.out.println(authResponceDto.getStatus());

    }

    @Test
    public void wrongPassword() {
        AuthRequestDto auth = AuthRequestDto.builder()
                .email("noa@gmail.com")
                .password("Nnoa12345")
                .build();

        ErrorDto errorDto = given().body(auth).contentType("application/json")
                .when().post("login")
                .then().assertThat().statusCode(401)
                .extract().response().as(ErrorDto.class);

        System.out.println(errorDto.toString());
        Assert.assertTrue(errorDto.getMessage().contains("Wrong email"));

    }

    @Test
    public void wrongPassword2() {
        AuthRequestDto auth = AuthRequestDto.builder()
                .email("noa@gmail.com")
                .password("Nnoa12345")
                .build();

        String message = given().body(auth).contentType("application/json")
                .when().post("login")
                .then().assertThat().statusCode(401)
                .extract().path("message");    //возращаем сразу значение определенного поля ответа без привязки к конкретному классу

        Assert.assertEquals(message,"Wrong email or password");



    }

    @Test
    public void registrationTest() {
        AuthRequestDto auth = AuthRequestDto.builder()
                .email("aaa@gmail.com")
                .password("Nnoa12345$")
                .build();

        String token = given()
                .contentType(ContentType.JSON).body(auth)
                .when().post("login")
                .then().assertThat().statusCode(200)
                .assertThat().body("status", containsString("Registration success"))
                .assertThat().body("registration", equalTo(true))
                .extract().path("token");//спомощью библиотеки можно сравниваться напрямую с полями ответа


        System.out.println(token);


    }

    @Test
    public void logTest() {
        AuthRequestDto auth = AuthRequestDto.builder()
                .email("noa@gmail.com")
                .password("Nnoa12345$")
                .build();

        String token = given()
                .contentType(ContentType.JSON).body(auth)
                .when().post("login")
                .then().assertThat().statusCode(200)
                .assertThat().body("status", containsString("Login success"))
                .assertThat().body("registration", equalTo(false))
                .extract().path("token");//спомощью библиотеки можно сравниваться напрямую с полями ответа


        System.out.println(token);


    }


}

