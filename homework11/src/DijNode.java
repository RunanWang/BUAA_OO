import java.util.HashMap;

public class DijNode {
    private Integer nativeid = 0;
    private HashMap<Integer, DijNodeInfo> nextNodes = new HashMap<>(120);
    
    public DijNode(int id) {
        nativeid = id;
    }
    
    public void addNextNode(int id) {
        DijNodeInfo info = new DijNodeInfo(id);
        nextNodes.put(id, info);
    }
    
    public void setLeastChange(int leastChange, int id) {
        if (!nextNodes.containsKey(id)) {
            DijNodeInfo info = new DijNodeInfo(id);
            nextNodes.put(id, info);
        }
        nextNodes.get(id).setLeastChange(leastChange);
    }
    
    public void setLeastPrice(int leastPrice, int id) {
        if (!nextNodes.containsKey(id)) {
            DijNodeInfo info = new DijNodeInfo(id);
            nextNodes.put(id, info);
        }
        nextNodes.get(id).setLeastPrice(leastPrice);
    }
    
    public void setMostSat(int mostSat, int id) {
        if (!nextNodes.containsKey(id)) {
            DijNodeInfo info = new DijNodeInfo(id);
            nextNodes.put(id, info);
        }
        nextNodes.get(id).setMostSat(mostSat);
    }
    
    public void setShortest(int shortest, int id) {
        if (!nextNodes.containsKey(id)) {
            DijNodeInfo info = new DijNodeInfo(id);
            nextNodes.put(id, info);
        }
        nextNodes.get(id).setShortest(shortest);
    }
    
    public int getLeastChange(int id) {
        if (nextNodes.containsKey(id)) {
            return nextNodes.get(id).getLeastChange();
        } else {
            addNextNode(id);
            return -1;
        }
    }
    
    public int getLeastPrice(int id) {
        if (nextNodes.containsKey(id)) {
            return nextNodes.get(id).getLeastPrice();
        } else {
            addNextNode(id);
            return -1;
        }
    }
    
    public int getMostSat(int id) {
        if (nextNodes.containsKey(id)) {
            return nextNodes.get(id).getMostSat();
        } else {
            addNextNode(id);
            return -1;
        }
    }
    
    public int getShortest(int id) {
        if (nextNodes.containsKey(id)) {
            return nextNodes.get(id).getShortest();
        } else {
            addNextNode(id);
            return -1;
        }
    }
    
    @Override
    public int hashCode() {
        return nativeid.hashCode();
    }
}
