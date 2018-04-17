package com.company;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class Serialization {
    private String path;

    public void setPath(String path) {
        this.path = path;
    }

    public Serialization(String path) {
        this.path = path;
    }

    public int serializeObject(User user) {
        int smallestId = getSmallestId();
        try {
            FileOutputStream fos = new FileOutputStream(path + "\\" + smallestId + ".bin");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(user);
            oos.flush();
            oos.close();
            fos.close();
            return smallestId;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public User deserializeObject(int id) {
        try {
            FileInputStream fis = new FileInputStream(path + "\\" + id + ".bin");
            ObjectInputStream oin = new ObjectInputStream(fis);
            User user = (User) oin.readObject();
            oin.close();
            fis.close();
            return user;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int getSmallestId() {
        File file = new File(path);
        File[] filesArray = file.listFiles();
        Set<Integer> filesId = new HashSet<>();
        int dotIndex;
        for (File elem : filesArray) {
            dotIndex = elem.getName().lastIndexOf('.');
            filesId.add(Integer.parseInt(elem.getName().substring(0, dotIndex)));
        }
        int id = 1;
        while (filesId.contains(id)) {
            id++;
        }
        return id;
    }
}
