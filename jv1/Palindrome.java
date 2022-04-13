public class Palindrome {
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i] + " - " + isPalindrome(args[i]) );
        }

        
    }

    public static String reverseString(String str) {
        String revStr = "";
        for (int i = str.length()-1; i >= 0; i--) {
            revStr += str.charAt(i);
        }
        return revStr;
    }

    public static boolean isPalindrome(String str) {
        String str2 = reverseString(str);
        return str.equals(str2);
    }
}