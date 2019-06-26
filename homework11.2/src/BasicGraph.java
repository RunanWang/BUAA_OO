import com.oocourse.specs3.models.Path;

import java.util.ArrayList;
import java.util.HashMap;

public class BasicGraph {
    private HashMap<Integer, Integer> idToNum;
    //下方维护一个可达点图
    private ArrayList<HashMap<Integer, Integer>> pointSet = new ArrayList<>();
    //节点与节点的序号一一对应
    private ArrayList<Integer> stationList = new ArrayList<>(121);
    private int[][] graphPrice = new int[121][121];
    private int[][] graphTrans = new int[121][121];
    private int[][] graphSat = new int[121][121];
    private int[][] graphLen = new int[121][121];
    private int stationCount = 0;
    
    public BasicGraph() {
        idToNum = new HashMap<>(121);
    }
    
    public int size() {
        return stationCount;
    }
    
    public int idToNum(int id) {
        return idToNum.get(id);
    }
    
    public void addPath(Path p) {
        int i;
        int j;
        int[][] graph = new int[121][121];
        for (i = 0; i < 121; i++) {
            for (j = 0; j < 121; j++) {
                graph[i][j] = -1;
            }
        }
        ArrayList list = new ArrayList(81);
        //先把路径中所有的点加到stationlist，并把相邻两站设置为距离为3
        for (i = 0; i < p.size(); i++) {
            //如果图中不包含这个点，将id加入stationlist中
            int id = p.getNode(i);
            if (!idToNum.containsKey(id)) {
                stationList.add(id);
                idToNum.put(id, stationCount);
                stationCount++;
            }
            //获取id对应的list中序号
            list.add((int) idToNum.get(id));
            //将这点和下一点之间的距离设置为3
            if (i != 0) {
                int id2 = p.getNode(i - 1);
                //graphPrice[idToNum.get(id)][idToNum.get(id2)] = 3;
                //graphPrice[idToNum.get(id2)][idToNum.get(id)] = 3;
                graph[idToNum.get(id)][idToNum.get(id2)] = 3;
                graph[idToNum.get(id2)][idToNum.get(id)] = 3;
            }
            //graph[idToNum.get(id)][idToNum.get(id)] = 0;
        }
        
        //所有的点轮流做起始点
        while (!list.isEmpty()) {
            int beginNum = (int) list.get(0);
            list.remove(0);
            //找出剩余点list2中距离最小的点
            ArrayList list2 = (ArrayList) list.clone();
            while (!list2.isEmpty()) {
                int minDis = -1;
                int minNum = -1;
                //找出距离beginNum最小的点minNum
                for (i = 0; i < list2.size(); i++) {
                    int temp = min(minDis, graph[beginNum][(int) list2.get(i)]);
                    if (temp != minDis) {
                        minNum = (int) list2.get(i);
                    }
                }
                //加入到已求解的list中
                //System.out.println(minNum);
                //System.out.println(list2);
                list2.remove(list2.indexOf(minNum));
                
                //更新minNum为起始点的所有剩余点的权值
                for (i = 0; i < list2.size(); i++) {
                    int tempNum = (int) list2.get(i);
                    if (graph[tempNum][minNum] == 3) {
                        int temp = min(graph[beginNum][tempNum],
                                graph[beginNum][minNum] + 1);
                        graph[beginNum][tempNum] = temp;
                        graph[tempNum][beginNum] = temp;
                    }
                }
                
            }
        }
        
        for (i = 0; i < 121; i++) {
            for (j = 0; j < 121; j++) {
                graphPrice[i][j] = min(graphPrice[i][j], graph[i][j]);
            }
        }
    }
    
    public void addPathLen(Path p) {
        int i;
        int j;
        int[][] graph = new int[121][121];
        for (i = 0; i < 121; i++) {
            for (j = 0; j < 121; j++) {
                graph[i][j] = -1;
            }
        }
        ArrayList list = new ArrayList(81);
        //先把路径中所有的点加到stationlist，并把相邻两站设置为距离为3
        for (i = 0; i < p.size(); i++) {
            //如果图中不包含这个点，将id加入stationlist中
            int id = p.getNode(i);
            if (!idToNum.containsKey(id)) {
                stationList.add(id);
                idToNum.put(id, stationCount);
                stationCount++;
            }
            //获取id对应的list中序号
            list.add((int) idToNum.get(id));
            //将这点和下一点之间的距离设置为1
            if (i != 0) {
                int id2 = p.getNode(i - 1);
                //graphPrice[idToNum.get(id)][idToNum.get(id2)] = 3;
                //graphPrice[idToNum.get(id2)][idToNum.get(id)] = 3;
                graph[idToNum.get(id)][idToNum.get(id2)] = 1;
                graph[idToNum.get(id2)][idToNum.get(id)] = 1;
            }
            //graph[idToNum.get(id)][idToNum.get(id)] = 0;
        }
        
        //所有的点轮流做起始点
        while (!list.isEmpty()) {
            int beginNum = (int) list.get(0);
            list.remove(0);
            //找出剩余点list2中距离最小的点
            ArrayList list2 = (ArrayList) list.clone();
            while (!list2.isEmpty()) {
                int minDis = -1;
                int minNum = -1;
                //找出距离beginNum最小的点minNum
                for (i = 0; i < list2.size(); i++) {
                    int temp = min(minDis, graph[beginNum][(int) list2.get(i)]);
                    if (temp != minDis) {
                        minNum = (int) list2.get(i);
                    }
                }
                //加入到已求解的list中
                //System.out.println(minNum);
                //System.out.println(list2);
                list2.remove(list2.indexOf(minNum));
                
                //更新minNum为起始点的所有剩余点的权值
                for (i = 0; i < list2.size(); i++) {
                    int tempNum = (int) list2.get(i);
                    if (graph[tempNum][minNum] == 1) {
                        int temp = min(graph[beginNum][tempNum],
                                graph[beginNum][minNum] + 1);
                        graph[beginNum][tempNum] = temp;
                        graph[tempNum][beginNum] = temp;
                    }
                }
                
            }
        }
        
        for (i = 0; i < 121; i++) {
            for (j = 0; j < 121; j++) {
                graphLen[i][j] = min(graphLen[i][j], graph[i][j]);
            }
        }
    }
    
    public void addPathTrans(Path p) {
        int i;
        int j;
        int[][] graph = new int[121][121];
        for (i = 0; i < 121; i++) {
            for (j = 0; j < 121; j++) {
                graph[i][j] = -1;
            }
        }
        ArrayList list = new ArrayList(81);
        //先把路径中所有的点加到stationlist，并把相邻两站设置为距离为3
        for (i = 0; i < p.size(); i++) {
            //如果图中不包含这个点，将id加入stationlist中
            int id = p.getNode(i);
            if (!idToNum.containsKey(id)) {
                stationList.add(id);
                idToNum.put(id, stationCount);
                stationCount++;
            }
            //获取id对应的list中序号
            list.add((int) idToNum.get(id));
            //将这点和下一点之间的距离设置为3
            if (i != 0) {
                int id2 = p.getNode(i - 1);
                //graphPrice[idToNum.get(id)][idToNum.get(id2)] = 3;
                //graphPrice[idToNum.get(id2)][idToNum.get(id)] = 3;
                graph[idToNum.get(id)][idToNum.get(id2)] = 1;
                graph[idToNum.get(id2)][idToNum.get(id)] = 1;
            }
            //graph[idToNum.get(id)][idToNum.get(id)] = 0;
        }
        
        //所有的点轮流做起始点
        while (!list.isEmpty()) {
            int beginNum = (int) list.get(0);
            list.remove(0);
            //找出剩余点list2中距离最小的点
            ArrayList list2 = (ArrayList) list.clone();
            while (!list2.isEmpty()) {
                int minDis = -1;
                int minNum = -1;
                //找出距离beginNum最小的点minNum
                for (i = 0; i < list2.size(); i++) {
                    int temp = min(minDis, graph[beginNum][(int) list2.get(i)]);
                    if (temp != minDis) {
                        minNum = (int) list2.get(i);
                    }
                }
                //加入到已求解的list中
                //System.out.println(minNum);
                //System.out.println(list2);
                list2.remove(list2.indexOf(minNum));
                
                //更新minNum为起始点的所有剩余点的权值
                for (i = 0; i < list2.size(); i++) {
                    int tempNum = (int) list2.get(i);
                    if (graph[tempNum][minNum] == 1) {
                        int temp = min(graph[beginNum][tempNum],
                                1);
                        graph[beginNum][tempNum] = temp;
                        graph[tempNum][beginNum] = temp;
                    }
                }
                
            }
        }
        
        for (i = 0; i < 121; i++) {
            for (j = 0; j < 121; j++) {
                graphTrans[i][j] = min(graphTrans[i][j], graph[i][j]);
            }
        }
    }
    
    public void addPathSat(MyPath p) {
        int i;
        int j;
        int[][] graph = new int[121][121];
        int[][] graph2 = new int[121][121];
        for (i = 0; i < 121; i++) {
            for (j = 0; j < 121; j++) {
                graph[i][j] = -1;
                graph2[i][j] = -1;
            }
        }
        ArrayList list = new ArrayList(81);
        //先把路径中所有的点加到stationlist，并把相邻两站设置为距离为3
        for (i = 0; i < p.size(); i++) {
            //如果图中不包含这个点，将id加入stationlist中
            int id = p.getNode(i);
            //获取id对应的list中序号
            list.add((int) idToNum.get(id));
            //将这点和下一点之间的距离设置为3
            if (i != 0) {
                int id2 = p.getNode(i - 1);
                //graphPrice[idToNum.get(id)][idToNum.get(id2)] = 3;
                //graphPrice[idToNum.get(id2)][idToNum.get(id)] = 3;
                int sat = max((id % 5 + 5) % 5, (id2 % 5 + 5) % 5);
                sat = pow4(sat) + 32;
                graph[idToNum.get(id)][idToNum.get(id2)] = sat;
                graph[idToNum.get(id2)][idToNum.get(id)] = sat;
                graph2[idToNum.get(id)][idToNum.get(id2)] = 1;
                graph2[idToNum.get(id2)][idToNum.get(id)] = 1;
            }
        }
        //所有的点轮流做起始点
        while (!list.isEmpty()) {
            int beginNum = (int) list.get(0);
            list.remove(0);
            //找出剩余点list2中距离最小的点
            ArrayList list2 = (ArrayList) list.clone();
            while (!list2.isEmpty()) {
                int minDis = -1;
                int minNum = -1;
                //找出距离beginNum最小的点minNum
                for (i = 0; i < list2.size(); i++) {
                    int temp = min(minDis, graph[beginNum][(int) list2.get(i)]);
                    if (temp != minDis) {
                        minNum = (int) list2.get(i);
                    }
                }
                //加入到已求解的list中
                list2.remove(list2.indexOf(minNum));
                //更新minNum为起始点的所有剩余点的权值
                for (i = 0; i < list2.size(); i++) {
                    int tempNum = (int) list2.get(i);
                    if (graph2[tempNum][minNum] == 1) {
                        int temp = min(graph[beginNum][tempNum], graph
                                [beginNum][minNum] + comSat(minNum, tempNum));
                        graph[beginNum][tempNum] = temp;
                        graph[tempNum][beginNum] = temp;
                    }
                }
                
            }
        }
        for (i = 0; i < 121; i++) {
            for (j = 0; j < 121; j++) {
                graphSat[i][j] = min(graphSat[i][j], graph[i][j]);
            }
        }
    }
    
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
        idToNum.clear();
        pointSet.clear();
        stationList.clear();
        stationCount = 0;
        for (int i = 0; i < 121; i++) {
            for (int j = 0; j < 121; j++) {
                graphPrice[i][j] = -1;
                graphTrans[i][j] = -1;
                graphSat[i][j] = -1;
                graphLen[i][j] = -1;
            }
        }
        for (Path path : pathMap.keySet()) {
            addPath(path);
            addPathTrans(path);
            addPathSat((MyPath) path);
            addPathLen(path);
            departSet(path);
        }
    }
    
    public int getGraphPrice(int a, int b) {
        return graphPrice[a][b];
    }
    
    public int getGraphTrans(int a, int b) {
        return graphTrans[a][b];
    }
    
    public int getGraphSat(int a, int b) {
        return graphSat[a][b];
    }
    
    public int getGraphLen(int a, int b) {
        return graphLen[a][b];
    }
    
    public int[][] getGraphTransList() {
        return graphTrans;
    }
    
    public int[][] getGraphLenList() {
        return graphLen;
    }
    
    public int[][] getGraphPriceList() {
        return graphPrice;
    }
    
    public int[][] getGraphSatList() {
        return graphSat;
    }
    
    private int max(int a, int b) {
        if (a >= b) {
            return a;
        } else {
            return b;
        }
    }
    
    private int pow4(int num) {
        switch (num) {
            case 0:
                return 1;
            case 1:
                return 4;
            case 2:
                return 16;
            case 3:
                return 64;
            case 4:
                return 256;
            default:
                return 0;
        }
    }
    
    private int comSat(int a, int b) {
        int id1 = stationList.get(a);
        int id2 = stationList.get(b);
        int sat = max((id1 % 5 + 5) % 5, (id2 % 5 + 5) % 5);
        sat = pow4(sat);
        return sat;
    }
}
