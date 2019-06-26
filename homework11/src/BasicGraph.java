import com.oocourse.specs3.models.Path;

import java.util.ArrayList;
import java.util.HashMap;

public class BasicGraph {
    //这个图里包含两个信息：
    //本点，本点链接距离为1的点以及到达这些点的路线
    private HashMap<Integer, BasicNode> basicGraph;
    //下方维护一个可达点图
    private ArrayList<HashMap<Integer, Integer>> pointSet = new ArrayList<>();
    
    public BasicGraph() {
        basicGraph = new HashMap<>(120);
    }
    
    public void addPath(Path p, int line) {
        int i = 0;
        for (i = 0; i < p.size(); i++) {
            //如果图中包含这个点
            int id = p.getNode(i);
            if (!basicGraph.containsKey(id)) {
                BasicNode node = new BasicNode(id);
                basicGraph.put(id, node);
            }
            //图中不包含这个点
            
            if (i != 0) {
                BasicNode node = basicGraph.get(id);
                node.addNestNode(p.getNode(i - 1), line);
            }
            if (i != p.size() - 1) {
                BasicNode node = basicGraph.get(id);
                node.addNestNode(p.getNode(i + 1), line);
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
    
    public int blockNum() {
        return pointSet.size();
    }
    
    public boolean connectedPath(int a, int b) {
        HashMap<Integer, Integer> map = new HashMap<>(260);
        for (int i = 0; i < pointSet.size(); i++) {
            //System.out.println("inLoop");
            map = pointSet.get(i);
            if (map.containsKey(a)) {
                //System.out.println(i);
                break;
            }
        }
        //System.out.println(map.containsKey(b));
        return map.containsKey(b);
    }
    
    public void renewGraph(HashMap<Path, Integer> pathMap) {
        //graph.clear();
        basicGraph.clear();
        pointSet.clear();
        for (Path path : pathMap.keySet()) {
            addPath(path, pathMap.get(path));
            departSet(path);
        }
    }
    
    public HashMap<Integer, Integer> conSet(int a) {
        HashMap<Integer, Integer> map = new HashMap<>(260);
        for (int i = 0; i < pointSet.size(); i++) {
            map = pointSet.get(i);
            if (map.containsKey(a)) {
                break;
            }
        }
        return (HashMap<Integer, Integer>) map.clone();
    }
    
    public HashMap<Integer, BasicNode> getBasicGraph() {
        return basicGraph;
    }
}
