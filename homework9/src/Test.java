import java.util.ArrayList;

public class Test {
    //@ public instance model non_null int[] nodes = {1,2,3};
    private int[] node = {1,2,3};
    private int pathDisNode = 0;
    
    //@ ensures \result == nodes.length;
    public /*@pure@*/int size() {
        return node.length;
    }
    
    /*@ requires index >= 0 && index < size();
      @ assignable \nothing;
      @ ensures \result == nodes[index];
      @*/
    public /*@pure@*/ int getNode(int index) {
        if (index < 0 || index > node.length) {
            return -1;
        }
        return node[index];
    }
    
    //@ ensures \result == (\exists int i; 0 <= i && i < nodes.length; nodes[i] == node);
    public /*@pure@*/ boolean containsNode(int node) {
        int i = 0;
        for (i = 0; i < this.node.length; i++) {
            if (this.node[i] == node) {
                return true;
            }
        }
        return false;
    }
    
    /*@ ensures \result == (\num_of int i, j; 0 <= i && i < j&& j < nodes.length;nodes[i] != nodes[j]);
      @*/
    private  /*pure*/ int getNodeCount() {
        ArrayList<Integer> list = new ArrayList();
        int i = 0;
        for (i = 0; i < node.length; i++) {
            if (!list.contains(node[i])) {
                list.add(node[i]);
            }
        }
        pathDisNode = list.size();
        return pathDisNode;
    }
    
    public /*pure*/ int getDistinctNodeCount() {
        return pathDisNode;
    }
    
    /*@ also
      @ public normal_behavior
      @ requires obj != null && obj instanceof Test;
      @ assignable \nothing;
      @ ensures \result == (\forall int i; 0 <= i && i < nodes.length;
      @                      nodes[i] == ((Test) obj).nodes[i]);
      @ also
      @ public normal_behavior
      @ requires obj == null || !(obj instanceof Test);
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
        if (node.length != ((Test) obj).size()) {
            return false;
        }
        for (i = 0; i < node.length; i++) {
            if (node[i] != ((Test) obj).getNode(i)) {
                return false;
            }
        }
        return true;
    }
    
    //@ ensures \result == (nodes.length >= 2);
    public /*@pure@*/ boolean isValid() {
        return node.length >= 2;
    }
    
}
