import com.oocourse.specs3.models.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MyPath implements Path {
    private ArrayList<Integer> nodes = new ArrayList<>();
    private HashMap<Integer, Integer> nodesMap = new HashMap<>(81);
    private int nodesCount = 0;
    private int pathDisNode = 0;
    
    public MyPath(int[] nodeList) {
        for (nodesCount = 0; nodesCount < nodeList.length; nodesCount++) {
            nodes.add(nodeList[nodesCount]);
            nodesMap.put(nodeList[nodesCount], nodesCount);
        }
        getNodeCount();
    }
    
    //@ ensures \result == nodes.length;
    public /*@pure@*/int size() {
        return nodesCount;
    }
    
    /*@ requires index >= 0 && index < size();
      @ assignable \nothing;
      @ ensures \result == nodes[index];
      @*/
    public /*@pure@*/ int getNode(int index) {
        //这里可能有问题
        /*
        if (index < 0 || index > nodes.size()) {
            return -1;
        }*/
        return nodes.get(index);
    }
    
    //@ ensures \result == (\exists int i; 0 <= i
    //@ && i < nodes.length; nodes[i] == node);
    public /*@pure@*/ boolean containsNode(int node) {
        /*int i = 0;
        for (i = 0; i < nodes.length; i++) {
            if (nodes[i] == node) {
                return true;
            }
            return false;
        }*/
        return nodesMap.containsKey(node);
    }
    
    
    /*@ ensures \result == (\num_of int i, j; 0 <= i && i < j
                             && j < nodes.length;
                             nodes[i] != nodes[j]);
      @*/
    private  /*pure*/ void getNodeCount() {
        /*
        ArrayList<Integer> list = new ArrayList<>();
        int i = 0;
        for (i = 0; i < nodes.size(); i++) {
            if (!list.contains(nodes.get(i))) {
                list.add(nodes.get(i));
            }
        }
        pathDisNode = list.size();*/
        pathDisNode = nodesMap.size();
    }
    
    @Override
    public int getUnpleasantValue(int i) {
        return (i % 5 + 5) % 5;
    }
    
    public /*pure*/ int getDistinctNodeCount() {
        return pathDisNode;
    }
    
    /*@ also
      @ public normal_behavior
      @ requires obj != null && obj instanceof Path;
      @ assignable \nothing;
      @ ensures \result == (\forall int i; 0 <= i && i < nodes.length;
      @                      nodes[i] == ((Path) obj).nodes[i]);
      @ also
      @ public normal_behavior
      @ requires obj == null || !(obj instanceof Path);
      @ assignable \nothing;
      @ ensures \result == false;
      @*/
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj.getClass() != this.getClass()) {
            //System.out.println("not valid");
            return false;
        } else if (obj.hashCode() != this.hashCode()) {
            return false;
        }
        //System.out.println("valid");
        int i = 0;
        if (nodes.size() != ((Path) obj).size()) {
            return false;
        }
        for (i = 0; i < nodes.size(); i++) {
            if (nodes.get(i) != ((Path) obj).getNode(i)) {
                return false;
            }
        }
        return true;
    }
    
    //@ ensures \result == (nodes.length >= 2);
    public /*@pure@*/ boolean isValid() {
        return nodes.size() >= 2;
    }
    
    @Override
    public Iterator<Integer> iterator() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            arrayList.add(nodes.get(i));
        }
        return arrayList.iterator();
    }
    
    @Override
    public int compareTo(Path o) {
        /*
        if(this.size()<o.size()){
            return this.size()-o.size();
        }else if(this.size()>o.size()){
            return this.size()-o.size();
        }else{
            for (int i = 0; i < nodes.length; i++) {
                if (nodes[i] > o.getNode(i)) {
                    return 1;
                }else if(nodes[i] < o.getNode(i)){
                    return -1;
                }
            }
            return 0;
        }*/
        int i = 0;
        try {
            for (i = 0; i < nodes.size(); i++) {
                if (nodes.get(i) > o.getNode(i)) {
                    return 1;
                } else if (nodes.get(i) < o.getNode(i)) {
                    return -1;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return 1;
        }
        if (nodes.size() < o.size()) {
            return -1;
        }
        return 0;
    }
    
    @Override
    public int hashCode() {
        return nodes.hashCode();
    }
    
    public boolean hasEdge(int a, int b) {
        for (int i = 0; i < size() - 1; i++) {
            if (getNode(i) == a && getNode(i + 1) == b) {
                return true;
            }
        }
        return false;
    }
}
