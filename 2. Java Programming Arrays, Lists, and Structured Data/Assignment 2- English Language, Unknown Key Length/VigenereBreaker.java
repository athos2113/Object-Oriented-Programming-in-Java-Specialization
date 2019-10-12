import java.util.*;
import edu.duke.*;
import java.io.File;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        //REPLACE WITH YOUR CODE
        String slice = "";
        for(int i = whichSlice ; i < message.length(); i+= totalSlices){
            slice += message.charAt(i);
        }
        return slice;
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        CaesarCracker thisCracker = new CaesarCracker();
        for(int i = 0; i < klength; i++){
            String slice = sliceString(encrypted, i, klength);
            int temp = thisCracker.getKey(slice);
            key[i] = temp;
        }
        return key;
    }

    public void breakVigenere () {
       
        HashMap<String, HashSet<String>> dictionary = new HashMap<String, HashSet<String>>();
        System.out.println("Select the dictionaries");
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()){
            System.out.println(f.getName());
            FileResource fileResource = new FileResource("dictionaries/"+f.getName());
            dictionary.put(f.getName(), readDictionary(fileResource));
        }
        System.out.println("Dictionaries have been read and stored !");
        
        System.out.println("Select the encrypted file");
        FileResource fr = new FileResource();
        String message = fr.asString().toLowerCase();
        
        breakForAllLangs(message,dictionary);
        //FileResource tr = new FileResource();
        //HashSet<String> readDict = readDictionary(tr);
        
        //String decrypted = breakForLanguage(message, readDict);
        //System.out.println(decrypted);
        //int[] key = tryKeyLength(message, 4, 'e');
        //System.out.println("KEY USED :");
        //for(int i = 0; i < key.length; i++){
        //    System.out.print(key[i] + ", " );
        //}
        
        //VigenereCipher thisCipher = new VigenereCipher(key);
        //String decrypted = thisCipher.decrypt(message);
        //System.out.println(decrypted.substring(0, 1000));
        
         
    }
    
    
    
    public HashSet<String> readDictionary(FileResource fr){
        HashSet<String> words = new HashSet<String>();
        for(String line : fr.lines()){
            String temp = line.toLowerCase();
            words.add(temp);
        
        }
        
        
        return words;
    }
    
    public int countWords(String message, HashSet<String> dictionary){
        int count = 0;
        message = message.toLowerCase();
        String[] messageArray = message.split("\\W");
        for(String word : messageArray){
            if(dictionary.contains(word)){
                count++;
            }
            
        }
        
        return count;
    }
    
    public String breakForLanguage(String encrypted, HashSet<String> dictionary, char common){
        String decrypted = "";
        int keylong = 0;
        int max = 0;
        for(int i = 1; i < 100; i++){
            int keys[] = tryKeyLength(encrypted, i, common);
            VigenereCipher thisCipher = new VigenereCipher(keys);
            String decrypted_temp = thisCipher.decrypt(encrypted);
            int count = countWords(decrypted_temp, dictionary);
            if(count > max){
                max = count;
                decrypted = decrypted_temp;
                keylong = i;
        
            }
        
        }
        
        System.out.println("Key length used to decrypt is " + keylong);
        return decrypted;
    }
    
    public void breakForAllLangs(String encrypted, HashMap<String, HashSet<String>> languages){
        String decrypted = "";
        String trueLang = "";
        int max = 0;
        for(String lang : languages.keySet()){
            HashSet<String> dict = languages.get(lang);
            char mostCommon = mostCommonCharIn(dict);
            String temp = breakForLanguage(encrypted, dict, mostCommon);
            int count = countWords(encrypted, dict);
            if(count > max){
                max = count;
                decrypted = temp;
                trueLang = lang;
            }
        }
        
        System.out.println("Language : " + trueLang);
        System.out.println("Decrypted Message : ");
        System.out.println(decrypted);
        //return decrypted;
    }
    
    public char mostCommonCharIn(HashSet<String> dictionary){
    HashMap<Character, Integer> count = new HashMap<Character, Integer>();
    char common = 'a';
    for(String character : dictionary){
        for(int i = 0; i <character.length(); i++){
            if(count.containsKey(character.charAt(i))){
                count.put(character.charAt(i), count.get(character.charAt(i)) + 1);
            }
            else{
                count.put(character.charAt(i), 1);
            }
        }
    
    }
    int max = 0;
    for(char c : count.keySet()){
       //int value = count.get(c);
       if(count.get(c) > max){
           max = count.get(c);
           common = c;
       }
    }
     System.out.println("The most common char is " + common);
    return common;
    }
    
}
