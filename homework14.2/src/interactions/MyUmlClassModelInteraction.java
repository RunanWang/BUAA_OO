package interactions;

import com.oocourse.uml2.models.elements.UmlClassOrInterface;
import models.mclass.MyUmlClass;
import models.mclass.MyUmlInterface;
import com.oocourse.uml2.interact.common.AttributeClassInformation;
import com.oocourse.uml2.interact.common.AttributeQueryType;
import com.oocourse.uml2.interact.common.OperationQueryType;
import com.oocourse.uml2.interact.exceptions.user.AttributeDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.AttributeNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.ClassDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.ClassNotFoundException;
import com.oocourse.uml2.interact.format.UmlClassModelInteraction;
import com.oocourse.uml2.models.common.Visibility;
import com.oocourse.uml2.models.elements.UmlAttribute;
import com.oocourse.uml2.interact.exceptions.user.UmlRule002Exception;
import com.oocourse.uml2.interact.exceptions.user.UmlRule008Exception;
import com.oocourse.uml2.interact.exceptions.user.UmlRule009Exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class MyUmlClassModelInteraction implements UmlClassModelInteraction {
    //private ArrayList<UmlElement> list = new ArrayList<>();
    private ArrayList<MyUmlClass> classes = new ArrayList<>();
    private HashMap<String, Integer> classRefNum = new HashMap<>();
    private HashMap<String, MyUmlClass> classIdClass = new HashMap<>();
    private ArrayList<models.mclass.MyUmlInterface> inters = new ArrayList<>();
    private int classNum = 0;
    
    public MyUmlClassModelInteraction(Input in) {
        this.classes = in.getClasses();
        this.classRefNum = in.getClassRefNum();
        this.classIdClass = in.getClassIdClass();
        this.classNum = in.getClassNum();
        this.inters = in.getInters();
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
            } catch (Exception e) {
                if (e.getClass() == AttributeNotFoundException.class) {
                    throw new AttributeNotFoundException(
                            className, attributeName);
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
    
    public void checkForUml002() throws UmlRule002Exception {
        HashSet<AttributeClassInformation> ans = new HashSet<>();
        for (MyUmlClass cla : classes) {
            ans.addAll(cla.check1());
        }
        if (!ans.isEmpty()) {
            //System.out.println(ans);
            throw new UmlRule002Exception(ans);
        }
    }
    
    public void checkForUml008() throws UmlRule008Exception {
        HashSet<UmlClassOrInterface> ans = new HashSet<>();
        for (MyUmlClass cla : classes) {
            ans.addAll(cla.check2());
        }
        for (MyUmlInterface inter : inters) {
            ans.addAll(inter.check2());
        }
        if (!ans.isEmpty()) {
            throw new UmlRule008Exception(ans);
        }
    }
    
    public void checkForUml009() throws UmlRule009Exception {
        HashSet<UmlClassOrInterface> ans = new HashSet<>();
        for (MyUmlClass cla : classes) {
            ans.addAll(cla.check32());
        }
        for (MyUmlInterface inter : inters) {
            ans.addAll(inter.check32());
        }
        if (!ans.isEmpty()) {
            throw new UmlRule009Exception(ans);
        }
    }
}