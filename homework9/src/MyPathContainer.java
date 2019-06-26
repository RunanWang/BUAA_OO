import com.oocourse.specs1.models.Path;
import com.oocourse.specs1.models.PathContainer;
import com.oocourse.specs1.models.PathIdNotFoundException;
import com.oocourse.specs1.models.PathNotFoundException;

import java.util.ArrayList;

public class MyPathContainer implements PathContainer {
    //@ public instance model non_null Path[] paList;
    private ArrayList<Path> paList;
    //@ public instance model non_null int[] pidList;
    private ArrayList<Integer> pidList;
    private ArrayList<Integer> pnList = new ArrayList<>();
    private int disNum = 0;
    
    private int pathIdCounter = 0;
    
    public MyPathContainer() {
        pathIdCounter = 1;
        paList = new ArrayList<>();
        pidList = new ArrayList<>();
    }
    
    private void counterId() {
        pathIdCounter++;
    }
    
    //@ ensures \result == paList.length;
    public /*@pure@*/int size() {
        return paList.size();
    }
    
    /*@ requires path != null;
      @ assignable \nothing;
      @ ensures \result == (\exists int i; 0 <= i && i < paList.length;
      @                     paList[i].equals(path));
      @*/
    public /*@pure@*/ boolean containsPath(Path path) {
        int i;
        if (path == null) {
            return false;
        }
        if (paList.size() == 0) {
            //System.out.println("size=0");
            return false;
        }
        for (i = 0; i < paList.size(); i++) {
            //System.out.println("here");
            if (paList.get(i).equals(path)) {
                //System.out.println("contains");
                return true;
            }
        }
        return false;
    }
    
    /*@ ensures \result == (\exists int i; 0 <= i && i < pidList.length;
      @                      pidList[i] == pathId);
      @*/
    public /*@pure@*/ boolean containsPathId(int pathId) {
        if (pidList.size() == 0) {
            return false;
        }
        for (int i = 0; i < pidList.size(); i++) {
            if (pidList.get(i) == pathId) {
                return true;
            }
        }
        return false;
    }
    
    /*@ public normal_behavior
      @ requires containsPathId(pathId);
      @ assignable \nothing;
      @ ensures (pidList.length == paList.length)
      &&(\exists int i; 0 <= i && i < paList.length; pidList[i] == pathId
      && \result == paList[i]);
      @ also
      @ public exceptional_behavior
      @ requires !containsPathId(pathId);
      @ assignable \nothing;
      @ signals_only PathIdNotFoundException;
      @*/
    public /*@pure@*/ Path getPathById(int pathId)
            throws PathIdNotFoundException {
        if (!containsPathId(pathId)) {
            throw new PathIdNotFoundException(pathId);
        }
        int i;
        for (i = 0; i < pidList.size(); i++) {
            if (pidList.get(i) == pathId) {
                break;
            }
        }
        return paList.get(i);
    }
    
    /*@ public normal_behavior
      @ requires path != null && path.isValid();
      @ assignable \nothing;
      @ ensures (pidList.length == paList.length)
      &&(\exists int i; 0 <= i && i < paList.length; paList[i].equals(path)
      && pidList[i] == \result);
      @ also
      @ public exceptional_behavior
      @ signals (PathNotFoundException e) path == null;
      @ signals (PathNotFoundException e) !path.isValid();
      @ signals (PathNotFoundException e)
      (\forall int i; 0 <= i && i < paList.length; !paList[i].equals(path));
      @*/
    public /*@pure@*/ int getPathId(Path path) throws PathNotFoundException {
        if (path == null) {
            throw new PathNotFoundException(path);
        } else if (!path.isValid()) {
            throw new PathNotFoundException(path);
        }
        int i;
        for (i = 0; i < paList.size(); i++) {
            if (paList.get(i).equals(path)) {
                break;
            }
        }
        if (i == paList.size()) {
            throw new PathNotFoundException(path);
        } else {
            return pidList.get(i);
        }
    }
    
    /*@ normal_behavior
      @ requires path != null && path.isValid();
      @ assignable paList, pidList;
      @ ensures (pidList.length == paList.length);
      @ ensures (\exists int i; 0 <= i && i < paList.length; paList[i] == path
      &&
      @           \result == pidList[i]);
      @ ensures !\old(containsPath(path)) ==>
      @          paList.length == (\old(paList.length) + 1) &&
      @          pidList.length == (\old(pidList.length) + 1);
      @ ensures (\forall int i; 0 <= i && i < \old(paList.length);
      @          containsPath(\old(paList[i]))
                 && containsPathId(\old(pidList[i])));
      @ also
      @ normal_behavior
      @ requires path == null || path.isValid() == false;
      @ assignable \nothing;
      @ ensures \result == 0;
      @*/
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
        paList.add(path);
        pidList.add(pathId);
        addDisNode(path);
        return pathId;
    }
    
    /*@ public normal_behavior
      @ requires path != null && path.isValid();
      @ assignable paList, pidList;
      @ ensures containsPath(path) == false;
      @ ensures (pidList.length == paList.length);
      @ ensures \old(containsPath(path)) ==> (\exists int i; 0 <= i &&
                 i < \old(paList.length); \old(paList[i].equals(path)) &&
                 \result == \old(pidList[i]));
      @ public exceptional_behavior
      @ assignable \nothing;
      @ signals (PathNotFoundException e) path == null;
      @ signals (PathNotFoundException e) path.isValid()==false;
      @ signals (PathNotFoundException e) !containsPath(path);
      @*/
    public int removePath(Path path) throws PathNotFoundException {
        if (path == null) {
            throw new PathNotFoundException(path);
        } else if (!path.isValid()) {
            throw new PathNotFoundException(path);
        }
        int i;
        for (i = 0; i < paList.size(); i++) {
            if (paList.get(i).equals(path)) {
                break;
            }
        }
        if (i == paList.size()) {
            throw new PathNotFoundException(path);
        } else {
            paList.remove(i);
            int id = pidList.get(i);
            pidList.remove(i);
            renewDisNode();
            return id;
        }
    }
    
    /*@ public normal_behavior
      @ assignable paList, pidList;
      @ ensures \result == \old(containsPathId(pathId));
      @ ensures paList.length == pidList.length;
      @ ensures (\forall int i; 0 <= i
                && i < pidList.length; pidList[i] != pathId);
      @ ensures (\forall int i; 0 <= i
                && i < paList.length;
                !paList[i].equals(\old(getPathById(pathId))));
      @ also
      @ public exceptional_behavior
      @ assignable \nothing;
      @ signals (PathIdNotFoundException e) !containsPathId(pathId);
      @*/
    public void removePathById(int pathId) throws PathIdNotFoundException {
        if (!containsPathId(pathId)) {
            throw new PathIdNotFoundException(pathId);
        }
        int i = 0;
        for (i = 0; i < pidList.size(); i++) {
            if (pidList.get(i) == pathId) {
                pidList.remove(i);
                paList.remove(i);
            }
        }
        renewDisNode();
    }
    
    /*@ ensures \result == (\num_of int[] nlist;
           (\forall int i,j; 0 <= i && i < paList.length && 0 <= j
           && j < paList[i].size();
           (\exists int k; 0 <= k
           && k < nlist.length; nlist[k] == paList[i].getNode(j)));
           (\forall int m, n; 0 <= m && m < n
           && n < nlist.length; nlist[m] != nlist[n]));
      @*/
    public /*@pure@*/int getDistinctNodeCount() {
        return disNum;
    }
    
    private void renewDisNode() {
        pnList.clear();
        int i;
        int j;
        for (i = 0; i < paList.size(); i++) {
            for (j = 0; j < paList.get(i).size(); j++) {
                if (!pnList.contains(paList.get(i).getNode(j))) {
                    pnList.add(paList.get(i).getNode(j));
                }
            }
        }
        disNum = pnList.size();
    }
    
    private void addDisNode(Path path) {
        for (int j = 0; j < path.size(); j++) {
            if (!pnList.contains(path.getNode(j))) {
                pnList.add(path.getNode(j));
            }
        }
        disNum = pnList.size();
    }
}