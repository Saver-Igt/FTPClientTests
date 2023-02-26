package org.example;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Scanner;

public class App {
    public static void main( String[] args ) throws IOException {
        System.out.println("Введите IP-адрес и порт FTP-сервера:");
        Scanner scanner = new Scanner(System.in);
        String ip = scanner.nextLine();

        System.out.println("Введите логин:");
        String user = scanner.nextLine();

        System.out.println("Введите пароль:");
        String password = scanner.nextLine();

        System.out.println("Укажите путь к файлу:");
        String filePath = scanner.nextLine();

        StudentService studentService = new StudentService(ip,user,password, filePath);
        studentService.start();
    }
}
