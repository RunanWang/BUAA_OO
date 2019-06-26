//Producer.java
package demo1;

import machine.Container;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 生产者
 */
public class Producer implements Runnable {
    //生产容器
    private final Container<Customer> container;
    // 监听生产者线程
    private final Object producerMonitor;
    //监听消费者线程
    private final Object consumerMonitor;
    static final int produceSpeed = 300;
    
    public Producer(Object producerMonitor, Object consumerMonitor, Container<Customer> container) {
        this.producerMonitor = producerMonitor;
        this.consumerMonitor = consumerMonitor;
        this.container = container;
    }
    
    @Override
    public void run() {
        while (true) {
            produce();
            
        }
    }
    
    //取号机生产等待的客户，注意模拟前后两个客户之间的时间间隔
    public void produce() {
        synchronized (producerMonitor) {
        while (container.isFull()) {
            try {
                
                    System.out.println("大厅容量已满暂停发号");
                    producerMonitor.wait();
                    //System.out.println("wait end");
                
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }}
        Customer c = new Customer();
        container.add(c);
        if (container.getSize() == 1) {
            synchronized (consumerMonitor) {
                consumerMonitor.notify();
            }
        }
        String id = String.valueOf(c.getId());
        System.out.println(id + "号顾客正在等待服务");
        Random ran = new Random();
        int sleepTime = ran.nextInt(300);
        try {
            Thread.sleep(sleepTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}