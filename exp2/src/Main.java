import java.util.Scanner;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        ArrayList<Employee> list = new ArrayList<>();
        int searchType=0;
        EmployeeR emp001 = new EmployeeR(1,5);
        EmployeeR emp002 = new EmployeeR(2,1.2);
        EmployeeF emp003 = new EmployeeF(3);
        EmployeeF emp004 = new EmployeeF(4);
        EmployeeM emp005 = new EmployeeM(5);
        EmployeeM emp006 = new EmployeeM(6);
        EmployeeS emp007 = new EmployeeS(7);
        EmployeeS emp008 = new EmployeeS(8);
        
        Scanner scanner = new Scanner(System.in);
        searchType = scanner.nextInt();
        if(searchType == 1){
            searchType = scanner.nextInt();
            try{
            if(searchType ==1){
                System.out.print(emp001.getSalary(scanner.nextInt()));
            }
            else if(searchType ==2){
                System.out.print(emp002.getSalary(scanner.nextInt()));
            }
            else if(searchType ==3){
                System.out.print(emp003.getSalary(scanner.nextInt()));
            }
            else if(searchType ==4){
                System.out.print(emp004.getSalary(scanner.nextInt()));
            }
            else if(searchType ==5){
                
                System.out.print(emp005.getSalary(scanner.nextInt()));
            }
            else if(searchType ==6){
                System.out.print(emp006.getSalary(scanner.nextInt()));
            }
            else if(searchType ==7){
                scanner.nextInt();
                System.out.print(emp007.getSalary(scanner.nextDouble()));
            }
            else if(searchType ==8){
                scanner.nextInt();
                System.out.print(emp008.getSalary(scanner.nextDouble()));
            }
            else{
                System.out.print("Invalid Query!");
            }}
            catch(Exception e){
                System.out.print("Invalid Query!");
            }
        }
        else if(searchType ==2){
            searchType = scanner.nextInt();
            if(searchType >=1&&searchType<=4){
                ReimF r = new ReimF();
                String in= scanner.next();
                r.judge(searchType, in.charAt(0), in.charAt(2), "",'Y');
            }
            else if(searchType >=5&&searchType<=8){
                ReimM r = new ReimM();
                String in= scanner.next();
                r.judge(searchType, in.charAt(0), in.charAt(2), "",'Y');
            }
            else{
                System.out.print("Invalid Query!");
            }
        }
    }
    
}
