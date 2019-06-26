import java.util.HashMap;

public class GraphNode {
    //nextID, shortest length
    private HashMap<Integer, Integer> nextNodes = new HashMap<>(260);
    private Integer nativeid;
    
    public GraphNode(int nativeid) {
        this.nativeid = nativeid;
    }
    
    public HashMap<Integer, Integer> getNextNodes() {
        return nextNodes;
    }
    
    public void addNextNode(int nextId) {
        if (nextId != nativeid) {
            nextNodes.put(nextId, 1);
            //System.out.println(nativeid + " and " + nextID);
        }
    }
    
    public void addNextNode(int nextId, int length) {
        nextNodes.put(nextId, length);
    }
    
    public void deleteNextNode(int nextId) {
        int i = nextNodes.remove(nextId);
    }
    
    public boolean containsNextNode(int nextId) {
        return nextNodes.containsKey(nextId);
    }
    
    @Override
    public int hashCode() {
        return nativeid.hashCode();
    }
    
    @Override
    public String toString() {
        String out = nativeid.toString() + "\n";
        for (Integer id : nextNodes.keySet()) {
            out = out + id.toString() +
                    "length is " + nextNodes.get(id).toString();
        }
        return out;
    }
}
