package some_exercise1_anagram;

import java.util.Arrays;

public class Anagram {

    public static boolean anagram(String word1, String word2) {
        if (word1.equals(word2)) {
            return true;
        }

        if (word1.length() == word2.length()) {
            char[] charArr1 = word1.toCharArray();
            char[] charArr2 = word2.toCharArray();
            System.out.println(word1 + " - > " + Arrays.toString(charArr1));
            System.out.println(word2 + " - > " + Arrays.toString(charArr2));
            System.out.println();
            Arrays.sort(charArr1);
            Arrays.sort(charArr2);

            System.out.println("sorted array from word number 1 - " + Arrays.toString(charArr1));
            System.out.println("sorted array from word number 2 - " + Arrays.toString(charArr1));


            for (int i = 0; i < charArr1.length; i++) {
                if (charArr1[i] != charArr2[i]) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

}
