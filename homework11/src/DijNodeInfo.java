
//这个类是为了存储节点信息所建的类，里面包含所有与这个点相连接的点的信息
public class DijNodeInfo {
    // 这个节点的id
    private Integer id;
    //最短路径
    private int shortest = -1;
    //最少票价
    private int leastPrice = -1;
    //最高满意度
    private int mostSat = -1;
    //最少换乘
    private int leastChange = -1;
    
    public DijNodeInfo(int id) {
        this.id = id;
    }
    
    public int getLeastChange() {
        return leastChange;
    }
    
    public int getLeastPrice() {
        return leastPrice;
    }
    
    public int getMostSat() {
        return mostSat;
    }
    
    public int getShortest() {
        return shortest;
    }
    
    public void setLeastChange(int leastChange) {
        this.leastChange = leastChange;
    }
    
    public void setLeastPrice(int leastPrice) {
        this.leastPrice = leastPrice;
    }
    
    public void setMostSat(int mostSat) {
        this.mostSat = mostSat;
    }
    
    public void setShortest(int shortest) {
        this.shortest = shortest;
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
