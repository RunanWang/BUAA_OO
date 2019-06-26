package models.mclass;

import com.oocourse.uml2.models.elements.UmlInterface;
import com.oocourse.uml2.models.elements.UmlClassOrInterface;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;

public class MyUmlInterface {
    private UmlInterface umlInterface;
    private ArrayList<MyUmlInterface> fathers;
    private int associationNum = 0;
    private ArrayList<MyUmlInterface> sons;
    private ArrayList<MyUmlClass> realized;
    
    public MyUmlInterface(UmlInterface in) {
        umlInterface = in;
        fathers = new ArrayList<>();
        sons = new ArrayList<>();
        realized = new ArrayList<>();
    }
    
    public void newGeneration(MyUmlInterface father) {
        this.fathers.add(father);
    }
    
    public void newSon(MyUmlInterface son) {
        this.sons.add(son);
    }
    
    public void newReal(MyUmlClass c) {
        realized.add(c);
    }
    
    public boolean interEqual(String id) {
        return id.equals(umlInterface.getId());
    }
    
    public String getUmlInterfaceId() {
        return umlInterface.getId();
    }
    
    public String getUmlInterfaceName() {
        return umlInterface.getName();
    }
    
    public ArrayList<MyUmlInterface> getFather() {
        return fathers;
    }
    
    public void addAss() {
        associationNum++;
    }
    
    public UmlInterface getUmlInterface() {
        return umlInterface;
    }
    
    public HashSet<UmlClassOrInterface> check2() {
        HashSet<UmlClassOrInterface> ans = new HashSet<>();
        HashMap<String, Integer> names = new HashMap<>();
        MyUmlInterface inter = this;
        names.put(this.getUmlInterfaceName(), 1);
        ArrayList<MyUmlInterface> nextList = new ArrayList<>();
        nextList.addAll(this.fathers);
        while (!nextList.isEmpty()) {
            inter = nextList.remove(0);
            if (inter.equals(this)) {
                ans.add(this.getUmlInterface());
                return ans;
            }
            names.put(inter.getUmlInterfaceName(), 1);
            nextList.addAll(inter.fathers);
        }
        return ans;
    }
    
    public HashSet<UmlClassOrInterface> check32() {
        HashSet<UmlClassOrInterface> ans = new HashSet<>();
        HashMap<MyUmlInterface, Integer> interRealTimes = new HashMap<>();
        ArrayList<MyUmlInterface> inters = this.getFather();
        while (!inters.isEmpty()) {
            MyUmlInterface inter = inters.remove(0);
            if (interRealTimes.containsKey(inter)) {
                interRealTimes.put(inter, interRealTimes.get(inter) + 1);
            } else {
                interRealTimes.put(inter, 1);
            }
            inters.addAll(inter.getFather());
        }
        for (MyUmlInterface in : interRealTimes.keySet()) {
            if (interRealTimes.get(in) != 1) {
                ans.add(this.getUmlInterface());
                return ans;
            }
        }
        return ans;
    }
    
    public HashSet<UmlClassOrInterface> check3() {
        HashSet<UmlClassOrInterface> ans = new HashSet<>();
        HashMap<String, Integer> names = new HashMap<>();
        MyUmlInterface inter = this;
        names.put(this.getUmlInterfaceName(), 1);
        ArrayList<MyUmlInterface> nextList = new ArrayList<>();
        nextList.addAll(this.fathers);
        nextList.addAll(this.sons);
        //ArrayList<MyUmlInterface> alreadyList = new ArrayList<>();
        //alreadyList.add(inter);
        while (!nextList.isEmpty()) {
            inter = nextList.remove(0);
            if (inter.equals(this)) {
                continue;
            }
            if (names.containsKey(inter.getUmlInterfaceName())) {
                ans.add(this.getUmlInterface());
                //ans.add(inter.getUmlInterface());
                return ans;
            }
            names.put(inter.getUmlInterfaceName(), 1);
            nextList.addAll(inter.fathers);
            //alreadyList.add(inter);
        }
        return ans;
    }
    
    @Override
    public String toString() {
        return umlInterface.toString();
    }
}
