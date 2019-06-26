package interactions;

import com.oocourse.uml2.interact.common.AttributeClassInformation;
import com.oocourse.uml2.interact.common.AttributeQueryType;
import com.oocourse.uml2.interact.common.OperationQueryType;
import com.oocourse.uml2.interact.exceptions.user.ClassDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.ClassNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.AttributeDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.AttributeNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.UmlRule002Exception;
import com.oocourse.uml2.interact.exceptions.user.UmlRule008Exception;
import com.oocourse.uml2.interact.exceptions.user.UmlRule009Exception;
import com.oocourse.uml2.interact.exceptions.user.PreCheckRuleException;
import com.oocourse.uml2.interact.exceptions.user.InteractionDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.InteractionNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.LifelineDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.LifelineNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.StateDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.StateMachineDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.StateMachineNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.StateNotFoundException;
import com.oocourse.uml2.interact.format.UmlGeneralInteraction;
import com.oocourse.uml2.models.common.Visibility;
import com.oocourse.uml2.models.elements.UmlElement;

import java.util.List;
import java.util.Map;

public class MyUmlGeneralInteraction implements UmlGeneralInteraction {
    private MyUmlClassModelInteraction classModel;
    private MyUmlStateInteraction stateModel;
    private MyUmlCollaborationInteraction interModel;
    
    public MyUmlGeneralInteraction(UmlElement[] elements) {
        Input input = new Input(elements);
        classModel = new MyUmlClassModelInteraction(input);
        stateModel = new MyUmlStateInteraction(input);
        interModel = new MyUmlCollaborationInteraction(input);
    }
    
    public int getClassCount() {
        return classModel.getClassCount();
    }
    
    public int getClassOperationCount(String var1, OperationQueryType var2)
            throws ClassNotFoundException, ClassDuplicatedException {
        return classModel.getClassOperationCount(var1, var2);
    }
    
    public int getClassAttributeCount(String var1, AttributeQueryType var2)
            throws ClassNotFoundException, ClassDuplicatedException {
        return classModel.getClassAttributeCount(var1, var2);
    }
    
    public int getClassAssociationCount(String var1)
            throws ClassNotFoundException, ClassDuplicatedException {
        return classModel.getClassAssociationCount(var1);
    }
    
    public List<String> getClassAssociatedClassList(String var1)
            throws ClassNotFoundException, ClassDuplicatedException {
        return classModel.getClassAssociatedClassList(var1);
    }
    
    public Map<Visibility, Integer> getClassOperationVisibility(
            String var1, String var2)
            throws ClassNotFoundException, ClassDuplicatedException {
        return classModel.getClassOperationVisibility(var1, var2);
    }
    
    public Visibility getClassAttributeVisibility(String var1, String var2)
            throws ClassNotFoundException, ClassDuplicatedException,
            AttributeNotFoundException, AttributeDuplicatedException {
        return classModel.getClassAttributeVisibility(var1, var2);
    }
    
    public String getTopParentClass(String var1)
            throws ClassNotFoundException, ClassDuplicatedException {
        return classModel.getTopParentClass(var1);
    }
    
    public List<String> getImplementInterfaceList(String var1)
            throws ClassNotFoundException, ClassDuplicatedException {
        return classModel.getImplementInterfaceList(var1);
    }
    
    public List<AttributeClassInformation> getInformationNotHidden(String var1)
            throws ClassNotFoundException, ClassDuplicatedException {
        return classModel.getInformationNotHidden(var1);
    }
    
    public int getParticipantCount(String var1)
            throws InteractionNotFoundException,
            InteractionDuplicatedException {
        return interModel.getParticipantCount(var1);
    }
    
    public int getMessageCount(String var1)
            throws InteractionNotFoundException,
            InteractionDuplicatedException {
        return interModel.getMessageCount(var1);
    }
    
    public int getIncomingMessageCount(String var1, String var2)
            throws InteractionNotFoundException, InteractionDuplicatedException,
            LifelineNotFoundException, LifelineDuplicatedException {
        return interModel.getIncomingMessageCount(var1, var2);
    }
    
    public void checkForAllRules() throws PreCheckRuleException {
        checkForUml002();
        checkForUml008();
        checkForUml009();
    }
    
    public void checkForUml002() throws UmlRule002Exception {
        classModel.checkForUml002();
    }
    
    public void checkForUml008() throws UmlRule008Exception {
        classModel.checkForUml008();
    }
    
    public void checkForUml009() throws UmlRule009Exception {
        classModel.checkForUml009();
    }
    
    public int getStateCount(String var1)
            throws StateMachineNotFoundException,
            StateMachineDuplicatedException {
        return stateModel.getStateCount(var1);
    }
    
    public int getTransitionCount(String var1)
            throws StateMachineNotFoundException,
            StateMachineDuplicatedException {
        return stateModel.getTransitionCount(var1);
    }
    
    public int getSubsequentStateCount(String var1, String var2)
            throws StateMachineNotFoundException,
            StateMachineDuplicatedException,
            StateNotFoundException, StateDuplicatedException {
        return stateModel.getSubsequentStateCount(var1, var2);
    }
    
}