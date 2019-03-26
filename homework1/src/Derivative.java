import java.util.Scanner;

public class Derivative {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String originin = scanner.nextLine();
        Expression exp = new Expression(originin);
        if (exp.ExpressionDivide() == -1) {
            return;
        }
        exp.expDer();
        exp.printExpression();
    }
}





