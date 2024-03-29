package machine;

import java.util.ArrayList;
import java.util.List;

public class Container<T> {
    
    private final int capacity;
    private final List<T> list;
    
    public Container(int capacity) {
        this.capacity = capacity;
        list = new ArrayList<T>(capacity);
    }
    
    public List<T> getList() {
        return list;
    }
    
    public synchronized boolean add(T product) {
        if (isFull()) {
            return false;
        }
        list.add(product);
        return true;
    }
    
    public synchronized boolean isFull() {
        return list.size() >= capacity;
    }
    
    public synchronized boolean isEmpty() {
        return list.isEmpty();
    }
    
    public synchronized T get() {
        if (isEmpty()) {
            return null;
        }
        T t = list.get(0);
        list.remove(0);
        return t;
    }
    
    public int getSize() {
        return list.size();
    }
    
    public int getCapacity() {
        return capacity;
    }
}