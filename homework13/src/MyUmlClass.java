import com.oocourse.uml1.models.common.Visibility;
import com.oocourse.uml1.models.elements.UmlClass;
import com.oocourse.uml1.models.elements.UmlAttribute;

import java.util.ArrayList;
import java.util.HashMap;

public class MyUmlClass {
    //name id vis
    private UmlClass umlClass;
    private ArrayList<MyUmlOperation> operations;
    private ArrayList<UmlAttribute> attributes;
    //private ArrayList<MyUmlAssociation> associations;
    //private ArrayList<UmlInterface> interfaces;
    private ArrayList<MyUmlInterface> realization;
    private MyUmlClass father;
    private ArrayList<MyUmlClass> asses;
    
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
        //MyUmlInterface temp = realization.get(0);
        //realization.remove(0);
        //return temp;
        return realization;
    }
    
    public ArrayList<UmlAttribute> getAttributes() {
        return attributes;
    }
}
