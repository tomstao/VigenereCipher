import edu.duke.FileResource;

import java.util.Arrays;
import java.util.HashSet;

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

    public void testVigenereBreaker() {

        VigenereBreaker vcb = new VigenereBreaker();
        String test = vcb.sliceString("abcdefghijklm", 0 , 3);
        String test2 = vcb.sliceString("abcdefghijklm", 1 , 3);
        String test3 = vcb.sliceString("abcdefghijklm", 2 , 3);
        String test4 = vcb.sliceString("abcdefghijklm", 2 , 5);
        String test5 = vcb.sliceString("abcdefghijklm", 4 , 5);
        System.out.println(test);
        System.out.println(test2);
        System.out.println(test3);
        System.out.println(test4);
        System.out.println(test5);

    }

    public void testVigenereBreaker_tryKeyLength() {
        VigenereBreaker vcb = new VigenereBreaker();
        FileResource fr = new FileResource("messages/2.txt");

        int[] keySet = vcb.tryKeyLength(fr.asString(), 4, 'e' );
        System.out.println(Arrays.toString(keySet));
    }

    public void testVigenereBreaker_breakVigenere() {
        VigenereBreaker vcb = new VigenereBreaker();
        vcb.breakVigenere();
    }

    public void testVigenereBreaker_breakVigenere2() {
        VigenereBreaker vcb = new VigenereBreaker();
        vcb.breakVigenere();

    }

    public void testVigenereBreaker_breakVigenere3() {
        VigenereBreaker vcb = new VigenereBreaker();
        FileResource fr = new FileResource();
        String message = fr.asString().toLowerCase();
        int[] key = vcb.tryKeyLength(message, 38, 'e');
        VigenereCipher vc = new VigenereCipher(key);

        fr = new FileResource();
        HashSet<String> dic = vcb.readDictionary(fr);
        int count = vcb.countWords(message,dic);

        System.out.println(count);

    }


}
