import java.util.HashMap;

public class BasicNode {
    private Integer nativeid;
    private int temp;
    private int lineTemp;
    private HashMap<Integer, Integer> onLines = new HashMap<>(50);
    // 第一个是节点到达这个node的搭乘线路，
    // 1,<1,2>即为可乘坐1\2号线从出发点到这个id点
    private HashMap<Integer, BasicNodeInfo> nextNodes = new HashMap<>(120);
    //private HashMap<Integer, Integer> nextNodes = new HashMap<>(120);
    private HashMap<Integer, Integer> linesid = new HashMap<>(50);
    
    public BasicNode(int id) {
        nativeid = id;
    }
    
    public void addNestNode(int id, int line) {
        //有这个邻接点
        if (nextNodes.containsKey(id)) {
            BasicNodeInfo info = nextNodes.get(id);
            info.addLine(line);
            linesid.put(line, 1);
        }
        //没有这份邻接点
        else {
            BasicNodeInfo info = new BasicNodeInfo(id);
            info.addLine(line);
            nextNodes.put(id, info);
            linesid.put(line, 1);
        }
    }
    
    /*
    public void addNestNode(int id, int line) {
        nextNodes.put(id, line);
        linesid.put(line, 1);
    }
    */
    public HashMap<Integer, BasicNodeInfo> getNextNodes() {
        return nextNodes;
    }
    
    public HashMap<Integer, Integer> getLinesid() {
        return (HashMap<Integer, Integer>) linesid.clone();
    }
    
    public void setTemp(int temp) {
        this.temp = temp;
    }
    
    public int getTemp() {
        return temp;
    }
    
    public int getLineTemp() {
        return lineTemp;
    }
    
    public void setLineTemp(int lineTemp) {
        this.lineTemp = lineTemp;
    }
    
    public HashMap<Integer, Integer> getOnLines() {
        return onLines;
    }
    
    public void addOnLines(Integer lineId, int price) {
        if (!onLines.containsKey(lineId)) {
            onLines.put(lineId, price);
        } else {
            if (onLines.get(lineId) > price) {
                onLines.put(lineId, price);
            }
        }
    }
    
    public void addOnLines(Integer lineId) {
        onLines.put(lineId, 1);
    }
    
    public void clearOnlines() {
        onLines = new HashMap<>(50);
    }
    
    @Override
    public int hashCode() {
        return nativeid.hashCode();
    }
}
