package demo1;

import machine.Container;

import java.util.Random;
import java.util.concurrent.TimeUnit;

// 消费者
public class Consumer implements Runnable {
    private int windowId;
    //模拟生产容器
    private final Container<Customer> container;
    //监听生产者线程
    private final Object producerMonitor;
    //监听消费者线程
    private final Object consumerMonitor;
    static final int consumeSpeed = 10000;
    
    public Consumer(Object producerMonitor, Object consumerMonitor, Container<Customer> container, int id) {
        this.producerMonitor = producerMonitor;
        this.consumerMonitor = consumerMonitor;
        this.container = container;
        this.windowId = id;
    }
    
    @Override
    public void run() {
        while (true) {
            consume();
        }
    }
    
    //消费
    public void consume() {
        synchronized (producerMonitor) {
            if (container.getSize()<100) {
                //System.out.println("empty and notify");
                producerMonitor.notifyAll();
            }
        }
        
        while (container.isEmpty()) {
            try {
                synchronized (consumerMonitor) {
                    consumerMonitor.wait();
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        
        Customer c = container.get();
        
        //System.out.println("unlockpro");
        String cid = String.valueOf(c.getId());
        String wid = String.valueOf(windowId);
        System.out.println(cid + "号顾客请到" + wid + "号窗口");
        
        //System.out.println("unlockcon");
        Random ran = new Random();
        int sleepTime = ran.nextInt(consumeSpeed);
        try {
            Thread.sleep(sleepTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //}
    
}
