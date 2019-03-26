public class EmployeeR extends Employee {
    public EmployeeR(int no, double workTime) {
        super(no, 'R', workTime);
    }
    
    public double getSalary(int weekTime) {
        super.setEmSalary(super.getAveSalary() + super.getWorkTime() * 1000);
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
