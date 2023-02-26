package org.example;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class ConnectionTest {
    private StudentService studentService;
    private String ip, login, pass,  filePath;
    @BeforeClass
    public void setUp(){
        ip = "ftp.byethost7.com";
        login = "b7_33664740";
        pass = "Al3f6g531";
        filePath = "123.txt";
    }
    @Test
    public void connection() throws IOException {
        studentService = new StudentService(ip, login, pass, filePath);
        Assert.assertNotNull(studentService.connection());
    }
}
