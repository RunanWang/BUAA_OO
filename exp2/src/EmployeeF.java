public class EmployeeF extends Employee{
    public EmployeeF(int no) {
        super(no, 'F', 0);
    }
    
    public double getSalary(int weekTime) {
        super.setEmSalary(super.getAveSalary());
        if(weekTime>=super.getAveTime()){
            System.out.print("A ");
            return super.getSalary()+0.3*super.getAveSalary();
        }
        else{
            System.out.print("B ");
            return super.getSalary()+0.1*super.getAveSalary();
        }
    }
    
}
