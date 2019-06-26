import com.oocourse.specs2.models.Path;
import com.oocourse.specs2.models.PathContainer;
import com.oocourse.specs2.models.PathIdNotFoundException;
import com.oocourse.specs2.models.PathNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;

public class MyPathContainer implements PathContainer {
    //@ public instance model non_null Path[] paList;
    private ArrayList<Path> paList;
    //@ public instance model non_null int[] pidList;
    private ArrayList<Integer> pidList;
    //private ArrayList<Integer> pnList = new ArrayList<>();
    private HashMap<Integer, Integer> nodeMap = new HashMap<>(300);
    private HashMap<Path, Integer> pathMap = new HashMap<>(300);
    private HashMap<Integer, Integer> idMap = new HashMap<>(300);
    private int disNum = 0;
    private int listSize = 0;
    
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
        return listSize;
    }
    
    /*@ requires path != null;
      @ assignable \nothing;
      @ ensures \result == (\exists int i; 0 <= i && i < paList.length;
      @                     paList[i].equals(path));
      @*/
    public /*@pure@*/ boolean containsPath(Path path) {
        //int i;
        if (path == null) {
            return false;
        }
        if (paList.size() == 0) {
            //System.out.println("size=0");
            return false;
        }
        /*
        for (i = 0; i < paList.size(); i++) {
            //System.out.println("here");
            if (paList.get(i).equals(path)) {
                //System.out.println("contains");
                return true;
            }
        }
        return false;*/
        return pathMap.containsKey(path);
    }
    
    /*@ ensures \result == (\exists int i; 0 <= i && i < pidList.length;
      @                      pidList[i] == pathId);
      @*/
    public /*@pure@*/ boolean containsPathId(int pathId) {
        if (pidList.size() == 0) {
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
        int i = idMap.get(pathId);
        //不知道为啥有一句pidList.length == paList.length
        /*
        for (i = 0; i < pidList.size(); i++) {
            if (pidList.get(i) == pathId) {
                break;
            }
        }*/
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
        } else if (!containsPath(path)) {
            throw new PathNotFoundException(path);
        }
        int i = pathMap.get(path);/*
        for (i = 0; i < paList.size(); i++) {
            if (paList.get(i).equals(path)) {
                break;
            }
        }*/
        return pidList.get(i);
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
        listSize++;
        idMap.put(pathId, listSize);
        pathMap.put(path, listSize);
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
        /*
        for (i = 0; i < paList.size(); i++) {
            if (paList.get(i).equals(path)) {
                break;
            }
        }*/
        if (!containsPath(path)) {
            throw new PathNotFoundException(path);
        } else {
            int i = pathMap.get(path);
            paList.remove(i);
            int id = pidList.get(i);
            pidList.remove(i);
            pathMap.remove(path);
            idMap.remove(id);
            listSize--;
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
        int i = idMap.get(pathId);/*
        for (i = 0; i < pidList.size(); i++) {
            if (pidList.get(i) == pathId) {
                pidList.remove(i);
                paList.remove(i);
                listSize--;
            }
        }*/
        pidList.remove(i);
        Path path = paList.remove(i);
        pathMap.remove(path);
        idMap.remove(pathId);
        listSize--;
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
    } //在容器全局范围内查找不同的节点数
    
    private void renewDisNode() {
        //pnList.clear();
        nodeMap.clear();
        int i;
        int j;
        for (i = 0; i < paList.size(); i++) {
            for (j = 0; j < paList.get(i).size(); j++) {
                nodeMap.put(paList.get(i).getNode(j), pidList.get(i));
            }
        }
        disNum = nodeMap.size();
    }
    
    private void addDisNode(Path path) {
        for (int j = 0; j < path.size(); j++) {
            nodeMap.put(path.getNode(j), pathIdCounter - 1);
        }
        disNum = nodeMap.size();
    }
}