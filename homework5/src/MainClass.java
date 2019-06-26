public class MainClass {
    public static void main(String[] args) {
        
        PersonQueue queue = new PersonQueue();
        Elevator ele = new Elevator(queue);
        Input in = new Input(queue);
        Output.printInit();
        //ele.setPriority(1);
        in.start();
        ele.start();
    }
}
