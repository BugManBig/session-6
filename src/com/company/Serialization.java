package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
            String fullPath = path + "\\" + id + ".bin";
            if (!new File(fullPath).exists()) return null;
            FileInputStream fis = new FileInputStream(fullPath);
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

    public boolean deleteUser(int id) {
        File file = new File(path + "\\" + id + ".bin");
        return file.delete();
    }

    public List<User> getUsersArray() {
        File file = new File(path);
        File[] filesArray = file.listFiles();
        int id;
        String fileName;
        List<User> users = new ArrayList<>();
        for (File elem : filesArray) {
            fileName = elem.getName();
            id = Integer.parseInt(fileName.substring(0, fileName.lastIndexOf('.')));
            users.add(deserializeObject(id));
        }
        return users;
    }
}
