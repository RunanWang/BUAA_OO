import com.oocourse.specs3.models.RailwaySystem;
import com.oocourse.specs3.models.Path;
import com.oocourse.specs3.models.NodeIdNotFoundException;
import com.oocourse.specs3.models.NodeNotConnectedException;
import com.oocourse.specs3.models.PathNotFoundException;
import com.oocourse.specs3.models.PathIdNotFoundException;

import java.util.HashMap;

public class MyRailwaySystem implements RailwaySystem {
    private HashMap<Integer, Integer> nodeMap = new HashMap<>(300);
    private HashMap<Path, Integer> pathMap = new HashMap<>(300);
    private HashMap<Integer, Path> idMap = new HashMap<>(300);
    private HashMap<GraphEdge, Integer> edgeMap = new HashMap<>(4000);
    private BasicGraph graph = new BasicGraph();
    private DijGraph dijGraph = new DijGraph(graph, idMap);
    private int disNum = 0;
    private int listSize = 0;
    private int dirtyFlag = 0;
    private int graghDirty = 0;
    private int pathIdCounter;
    
    public MyRailwaySystem() {
        pathIdCounter = 1;
        //paList = new ArrayList<>();
        //pidList = new ArrayList<>();
    }
    
    private void counterId() {
        pathIdCounter++;
    }
    
    public int size() {
        return listSize;
    }
    
    public boolean containsPath(Path path) {
        if (path == null) {
            return false;
        }
        if (pathMap.size() == 0) {
            return false;
        }
        return pathMap.containsKey(path);
    }
    
    public boolean containsPathId(int pathId) {
        if (idMap.size() == 0) {
            return false;
        }
        /*
        for (int i = 0; i < pidList.size(); i++) {
            if (pidList.get(i) == pathId) {
                return true;
            }
        }
        return false;*/
        return idMap.containsKey(pathId);
    }
    
    public Path getPathById(int pathId)
            throws PathIdNotFoundException {
        if (!containsPathId(pathId)) {
            throw new PathIdNotFoundException(pathId);
        }
        return idMap.get(pathId);
        //不知道为啥有一句pidList.length == paList.length
        /*
        for (i = 0; i < pidList.size(); i++) {
            if (pidList.get(i) == pathId) {
                break;
            }
        }*/
        //return paList.get(i);
    }
    
    public int getPathId(Path path) throws PathNotFoundException {
        if (path == null) {
            throw new PathNotFoundException(path);
        } else if (!path.isValid()) {
            throw new PathNotFoundException(path);
        } else if (!containsPath(path)) {
            throw new PathNotFoundException(path);
        }
        return pathMap.get(path);/*
        for (i = 0; i < paList.size(); i++) {
            if (paList.get(i).equals(path)) {
                break;
            }
        }*/
        //return pidList.get(i);
    }
    
    public int addPath(Path path) {
        if (path == null || !path.isValid()) {
            return 0;
        }
        if (containsPath(path)) {
            try {
                return getPathId(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int pathId = pathIdCounter;
        counterId();
        //paList.add(path);
        //pidList.add(pathId);
        //addDisNode(path);
        listSize++;
        idMap.put(pathId, path);
        pathMap.put(path, pathId);
        dirtyFlag = 1;
        graghDirty = 1;
        return pathId;
    }
    
    public int removePath(Path path) throws PathNotFoundException {
        if (path == null) {
            throw new PathNotFoundException(path);
        } else if (!path.isValid()) {
            throw new PathNotFoundException(path);
        }
        if (!containsPath(path)) {
            throw new PathNotFoundException(path);
        } else {
            int id = pathMap.remove(path);
            idMap.remove(id);
            listSize--;
            dirtyFlag = 1;
            graghDirty = 1;
            return id;
        }
    }
    
    public void removePathById(int pathId) throws PathIdNotFoundException {
        if (!containsPathId(pathId)) {
            throw new PathIdNotFoundException(pathId);
        }
        Path path = idMap.get(pathId);
        pathMap.remove(path);
        idMap.remove(pathId);
        listSize--;
        dirtyFlag = 1;
        graghDirty = 1;
    }
    
    public int getDistinctNodeCount() {
        renewNodeMap();
        return disNum;
    } //在容器全局范围内查找不同的节点数
    
    private void renewNodeMap() {
        if (dirtyFlag == 1) {
            nodeMap.clear();
            edgeMap.clear();
            for (Path path : pathMap.keySet()) {
                int j;
                for (j = 0; j < path.size() - 1; j++) {
                    nodeMap.put(path.getNode(j), pathIdCounter - 1);
                    GraphEdge edge = new
                            GraphEdge(path.getNode(j), path.getNode(j + 1));
                    edgeMap.put(edge, j);
                }
                nodeMap.put(path.getNode(j), pathIdCounter - 1);
            }
            disNum = nodeMap.size();
        }
        dirtyFlag = 0;
    }
    
    private void renewGraph() {
        if (graghDirty == 1) {
            //System.out.println("renew");
            graph.renewGraph(pathMap);
            dijGraph.renewGraph(graph, idMap);
        }
        
        graghDirty = 0;
    }
    
    public boolean containsNode(int nodeId) {
        renewNodeMap();
        return nodeMap.containsKey(nodeId);
    }
    
    public boolean containsEdge(int fromNodeId, int toNodeId) {
        renewNodeMap();
        if (!containsNode(fromNodeId) || !containsNode(toNodeId)) {
            return false;
        }
        /*if (fromNodeId == toNodeId) {
            return true;
        }*/
        GraphEdge edge = new GraphEdge(fromNodeId, toNodeId);
        return edgeMap.containsKey(edge);
    }
    
    public boolean isConnected(int fromNodeId, int toNodeId)
            throws NodeIdNotFoundException {
        renewGraph();
        renewNodeMap();
        if (!nodeMap.containsKey(fromNodeId)) {
            throw new NodeIdNotFoundException(fromNodeId);
        }
        if (!nodeMap.containsKey(toNodeId)) {
            throw new NodeIdNotFoundException(toNodeId);
        }
        //System.out.println(fromNodeId);
        //System.out.println(toNodeId);
        return graph.connectedPath(fromNodeId, toNodeId);
    }
    
    public int getShortestPathLength(int fromNodeId, int toNodeId)
            throws NodeIdNotFoundException, NodeNotConnectedException {
        renewGraph();
        renewNodeMap();
        if (!containsNode(fromNodeId)) {
            throw new NodeIdNotFoundException(fromNodeId);
        }
        if (!containsNode(toNodeId)) {
            throw new NodeIdNotFoundException(toNodeId);
        }
        if (!isConnected(fromNodeId, toNodeId)) {
            throw new NodeNotConnectedException(fromNodeId, toNodeId);
        }
        if (fromNodeId == toNodeId) {
            return 0;
        }
        //System.out.println(isConnected(fromNodeId,toNodeId));
        return dijGraph.getShortest(fromNodeId, toNodeId);
    }
    
    private void addDisNode(Path path) {
        for (int j = 0; j < path.size(); j++) {
            nodeMap.put(path.getNode(j), pathIdCounter - 1);
        }
        disNum = nodeMap.size();
    }
    
    public int getLeastTicketPrice(int var1, int var2)
            throws NodeIdNotFoundException, NodeNotConnectedException {
        renewGraph();
        renewNodeMap();
        if (!containsNode(var1)) {
            throw new NodeIdNotFoundException(var1);
        }
        if (!containsNode(var2)) {
            throw new NodeIdNotFoundException(var2);
        }
        if (!isConnected(var1, var2)) {
            //System.out.println(var1);
            //System.out.println(var2);
            throw new NodeNotConnectedException(var1, var2);
        }
        //System.out.println("length");
        if (var1 == var2) {
            return 0;
        }
        return dijGraph.getLeastPrice(var1, var2);
    }
    
    public int getLeastTransferCount(int var1, int var2)
            throws NodeIdNotFoundException, NodeNotConnectedException {
        renewGraph();
        renewNodeMap();
        if (!containsNode(var1)) {
            throw new NodeIdNotFoundException(var1);
        }
        if (!containsNode(var2)) {
            throw new NodeIdNotFoundException(var2);
        }
        if (!isConnected(var1, var2)) {
            throw new NodeNotConnectedException(var1, var2);
        }
        if (var1 == var2) {
            return 0;
        }
        return dijGraph.getLeastChange(var1, var2);
    }
    
    public int getLeastUnpleasantValue(int var1, int var2)
            throws NodeIdNotFoundException, NodeNotConnectedException {
        renewGraph();
        renewNodeMap();
        if (!containsNode(var1)) {
            throw new NodeIdNotFoundException(var1);
        }
        if (!containsNode(var2)) {
            throw new NodeIdNotFoundException(var2);
        }
        if (!isConnected(var1, var2)) {
            throw new NodeNotConnectedException(var1, var2);
        }
        if (var1 == var2) {
            return 0;
        }
        return dijGraph.getMostSat(var1, var2);
    }
    
    public int getUnpleasantValue(Path var1, int var2, int var3) {
        return 0;
    }
    
    public int getConnectedBlockCount() {
        renewGraph();
        renewNodeMap();
        return graph.blockNum();
    }
}