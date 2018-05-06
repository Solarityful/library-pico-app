package tests;

import application.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.Parameter;
import org.picocontainer.behaviors.Caching;
import org.picocontainer.parameters.ComponentParameter;
import org.picocontainer.parameters.ConstantParameter;

import java.util.TreeMap;

public class LibraryManagerTest {

    private MutablePicoContainer picoToTest;

    @Before
    public void setUp(){
        picoToTest = new DefaultPicoContainer(new Caching());
        picoToTest.addComponent(LibraryManager.class);
        picoToTest.addComponent(TreeMap.class);
        Parameter[] params = {new ComponentParameter(), new ConstantParameter("test.ser")};
        picoToTest.addComponent(LibraryDatabase.class, LibraryDatabase.class, params );
        Database database = picoToTest.getComponent(Database.class);

        Book book = new Book();
        book.setId(1);
        book.setAuthor("Mock author name");
        book.setTitle("Mock book title");
        Library library = new Library();
        library.setName("Mock library name");
        library.addBook(book);

        Book book2 = new Book();
        book2.setId(2);
        book2.setAuthor("Another mock author name");
        book2.setTitle("Another mock book title");
        Library library2 = new Library();
        library2.setName("Another mock library name");
        library2.addBook(book2);

        database.add(library2);
        database.add(library);
    }

    @Test
    public void testLibraryManagerInstance(){
        Manager manager = picoToTest.getComponent(Manager.class);
        Assert.assertNotNull(manager);
    }

}
