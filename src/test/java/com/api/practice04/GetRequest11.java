package com.api.practice04;

import com.api.testBase.JsonPlaceHolderTestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class GetRequest11 extends JsonPlaceHolderTestBase {
      /*
https://jsonplaceholder.typicode.com/todos/2 url ‘ine istek gönderildiğinde,
 Dönen response un
 Status kodunun 200, dönen body de,
 "completed": değerinin false
"title”: değerinin “quis ut nam facilis et officia qui”
"userId" sinin 1 ve
header değerlerinden
 "Via" değerinin “1.1 vegur” ve
 "Server" değerinin “cloudflare” olduğunu test edin…
 */

    /*
    url oluştur
    --expected data
    request gönder
   -- actual data
    assertion
     */

    @Test
    public void test(){

        spec01.pathParams("parametre1","todos",
                "parameter2","2");
        HashMap<String,Object> expectedData=new HashMap<String,Object>();
        expectedData.put("statusCode",200);
        expectedData.put("Via","1.1 vegur");
        expectedData.put("Server","cloudflare");
        expectedData.put("userId",1);
        expectedData.put("title","quis ut nam facilis et officia qui");
        expectedData.put("completed",false);
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




    }
}
