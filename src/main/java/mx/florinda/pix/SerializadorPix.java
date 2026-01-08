package mx.florinda.pix;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.time.Instant;

public class SerializadorPix {
    public static void main(String[] args) throws IOException {
        Pix pix = new Pix(1L, BigDecimal.valueOf(10.99), "marciliodevjava@gmail.com", Instant.now(), "PIX Realizado");
        try(FileOutputStream fos = new FileOutputStream("pix.ser")){
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(pix);
        }
    }
}
