package org.example;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

public class StudentService {
    private List<Student> students = new ArrayList<>();
    private JsonService jsonService = new JsonService();
    private String ftpUrl = "ftp://%s:%s@%s/%s";
    private String host;
    private String login;
    private String pass;
    private String filePath;
    private String fileName;

    public StudentService(String ip, String login, String password, String filePath){
        this.host = ip;
        this.login = login;
        this.pass = password;
        this.filePath = filePath;
        this.fileName = getFileName(filePath);
    }
    public URLConnection connection() throws IOException{
        ftpUrl = String.format(ftpUrl, login, pass, host, filePath);

        URL url = new URL(ftpUrl);
        return url.openConnection();
    }
    public void setStudents() throws IOException{
        InputStreamReader inputStreamReader = new InputStreamReader(connection().getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        int id = 0;
        while ((line = bufferedReader.readLine()) != null){
            if(line.contains("\"id\": ")){
                id = jsonService.getIdFromJson(line);
            }
            if (line.contains("\"name\": ")){
                String name = jsonService.getNameFromJson(line);
                students.add(new Student(id, name));
            }
        }
    }
    public void printMenu(){
        System.out.println("Выберите действие:");
        System.out.println("1. Получить список студентов по имени");
        System.out.println("2. Получить информацию о студенте по id");
        System.out.println("3. Добавить студента");
        System.out.println("4. Удалить студента по id");
        System.out.println("5. Завершение работы");
    }
    public void start() throws IOException {
        setStudents();
        printMenu();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()){
            int action = scanner.nextInt();
            if (action == 1){
                getAllStudents();
            }if (action == 2){
                getStudent();
            }if (action == 3){
                addStudent();
            }if (action == 4){
                deleteStudent();
            }if (action == 5){
                break;
            }
        }
    }
    public void getAllStudents(){
        sortStudentsByName();
        for (Student s: students) {
            System.out.println(s.toString());
        }
        System.out.println("Выберите следующее действие:");
    }
    public void getStudent(){
        Scanner scanner  = new Scanner(System.in);
        System.out.println("Введите id студента: ");
        int id = scanner.nextInt();
        System.out.println(students.stream().filter(student -> student.getId() == id).findFirst()
                .orElse(new Student()));
        System.out.println("Выберите следующее действие:");
    }
    public void deleteStudent() {
        Scanner scanner  = new Scanner(System.in);
        System.out.println("Введите id студента:");
        int id = scanner.nextInt();
        students = students.stream().filter(student -> student.getId() != id)
                .collect(Collectors.toList());

        writeStudentsToFile();
        putFileToServer();
        System.out.println("Студент успешно удален.");
        System.out.println("Выберите следующее действие:");
    }
    public void addStudent(){
        Scanner scanner  = new Scanner(System.in);
        System.out.println("Введите имя студента:");
        String studentName = scanner.nextLine();

        int size = students.size();
        students.add(new Student(size + 1, studentName));
        writeStudentsToFile();
        putFileToServer();

        System.out.println("Студент - " + students.get(size).toString() + " добавлен.");
        System.out.println("Выберите следующее действие:");
    }
    public String getFileName(String filePath){
        File f = new File(filePath);
        return f.getName();
    }
    public void sortStudentsByName(){
        students.sort(Comparator.comparing(Student::getName));
    }
    public void putFileToServer(){
        try {
            URL url = new URL(ftpUrl);
            URLConnection conn = url.openConnection();
            OutputStream outputStream = conn.getOutputStream();
            FileInputStream inputStream = new FileInputStream(fileName);

            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeStudentsToFile(){
        jsonService.setBegin();
        for (Student s: students) {
            if(s.getId() == students.size()){
                jsonService.addLastStudent(s.getId(), s.getName());
            }else {
                jsonService.addStudent(s.getId(), s.getName());
            }
        }
        jsonService.setEnd();
        String text = jsonService.getJsonString();

        try(FileWriter fw = new FileWriter(fileName))
        {
            fw.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
