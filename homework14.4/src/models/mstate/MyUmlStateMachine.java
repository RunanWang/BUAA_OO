package models.mstate;

import com.oocourse.uml2.interact.exceptions.user.StateDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.StateNotFoundException;

import com.oocourse.uml2.models.elements.UmlStateMachine;

import java.util.ArrayList;

public class MyUmlStateMachine {
    private UmlStateMachine stateMachine;
    private ArrayList<MyUmlState> states;
    private ArrayList<MyUmlState> pstates = new ArrayList<>();
    private ArrayList<MyUmlState> fstates = new ArrayList<>();
    private ArrayList<MyUmlTrans> transes = new ArrayList<>();
    private int stateNum;
    private int transNum;
    private int pstateNum;
    private int fstateNum;
    
    public MyUmlStateMachine(UmlStateMachine s) {
        stateMachine = s;
        states = new ArrayList<>();
        stateNum = 0;
        transNum = 0;
        pstateNum = 0;
        fstateNum = 0;
    }
    
    public void addState(MyUmlState s) {
        states.add(s);
        stateNum++;
        if (s.getType() == 1) {
            pstateNum++;
            pstates.add(s);
        }
        if (s.getType() == 3) {
            fstateNum++;
            fstates.add(s);
        }
    }
    
    public void addTran(MyUmlState src, MyUmlState tar) {
        MyUmlTrans trans = new MyUmlTrans(src, tar);
        if (!transes.contains(trans)) {
            transes.add(trans);
            transNum++;
        }
    }
    
    public String getName() {
        return stateMachine.getName();
    }
    
    public int getStateNum() {
        int ans = stateNum;
        if (pstateNum != 0) {
            ans = ans - pstateNum + 1;
        }
        if (fstateNum != 0) {
            ans = ans - fstateNum + 1;
        }
        return ans;
    }
    
    public int getTransNum() {
        return transNum;
    }
    
    public MyUmlState check(String name)
            throws StateNotFoundException, StateDuplicatedException {
        int num = 0;
        int i = 0;
        MyUmlState ans = null;
        for (i = 0; i < states.size(); i++) {
            if (name.equals(states.get(i).getName())) {
                num++;
                ans = states.get(i);
            }
        }
        if (num == 0) {
            throw new StateNotFoundException(stateMachine.getName(), name);
        }
        if (num != 1) {
            throw new StateDuplicatedException(stateMachine.getName(), name);
        }
        return ans;
    }
}
