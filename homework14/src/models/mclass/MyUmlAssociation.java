package models.mclass;

import com.oocourse.uml2.models.elements.UmlAssociation;
import com.oocourse.uml2.models.elements.UmlAssociationEnd;

public class MyUmlAssociation {
    private UmlAssociation association;
    private UmlAssociationEnd end1;
    private UmlAssociationEnd end2;
    
    public MyUmlAssociation(UmlAssociation a) {
        association = a;
        end1 = null;
        end2 = null;
    }
    
    public void addEnd(UmlAssociationEnd end) {
        if (end.getId().equals(association.getEnd1())) {
            end1 = end;
        } else if (end.getId().equals(association.getEnd2())) {
            end2 = end;
        }
    }
    
    public boolean isEnd(UmlAssociationEnd end) {
        return end.getParentId().equals(association.getId());
    }
    
    public boolean isSet() {
        return (end1 != null && end2 != null);
    }
    
    public UmlAssociationEnd getEnd1() {
        return end1;
    }
    
    public UmlAssociationEnd getEnd2() {
        return end2;
    }
}
