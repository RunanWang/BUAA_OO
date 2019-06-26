import com.oocourse.elevator1.PersonRequest;

public class Elevator extends Thread {
    private int eleFloor = 1;
    private PersonQueue queue;
    private PersonRequest request;
    
    public Elevator(PersonQueue q) {
        queue = q;
    }
    
    public void run() {
        long sleeptime = 0;
        int floor = 0;
        while (true) {
            //若没有乘客则进行等待
            floor = getFirst();
            if (floor == -1) {
                break;
            }
            //有乘客则先运行到第一位乘客所在楼层然后开门
            sleeptime = eleGoto(floor);
            waitTime(sleeptime);
            Output.printOpen(eleFloor);
            //接客
            floor = eleOpen();
            sleeptime = 500;
            try {
                sleep(sleeptime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //关门
            Output.printClose(eleFloor);
            //运行送乘客
            sleeptime = eleGoto(floor);
            waitTime(sleeptime);
            //开门关门
            Output.printOpen(eleFloor);
            Output.printOut(request.getPersonId(), request.getToFloor());
            sleeptime = 500;
            try {
                sleep(sleeptime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Output.printClose(eleFloor);
        }
    }
    
    private long eleGoto(int dest) {
        long time = 0;
        if (dest >= eleFloor) {
            time = (dest - eleFloor) * 500;
        } else {
            time = (eleFloor - dest) * 500;
        }
        eleFloor = dest;
        return time;
    }
    
    private int eleOpen() {
        request = queue.outQueue();
        int dest = request.getToFloor();
        Output.printIn(request.getPersonId(), request.getFromFloor());
        return dest;
    }
    
    private int getFirst() {
        int dest = queue.getPass();
        //eleFloor = dest;
        return dest;
    }
    
    private void waitTime(long sleeptime) {
        try {
            sleep(sleeptime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
