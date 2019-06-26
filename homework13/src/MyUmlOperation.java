import com.oocourse.uml1.models.common.Direction;
import com.oocourse.uml1.models.common.Visibility;
import com.oocourse.uml1.models.elements.UmlOperation;
import com.oocourse.uml1.models.elements.UmlParameter;

import java.util.ArrayList;

public class MyUmlOperation {
    private UmlOperation operation;
    private ArrayList<UmlParameter> paraList = new ArrayList<>();
    private boolean returnPara = false;
    private boolean inPara = false;
    
    public MyUmlOperation(UmlOperation op) {
        operation = op;
    }
    
    public void addPara(UmlParameter para) {
        paraList.add(para);
        if (!returnPara) {
            if (para.getDirection() == Direction.RETURN) {
                returnPara = true;
            }
        }
        if (!inPara) {
            if (para.getDirection() == Direction.IN) {
                inPara = true;
            }
        }
    }
    
    public boolean opEqual(String id) {
        return id.equals(operation.getId());
    }
    
    public boolean isReturn() {
        return returnPara;
    }
    
    public boolean isIn() {
        return inPara;
    }
    
    public Visibility opVis() {
        return operation.getVisibility();
    }
    
    public String getName() {
        return operation.getName();
    }
}
