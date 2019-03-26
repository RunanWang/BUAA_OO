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
        Expression exp = new Expression(originin);
        if (exp.expressionDivide() == -1) {
            return;
        }
        exp.expDer();
        //exp.printExpression();
        exp.expressionMerge();
        exp.printDerExpression();
    }
}
