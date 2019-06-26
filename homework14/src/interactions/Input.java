package interactions;

import com.oocourse.uml2.models.elements.UmlMessage;
import com.oocourse.uml2.models.elements.UmlLifeline;
import com.oocourse.uml2.models.elements.UmlInteraction;
import com.oocourse.uml2.models.elements.UmlFinalState;
import com.oocourse.uml2.models.elements.UmlPseudostate;
import com.oocourse.uml2.models.elements.UmlTransition;
import com.oocourse.uml2.models.elements.UmlState;
import com.oocourse.uml2.models.elements.UmlRegion;
import com.oocourse.uml2.models.elements.UmlStateMachine;
import com.oocourse.uml2.models.elements.UmlClass;
import com.oocourse.uml2.models.elements.UmlGeneralization;
import com.oocourse.uml2.models.elements.UmlAssociation;
import com.oocourse.uml2.models.elements.UmlOperation;
import com.oocourse.uml2.models.elements.UmlAttribute;
import com.oocourse.uml2.models.elements.UmlInterfaceRealization;
import com.oocourse.uml2.models.elements.UmlInterface;
import com.oocourse.uml2.models.elements.UmlAssociationEnd;
import com.oocourse.uml2.models.elements.UmlParameter;
import com.oocourse.uml2.models.elements.UmlElement;
import models.minter.MyUmlInteraction;
import models.minter.MyUmlLifeline;
import models.mstate.MyUmlState;
import models.mstate.MyUmlStateMachine;
import models.mclass.MyUmlAssociation;
import models.mclass.MyUmlClass;
import models.mclass.MyUmlInterface;
import models.mclass.MyUmlOperation;

import java.util.ArrayList;
import java.util.HashMap;

public class Input {
    private ArrayList<UmlElement> list = new ArrayList<>();
    private ArrayList<MyUmlClass> classes = new ArrayList<>();
    private HashMap<String, Integer> classRefNum = new HashMap<>();
    private HashMap<String, MyUmlClass> classIdClass = new HashMap<>();
    private HashMap<String, MyUmlInterface> idInter = new HashMap<>();
    private ArrayList<MyUmlOperation> opList = new ArrayList<>();
    private ArrayList<MyUmlAssociation> assList = new ArrayList<>();
    private ArrayList<MyUmlInterface> inters = new ArrayList<>();
    private int classNum = 0;
    
    private ArrayList<MyUmlStateMachine> machines = new ArrayList<>();
    private HashMap<String, MyUmlStateMachine> idToMachine = new HashMap<>();
    private HashMap<String, MyUmlStateMachine> ridToMachine = new HashMap<>();
    private HashMap<String, MyUmlState> states = new HashMap<>();
    
    private ArrayList<MyUmlLifeline> lifes = new ArrayList<>();
    private HashMap<String, MyUmlLifeline> idToLife = new HashMap<>();
    private HashMap<String, MyUmlInteraction> interactions = new HashMap<>();
    private ArrayList<MyUmlInteraction> interactionList = new ArrayList<>();
    
    public Input(UmlElement[] elements) {
        for (int i = 0; i < elements.length; i++) {
            list.add(elements[i]);
        }
        umlInit();
    }
    
    private void umlInit() {
        //类、接口、状态机
        for (UmlElement ele : list) {
            if (ele.getClass() == UmlClass.class) {
                if (classRefNum.containsKey(ele.getName())) {
                    classRefNum.put(ele.getName(), 2);
                } else {
                    classRefNum.put(ele.getName(), 1);
                }
                MyUmlClass newMyClass = new MyUmlClass((UmlClass) ele);
                classes.add(newMyClass);
                classIdClass.put(ele.getId(), newMyClass);
                classNum++;
                //list.remove(ele);
            }
            if (ele.getClass() == UmlInterface.class) {
                MyUmlInterface newMyInterface =
                        new MyUmlInterface((UmlInterface) ele);
                inters.add(newMyInterface);
                idInter.put(ele.getId(), newMyInterface);
                //classIdClass.put(ele.getId(), newMyClass);
                //classNum++;
            }
            if (ele.getClass() == UmlStateMachine.class) {
                umlStateMachine((UmlStateMachine) ele);
            }
            if (ele.getClass() == UmlInteraction.class) {
                umlInteraction((UmlInteraction) ele);
            }
        }
        //方法、region
        for (UmlElement ele : list) {
            if (ele.getClass() == UmlOperation.class) {
                MyUmlOperation myop = new MyUmlOperation((UmlOperation) ele);
                opList.add(myop);
                for (MyUmlClass temp : classes) {
                    if (temp.classEqual(ele.getParentId())) {
                        temp.newOperation(myop);
                        break;
                    }
                }
                //list.remove(ele);
            }
            if (ele.getClass() == UmlRegion.class) {
                umlRegion(ele);
            }
            if (ele.getClass() == UmlLifeline.class) {
                umlLifeline((UmlLifeline) ele);
            }
        }
        umlInit2();
    }
    
    private void umlInit2() {
        for (UmlElement ele : list) {
            //参数
            if (ele.getClass() == UmlParameter.class) {
                for (MyUmlOperation op : opList) {
                    if (op.opEqual(ele.getParentId())) {
                        op.addPara((UmlParameter) ele);
                    }
                }
            }
            //属性
            if (ele.getClass() == UmlAttribute.class) {
                for (MyUmlClass temp : classes) {
                    if (temp.classEqual(ele.getParentId())) {
                        temp.newAttribute((UmlAttribute) ele);
                        break;
                    }
                }
            }
            //继承
            umlGener(ele);
            //关联
            if (ele.getClass() == UmlAssociation.class) {
                for (MyUmlClass temp : classes) {
                    if (temp.classEqual(ele.getParentId())) {
                        MyUmlAssociation association =
                                new MyUmlAssociation((UmlAssociation) ele);
                        assList.add(association);
                        break;
                    }
                }
            }
            //关联点
            umlInitAss(ele);
            //接口实现
            umlInitInter(ele);
            //state
            if (ele.getClass() == UmlState.class
                    || ele.getClass() == UmlPseudostate.class
                    || ele.getClass() == UmlFinalState.class
            ) {
                umlState(ele);
            }
            
            if (ele.getClass() == UmlMessage.class) {
                umlMessages((UmlMessage) ele);
            }
        }
        for (UmlElement ele : list) {
            if (ele.getClass() == UmlTransition.class) {
                umlTrans((UmlTransition) ele);
            }
        }
        for (MyUmlClass cla : classes) {
            cla.calOp();
        }
    }
    
    private void umlGener(UmlElement ele) {
        if (ele.getClass() == UmlGeneralization.class) {
            for (MyUmlClass temp : classes) {
                if (temp.classEqual(ele.getParentId())) {
                    //srcid应该等于temp(parentId)
                    //String srcid = ((UmlGeneralization)ele).getSource();
                    String tarid = ((UmlGeneralization) ele).getTarget();
                    for (MyUmlClass temp2 : classes) {
                        if (temp2.getUmlClassId().equals(tarid)) {
                            temp.newGeneration(temp2);
                            temp2.newSon(temp);
                        }
                    }
                    break;
                }
            }
            for (MyUmlInterface temp : inters) {
                if (temp.interEqual(ele.getParentId())) {
                    //srcid应该等于temp(parentId)
                    //String srcid = ((UmlGeneralization)ele).getSource();
                    String tarid = ((UmlGeneralization) ele).getTarget();
                    for (MyUmlInterface temp2 : inters) {
                        if (temp2.getUmlInterfaceId().equals(tarid)) {
                            temp.newGeneration(temp2);
                            temp2.newSon(temp);
                        }
                    }
                    break;
                }
            }
        }
    }
    
    private void umlInitInter(UmlElement ele) {
        if (ele.getClass() == UmlInterfaceRealization.class) {
            for (MyUmlInterface temp : inters) {
                //targetId是interface
                if (temp.interEqual(((
                        UmlInterfaceRealization) ele).getTarget())) {
                    //srcid应该等于temp(parentId)=>class
                    String srcid = ((UmlInterfaceRealization) ele).getSource();
                    for (MyUmlClass temp2 : classes) {
                        if (temp2.getUmlClassId().equals(srcid)) {
                            temp2.newInterface(temp);
                            temp.newReal(temp2);
                        }
                    }
                    for (MyUmlInterface temp2 : inters) {
                        if (temp2.getUmlInterfaceId().equals(srcid)) {
                            temp2.newGeneration(temp);
                        }
                    }
                    break;
                }
            }
        }
    }
    
    private void umlInitAss(UmlElement ele) {
        if (ele.getClass() == UmlAssociationEnd.class) {
            for (MyUmlAssociation ass : assList) {
                if (ass.isEnd((UmlAssociationEnd) ele)) {
                    ass.addEnd((UmlAssociationEnd) ele);
                    if (ass.isSet()) {
                        MyUmlClass end1 = classIdClass.
                                get(ass.getEnd1().getReference());
                        MyUmlClass end2 = classIdClass.
                                get(ass.getEnd2().getReference());
                        if (end1 != null && end2 != null) {
                            end1.newAssociation(end2);
                            end2.newAssociation(end1);
                            end1.addEndName(ass.getEnd2().getName());
                            end2.addEndName(ass.getEnd1().getName());
                            end1.addAss();
                            end2.addAss();
                        }
                        if (end1 == null && end2 != null) {
                            MyUmlInterface end11 = idInter.
                                    get(ass.getEnd1().getReference());
                            end11.addAss();
                            end2.addAss();
                            end2.addEndName(ass.getEnd1().getName());
                        }
                        if (end1 != null && end2 == null) {
                            MyUmlInterface end22 = idInter.
                                    get(ass.getEnd2().getReference());
                            //end1.newAssociation(end2);
                            //end2.newAssociation(end1);
                            end22.addAss();
                            end1.addAss();
                            end1.addEndName(ass.getEnd2().getName());
                        }
                        if (end1 == null && end2 == null) {
                            MyUmlInterface end11 = idInter.
                                    get(ass.getEnd1().getReference());
                            MyUmlInterface end22 = idInter.
                                    get(ass.getEnd2().getReference());
                            //end1.newAssociation(end2);
                            //end2.newAssociation(end1);
                            end11.addAss();
                            end22.addAss();
                        }
                        
                    }
                }
            }
        }
    }
    
    private void umlStateMachine(UmlStateMachine ele) {
        MyUmlStateMachine m = new MyUmlStateMachine(ele);
        machines.add(m);
        idToMachine.put(ele.getId(), m);
    }
    
    private void umlRegion(UmlElement ele) {
        MyUmlStateMachine machine = idToMachine.get(ele.getParentId());
        ridToMachine.put(ele.getId(), machine);
    }
    
    private void umlState(UmlElement ele) {
        MyUmlState state = new MyUmlState(ele);
        MyUmlStateMachine machine = ridToMachine.get(ele.getParentId());
        machine.addState(state);
        states.put(ele.getId(), state);
    }
    
    private void umlTrans(UmlTransition ele) {
        MyUmlStateMachine machine = ridToMachine.get(ele.getParentId());
        MyUmlState state1 = states.get(ele.getSource());
        MyUmlState state2 = states.get(ele.getTarget());
        machine.addTran(state1, state2);
        state1.addNext(state2);
    }
    
    private void umlInteraction(UmlInteraction ele) {
        MyUmlInteraction in = new MyUmlInteraction(ele);
        interactions.put(ele.getId(), in);
        interactionList.add(in);
    }
    
    private void umlLifeline(UmlLifeline ele) {
        MyUmlLifeline life = new MyUmlLifeline(ele);
        MyUmlInteraction in = interactions.get(ele.getParentId());
        in.addLife(life);
        lifes.add(life);
        idToLife.put(ele.getId(), life);
    }
    
    private void umlMessages(UmlMessage ele) {
        MyUmlInteraction in = interactions.get(ele.getParentId());
        MyUmlLifeline srcLife = idToLife.get(ele.getSource());
        MyUmlLifeline tarLife = idToLife.get(ele.getTarget());
        in.addmsg(ele);
        if (srcLife == null && tarLife != null) {
            tarLife.addFrom(srcLife);
        } else if (tarLife != null) {
            tarLife.addFrom(srcLife);
            srcLife.addNext(tarLife);
        }
    }
    
    public ArrayList<MyUmlClass> getClasses() {
        return classes;
    }
    
    public ArrayList<MyUmlInterface> getInters() {
        return inters;
    }
    
    public HashMap<String, Integer> getClassRefNum() {
        return classRefNum;
    }
    
    public HashMap<String, MyUmlClass> getClassIdClass() {
        return classIdClass;
    }
    
    public int getClassNum() {
        return classNum;
    }
    
    public ArrayList<MyUmlStateMachine> getMachines() {
        return machines;
    }
    
    public HashMap<String, MyUmlLifeline> getIdToLife() {
        return idToLife;
    }
    
    public HashMap<String, MyUmlInteraction> getInteractions() {
        return interactions;
    }
    
    public ArrayList<MyUmlInteraction> getInteractionList() {
        return interactionList;
    }
    
    public ArrayList<MyUmlLifeline> getLifes() {
        return lifes;
    }
}
