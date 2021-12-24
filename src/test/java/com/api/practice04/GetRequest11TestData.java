package com.api.practice04;

import com.api.TestData.JsonPlaceHolderTestData;
import com.api.testBase.JsonPlaceHolderTestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class GetRequest11TestData extends JsonPlaceHolderTestBase {

    @Test
    public void test(){

        spec01.pathParams("parametre1","todos",
                "parameter2","2");

        JsonPlaceHolderTestData expectedObject=new JsonPlaceHolderTestData();
        HashMap<String,Object> expectedData= (HashMap<String, Object>) expectedObject.setUpTestData();

        System.out.println(expectedData);
        System.out.println("");
        Response response= given().
                accept("application/json").
                spec(spec01).
                when().
                get("/{parametre1}/{parameter2}");
        response.prettyPrint();

        //1- Matcher class ile assertion yontemi
        response.then().
                assertThat().
                statusCode((int)expectedData.get("statusCode")).
                headers("via", equalTo(expectedData.get("Via")),
                        "Server",equalTo(expectedData.get("Server"))).
                body("userId",equalTo(expectedData.get("userId")),
                        "title",equalTo(expectedData.get("title")),
                        "completed",equalTo(expectedData.get("completed")));
        //2- JsonPath ile assertion yontemi

        JsonPath jsonPath=response.jsonPath();
        Assert.assertEquals(expectedData.get("statusCode"),response.getStatusCode());
        Assert.assertEquals(expectedData.get("Via"),response.header("via"));
        Assert.assertEquals(expectedData.get("Server"),response.header("Server"));
        Assert.assertEquals(expectedData.get("userId"),jsonPath.getInt("userId"));
        Assert.assertEquals(expectedData.get("title"),jsonPath.getString("title"));
        Assert.assertEquals(expectedData.get("completed"),jsonPath.getBoolean("completed"));

        //3- Deserialization
        // - object mapper
        // - pojo class

        HashMap<String,Object> actualData= response.as(HashMap.class);
        System.out.println(actualData);

        Assert.assertEquals(expectedData.get("userId"),actualData.get("userId"));
        Assert.assertEquals(expectedData.get("title"),actualData.get("title"));
        Assert.assertEquals(expectedData.get("completed"),actualData.get("completed"));

    }

}
