package interactions;

import com.oocourse.uml2.interact.exceptions.user.InteractionDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.InteractionNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.LifelineDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.LifelineNotFoundException;
import models.minter.MyUmlInteraction;
import models.minter.MyUmlLifeline;

import java.util.ArrayList;
import java.util.HashMap;

public class MyUmlCollaborationInteraction {
    private HashMap<String, MyUmlLifeline> idToLife = new HashMap<>();
    private HashMap<String, MyUmlInteraction> interactions = new HashMap<>();
    private ArrayList<MyUmlInteraction> interactionList = new ArrayList<>();
    private ArrayList<MyUmlLifeline> lifes = new ArrayList<>();
    
    public MyUmlCollaborationInteraction(Input in) {
        idToLife = in.getIdToLife();
        interactions = in.getInteractions();
        lifes = in.getLifes();
        interactionList = in.getInteractionList();
    }
    
    private MyUmlInteraction check1(String name)
            throws InteractionNotFoundException,
            InteractionDuplicatedException {
        int findNum = 0;
        MyUmlInteraction ans = null;
        for (MyUmlInteraction in : interactionList) {
            if (in.getInterName().equals(name)) {
                findNum++;
                ans = in;
            }
        }
        if (findNum == 0) {
            throw new InteractionNotFoundException(name);
        }
        if (findNum != 1) {
            throw new InteractionDuplicatedException(name);
        }
        return ans;
    }
    
    /**
     * 获取参与对象数
     *
     * @param interactionName 交互名称
     * @return 参与对象数
     * @throws InteractionNotFoundException   交互未找到
     * @throws InteractionDuplicatedException 交互重名
     */
    public int getParticipantCount(String interactionName)
            throws InteractionNotFoundException,
            InteractionDuplicatedException {
        MyUmlInteraction in = check1(interactionName);
        return in.getLifeNum();
    }
    
    /**
     * 获取消息数
     *
     * @param interactionName 交互名称
     * @return 消息数
     * @throws InteractionNotFoundException   交互未找到
     * @throws InteractionDuplicatedException 交互重名
     */
    public int getMessageCount(String interactionName)
            throws InteractionNotFoundException,
            InteractionDuplicatedException {
        MyUmlInteraction in = check1(interactionName);
        return in.getMsgNum();
    }
    
    /**
     * 获取对象的进入消息数
     *
     * @param interactionName 交互名称
     * @param lifelineName    消息名称
     * @return 进入消息数
     * @throws InteractionNotFoundException   交互未找到
     * @throws InteractionDuplicatedException 交互重名
     * @throws LifelineNotFoundException      生命线未找到
     * @throws LifelineDuplicatedException    生命线重名
     */
    public int getIncomingMessageCount(String interactionName,
                                       String lifelineName)
            throws InteractionNotFoundException, InteractionDuplicatedException,
            LifelineNotFoundException, LifelineDuplicatedException {
        MyUmlInteraction in = check1(interactionName);
        MyUmlLifeline life = in.check2(interactionName, lifelineName);
        return life.getFromNum();
    }
}
