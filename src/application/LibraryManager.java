package application;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Scanner;

public class LibraryManager implements Manager {

    Database database;

    public LibraryManager(Database db){
        this.database = db;
    }

    @Override
    public void list() {
        database.printAllEntities();
    }

    @Override
    public void create() {
        System.out.println("Library (JSON):");

        ObjectMapper objectMapper = new ObjectMapper();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.println("Attempting to add:" + input);

        Library inputLibrary = null;
        try {
            inputLibrary = objectMapper.readValue(input, Library.class);
            database.add(inputLibrary);
            System.out.println("Success!");
        } catch (IOException e) {
            System.out.println("Invalid format");
        }
    }

    @Override
    public void delete() {
        System.out.println("ID to delete:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        try {
            database.removeById(Integer.parseInt(input));
        }
        catch (NumberFormatException e){
            System.out.println("Could not parse entered id: " + input);
        }


    }

    @Override
    public void quit() {
        database.saveCurrentDatabase();
    }
}
