public class GraphEdge {
    private Integer id1;
    private Integer id2;
    
    public GraphEdge(int a, int b) {
        id1 = a;
        id2 = b;
    }
    
    public int getId1() {
        return id1;
    }
    
    public int getId2() {
        return id2;
    }
    
    @Override
    public boolean equals(Object edg) {
        return (id1 == ((GraphEdge) edg).getId1()
                && id2 == ((GraphEdge) edg).getId2()
                || id2 == ((GraphEdge) edg).getId1()
                && id1 == ((GraphEdge) edg).getId2());
    }
    
    @Override
    public int hashCode() {
        return id1.hashCode() ^ id2.hashCode();
    }
}
