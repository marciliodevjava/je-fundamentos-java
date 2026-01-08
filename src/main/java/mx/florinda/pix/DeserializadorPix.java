package mx.florinda.pix;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class DeserializadorPix {

    public static void main(String[] args) throws IOException {
        try (FileInputStream fis = new FileInputStream("pix.ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Pix pix = (Pix) ois.readObject();
            System.out.println(pix);
        } catch (Exception e) {

        }
    }
}
