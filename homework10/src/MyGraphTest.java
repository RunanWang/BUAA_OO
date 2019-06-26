import com.oocourse.specs2.models.PathIdNotFoundException;
import com.oocourse.specs2.models.PathNotFoundException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyGraphTest {
    MyGraph myGraph = new MyGraph();
    
    @Before
    public void setUp() throws Exception {
        int[] pathList = {1, 2, 3, 4, 4, 5};
        MyPath path = new MyPath(pathList);
        myGraph.addPath(path);
        int[] pathList2 = {1, 2, 3, 4, 4, 5};
        path = new MyPath(pathList2);
        myGraph.addPath(path);
        int[] pathList3 = {1, 2, 3, 4, 6, 7};
        path = new MyPath(pathList3);
        myGraph.addPath(path);
        int[] pathList4 = {6, 7, 9, 10};
        path = new MyPath(pathList4);
        myGraph.addPath(path);
    }
    
    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void size() {
        assertEquals(myGraph.size(), 3);
    }
    
    @Test
    public void containsPath() {
        int[] pathList = {1, 2, 3, 4, 4, 5};
        MyPath path = new MyPath(pathList);
        assertTrue(myGraph.containsPath(path));
        int[] pathList5 = {1, 2, 3, 4, 5};
        path = new MyPath(pathList5);
        assertFalse(myGraph.containsPath(path));
        myGraph.addPath(path);
        assertTrue(myGraph.containsPath(path));
        try {
            myGraph.removePath(path);
        } catch (Exception e) {
        
        }
        assertFalse(myGraph.containsPath(path));
        assertFalse(myGraph.containsPath(null));
        
        myGraph.addPath(path);
        assertTrue(myGraph.containsPath(path));
        assertFalse(myGraph.containsPathId(4));
        try {
            myGraph.removePathById(5);
        } catch (Exception e) {
        
        }
        assertFalse(myGraph.containsPathId(5));
        assertFalse(myGraph.containsPathId(6));
        assertFalse(myGraph.containsPath(path));
    }
    
    @Test
    public void containsPathId() {
        assertTrue(myGraph.containsPathId(2));
        assertFalse(myGraph.containsPathId(5));
        assertFalse(myGraph.containsPathId(6));
    }
    
    @Test
    public void getPathById() {
        int[] pathList = {1, 2, 3, 4, 4, 5};
        MyPath path = new MyPath(pathList);
        try {
            assertEquals(myGraph.getPathById(1), path);
        } catch (Exception e) {
        
        }
        try {
            myGraph.getPathById(10);
        } catch (Exception e) {
            // assertEquals(e,new PathIdNotFoundException(10));
        }
    }
    
    @Test
    public void getPathId() {
        int[] pathList = {1, 2, 3, 4, 4, 5};
        MyPath path = new MyPath(pathList);
        try {
            assertEquals(myGraph.getPathId(path), 1);
        } catch (Exception e) {
        
        }
        try {
            myGraph.getPathId(null);
        } catch (Exception e) {
            //assertEquals(e,new PathNotFoundException(null));
        }
    }
    
    
    @Test
    public void getDistinctNodeCount() {
        assertEquals(myGraph.getDistinctNodeCount(), 9);
        int[] pathList = {8, 8, 11};
        MyPath path = new MyPath(pathList);
        assertEquals(path.getDistinctNodeCount(), 2);
        myGraph.addPath(path);
        assertEquals(myGraph.getDistinctNodeCount(), 11);
    }
    
    @Test
    public void containsNode() {
        assertTrue(myGraph.containsNode(1));
        assertFalse(myGraph.containsNode(-1));
    }
    
    @Test
    public void containsEdge() {
        assertFalse(myGraph.containsEdge(5, 6));
        assertTrue(myGraph.containsEdge(1, 2));
        int[] pathList = {5, 6};
        MyPath path = new MyPath(pathList);
        myGraph.addPath(path);
        assertTrue(myGraph.containsEdge(5, 6));
    }
    
    @Test
    public void isConnected() {
        int[] pathList = {8, 11};
        MyPath path = new MyPath(pathList);
        myGraph.addPath(path);
        try {
            assertTrue(myGraph.isConnected(1, 2));
            assertTrue(myGraph.isConnected(5, 6));
            assertFalse(myGraph.isConnected(8, 9));
        } catch (Exception e) {
        
        }
    }
    
    @Test
    public void getShortestPathLength() {
        try {
            assertEquals(myGraph.getShortestPathLength(1, 4), 3);
            assertEquals(myGraph.getShortestPathLength(1, 3), 2);
            assertEquals(myGraph.getShortestPathLength(5, 6), 2);
        } catch (Exception e) {
        
        }
        
    }
}