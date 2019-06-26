package models.mstate;

import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlFinalState;
import com.oocourse.uml2.models.elements.UmlPseudostate;
import com.oocourse.uml2.models.elements.UmlState;

import java.util.ArrayList;

public class MyUmlState {
    private UmlState state = null;
    private UmlPseudostate pstate = null;
    private UmlFinalState fstate = null;
    //type=1：开始 2：中间 3：结束
    private int type = 0;
    private ArrayList<MyUmlState> nextState = new ArrayList<>();
    private int nextNum = 0;
    
    public MyUmlState(UmlElement e) {
        if (e.getClass() == UmlPseudostate.class) {
            pstate = (UmlPseudostate) e;
            type = 1;
        }
        if (e.getClass() == UmlState.class) {
            state = (UmlState) e;
            type = 2;
        }
        if (e.getClass() == UmlFinalState.class) {
            fstate = (UmlFinalState) e;
            type = 3;
        }
    }
    
    public void addNext(MyUmlState s) {
        if (!nextState.contains(s)) {
            nextState.add(s);
            nextNum++;
        }
    }
    
    public String getName() {
        if (type == 1) {
            return pstate.getName();
        }
        if (type == 2) {
            return state.getName();
        }
        return fstate.getName();
    }
    
    public int getNextNum() {
        return nextNum;
    }
    
    public ArrayList<MyUmlState> getNextState() {
        return nextState;
    }
    
    @Override
    public String toString() {
        if (type == 1) {
            return pstate.toString();
        }
        if (type == 2) {
            return state.toString();
        } else {
            return fstate.toString();
        }
    }
}
