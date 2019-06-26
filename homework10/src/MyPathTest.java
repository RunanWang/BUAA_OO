import org.junit.*;

import static org.junit.Assert.*;

import java.util.Random;

public class MyPathTest {
    
    private static MyPath testPath;
    private static int[] pathList;
    
    @BeforeClass
    public static void beforeClass() {
        System.out.println("Before Class");
        Random r = new Random();
        int pathLength = r.nextInt(1998) + 2;
        pathList = new int[pathLength];
        for (int i = 0; i < pathLength; i++) {
            pathList[i] = r.nextInt();
        }
        testPath = new MyPath(pathList);
    }
    
    @Before
    public void setUp() throws Exception {
        //Random r = new Random();
    }
    
    @Test
    @Ignore
    public void getNode() {
        Random r = new Random();
        System.out.println("Test getNode()");
        for (int i = 0; i < 100; i++) {
            int node = r.nextInt(testPath.size());
            System.out.println(testPath.getNode(node));
            assertEquals(testPath.getNode(node),pathList[node]);
        }
    }
    
    @Test
    public void containsNode() {
    }
    
    @Test
    public void getDistinctNodeCount() {
    }
    
    @Test
    public void equals() {
    }
    
    @Test
    public void isValid() {
    }
    
    @Test
    public void iterator() {
    }
    
    @Test
    public void compareTo() {
    }
}