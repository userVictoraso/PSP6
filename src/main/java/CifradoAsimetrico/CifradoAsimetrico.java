package CifradoAsimetrico;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;

public class CifradoAsimetrico {

    private static final String FILEPATH = "src/main/resources/fichero.cifrado";
    private static PublicKey publicKey = null;
    private static PrivateKey privateKey = null;
    private static File file = new File(FILEPATH);
    private static Cipher cipher = null;

    public void generateKeyPair() {
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        setPrivateKey(keyPair.getPrivate());
        setPublicKey(keyPair.getPublic());
    }

    public void encryptFile(byte[] bufferNoSecure) {
        try {
            System.out.println("Mensaje no cifrado: ");
            mostrarBytes(bufferNoSecure);
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey());
            byte[] cipherBuffer = cipher.doFinal(bufferNoSecure);
            writeFile(cipherBuffer);
            System.out.println("\n  Mensaje cifrado: ");
            mostrarBytes(cipherBuffer);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void decryptFile() throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        byte[] buffer;
        cipher.init(Cipher.DECRYPT_MODE, getPrivateKey());
        buffer = cipher.doFinal(readFile());
        System.out.println("\n  Mensaje descifrado: ");
        mostrarBytes(buffer);
    }

    public static void mostrarBytes(byte[] buffer) throws IOException {
        System.out.write(buffer);
    }

    public static void writeFile(byte[] buffer) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file);) {
            fos.write(buffer);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public byte[] readFile() {
        byte[] encrypted = new byte[(int) file.length()];

        try (FileInputStream fileInputStream = new FileInputStream(file);) {
            fileInputStream.read(encrypted);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encrypted;
    }


    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey pubKey) {
        this.publicKey = pubKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privKey) {
        this.privateKey = privKey;
    }
}
