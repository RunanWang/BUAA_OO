import com.oocourse.specs2.models.Path;

import java.util.ArrayList;
import java.util.HashMap;

public class GraphAlg {
    //节点，引用数
    //graph用于维护一个可达的节点路径图
    private ArrayList<HashMap<Integer, Integer>> pointSet = new ArrayList<>();
    //private HashMap<Integer, GraphNode> graph = new HashMap<>(260);
    //comgraph用于维护一个临近节点，用于计算
    private HashMap<Integer, GraphNode> comgraph = new HashMap<>(260);
    
    public GraphAlg() {
    
    }
    
    //addPath时间复杂度o(n)
    public void addPath(Path p) {
        int i = 0;
        GraphNode node;
        for (i = 0; i < p.size(); i++) {
            if (!comgraph.containsKey(p.getNode(i))) {
                node = new GraphNode(p.getNode(i));
                comgraph.put(p.getNode(i), node);
                if (i != 0) {
                    node.addNextNode(p.getNode(i - 1));
                }
                if (i != p.size() - 1) {
                    node.addNextNode(p.getNode(i + 1));
                }
            } else {
                node = comgraph.get(p.getNode(i));
                if (i != 0) {
                    node.addNextNode(p.getNode(i - 1));
                }
                if (i != p.size() - 1) {
                    node.addNextNode(p.getNode(i + 1));
                }
            }
        }
    }
    
    public void departSet(Path p) {
        int i = 0;
        int j = 0;
        int keyPoint = 0;
        HashMap<Integer, Integer> map = new HashMap<>(260);
        HashMap<Integer, Integer> map1;
        HashMap<Integer, Integer> map2;
        for (j = 0; j < pointSet.size(); j++) {
            map = pointSet.get(j);
            for (i = 0; i < p.size(); i++) {
                keyPoint = p.getNode(i);
                if (map.containsKey(keyPoint)) {
                    break;
                }
            }
            if (map.containsKey(keyPoint)) {
                break;
            }
        }
        if (map.containsKey(keyPoint)) {
            for (i = 0; i < p.size(); i++) {
                map.put(p.getNode(i), 1);
            }
            //看之前分开的点集是否有机会合并
            for (i = 0; i < pointSet.size(); i++) {
                map1 = pointSet.get(i);
                for (j = i + 1; j < pointSet.size(); j++) {
                    map2 = pointSet.get(j);
                    for (Integer point : map2.keySet()) {
                        if (map1.containsKey(point)) {
                            map1.putAll(map2);
                            pointSet.remove(map2);
                        }
                    }
                }
            }
        } else {
            map = new HashMap<>(260);
            for (i = 0; i < p.size(); i++) {
                map.put(p.getNode(i), 1);
            }
            pointSet.add(map);
        }
    }
    
    public int distanceCompute(Integer beginPoint, Integer endPoint) {
        ArrayList<GraphNode> list = new ArrayList<>();
        ArrayList<GraphNode> list2;
        int length = 1;
        for (Integer nextNodei : comgraph.get(beginPoint)
                .getNextNodes().keySet()) {
            //System.out.println(nextNodei);
            GraphNode nextNode = comgraph.get(nextNodei);
            list.add(nextNode);
            //System.out.println(nextNode);
            if (nextNodei.equals(endPoint)) {
                //System.out.println("has that");
                return comgraph.get(beginPoint).getNextNodes().get(endPoint);
            }
        }
        do {
            list2 = new ArrayList<>();
            length++;
            while (list.size() != 0) {
                GraphNode node = list.remove(0);
                for (Integer nextNodei : node.getNextNodes().keySet()) {
                    if (node.getNextNodes().get(nextNodei) == 1) {
                        GraphNode nextNode = comgraph.get(nextNodei);
                        if (nextNode.getNextNodes().containsKey(beginPoint)) {
                            if (nextNode.getNextNodes()
                                    .get(beginPoint) <= length) {
                                continue;
                            }
                        }
                        nextNode.addNextNode(beginPoint, length);
                        comgraph.get(beginPoint).addNextNode(nextNodei, length);
                        list2.add(nextNode);
                    }
                }
            }
            list = list2;
        } while (list2.size() != 0);
        //System.out.println(comgraph.get(beginPoint));
        return comgraph.get(beginPoint).getNextNodes().get(endPoint);
    }
    
    public void renewGraph(HashMap<Path, Integer> pathMap) {
        //graph.clear();
        comgraph.clear();
        pointSet.clear();
        for (Path path : pathMap.keySet()) {
            addPath(path);
            departSet(path);
        }
    }
    
    public boolean connectedPath(int a, int b) {
        HashMap<Integer, Integer> map = new HashMap<>(260);
        for (int i = 0; i < pointSet.size(); i++) {
            map = pointSet.get(i);
            if (map.containsKey(a)) {
                break;
            }
        }
        return map.containsKey(b);
    }
}
