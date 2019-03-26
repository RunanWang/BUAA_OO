public class Employee {
    private int emNo;
    private char emCat;
    private double emSalary;
    private double emAveSalary;
    private int emAveTime;
    private double emWorkTime;
    
    public Employee(int no, char cat, double workTime) {
        emNo = no;
        emCat = cat;
        emAveSalary = 50000;
        emWorkTime = workTime;
        emAveTime = 40;
    }
    
    public int getNo() {
        return emNo;
    }
    
    public char getCat() {
        return emCat;
    }
    
    public double getWorkTime() {
        return emWorkTime;
    }
    
    public int getAveTime() {
        return emAveTime;
    }
    
    public double getAveSalary() {
        return emAveSalary;
    }
    
    public double getSalary() {
        return emSalary;
    }
    
    public void setEmCat(char emCat) {
        this.emCat = emCat;
    }
    
    public void setEmNo(int emNo) {
        this.emNo = emNo;
    }
    
    public void setEmSalary(double emSalary) {
        this.emSalary = emSalary;
    }
    
    public void setEmWorkTime(double emWorkTime) {
        this.emWorkTime = emWorkTime;
    }
}
