import java.util.Scanner;

public class MatrixCal {
    
    private int[][] matrix1;
    private int[][] matrix2;
    private int dim;
    private char operator;
    
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        Matrix m1 = new Matrix(keyboard.nextLine());
        String op;
        char operator = '\0';
        op = keyboard.nextLine();
        operator = op.charAt(0);
        if (operator == 't') {
            System.out.print(m1.transpose());
        }
        else if (operator == '+') {
            Matrix m2 = new Matrix(keyboard.nextLine());
            System.out.print(m1.add(m2));
        }
        else if (operator == '-') {
            Matrix m2 = new Matrix(keyboard.nextLine());
            System.out.print(m1.sub(m2));
        }
        else if (operator == '*') {
            Matrix m2 = new Matrix(keyboard.nextLine());
            System.out.print(m1.multiply(m2));
        }
        else {
            System.out.println("Illegal Input!");
            System.exit(0);
        }
        keyboard.close();
    }
}

