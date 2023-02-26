package org.example;

public class JsonService {
    private String jsonString;
    public int getIdFromJson(String line){
        line = line.split("\"id\": ")[1];
        line = String.valueOf(line.charAt(0));
        return Integer.parseInt(line);
    }
    public String getNameFromJson(String line){
        line = line.split("\"name\": ")[1];
        line = line.replace("\"", "");
        line = line.replace("}", "");
        line = line.replace(",", "");
        line = line.replaceAll("\\s", "");
        return line;
    }
   public void addStudent(int id, String name){
       this.jsonString += "\n\t\t{\n\t\t\t\"id\": " +
               id + ",\n\t\t\t\"name\": \"" +
               name + "\"\n\t\t},";
   }
   public void addLastStudent(int id, String name){
       this.jsonString += "\n\t\t{\n\t\t\t\"id\": " +
               id + ",\n\t\t\t\"name\": \"" +
               name + "\"\n\t\t}";
   }
   public void setBegin(){
       this.jsonString = "{\n\t\"students\": [";
   }
   public void setEnd(){
        this.jsonString += "\n\t]\n}";
   }
   public String getJsonString(){
        return jsonString;
   }
}
