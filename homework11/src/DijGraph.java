import com.oocourse.specs3.models.Path;

import java.util.HashMap;

public class DijGraph {
    private HashMap<Integer, DijNode> dijGraph;
    private BasicGraph basicGraph;
    private HashMap<Integer, BasicNode> graph;
    private HashMap<Integer, Path> idMap = new HashMap<>(300);
    
    private int mintempid;
    private int mintemp;
    
    private int minmintempid;
    private int minmintemp;
    
    public DijGraph(BasicGraph g, HashMap<Integer, Path> idMap) {
        dijGraph = new HashMap<>(120);
        basicGraph = g;
        graph = g.getBasicGraph();
        this.idMap = idMap;
    }
    
    public void renewGraph(BasicGraph g, HashMap<Integer, Path> idMap) {
        dijGraph.clear();
        basicGraph = g;
        graph = g.getBasicGraph();
        this.idMap = idMap;
    }
    
    public void addNode(int id) {
        if (!dijGraph.containsKey(id)) {
            DijNode node = new DijNode(id);
            dijGraph.put(id, node);
            //System.out.print("No added: ");
            //System.out.println(id);
        }
    }
    
    public void setLeastChange(int leastChange, int fromid, int toid) {
        addNode(fromid);
        addNode(toid);
        dijGraph.get(fromid).setLeastChange(leastChange, toid);
        dijGraph.get(toid).setLeastChange(leastChange, fromid);
    }
    
    public void setLeastPrice(int leastPrice, int fromid, int toid) {
        addNode(fromid);
        addNode(toid);
        dijGraph.get(fromid).setLeastPrice(leastPrice, toid);
        dijGraph.get(toid).setLeastPrice(leastPrice, fromid);
    }
    
    public void setMostSat(int mostSat, int fromid, int toid) {
        addNode(fromid);
        addNode(toid);
        dijGraph.get(fromid).setMostSat(mostSat, toid);
        dijGraph.get(toid).setMostSat(mostSat, fromid);
    }
    
    public void setShortest(int shortest, int fromid, int toid) {
        addNode(fromid);
        addNode(toid);
        dijGraph.get(fromid).setShortest(shortest, toid);
        dijGraph.get(toid).setShortest(shortest, fromid);
    }
    
    public int getLeastChange(int fromid, int toid) {
        if (!dijGraph.containsKey(fromid)) {
            addNode(fromid);
        }
        int ans = dijGraph.get(fromid).getLeastChange(toid);
        if (ans != -1) {
            return ans;
        } else {
            calLeastChange(fromid);
            ans = dijGraph.get(fromid).getLeastChange(toid);
            return ans;
        }
    }
    
    private void calLeastChange(int beginid) {
        //map是包含beginid的一个连通集合
        HashMap<Integer, Integer> map = basicGraph.conSet(beginid);
        tempInit(map);
        //map中是未被纳入图的点
        map.remove(beginid);
        //起始点设置为0次换乘
        graph.get(beginid).setTemp(0);
        int nextid = beginid;
        while (!map.isEmpty()) {
            mintempid = nextid;
            mintemp = -1;
            //对整个图进行更新
            for (Integer id : map.keySet()) {
                //如果id和nextid之间有相同的线路号，说明一趟车就可以过去，
                //所以取id和nextid换乘一次的最小值
                //System.out.println(nextid);
                for (Integer lineid : graph.get(nextid).getLinesid().keySet()) {
                    if (graph.get(id).getLinesid().containsKey(lineid)) {
                        int temp;
                        if (nextid == beginid) {
                            //System.out.println(graph.get(nextid).getTemp());
                            temp = min(graph.get(nextid).getTemp(),
                                    graph.get(id).getTemp());
                        } else {
                            temp = min(graph.get(nextid).getTemp() + 1,
                                    graph.get(id).getTemp());
                        }
                        graph.get(id).setTemp(temp);
                        if (map.containsKey(id)) {
                            minid(temp, id);
                        }
                    } else {
                        int temp = min(-1, graph.get(id).getTemp());
                        graph.get(id).setTemp(temp);
                        if (map.containsKey(id)) {
                            minid(temp, id);
                        }
                    }
                }
            }
            if (mintemp == -1) {
                for (Integer id : map.keySet()) {
                    minid(graph.get(id).getTemp(), id);
                }
            }
            //System.out.println(mintempid);
            map.remove(mintempid);
            setLeastChange(mintemp, beginid, mintempid);
            nextid = mintempid;
            /*
            System.out.print(mintempid);
            System.out.print(" is ");
            System.out.println(mintemp);*/
        }
    }
    
    public int getLeastPrice(int fromid, int toid) {
        if (!dijGraph.containsKey(fromid)) {
            addNode(fromid);
        }
        int ans = dijGraph.get(fromid).getLeastPrice(toid);
        if (ans != -1) {
            return ans;
        } else {
            calLeastPrice(fromid);
            ans = dijGraph.get(fromid).getLeastPrice(toid);
            return ans;
        }
    }
    
    private void calLeastPrice(int beginid) {
        HashMap<Integer, Integer> map = basicGraph.conSet(beginid);
        tempInit(map);
        int price = 0;
        graph.get(beginid).setTemp(price);
        map.remove(beginid);
        int nextid = beginid;
        //将每一个lineid加入到beginid，并设置起始价为0
        //nextid是
        for (Integer lineid : graph.get(beginid).getLinesid().keySet()) {
            graph.get(beginid).addOnLines(lineid, 0);
        }
        while (!map.isEmpty()) {
            for (Integer id : graph.get(nextid).getNextNodes().keySet()) {
                if (map.containsKey(id)) {
                    for (Integer lineid : graph.get(id).getLinesid().keySet()) {
                        if (graph.get(nextid).getOnLines().containsKey(lineid)
                                && graph.get(nextid).getNextNodes().get(id).
                                getLinesid().containsKey(lineid)) {
                            int temp = min(graph.get(nextid).getOnLines()
                                            .get(lineid) + 1,
                                    graph.get(id).getTemp());
                            graph.get(id).addOnLines(lineid,
                                    graph.get(nextid)
                                            .getOnLines().get(lineid) + 1);
                            graph.get(id).setTemp(temp);
                        }
                        if (graph.get(nextid).getLinesid().containsKey(lineid)
                                && graph.get(nextid).getNextNodes().get(id).
                                getLinesid().containsKey(lineid)) {
                            int temp = min(graph.get(nextid).getTemp() + 3,
                                    graph.get(id).getTemp());
                            /*
                            if (temp != graph.get(id).getTemp()) {
                                graph.get(id).clearOnlines();
                                graph.get(id).addOnLines(lineid);
                                graph.get(id).setTemp(temp);
                            } else if (graph.get(nextid).getTemp() + 1 ==
                                    graph.get(id).getTemp()) {
                                graph.get(id).addOnLines(lineid);
                            }*/
                            graph.get(id).addOnLines(lineid,
                                    graph.get(nextid).getTemp() + 3);
                            graph.get(id).setTemp(temp);
                        }
                    }
                }
            }
            
            price++;
            for (Integer id : map.keySet()) {
                if (graph.get(id).getTemp() == price) {
                    nextid = id;
                    price--;
                    break;
                }
            }
            setLeastPrice(graph.get(nextid).getTemp(), beginid, nextid);
            map.remove(nextid);
        }
    }
    
    public int getMostSat(int fromid, int toid) {
        if (!dijGraph.containsKey(fromid)) {
            addNode(fromid);
        }
        int ans = dijGraph.get(fromid).getMostSat(toid);
        if (ans != -1) {
            return ans;
        } else {
            calMostSat(fromid);
            ans = dijGraph.get(fromid).getMostSat(toid);
            return ans;
        }
    }
    
    private void calMostSat(int beginid) {
        HashMap<Integer, Integer> map = basicGraph.conSet(beginid);
        tempInit(map);
        int price = 0;
        graph.get(beginid).setTemp(price);
        map.remove(beginid);
        int nextid = beginid;
        for (Integer lineid : graph.get(beginid).getLinesid().keySet()) {
            graph.get(beginid).addOnLines(lineid, 0);
        }
        while (!map.isEmpty()) {
            mintemp = -1;
            mintempid = -1;
            for (Integer id : graph.get(nextid).getNextNodes().keySet()) {
                if (map.containsKey(id)) {
                    for (Integer lineid : graph.get(id).getLinesid().keySet()) {
                        if (graph.get(nextid).getOnLines().containsKey(lineid)
                                && graph.get(nextid).getNextNodes().get(id).
                                getLinesid().containsKey(lineid)) {
                            int ang = (int) Math.pow(4, max(
                                    (nextid % 5 + 5) % 5, (id % 5 + 5) % 5));
                            int temp = min(graph.get(nextid)
                                            .getOnLines().get(lineid) + ang,
                                    graph.get(id).getTemp());
                            graph.get(id).addOnLines(lineid,
                                    graph.get(nextid)
                                            .getOnLines().get(lineid) + ang);
                            graph.get(id).setTemp(temp);
                        }
                        if (graph.get(nextid).getLinesid().containsKey(lineid)
                                && graph.get(nextid).getNextNodes().get(id)
                                .getLinesid().containsKey(lineid)) {
                            int ang = (int) Math.pow(4, max(
                                    (nextid % 5 + 5) % 5, (id % 5 + 5) % 5));
                            int temp = min(graph.get(nextid).getTemp()
                                    + ang + 32, graph.get(id).getTemp());
                            graph.get(id).addOnLines(lineid, graph.get(nextid)
                                    .getTemp() + ang + 32);
                            graph.get(id).setTemp(temp);
                        }
                    }
                }
            }
            
            
            for (Integer id : map.keySet()) {
                minid(graph.get(id).getTemp(), id);
            }
            //System.out.println(mintempid);
            //System.out.println(graph.get(2).getTemp());
            nextid = mintempid;
            setMostSat(graph.get(nextid).getTemp(), beginid, nextid);
            map.remove(nextid);
            // System.out.println(map);
        }
    }
    
    public int getShortest(int fromid, int toid) {
        if (!dijGraph.containsKey(fromid)) {
            addNode(fromid);
        }
        int ans = dijGraph.get(fromid).getShortest(toid);
        if (ans != -1) {
            return ans;
        } else {
            calShortest(fromid);
            ans = dijGraph.get(fromid).getShortest(toid);
            return ans;
        }
    }
    
    private void calShortest(int beginid) {
        //map是包含beginid的一个连通集合
        HashMap<Integer, Integer> map = basicGraph.conSet(beginid);
        tempInit(map);
        HashMap<Integer, BasicNodeInfo> nextNodes = new HashMap<>(120);
        int price;
        //map中是未被纳入图的点
        map.remove(beginid);
        //起始点设置为0元
        graph.get(beginid).setTemp(0);
        int nextid = beginid;
        while (!map.isEmpty()) {
            mintempid = nextid;
            mintemp = -1;
            //对整个图进行更新
            nextNodes = graph.get(nextid).getNextNodes();
            //int onLine = graph.get(nextid).getLineTemp();
            for (Integer id : nextNodes.keySet()) {
                if (id != nextid && map.containsKey(id)) {
                    price = graph.get(nextid).getTemp();
                    price++;
                    int temp = min(price, graph.get(id).getTemp());
                    graph.get(id).setTemp(temp);
                    minid(temp, id);
                }
            }
            if (mintemp == -1) {
                for (Integer id : map.keySet()) {
                    minid(graph.get(id).getTemp(), id);
                }
            }
            
            map.remove(mintempid);
            setShortest(mintemp, beginid, mintempid);
            nextid = mintempid;
            /*
            System.out.print(mintempid);
            System.out.print(" is ");
            System.out.println(mintemp);*/
        }
    }
    
    private void tempInit(HashMap<Integer, Integer> map) {
        for (Integer id : map.keySet()) {
            graph.get(id).setTemp(-1);
            graph.get(id).setLineTemp(-1);
            graph.get(id).clearOnlines();
        }
    }
    
    //用于选出ab中最小的（-1认为是无穷大）
    private int min(int a, int b) {
        if (a == -1 && b >= 0) {
            return b;
        }
        if (b == -1 && a >= 0) {
            return a;
        }
        if (a < b) {
            return a;
        }
        return b;
    }
    
    private int max(int a, int b) {
        if (a >= b) {
            return a;
        } else {
            return b;
        }
    }
    
    //用于选出所有对图的更改中结果最小的（-1认为是无穷大）
    private void minid(int temp, int id) {
        if (temp == -1) {
            return;
        }
        if (mintemp == -1 && temp >= 0) {
            mintemp = temp;
            mintempid = id;
        }
        if (mintemp > temp) {
            mintemp = temp;
            mintempid = id;
        }
    }
    
    private void minminid(int temp, int id) {
        if (temp == -1) {
            return;
        }
        if (minmintemp == -1 && temp >= 0) {
            minmintemp = temp;
            minmintempid = id;
        }
        if (minmintemp > temp) {
            minmintemp = temp;
            minmintempid = id;
        }
    }
}
