public class ReimM implements Reim {
    public void judge(int no, char cat, int val, String use, char color) {
        if(color=='P'){
            if(use.equals("")){
                System.out.print("Reimbursement Refused!");
                System.exit(0);
            }else{
                System.out.print("Reimbursement Accepted!");
                System.exit(0);
            }
        }else{
            System.out.print("Reimbursement Refused!");
            System.exit(0);
        }
    }
}