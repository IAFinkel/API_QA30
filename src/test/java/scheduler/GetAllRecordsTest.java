package scheduler;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import schedulerdto.GetAllRecordsDto;
import schedulerdto.GetRecordsRequestDto;
import schedulerdto.RecordDto;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;

public class GetAllRecordsTest {
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5vYUBnbWFpbC5jb20ifQ.G_wfK7FRQLRTPu9bs2iDi2fcs69FHmW-0dTY4v8o5Eo";

    @BeforeMethod
    public void precondition(){
        RestAssured.baseURI = "https://super-scheduler-app.herokuapp.com/";
        RestAssured.basePath = "api";
    }

    @Test
    public void allRecords(){
        GetRecordsRequestDto requestDto = GetRecordsRequestDto
                .builder()
                .monthFrom(5)
                .monthTo(12)
                .yearFrom(2021)
                .yearTo(2021)
                .build();

        GetAllRecordsDto recordsDto = given()
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post("records")
                .then().assertThat().statusCode(200)
                .extract().response().as(GetAllRecordsDto.class);
        for(RecordDto record: recordsDto.getRecords()){
            System.out.println(record.getId());
            System.out.println("***************");
        }
    }
}
