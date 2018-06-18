package part3;

public class mainApp {
    private static final String FILE = "Content signed by Noah.txt";

    public static void main(String[] args) {
        SignatureChecker sc = new SignatureChecker();
        boolean isSigner = sc.isSigned(FILE);
        System.out.println(isSigner ? "This file is verified to by signed by the signer" : "This file is NOT signed by the signer");
    }
}
