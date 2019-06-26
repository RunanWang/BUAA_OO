import com.oocourse.elevator3.ElevatorInput;
import com.oocourse.elevator3.PersonRequest;

public class Input extends Thread {
    //输入接口
    private ElevatorInput elevatorInput = new ElevatorInput(System.in);
    //请求队列（用于向其中加项）
    private ReQueue totalQueue;
    
    //初始化方法：告诉input向哪个队列加入输入
    public Input(ReQueue q) {
        totalQueue = q;
    }
    
    public void run() {
        //该线程的任务就是不断的getInput
        PersonRequest request = getInput();
        //如果第一个输入就是Ctrl-D则直接结束
        while (request != null) {
            //把这个输入加入到队列中
            Request re = new Request(request);
            totalQueue.addQueue(re);
            //然后再试图读下一个
            request = getInput();
        }
        //直到读到结尾然后告诉队列所有输入读完了
        totalQueue.setEnd();
        //然后关闭input并退出线程
        try {
            elevatorInput.close();
        } catch (Exception e) {
            //System.out.println(e);
            e.printStackTrace();
        }
        //System.out.println("IN close");
    }
    
    private PersonRequest getInput() {
        PersonRequest request = elevatorInput.nextPersonRequest();
        return request;
    }
    
}
