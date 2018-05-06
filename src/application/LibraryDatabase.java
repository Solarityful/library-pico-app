package application;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeMap;

public class LibraryDatabase implements Database {

    private TreeMap<Integer, Library> data;

    public TreeMap<Integer, Library> getData() {
        return data;
    }

    public void setData(TreeMap<Integer, Library> data) {
        this.data = data;
    }

    public String getSerializedName() {
        return serializedName;
    }

    public void setSerializedName(String serializedName) {
        this.serializedName = serializedName;
    }

    private String serializedName;

    public LibraryDatabase(TreeMap newData, String filename){
        this.data = newData;
        this.serializedName = filename;
    }

    @Override
    public void add(Object o) {
        int newKey;
        if (data.isEmpty()) {
            newKey = 1;
        }
        else {
            newKey = data.lastKey() + 1;
        }
        data.put(newKey, (Library) o);
    }

    @Override
    public void remove(Object o) {
        System.out.println("Not Implemented");
    }

    @Override
    public void removeById(int id) {
        Library removedLibrary = data.remove(id);
        if (removedLibrary == null){
            System.out.println("Could not find library with id " + id);
        }
        else{
            System.out.println("Successfully removed " + removedLibrary.getName());
        }
    }

    @Override
    public void update(Object o) {
        System.out.println("Not Implemented");
    }

    @Override
    public void printAllEntities() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            System.out.println(objectMapper.writeValueAsString(data));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean loadExistingDatabase() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(serializedName);
            ois = new ObjectInputStream(fis);
            data = (TreeMap) ois.readObject();
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            System.out.println("No existing database found");
            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean saveCurrentDatabase() {
        try
        {
            FileOutputStream fos = new FileOutputStream(serializedName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.close();
            fos.close();
            return true;
        }catch(IOException ioe)
        {
            ioe.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteCurrentDatabase() {
        Path fileToDeletePath = Paths.get(serializedName);
        try {
            Files.delete(fileToDeletePath);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int getNumberOfEntities() {
        return data.size();
    }

    public Object find(Object o) {
        System.out.println("Not Implemented");
        return null;
    }
}
