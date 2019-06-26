import com.oocourse.specs1.models.Path;

import java.util.ArrayList;
import java.util.Iterator;

public class MyPath implements Path {
    // Iterable<Integer>和Comparable<Path>接口的规格请参阅JDK
    //@ public instance model non_null int[] nodes;
    private int[] nodes;
    private int pathDisNode = 0;
    
    public MyPath(int[] nodeList) {
        nodes = nodeList;
        getNodeCount();
    }
    
    //@ ensures \result == nodes.length;
    public /*@pure@*/int size() {
        return nodes.length;
    }
    
    /*@ requires index >= 0 && index < size();
      @ assignable \nothing;
      @ ensures \result == nodes[index];
      @*/
    public /*@pure@*/ int getNode(int index) {
        if (index < 0 || index > nodes.length) {
            return -1;
        }
        return nodes[index];
    }
    
    //@ ensures \result == (\exists int i; 0 <= i && i < nodes.length; nodes[i] == node);
    public /*@pure@*/ boolean containsNode(int node) {
        int i = 0;
        for (i = 0; i < nodes.length; i++) {
            if (nodes[i] == node) {
                return true;
            }
        }
        return false;
    }
    
    /*@ ensures \result == (\num_of int i, j; 0 <= i && i < j
                             && j < nodes.length;
                             nodes[i] != nodes[j]);
      @*/
    private  /*pure*/ void getNodeCount() {
        ArrayList<Integer> list = new ArrayList<>();
        int i = 0;
        for (i = 0; i < nodes.length; i++) {
            if (!list.contains(nodes[i])) {
                list.add(nodes[i]);
            }
        }
        pathDisNode = list.size();
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
        }
        //System.out.println("valid");
        int i = 0;
        if (nodes.length != ((Path) obj).size()) {
            return false;
        }
        for (i = 0; i < nodes.length; i++) {
            if (nodes[i] != ((Path) obj).getNode(i)) {
                return false;
            }
        }
        return true;
    }
    
    //@ ensures \result == (nodes.length >= 2);
    public /*@pure@*/ boolean isValid() {
        return nodes.length >= 2;
    }
    
    @Override
    public Iterator<Integer> iterator() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < nodes.length; i++) {
            arrayList.add(nodes[i]);
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
            for (i = 0; i < nodes.length; i++) {
                if (nodes[i] > o.getNode(i)) {
                    return 1;
                } else if (nodes[i] < o.getNode(i)) {
                    return -1;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return 1;
        }
        if (nodes.length < o.size()) {
            return -1;
        }
        return 0;
    }
}
