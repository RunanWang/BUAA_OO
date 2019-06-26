package models.minter;

import com.oocourse.uml2.models.elements.UmlLifeline;

import java.util.ArrayList;

public class MyUmlLifeline {
    private UmlLifeline lifeline;
    private ArrayList<MyUmlLifeline> nextLife = new ArrayList<>();
    private ArrayList<MyUmlLifeline> fromLife = new ArrayList<>();
    private int fromNum = 0;
    
    public MyUmlLifeline(UmlLifeline l) {
        lifeline = l;
    }
    
    public void addNext(MyUmlLifeline l) {
        nextLife.add(l);
    }
    
    public void addFrom(MyUmlLifeline l) {
        fromLife.add(l);
        fromNum++;
    }
    
    public int getFromNum() {
        return fromNum;
    }
    
    public String getLifeName() {
        return lifeline.getName();
    }
}
