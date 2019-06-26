import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MyPathTest {
    private MyPath path1, path2, path3;
    
    @Before
    public void before() {
        path1 = new MyPath(1, 2, 3, 4);
        path2 = new MyPath(1, 2, 3, 4);
        path3 = new MyPath(1, 2, 3, 4, 5);
    }
    
    @After
    public void after() {
        // do nothing
    }
    
    @Test
    public void testEquals12() throws Exception {
        // this can be better actually:
        Assert.assertEquals(true, path1.equals(path2));
    }
    
    @Test
    public void testEquals11() throws Exception {
        // this can be better actually:
        Assert.assertEquals(true, path1.equals(path1));
    }
    
    @Test
    public void testEquals13() throws Exception {
        // this can be better actually:
        Assert.assertEquals(true, path1.equals(path1));
    }
}
