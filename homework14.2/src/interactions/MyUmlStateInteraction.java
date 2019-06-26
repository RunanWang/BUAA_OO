package interactions;

import com.oocourse.uml2.interact.format.UmlStateChartInteraction;
import com.oocourse.uml2.interact.exceptions.user.StateDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.StateMachineDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.StateMachineNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.StateNotFoundException;
import models.mstate.MyUmlState;
import models.mstate.MyUmlStateMachine;

import java.util.ArrayList;

public class MyUmlStateInteraction implements UmlStateChartInteraction {
    
    private ArrayList<MyUmlStateMachine> machines = new ArrayList<>();
    
    public MyUmlStateInteraction(Input in) {
        this.machines = in.getMachines();
    }
    
    private int check(String name)
            throws StateMachineNotFoundException,
            StateMachineDuplicatedException {
        int num = 0;
        int i = 0;
        int no = 0;
        for (i = 0; i < machines.size(); i++) {
            if (name.equals(machines.get(i).getName())) {
                num++;
                no = i;
            }
        }
        if (num == 0) {
            throw new StateMachineNotFoundException(name);
        }
        if (num != 1) {
            throw new StateMachineDuplicatedException(name);
        }
        return no;
    }
    
    /**
     * 获取状态机的状态数
     *
     * @param stateMachineName 状态机名称
     * @return 状态数
     * @throws StateMachineNotFoundException   状态机未找到
     * @throws StateMachineDuplicatedException 状态机存在重名
     */
    public int getStateCount(String stateMachineName)
            throws StateMachineNotFoundException,
            StateMachineDuplicatedException {
        int i = check(stateMachineName);
        return machines.get(i).getStateNum();
    }
    
    /**
     * 获取状态机转移数
     *
     * @param stateMachineName 状态机名称
     * @return 转移数
     * @throws StateMachineNotFoundException   状态机未找到
     * @throws StateMachineDuplicatedException 状态机存在重名
     */
    public int getTransitionCount(String stateMachineName)
            throws StateMachineNotFoundException,
            StateMachineDuplicatedException {
        int i = check(stateMachineName);
        return machines.get(i).getTransNum();
    }
    
    /**
     * 获取后继状态数
     *
     * @param stateMachineName 状态机名称
     * @param stateName        状态名称
     * @return 后继状态数
     * @throws StateMachineNotFoundException   状态机未找到
     * @throws StateMachineDuplicatedException 状态机存在重名
     * @throws StateNotFoundException          状态未找到
     * @throws StateDuplicatedException        状态存在重名
     */
    public int getSubsequentStateCount(String stateMachineName,
                                       String stateName)
            throws StateMachineNotFoundException,
            StateMachineDuplicatedException,
            StateNotFoundException, StateDuplicatedException {
        int i = check(stateMachineName);
        MyUmlStateMachine machine = machines.get(i);
        MyUmlState state = machine.check(stateName);
        MyUmlState begin = state;
        ArrayList<MyUmlState> states = new ArrayList<>();
        states.addAll(state.getNextState());
        ArrayList<MyUmlState> alr = new ArrayList<>();
        ArrayList<MyUmlState> arrive = new ArrayList<>();
        alr.add(state);
        //加入alr：已经将能抵达的state加入了states
        while (!states.isEmpty()) {
            state = states.remove(0);
            //System.out.println(state);
            if (state == begin && !arrive.contains(state)) {
                arrive.add(state);
            }
            if (!arrive.contains(state)) {
                arrive.add(state);
            }
            if (!alr.contains(state)) {
                for (MyUmlState s : state.getNextState()) {
                    if (!alr.contains(s) && !states.contains(s)) {
                        states.add(s);
                    }
                }
                alr.add(state);
            }
            
        }
        int r = arrive.size();
        //System.out.println(r);
        int ptempNum = 0;
        int ftempNum = 0;
        for (MyUmlState s : arrive) {
            if (s.getType() == 1) {
                ptempNum++;
            }
            if (s.getType() == 3) {
                ftempNum++;
            }
        }
        if (ptempNum != 0) {
            r = r - ptempNum + 1;
        }
        if (ftempNum != 0) {
            r = r - ftempNum + 1;
        }
        return r;
    }
}
