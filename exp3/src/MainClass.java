public class MainClass {
    
    public static void main(String[] args) {
        // 实例化银行对象
        Bank bank = new Bank();
        
        Person pa = new Person(bank,"Counter", 300);
        Person pb = new Person(bank,"ATM",200);
        Person pc = new Person(bank, "App",100);
        
        // 从不同终端取钱
        pa.start();
        pb.start();
        pc.start();
    }
}