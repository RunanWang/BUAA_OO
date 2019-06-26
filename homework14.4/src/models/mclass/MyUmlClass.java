package models.mclass;

import com.oocourse.uml2.interact.common.AttributeClassInformation;
import com.oocourse.uml2.models.common.Visibility;
import com.oocourse.uml2.models.elements.UmlClass;
import com.oocourse.uml2.models.elements.UmlAttribute;
import com.oocourse.uml2.models.elements.UmlClassOrInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MyUmlClass {
    //name id vis
    private UmlClass umlClass;
    private ArrayList<MyUmlOperation> operations;
    private ArrayList<UmlAttribute> attributes;
    //private ArrayList<models.mclass.MyUmlAssociation> associations;
    //private ArrayList<UmlInterface> interfaces;
    private ArrayList<MyUmlInterface> realization;
    private MyUmlClass father;
    private ArrayList<MyUmlClass> sons;
    private ArrayList<MyUmlClass> asses;
    private ArrayList<String> endNames;
    
    //in return
    private int opNumir = 0;
    //in
    private int opNumi = 0;
    //return
    private int opNumr = 0;
    //none
    private int opNum = 0;
    private int opTotal = 0;
    
    private int associationNum = 0;
    
    public MyUmlClass(UmlClass u) {
        umlClass = u;
        operations = new ArrayList<>();
        //associations = new ArrayList<>();
        attributes = new ArrayList<>();
        //interfaces = new ArrayList<>();
        father = null;
        asses = new ArrayList<>();
        realization = new ArrayList<>();
        endNames = new ArrayList<>();
        sons = new ArrayList<>();
    }
    
    public void newOperation(MyUmlOperation myOp) {
        operations.add(myOp);
    }
    
    public void newAttribute(UmlAttribute att) {
        attributes.add(att);
    }
    
    public void newGeneration(MyUmlClass father) {
        this.father = father;
    }
    
    public void newSon(MyUmlClass son) {
        sons.add(son);
    }
    
    public void newAssociation(MyUmlClass ass) {
        asses.add(ass);
    }
    
    public void newInterface(MyUmlInterface inter) {
        realization.add(inter);
    }
    
    public String getUmlClassId() {
        return umlClass.getId();
    }
    
    public String getUmlClassName() {
        return umlClass.getName();
    }
    
    public UmlClass getUmlClass() {
        return umlClass;
    }
    
    public boolean classEqual(String id) {
        return id.equals(umlClass.getId());
    }
    
    public void calOp() {
        for (MyUmlOperation op : operations) {
            if (op.isReturn() && op.isIn()) {
                opNumir++;
            } else if (op.isReturn() && !op.isIn()) {
                opNumr++;
            } else if (!op.isReturn() && op.isIn()) {
                opNumi++;
            } else {
                opNum++;
            }
            opTotal++;
        }
    }
    
    public int getOpNum() {
        return opNum;
    }
    
    public int getOpNumi() {
        return opNumi;
    }
    
    public int getOpNumir() {
        return opNumir;
    }
    
    public int getOpNumr() {
        return opNumr;
    }
    
    public int getOpTotal() {
        return opTotal;
    }
    
    public MyUmlClass getFather() {
        return father;
    }
    
    public int getAttributeCount() {
        return attributes.size();
    }
    
    public void addAss() {
        associationNum++;
    }
    
    public int getAssociationCount() {
        return associationNum;
    }
    
    public ArrayList<String> getAsses() {
        ArrayList<String> ans = new ArrayList<>();
        for (MyUmlClass temp : asses) {
            ans.add(temp.getUmlClassId());
        }
        return ans;
    }
    
    public HashMap<Visibility, Integer> getOperationsVcount(String methodName) {
        HashMap<Visibility, Integer> ans = new HashMap<>();
        int publicNum = 0;
        int privateNum = 0;
        int protectedNum = 0;
        int packageNum = 0;
        for (MyUmlOperation op : operations) {
            if (op.getName().equals(methodName)) {
                if (op.opVis() == Visibility.PUBLIC) {
                    publicNum++;
                }
                if (op.opVis() == Visibility.PRIVATE) {
                    privateNum++;
                }
                if (op.opVis() == Visibility.PACKAGE) {
                    packageNum++;
                }
                if (op.opVis() == Visibility.PROTECTED) {
                    protectedNum++;
                }
            }
        }
        ans.put(Visibility.PUBLIC, publicNum);
        ans.put(Visibility.PACKAGE, packageNum);
        ans.put(Visibility.PROTECTED, protectedNum);
        ans.put(Visibility.PRIVATE, privateNum);
        return ans;
    }
    
    public int checkAttribute(String attName) {
        int first = -1;
        int last = -1;
        for (int i = 0; i < attributes.size(); i++) {
            if (first == -1 && attributes.get(i).getName().equals(attName)) {
                first = i;
                last = i;
            }
            if (attributes.get(i).getName().equals(attName)) {
                last = i;
            }
        }
        if (first == -1) {
            return -1;
        }
        if (first != last) {
            return -2;
        }
        return 0;
    }
    
    public boolean containsAttribute(String attName) {
        for (int i = 0; i < attributes.size(); i++) {
            if (attributes.get(i).getName().equals(attName)) {
                return true;
            }
        }
        return false;
    }
    
    public Visibility attVis(String attName) {
        for (int i = 0; i < attributes.size(); i++) {
            if (attributes.get(i).getName().equals(attName)) {
                return attributes.get(i).getVisibility();
            }
        }
        return null;
    }
    
    public ArrayList<MyUmlInterface> getRealization() {
        //models.mclass.MyUmlInterface temp = realization.get(0);
        //realization.remove(0);
        //return temp;
        return realization;
    }
    
    public ArrayList<UmlAttribute> getAttributes() {
        return attributes;
    }
    
    public void addEndName(String name) {
        endNames.add(name);
    }
    
    public HashSet<AttributeClassInformation> check1() {
        HashSet<AttributeClassInformation> ans = new HashSet<>();
        HashMap<String, Integer> names = new HashMap<>();
        for (UmlAttribute att : attributes) {
            if (!names.containsKey(att.getName())) {
                names.put(att.getName(), 1);
            } else {
                int num = names.get(att.getName());
                names.put(att.getName(), num + 1);
            }
        }
        for (String sname : endNames) {
            if (!names.containsKey(sname)) {
                names.put(sname, 1);
            } else {
                int num = names.get(sname);
                names.put(sname, num + 1);
            }
        }
        for (String s : names.keySet()) {
            if (names.get(s) != 1 && s != null) {
                AttributeClassInformation info =
                        new AttributeClassInformation(s, getUmlClassName());
                //System.out.println(s);
                ans.add(info);
            }
        }
        return ans;
    }
    
    public HashSet<UmlClassOrInterface> check2() {
        HashSet<UmlClassOrInterface> ans = new HashSet<>();
        HashMap<String, Integer> names = new HashMap<>();
        MyUmlClass cla = this;
        names.put(this.getUmlClassName(), 1);
        while (cla.father != null) {
            cla = cla.father;
            //System.out.println(cla);
            if (cla.equals(this)) {
                UmlClass a = this.getUmlClass();
                ans.add(a);
                return ans;
            }
            names.put(cla.getUmlClassName(), 1);
        }
        return ans;
    }
    
    public HashSet<UmlClassOrInterface> check32() {
        HashSet<UmlClassOrInterface> ans = new HashSet<>();
        HashMap<MyUmlInterface, Integer> interRealTimes = new HashMap<>();
        ArrayList<MyUmlInterface> inters = new ArrayList<>();
        MyUmlClass cla = this;
        inters.addAll(cla.getRealization());
        while (cla.father != null) {
            inters.addAll(cla.getRealization());
            cla = cla.getFather();
        }
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
                ans.add(this.getUmlClass());
                return ans;
            }
        }
        return ans;
    }
    
    public HashSet<UmlClassOrInterface> check3() {
        HashSet<UmlClassOrInterface> ans = new HashSet<>();
        HashMap<String, Integer> names = new HashMap<>();
        MyUmlInterface inter;
        ArrayList<MyUmlInterface> nextList = new ArrayList<>();
        nextList.addAll(this.realization);
        MyUmlClass cla = this;
        while (cla.father != null) {
            cla = cla.father;
            nextList.addAll(cla.realization);
        }
        cla = this;
        ArrayList<MyUmlClass> todo = new ArrayList<>();
        todo.addAll(this.sons);
        while (!todo.isEmpty()) {
            for (MyUmlClass son : cla.sons) {
                nextList.addAll(son.realization);
            }
            cla = todo.remove(0);
        }
        while (!nextList.isEmpty()) {
            inter = nextList.remove(0);
            /*
            if(alreadyList.contains(inter)){
                continue;
            }*/
            if (names.containsKey(inter.getUmlInterfaceName())) {
                ans.add(this.getUmlClass());
                //ans.add(inter.getUmlInterface());
                return ans;
            }
            names.put(inter.getUmlInterfaceName(), 1);
            nextList.addAll(inter.getFather());
            //alreadyList.add(inter);
        }
        return ans;
    }
    
    @Override
    public String toString() {
        return umlClass.toString();
    }
}
