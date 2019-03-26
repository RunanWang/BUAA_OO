public class EmployeeM extends Employee{
    public EmployeeM(int no) {
        super(no, 'M', 0);
    }
    
    public double getSalary(int weekTime) {
        super.setEmSalary(super.getAveSalary()+15000);
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