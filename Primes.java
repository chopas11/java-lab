public class Primes {

    public static void main(String[] args) {

        for(int i = 2; i < 100; i++) {
            if (isPrime(i)) System.out.println(i);
        }
    }

    public static boolean isPrime(int n) {
        
        for(int num = 2; num < n - 1; num++) {
            if (n % num == 0) return false;
        }
        return true;
    }


}