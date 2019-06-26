//Customer.java
package demo1;

public class Customer {
    
    //模拟取号机，号码每次自动递增，得到客户编号
    private final int id = counter++;
    private static int counter = 1;
    
    public int getId() {
        return id;
    }
}
