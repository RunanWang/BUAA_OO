import java.util.HashMap;

public class Test {
    private static HashMap<Integer,Integer> map = new HashMap<>();
    
    public static void main(String[] args) {
       map.put(1,2);
       System.out.println("The size of the map is "+map.size());
       map.put(0,3);
       System.out.println("The size of the map is "+map.size());
       System.out.println("The key 1 refers to "+map.get(1));
    }
    
}
