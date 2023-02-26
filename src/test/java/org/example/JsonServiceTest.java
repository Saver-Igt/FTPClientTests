package org.example;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class JsonServiceTest {
    private JsonService jsonService;
    @BeforeClass
    public void setUp(){
        jsonService = new JsonService();
    }
    @Test(dataProvider = "jsonId")
    public void gettingIdFromJsonServiceTest(String line, int expect){
        int id = jsonService.getIdFromJson(line);
        Assert.assertEquals(id,expect);
    }
    @Test(dataProvider = "jsonName")
    public void gettingNameFromJsonService(String line, String expect){
        String name = jsonService.getNameFromJson(line);
        Assert.assertEquals(name, expect);
    }
    @DataProvider
    public Object[][] jsonId(){
        return new Object[][]{
            {"{\n" +
            "\t\t\t\"id\": 1,\n" +
            "\t\t\t\"name\": \"Student1\"\n" +
            "\t\t},", 1},
            {"{\n" +
            "\t\t\t\"id\": 2,\n" +
            "\t\t\t\"name\": \"Student2\"\n" +
            "\t\t}", 2},
            {"{\n" +
            "\t\t\t\"id\": 3,\n" +
            "\t\t\t\"name\": \"Student3\"\n" +
            "\t\t},", 3}
        };
    }
    @DataProvider
    public Object[][] jsonName(){
        return new Object[][]{
                {"{\n" +
                        "\t\t\t\"id\": 1,\n" +
                        "\t\t\t\"name\": \"Student1\"\n" +
                        "\t\t},", "Student1"},
                {"{\n" +
                        "\t\t\t\"id\": 2,\n" +
                        "\t\t\t\"name\": \"Student2\"\n" +
                        "\t\t}", "Student2"},
                {"{\n" +
                        "\t\t\t\"id\": 3,\n" +
                        "\t\t\t\"name\": \"Student3\"\n" +
                        "\t\t},", "Student3"}
        };
    }
}
