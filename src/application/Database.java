package application;

public interface Database {

     void add(Object o);

     void remove(Object o);

     void removeById(int id);

     void update(Object o);

     void printAllEntities();

     boolean loadExistingDatabase();

     boolean saveCurrentDatabase();

     boolean deleteCurrentDatabase();

     int getNumberOfEntities();
}
