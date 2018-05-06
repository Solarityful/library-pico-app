package tests;

import application.*;
import org.junit.*;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.Parameter;
import org.picocontainer.behaviors.Caching;
import org.picocontainer.parameters.ComponentParameter;
import org.picocontainer.parameters.ConstantParameter;
import java.util.TreeMap;

public class LibraryDatabaseTest {
    static String testFileName = "test.ser";
    private MutablePicoContainer picoToTest;

    @Before
    public void setUp(){
        picoToTest = new DefaultPicoContainer(new Caching());
        picoToTest.addComponent(LibraryManager.class);
        picoToTest.addComponent(TreeMap.class);
        Parameter[] params = {new ComponentParameter(), new ConstantParameter(testFileName)};
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
    public void testLibraryDatabaseInstance(){
        Database database = picoToTest.getComponent(Database.class);
        Assert.assertNotNull(database);
    }

    @Test
    public void testLibraryDatabaseAdd(){

        Database database = picoToTest.getComponent(Database.class);
        Assert.assertEquals(database.getNumberOfEntities(), 2);

        Book book = new Book();
        book.setId(3);
        book.setAuthor("Test author name");
        book.setTitle("Test book title");
        Library library = new Library();
        library.setName("Test library name");
        library.addBook(book);

        database.add(library);

        Assert.assertEquals(database.getNumberOfEntities(), 3);
    }

    @Test
    public void testLibraryDatabaseRemoveById(){
        int idToRemove = 1;

        Database database = picoToTest.getComponent(Database.class);
        Assert.assertEquals(database.getNumberOfEntities(), 2);
        database.removeById(idToRemove);
        Assert.assertEquals(database.getNumberOfEntities(), 1);
    }

    @Test
    public void testLoadExistingDatabaseFail(){
        Database database = picoToTest.getComponent(Database.class);
        database.deleteCurrentDatabase();
        Assert.assertEquals(database.loadExistingDatabase(), false);
    }

    @Test
    public void testSavingDatabaseSuccess(){
        Database database = picoToTest.getComponent(Database.class);
        Assert.assertEquals(database.saveCurrentDatabase(), true);
    }

    @Test
    public void testLoadExistingDatabaseSuccess(){
        Database database = picoToTest.getComponent(Database.class);
        Assert.assertEquals(database.saveCurrentDatabase(), true);
        Assert.assertEquals(database.loadExistingDatabase(), true);
    }

}
