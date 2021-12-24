package com.api.practice05;

import com.api.testBase.DummyTestBase;
import com.api.testData.DummyTestData;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static io.restassured.RestAssured.given;

public class GetRequest14 extends DummyTestBase {

    @Test
    public void test() {

    /*
    http://dummy.restapiexample.com/api/v1/employees url ine bir istek gönderildiğinde
Status kodun 200 olduğunu,
En yüksek maaşın 725000 olduğunu,
En küçük yaşın 19 olduğunu,
İkinci en yüksek maaşın 675000
olduğunu test edin.
     */
        spec03.pathParam("parametre1","employees");

        DummyTestData expectedObje=new DummyTestData();
        HashMap<String,Integer> expectedDataMap= expectedObje.setUpTestData02();
        System.out.println(expectedDataMap);

        Response response=given().
                accept("application/json").
                spec(spec03).
                when().
                get("/{parametre1}");

        //response.prettyPrint();

        //1-De-Serialization yöntemi ile
        HashMap<String,Object> actualDataMap=response.as(HashMap.class);


        Assert.assertEquals(expectedDataMap.get("statusCode"),(Integer) response.getStatusCode());

        List<Integer>salaryList=new ArrayList<Integer>();
        int dataSize=((List)actualDataMap.get("data")).size();
        for (int i=0; i< dataSize; i++){
       salaryList.add((Integer)((Map) ((List)actualDataMap.get("data")).get(i)).get("employee_salary"));
        }
       Collections.sort(salaryList);
        Assert.assertEquals( expectedDataMap.get("enYuksekMaas"),salaryList.get(dataSize-1));
        //İkinci en yüksek maaşın 675000

        Assert.assertEquals(expectedDataMap.get("ikinciYuksekMaas"),salaryList.get(salaryList.size()-2));

        //En küçük yaşın 19 olduğunu

        List<Integer> yasListesi=new ArrayList<Integer>();
        for (int i=0;i<dataSize; i++){
            yasListesi.add( (Integer) ((Map)((List<?>) actualDataMap.get("data")).get(i)).get("employee_age")   );
        }
        Collections.sort(yasListesi);
        Assert.assertEquals(expectedDataMap.get("enKucukYas"),yasListesi.get(0));

        //2-JsonPath yöntemi ile

        JsonPath json=response.jsonPath();

        //En yüksek maaşın 725000 olduğunu,

        List<Integer> maasListesijson=json.getList("data.employee_salary");
        Collections.sort(maasListesijson);
        Assert.assertEquals(expectedDataMap.get("enYuksekMaas"),maasListesijson.get(maasListesijson.size()-1));
        //İkinci en yüksek maaşın 675000
        Assert.assertEquals(expectedDataMap.get("ikinciYuksekMaas"),maasListesijson.get(maasListesijson.size()-2));
        //En küçük yaşın 19 olduğunu

        List<Integer> yasListesijson=json.getList("data.employee_age");
        Collections.sort(yasListesijson);
        Assert.assertEquals(expectedDataMap.get("enKucukYas"),yasListesijson.get(0));

    }
}
