import com.oocourse.uml1.models.elements.UmlInterface;

import java.util.ArrayList;

public class MyUmlInterface {
    private UmlInterface umlInterface;
    private ArrayList<MyUmlInterface> fathers;
    private int associationNum = 0;
    
    public MyUmlInterface(UmlInterface in) {
        umlInterface = in;
        fathers = new ArrayList<>();
    }
    
    public void newGeneration(MyUmlInterface father) {
        this.fathers.add(father);
    }
    
    public boolean interEqual(String id) {
        return id.equals(umlInterface.getId());
    }
    
    public String getUmlInterfaceId() {
        return umlInterface.getId();
    }
    
    public String getUmlInterfaceName() {
        return umlInterface.getName();
    }
    
    public ArrayList<MyUmlInterface> getFather() {
        return fathers;
    }
    
    public void addAss() {
        associationNum++;
    }
}
