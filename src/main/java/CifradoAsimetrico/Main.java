package CifradoAsimetrico;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static byte[] message;

    public static void main(String[] args) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, IOException {
        CifradoAsimetrico cifradoAsimetrico = new CifradoAsimetrico();
        cifradoAsimetrico.generateKeyPair();
        System.out.println("Escriba el mensaje a cifrar.");
        message = sc.next().getBytes();
        cifradoAsimetrico.encryptFile(message);
        cifradoAsimetrico.decryptFile();
    }
}
