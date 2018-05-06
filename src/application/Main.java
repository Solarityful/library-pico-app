package application;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.Parameter;
import org.picocontainer.behaviors.Caching;
import org.picocontainer.parameters.ComponentParameter;
import org.picocontainer.parameters.ConstantParameter;

import java.io.IOException;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) throws IOException {

        MutablePicoContainer pico = new DefaultPicoContainer(new Caching());
        pico.addComponent(LibraryManager.class);
        pico.addComponent(TreeMap.class);
        Parameter[] params = {new ComponentParameter(), new ConstantParameter("treemap.ser")};
        pico.addComponent(LibraryDatabase.class, LibraryDatabase.class, params );
        Database currentDatabase = pico.getComponent(Database.class);

        if (!currentDatabase.loadExistingDatabase()){
            LaunchInitialSession(pico);
        }


        while (true) {

            System.out.println("Menu:");
            System.out.println("list: displays all libraries");
            System.out.println("create: creates a library");
            System.out.println("delete: deletes a library");
            System.out.println("quit: closes the app");
            System.out.print("Command: ");

            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            switch (input.toLowerCase()) {
                case "quit":
                    pico.getComponent(Manager.class).quit();
                    ExitHandler();
                case "list":
                    pico.getComponent(Manager.class).list();
                    break;
                case "create":
                    pico.getComponent(Manager.class).create();
                    break;
                case "delete":
                    pico.getComponent(Manager.class).delete();
                    break;
                default:
                    System.out.println("unknown command");
            }
        }
    }


    public static void LaunchInitialSession(MutablePicoContainer pico){
        System.out.println("Creating initial database");
        Database database = pico.getComponent(Database.class);

        Library library = new Library();

        Book book = new Book();

        book.setId(1);
        book.setAuthor("Stendhal");
        book.setTitle("The red and the black");

        library.setName("Great library");
        library.addBook(book);

        database.add(library);
    }

    public static void ExitHandler(){

        System.out.println("Bye!");
        System.exit(0);
    }
}
