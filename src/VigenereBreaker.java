import java.io.File;
import java.util.*;
import edu.duke.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        //REPLACE WITH YOUR CODE
        StringBuilder result = new StringBuilder();
        for (int i = whichSlice; i < message.length(); i += totalSlices) {
            result.insert(result.length(), message.charAt(i));
        }

        return result.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        //WRITE YOUR CODE HERE
        CaesarCracker cracker = new CaesarCracker(mostCommon);
        String[] slicedStrings = new String[klength];
        for (int i = 0; i < klength; i++) {
            slicedStrings[i] = sliceString(encrypted, i, klength);
        }

        for(int i = 0; i < key.length; i++) {
            key[i] = cracker.getKey(slicedStrings[i]);
        }
        return key;
    }

    public void breakVigenere () {
        //WRITE YOUR CODE HERE
        HashMap<String, HashSet<String>> dictionaries = new HashMap<>();
        FileResource fr = new FileResource();
        String encryptedMessage = fr.asString();
        DirectoryResource dr = new DirectoryResource();
        for(File dictionaryF : dr.selectedFiles()) {
            FileResource dic = new FileResource(dictionaryF);
            dictionaries.put(dictionaryF.getName(), readDictionary(dic));
        }
//        FileResource fr2 = new FileResource();
//        HashSet<String> dictionary = readDictionary(fr2);

//        int[] keySet = tryKeyLength(encryptedMessage, 4, 'e');
//
//        VigenereCipher cipher = new VigenereCipher(keySet);
//        String decryptedMessage = cipher.decrypt(encryptedMessage);
//        System.out.println(decryptedMessage);
        System.out.println(breakForAllLangs(encryptedMessage, dictionaries));
    }

    public HashSet<String> readDictionary(FileResource fr) {
        HashSet<String> dict = new HashSet<>();
        for (String line : fr.lines()) {
            dict.add(line.toLowerCase()
            );
        }
        return dict;
    }

    public int countWords(String message, HashSet<String> dictionary) {
        String[] words = message.split("\\W+");
        int count = 0;
        for (String word : words) {
            if (dictionary.contains(word)) {
                count++;
            }
        }
        return count;
    }

    public String breakForLanguage(String encrypted, HashSet<String> dictionary, char mostCommon) {
        VigenereBreaker vb = new VigenereBreaker();
        int count = 0;
        int index = 0;
        int keyLength = 0;
        String lowerCase = encrypted.toLowerCase();
        HashMap<Integer, String> wordCounts = new HashMap<>();
        for(int i = 1; i <= 100; i++) {
            int[] keys = vb.tryKeyLength(lowerCase, i, mostCommon);
            VigenereCipher cipher = new VigenereCipher(keys);
            String decryptedMessage = cipher.decrypt(lowerCase);

            if(countWords(decryptedMessage, dictionary) > count) {
                count = countWords(decryptedMessage, dictionary);
                wordCounts.put(index, decryptedMessage);
                keyLength = i;
                index++;
            }
        }

        return "Key length and words:" + keyLength + " "+ count + "\n" + wordCounts.get(index - 1);
    }

    public char mostCommonCharIn(HashSet<String> dictionary) {
        HashMap<Character, Integer> wordCounts = new HashMap<>();
        char mostCommon = ' ';
        int frequency = 0;

        for(String word : dictionary) {
            for(char c : word.toCharArray()) {
                if(wordCounts.containsKey(c)) {
                    wordCounts.put(c, wordCounts.get(c) + 1);
                    if(wordCounts.get(c) > frequency) {
                        frequency = wordCounts.get(c);
                        mostCommon = c;
                    }
                } else {
                    wordCounts.put(c, 1);
                }
            }
        }
        return mostCommon;
    }

    public String breakForAllLangs(String encrypted, HashMap<String, HashSet<String>> languages) {
        int contWords = 0;
        StringBuilder result = new StringBuilder();
        for(String lang : languages.keySet()) {
            char mostCommon = mostCommonCharIn(languages.get(lang));
            String decrypted = breakForLanguage(encrypted, languages.get(lang), mostCommon);
            int count = countWords(decrypted, languages.get(lang));
            if(count > contWords) {
                contWords = count;
                result.delete(0, result.length());
                result.append(lang).append("\n").append(decrypted);
            }

        }

        return result.toString();
    }


}
