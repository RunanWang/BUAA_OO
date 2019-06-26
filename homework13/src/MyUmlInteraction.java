import com.oocourse.uml1.interact.common.AttributeClassInformation;
import com.oocourse.uml1.interact.common.AttributeQueryType;
import com.oocourse.uml1.interact.common.OperationQueryType;
import com.oocourse.uml1.interact.exceptions.user.AttributeDuplicatedException;
import com.oocourse.uml1.interact.exceptions.user.AttributeNotFoundException;
import com.oocourse.uml1.interact.exceptions.user.ClassDuplicatedException;
import com.oocourse.uml1.interact.exceptions.user.ClassNotFoundException;
import com.oocourse.uml1.interact.format.UmlInteraction;
import com.oocourse.uml1.models.common.Visibility;
import com.oocourse.uml1.models.elements.UmlClass;
import com.oocourse.uml1.models.elements.UmlGeneralization;
import com.oocourse.uml1.models.elements.UmlAssociation;
import com.oocourse.uml1.models.elements.UmlOperation;
import com.oocourse.uml1.models.elements.UmlAttribute;
import com.oocourse.uml1.models.elements.UmlInterfaceRealization;
import com.oocourse.uml1.models.elements.UmlInterface;
import com.oocourse.uml1.models.elements.UmlAssociationEnd;
import com.oocourse.uml1.models.elements.UmlParameter;
import com.oocourse.uml1.models.elements.UmlElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyUmlInteraction implements UmlInteraction {
    private ArrayList<UmlElement> list = new ArrayList<>();
    private ArrayList<MyUmlClass> classes = new ArrayList<>();
    private HashMap<String, Integer> classRefNum = new HashMap<>();
    private HashMap<String, MyUmlClass> classIdClass = new HashMap<>();
    private HashMap<String, MyUmlInterface> idInter = new HashMap<>();
    private ArrayList<MyUmlOperation> opList = new ArrayList<>();
    private ArrayList<MyUmlAssociation> assList = new ArrayList<>();
    private int classNum = 0;
    
    private ArrayList<MyUmlInterface> inters = new ArrayList<>();
    
    public MyUmlInteraction(UmlElement[] elements) {
        for (int i = 0; i < elements.length; i++) {
            list.add(elements[i]);
        }
        umlInit();
    }
    
    private void umlInit() {
        //类、接口
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
        }
        //方法
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
            if (ele.getClass() == UmlGeneralization.class) {
                for (MyUmlClass temp : classes) {
                    if (temp.classEqual(ele.getParentId())) {
                        //srcid应该等于temp(parentId)
                        //String srcid = ((UmlGeneralization)ele).getSource();
                        String tarid = ((UmlGeneralization) ele).getTarget();
                        for (MyUmlClass temp2 : classes) {
                            if (temp2.getUmlClassId().equals(tarid)) {
                                temp.newGeneration(temp2);
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
                            }
                        }
                        break;
                    }
                }
            }
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
        }
        for (MyUmlClass cla : classes) {
            cla.calOp();
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
                            end1.addAss();
                            end2.addAss();
                        }
                        if (end1 == null && end2 != null) {
                            MyUmlInterface end11 = idInter.
                                    get(ass.getEnd1().getReference());
                            end11.addAss();
                            end2.addAss();
                        }
                        if (end1 != null && end2 == null) {
                            MyUmlInterface end22 = idInter.
                                    get(ass.getEnd2().getReference());
                            //end1.newAssociation(end2);
                            //end2.newAssociation(end1);
                            end22.addAss();
                            end1.addAss();
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
    
    /**
     * 获取类数量
     * 指令：CLASS_COUNT
     *
     * @return 类数量
     */
    public int getClassCount() {
        return classNum;
    }
    
    private void check(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        if (!classRefNum.containsKey(className)) {
            throw new ClassNotFoundException(className);
        }
        if (classRefNum.get(className) == 2) {
            throw new ClassDuplicatedException(className);
        }
    }
    
    private MyUmlClass nameToMyClass(String className) {
        MyUmlClass tarClass = null;
        for (MyUmlClass temp : classes) {
            if (temp.getUmlClassName().equals(className)) {
                tarClass = temp;
                break;
            }
        }
        return tarClass;
    }
    
    /**
     * 获取类操作数量
     * 指令：CLASS_OPERATION_COUNT
     *
     * @param className 类名
     * @param queryType 操作类型
     * @return 指定类型的操作数量
     * @throws ClassNotFoundException   类未找到异常
     * @throws ClassDuplicatedException 类重复异常
     */
    public int getClassOperationCount(
            String className, OperationQueryType queryType)
            throws ClassNotFoundException, ClassDuplicatedException {
        check(className);
        MyUmlClass tarClass = nameToMyClass(className);
        if (tarClass == null) {
            return -1;
        }
        switch (queryType) {
            case RETURN:
                return tarClass.getOpNumr() + tarClass.getOpNumir();
            case ALL:
                return tarClass.getOpTotal();
            case PARAM:
                return tarClass.getOpNumir() + tarClass.getOpNumi();
            case NON_PARAM:
                return tarClass.getOpNum() + tarClass.getOpNumr();
            case NON_RETURN:
                return tarClass.getOpNum() + tarClass.getOpNumi();
            default:
                return -2;
        }
        //return -2;
    }
    
    /**
     * 获取类属性数量
     * 指令：CLASS_ATTR_COUNT
     *
     * @param className 类名
     * @param queryType 操作类型
     * @return 指定类型的操作数量
     * @throws ClassNotFoundException   类未找到异常
     * @throws ClassDuplicatedException 类重复异常
     */
    public int getClassAttributeCount(
            String className, AttributeQueryType queryType)
            throws ClassNotFoundException, ClassDuplicatedException {
        check(className);
        MyUmlClass tarClass = nameToMyClass(className);
        if (queryType == AttributeQueryType.SELF_ONLY) {
            return tarClass.getAttributeCount();
        } else if (queryType == AttributeQueryType.ALL) {
            int num = 0;
            while (tarClass != null) {
                num = num + tarClass.getAttributeCount();
                tarClass = tarClass.getFather();
            }
            return num;
        }
        return -1;
    }
    
    /**
     * 获取类关联数量
     * 指令：CLASS_ASSO_COUNT
     *
     * @param className 类名
     * @return 类关联数量
     * @throws ClassNotFoundException   类未找到异常
     * @throws ClassDuplicatedException 类重复异常
     */
    public int getClassAssociationCount(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        check(className);
        MyUmlClass tarClass = nameToMyClass(className);
        int num = 0;
        while (tarClass != null) {
            num = num + tarClass.getAssociationCount();
            tarClass = tarClass.getFather();
        }
        return num;
        //return 0;
    }
    
    /**
     * 获取与类相关联的类列表
     * 指令：CLASS_ASSO_CLASS_LIST
     *
     * @param className 类名
     * @return 与类关联的列表
     * @throws ClassNotFoundException   类未找到异常
     * @throws ClassDuplicatedException 类重复异常
     */
    public List<String> getClassAssociatedClassList(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        check(className);
        MyUmlClass tarClass = nameToMyClass(className);
        ArrayList<String> ans = new ArrayList<>();
        ArrayList<String> idList = new ArrayList<>();
        while (tarClass != null) {
            for (String id : tarClass.getAsses()) {
                if (!idList.contains(id)) {
                    idList.add(id);
                    ans.add(classIdClass.get(id).getUmlClassName());
                }
            }
            tarClass = tarClass.getFather();
        }
        return ans;
    }
    
    /**
     * 统计类操作可见性
     * 指令：CLASS_OPERATION_VISIBILITY
     *
     * @param className     类名
     * @param operationName 操作名
     * @return 类操作可见性统计结果
     * @throws ClassNotFoundException   类未找到异常
     * @throws ClassDuplicatedException 类重复异常
     */
    public Map<Visibility, Integer> getClassOperationVisibility(
            String className, String operationName)
            throws ClassNotFoundException, ClassDuplicatedException {
        check(className);
        MyUmlClass tarClass = nameToMyClass(className);
        return tarClass.getOperationsVcount(operationName);
    }
    
    /**
     * 获取类属性可见性
     * 指令：CLASS_ATTR_VISIBILITY
     *
     * @param className     类名
     * @param attributeName 属性名
     * @return 属性可见性
     * @throws ClassNotFoundException       类未找到异常
     * @throws ClassDuplicatedException     类重复异常
     * @throws AttributeNotFoundException   属性未找到异常
     * @throws AttributeDuplicatedException 属性重复异常
     */
    public Visibility getClassAttributeVisibility(
            String className, String attributeName)
            throws ClassNotFoundException, ClassDuplicatedException,
            AttributeNotFoundException, AttributeDuplicatedException {
        check(className);
        MyUmlClass tarClass = nameToMyClass(className);
        int ch = tarClass.checkAttribute(attributeName);
        if (ch == -1 && tarClass.getFather() == null) {
            throw new AttributeNotFoundException(className, attributeName);
        }
        if (ch == -1) {
            try {
                Visibility ans = getClassAttributeVisibility(
                        tarClass.getFather().getUmlClassName(), attributeName);
                return ans;
            }catch (Exception e){
                if(e.getClass()==AttributeNotFoundException.class){
                    throw new AttributeNotFoundException(className, attributeName);
                }
            }
        }
        if (ch == -2) {
            throw new AttributeDuplicatedException(className, attributeName);
        }
        Visibility vis = tarClass.attVis(attributeName);
        tarClass = tarClass.getFather();
        while (tarClass != null) {
            if (tarClass.containsAttribute(attributeName)) {
                throw new
                        AttributeDuplicatedException(className, attributeName);
            }
            tarClass = tarClass.getFather();
        }
        return vis;
    }
    
    /**
     * 获取顶级父类
     * 指令：CLASS_TOP_BASE
     *
     * @param className 类名
     * @return 顶级父类名
     * @throws ClassNotFoundException   类未找到异常
     * @throws ClassDuplicatedException 类重复异常
     */
    public String getTopParentClass(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        check(className);
        MyUmlClass tarClass = nameToMyClass(className);
        while (tarClass.getFather() != null) {
            tarClass = tarClass.getFather();
        }
        return tarClass.getUmlClassName();
    }
    
    public List<String> getImplementInterfaceList(String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        ArrayList<String> ans = new ArrayList<>();
        ArrayList<String> idList = new ArrayList<>();
        check(className);
        MyUmlClass tarClass = nameToMyClass(className);
        while (tarClass != null) {
            //System.out.println(tarClass);
            ArrayList<MyUmlInterface> real1 = tarClass.getRealization();
            ArrayList<MyUmlInterface> real = new ArrayList<>();
            for (MyUmlInterface tempIn : real1) {
                real.add(tempIn);
            }
            //ans.add(real.getUmlInterfaceName());
            while (real.size() != 0) {
                MyUmlInterface temp = real.get(0);
                if (!idList.contains(temp.getUmlInterfaceId())) {
                    ans.add(temp.getUmlInterfaceName());
                    idList.add(temp.getUmlInterfaceId());
                    ArrayList<MyUmlInterface> realtemp = temp.getFather();
                    for (MyUmlInterface tempIn : realtemp) {
                        real.add(tempIn);
                    }
                }
                real.remove(0);
            }
            tarClass = tarClass.getFather();
        }
        return ans;
    }
    
    public List<AttributeClassInformation> getInformationNotHidden(
            String className)
            throws ClassNotFoundException, ClassDuplicatedException {
        ArrayList<AttributeClassInformation> ans = new ArrayList<>();
        check(className);
        MyUmlClass tarClass = nameToMyClass(className);
        while (tarClass != null) {
            ArrayList<UmlAttribute> attributes = tarClass.getAttributes();
            for (UmlAttribute att : attributes) {
                if (att.getVisibility() != Visibility.PRIVATE) {
                    AttributeClassInformation temp;
                    temp = new AttributeClassInformation(att.getName(),
                            tarClass.getUmlClassName());
                    ans.add(temp);
                }
            }
            tarClass = tarClass.getFather();
        }
        return ans;
    }
}