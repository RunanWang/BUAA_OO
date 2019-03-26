public class ReimF implements Reim {
    public void judge(int no, char cat, int val, String use, char color) {
        if (color == 'Y') {
            if (val <= 1000) {
                if (cat == 'I') {
                    System.out.print("Reimbursement Accepted!");
                    System.exit(0);
                    
                } else {
                    
                    System.out.print("Reimbursement Refused!");
                    System.exit(0);
                    
                }
            } else {
                System.out.print("Reimbursement Refused!");
                System.exit(0);
            }
        } else {
            System.out.print("Reimbursement Refused!");
            System.exit(0);
        }
    }
}
