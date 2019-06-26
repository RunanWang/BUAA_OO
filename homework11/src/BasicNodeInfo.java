import java.util.HashMap;

public class BasicNodeInfo {
    // 这个类是为了存储节点的基础信息：
    // 邻接点的id；用于生成hashcode
    private Integer nativeid;
    // 第一个是节点到达这个node的搭乘线路，
    // 1即为可乘坐1号线从出发点到这个id点
    private HashMap<Integer, Integer> linesid = new HashMap<>(50);
    
    public BasicNodeInfo(int id) {
        nativeid = id;
    }
    
    //然后向图中加入到达这个点是几号线
    public void addLine(int lineid) {
        linesid.put(lineid, 1);
    }
    
    public HashMap<Integer, Integer> getLinesid() {
        return linesid;
    }
    
    @Override
    public int hashCode() {
        return nativeid.hashCode();
    }
}
