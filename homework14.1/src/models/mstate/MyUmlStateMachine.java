package models.mstate;

import com.oocourse.uml2.interact.exceptions.user.StateDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.StateNotFoundException;

import com.oocourse.uml2.models.elements.UmlStateMachine;

import java.util.ArrayList;

public class MyUmlStateMachine {
    private UmlStateMachine stateMachine;
    private ArrayList<MyUmlState> states;
    private int stateNum;
    private int transNum;
    
    public MyUmlStateMachine(UmlStateMachine s) {
        stateMachine = s;
        states = new ArrayList<>();
        stateNum = 0;
        transNum = 0;
    }
    
    public void addState(MyUmlState s) {
        states.add(s);
        stateNum++;
    }
    
    public void addTran() {
        transNum++;
    }
    
    public String getName() {
        return stateMachine.getName();
    }
    
    public int getStateNum() {
        return stateNum;
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
