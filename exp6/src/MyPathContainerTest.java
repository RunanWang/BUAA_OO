import com.oocourse.specs1.models.Path;
import com.oocourse.specs1.models.PathContainer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MyPathContainerTest {
    private final PathContainer pathContainer = new MyPathContainer();
    private Path path1, path2, path3;

    @Before
    public void before() {
        path1 = new MyPath(1, 2, 3, 4);
        path2 = new MyPath(1, 2, 3, 4);
        path3 = new MyPath(1, 2, 3, 4, 5);
    }

    @After
    public void after() {
        // do something here
    }

    @Test
    public void testAddPath1() throws Exception {
        Assert.assertEquals(false,pathContainer.containsPath(path1));
        Assert.assertEquals(1, pathContainer.addPath(path1));
        Assert.assertEquals(true,pathContainer.containsPath(path1));
    }
    
    @Test
    public void testAddPath12() throws Exception {
        Assert.assertEquals(false,pathContainer.containsPath(path1));
        Assert.assertEquals(1, pathContainer.addPath(path1), 1);
        Assert.assertEquals(true,pathContainer.containsPath(path1));
        Assert.assertEquals(true,pathContainer.containsPathId(1));
    }
    
    @Test
    public void testAddPath13() throws Exception {
        Assert.assertEquals(false,pathContainer.containsPath(path1));
        Assert.assertEquals(1, pathContainer.addPath(path1), 1);
        Assert.assertEquals(true,pathContainer.containsPath(path1));
        Assert.assertEquals(true,pathContainer.containsPathId(0));
        Assert.assertEquals(path1, pathContainer.getPathById(0));
        Assert.assertEquals(1, pathContainer.size());
    }
}
