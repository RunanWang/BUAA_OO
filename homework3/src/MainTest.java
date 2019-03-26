import org.junit.Assert;

import static org.junit.Assert.*;

public class MainTest {
    
    @org.junit.Before
    public void setUp() throws Exception {
    }
    
    @org.junit.After
    public void tearDown() throws Exception {
    }
    
    
    @org.junit.Test
    public void test1() {
        String originin = "x";
        Assert.assertEquals(process(originin).toString(), "1");
    }
    
    @org.junit.Test
    public void test2() {
        String originin = "sin(x)";
        Assert.assertEquals(process(originin).toString(), "cos(x)");
    }
    
    public Com process(String originin){
        In in = new In(originin);
        Com total = in.getTotal();
        System.out.print(total);
        Com ans = total.der();
        System.out.print("="+ans+"\n");
        return ans;
    }
}