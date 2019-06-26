package

;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * MainClass Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>���� 31, 2019</pre>
 */
public class MainClassTest extends Thread {
    
    @Before
    public void before() throws Exception {
        Output.printInit();
        PersonQueue queue = new PersonQueue();
        Elevator ele = new Elevator(queue);
        Input in = new Input(queue);
        in.start();
        ele.start();
    }
    
    @After
    public void after() throws Exception {
    }
    
    /**
     * Method: main(String[] args)
     */
    @Test
    public void testMain() throws Exception {
        long startTime=System.currentTimeMillis();
        String data = "Hello, World!\r\n";
        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            Scanner scanner = new Scanner(System.in);
            System.out.println(scanner.nextLine());
        } finally {
            System.setIn(stdin);
        }
    }
    
    
} 
