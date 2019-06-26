public class MainClass {
    public static void main(String[] args) {
        ReQueue totalQueue = new ReQueue();
        ReQueue queueA = new ReQueue();
        ReQueue queueB = new ReQueue();
        ReQueue queueC = new ReQueue();
        Input in = new Input(totalQueue);
        in.start();
        Output.printInit();
        EleA eleA = new EleA(queueA, totalQueue);
        EleB eleB = new EleB(queueB, totalQueue);
        EleC eleC = new EleC(queueC, totalQueue);
        eleA.start();
        eleB.start();
        eleC.start();
        Monitor monitor = new Monitor(totalQueue, queueA, queueB, queueC);
        monitor.start();
    }
}
