import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String originin;
        try {
            originin = scanner.nextLine();
        } catch (Exception e) {
            System.out.println("WRONG FORMAT!");
            originin = "";
            System.exit(0);
        }
        In in = new In(originin);
        Com total = in.getTotal();
        //System.out.println(total);
        Com ans = total.der();
        System.out.println(ans);
    }
}
