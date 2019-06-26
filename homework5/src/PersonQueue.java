import com.oocourse.elevator1.PersonRequest;

import java.util.ArrayList;

public class PersonQueue {
    private ArrayList<PersonRequest> listQueue = new ArrayList<>();
    private int total = 0;
    private boolean end = false;
    
    public synchronized void addQueue(PersonRequest r) {
        listQueue.add(r);
        total++;
        if (total == 1) {
            notifyAll();
        }
    }
    
    public synchronized PersonRequest outQueue() {
        PersonRequest r = listQueue.get(0);
        listQueue.remove(0);
        total--;
        return r;
    }
    
    public synchronized int getPass() {
        if (total == 0) {
            try {
                if (end) {
                    return -1;
                }
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (end && total == 0) {
            return -1;
        }
        PersonRequest r = listQueue.get(0);
        return r.getFromFloor();
    }
    
    public synchronized int getTotal() {
        return total;
    }
    
    public synchronized void setEnd() {
        end = true;
        notifyAll();
    }
}
