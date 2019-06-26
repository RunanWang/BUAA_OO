import com.oocourse.elevator2.PersonRequest;

import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

public class RequestQueue {
    private Vector<PersonRequest> queue = new Vector<>();
    private AtomicBoolean endFlag = new AtomicBoolean(false);
    private Integer total = new Integer(0);
    
    /* 关于共享资源
     * 每当电梯想要获取主任务但队列为空时会进入wait
     * 两种情况下输入程序会唤醒电梯
     * 一是输入了新的需求
     * 二是输入了结束
     * */
    
    /* 关于锁的遗憾
     * 理论上只锁queue变量就可以
     * 但是由于共享的变量是RequestQueue
     * 所以锁是整个类
     * 因此就要将每个方法设计的十分简单
     * 只实现基础的存取删，更复杂的逻辑在调用者中实现
     * */
    
    /* 用于向队列中加入需求
     * r是需要加入的需求，格式就是输入的格式
     * 会加入到queue中，当是第一项时会唤醒电梯
     * */
    public synchronized void addQueue(PersonRequest r) {
        queue.add(r);
        total++;
        if (total == 1) {
            notifyAll();
        }
    }
    
    /* 用于标记结束
     * 会把endFlag置成true
     * 当读到Ctrl-D时调用
     * */
    public synchronized void setEnd() {
        endFlag.set(true);
        notifyAll();
    }
    
    /* 用于判断需求queue是否是空
     * 空则true，不空false
     * */
    public synchronized boolean isEmpty() {
        if (total == 0) {
            return true;
        } else {
            return false;
        }
    }
    
    /* 用于判断需求是否结束(endFlag是否为true)
     * 结束则true，没结束false
     * */
    public synchronized boolean isEnd() {
        return endFlag.get();
    }
    
    /* 用于获取主需求所在楼层
     * 若需求队列为空，则wait
     * 再上一层会catch抛出，如果队列中什么都没有(outOfRange）
     * 说明输入结束了
     * 这里留了一个小接口，可以用来指定主需求的index
     * 设置为0时是ALS
     * */
    public synchronized int getFirstRequest(int i) {
        while (isEmpty()) {
            try {
                wait();
                //唤醒后如果需求输入结束则退出
                if (isEnd()) {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return queue.elementAt(i).getFromFloor();
    }
    
    /* 用于返回指定index的需求
     * 若index超出界限则返回null
     * 是优化的接口
     * */
    public synchronized PersonRequest getRequest(int index) {
        PersonRequest r;
        try {
            r = queue.elementAt(index);
        } catch (Exception e) {
            r = null;
        }
        return r;
    }
    
    
    /* 用于让index项的需求出列
     * 说明该项进电梯了
     * */
    public synchronized PersonRequest outQueue(int index) {
        PersonRequest r = queue.get(index);
        queue.remove(index);
        total--;
        return r;
    }
}
