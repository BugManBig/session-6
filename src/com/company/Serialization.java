package com.company;

import java.io.*;

public class Serialization {
    public static void serializeObject(Object object) {
        try {
            FileOutputStream fos = new FileOutputStream("D:\\Soft\\Temp\\1.tmp");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object deserializeObject() {
        try {
            FileInputStream fis = new FileInputStream("D:\\Soft\\Temp\\1.tmp");
            ObjectInputStream oin = new ObjectInputStream(fis);
            return oin.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
