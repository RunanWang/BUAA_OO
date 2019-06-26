import com.oocourse.elevator1.ElevatorInput;
import com.oocourse.elevator1.PersonRequest;

public class Input extends Thread {
    private ElevatorInput elevatorInput = new ElevatorInput(System.in);
    private PersonQueue queue;
    
    public Input(PersonQueue q) {
        queue = q;
    }
    
    public void run() {
        PersonRequest request = getInput();
        while (request != null) {
            queue.addQueue(request);
            //第一个乘客需要唤醒电梯
            request = getInput();
        }
        queue.setEnd();
        try {
            elevatorInput.close();
        } catch (Exception e) {
            //System.out.println(e);
            e.printStackTrace();
        }
    }
    
    private PersonRequest getInput() {
        PersonRequest request = elevatorInput.nextPersonRequest();
        // when request == null
        // it means there are no more lines in stdin
        //System.out.println(request);
        /*
        try {
            elevatorInput.close();
        } catch (Exception e) {
            //System.out.println(e);
            e.printStackTrace();
        }*/
        return request;
    }
    
}
