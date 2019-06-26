public class MainClass {
    public static void main(String[] args) {
        RequestQueue queue = new RequestQueue();
        Elevator ele = new Elevator(queue);
        Input in = new Input(queue);
        Output.printInit();
        //ele.setPriority(1);
        //ele.setPriority(1);
        in.start();
        ele.start();
    }
}
