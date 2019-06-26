package models.minter;

import com.oocourse.uml2.interact.exceptions.user.LifelineDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.LifelineNotFoundException;
import com.oocourse.uml2.models.elements.UmlInteraction;
import com.oocourse.uml2.models.elements.UmlMessage;

import java.util.ArrayList;

public class MyUmlInteraction {
    private UmlInteraction inter;
    private ArrayList<MyUmlLifeline> lifes = new ArrayList<>();
    private ArrayList<UmlMessage> messages = new ArrayList<>();
    private int lifeNum = 0;
    private int msgNum = 0;
    
    public MyUmlInteraction(UmlInteraction in) {
        inter = in;
    }
    
    public void addLife(MyUmlLifeline life) {
        lifes.add(life);
        lifeNum++;
    }
    
    public void addmsg(UmlMessage m) {
        msgNum++;
        messages.add(m);
    }
    
    public int getLifeNum() {
        return lifeNum;
    }
    
    public String getInterName() {
        return inter.getName();
    }
    
    public int getMsgNum() {
        return msgNum;
    }
    
    public MyUmlLifeline check2(String interactionName, String lifelineName)
            throws LifelineNotFoundException, LifelineDuplicatedException {
        MyUmlLifeline ans = null;
        int findNum = 0;
        for (MyUmlLifeline life : lifes) {
            if (life.getLifeName().equals(lifelineName)) {
                findNum++;
                ans = life;
            }
        }
        if (findNum == 0) {
            throw new LifelineNotFoundException(interactionName, lifelineName);
        }
        if (findNum != 1) {
            throw new LifelineDuplicatedException(interactionName,
                    lifelineName);
        }
        return ans;
    }
}
