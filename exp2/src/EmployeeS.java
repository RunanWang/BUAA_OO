public class EmployeeS extends Employee {
    private double aveSale;
    public EmployeeS(int no) {
        super(no, 'S', 0);
        aveSale = 300000;
    }
    
    public double getSalary(double sale) {
        super.setEmSalary(super.getAveSalary());
        if(sale>aveSale){
            System.out.print("A ");
        }else{
            System.out.print("B ");
        }
        return super.getSalary()+0.2*(sale-aveSale);
    }
}