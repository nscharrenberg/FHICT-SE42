package part2;

public class mainApp {
    private static final String SIGNER = "Kevin";

    public static void main(String[] args) {
       KeySignatureWriter skw = new KeySignatureWriter();

       skw.sign(SIGNER);
    }
}
