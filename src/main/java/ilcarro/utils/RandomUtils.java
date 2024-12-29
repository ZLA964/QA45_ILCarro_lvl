package ilcarro.utils;

import java.util.Random;



public class RandomUtils {

    static Random random = new Random();

    public static String generateString(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        char[] randomString = new char[length];
        int index;
        int charLength = characters.length();
        for (int i = 0; i < length; i++) {
            index = random.nextInt(charLength);
            randomString[i] = characters.charAt(index);
        }
        return new String(randomString);
    }

    // Generate a random string with a limited number of unique(if need) characters
    public static String generateStringTextOnly_stream(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyz";
        int charCount = characters.length();
        return new Random().ints(0, charCount)  // Generate a stream of random indices
  //              .distinct()  // Ensure unique indices
                .limit(length >= charCount ? charCount : length)  // Limit the stream length to the specified length or the maximum unique characters
                .mapToObj(characters::charAt)  // Convert indices to characters
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)  // Collect characters into a StringBuilder
                .toString();  // Convert StringBuilder to a String
    }

    public static String generateStringTextOnly(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyz";
        int charLength = characters.length();
        char[] randomString = new char[length];
        int index;

        for (int i = 0; i < length; i++) {
            index = random.nextInt(charLength);
            randomString[i] = characters.charAt(index);
        }
        return new String(randomString);
    }

    public static String generateEmail(int length) {
        String[] domains = {"gmail.com", "mail.ru", "yahoo.com", "yandex.ru"};
        String domain = domains[random.nextInt(domains.length)];
        return (generateString(length) + "@" + domain);
    }


}


