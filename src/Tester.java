import edu.duke.FileResource;

public class Tester {

    public void testCaserCipher() {
        CaesarCipher cc = new CaesarCipher(4);

        FileResource fr = new FileResource("VigenereTestData/titus-small.txt");

        String encryptedStr = cc.encrypt(fr.asString());
        System.out.println("Encrypted: " + encryptedStr);

        String decryptedStr = cc.decrypt(encryptedStr);
        System.out.println("Decrypted: " + decryptedStr);

    }


    public void testCaesarCracker() {

        CaesarCracker cc = new CaesarCracker();
        FileResource fr = new FileResource("VigenereTestData/titus-small_key5.txt");

        String decryptedStr = cc.decrypt(fr.asString());
        System.out.println("Decrypted: " + decryptedStr);

        CaesarCracker cc2 = new CaesarCracker('a');
        fr = new FileResource("VigenereTestData/oslusiadas_key17.txt");
        decryptedStr = cc2.decrypt(fr.asString());
        System.out.println("Decrypted 2: " + decryptedStr);
    }

    public void testVigenereCipher() {

        String key = "rome";
        int[] keyArr = new int[key.length()];
        for (int i = 0; i < keyArr.length; i++) {
            keyArr[i] = key.charAt(i)- 'a';
        }
        FileResource fr = new FileResource("VigenereTestData/titus-small.txt");
        VigenereCipher vc = new VigenereCipher(keyArr);
        String Decrypted = vc.decrypt(fr.asString());
        String Encrypted = vc.encrypt(fr.asString());
        System.out.println("Encrypted: " + Encrypted);
        System.out.println("Decrypted: " + Decrypted);

    }

}
