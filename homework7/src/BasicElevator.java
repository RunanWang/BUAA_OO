import java.util.ArrayList;

public class BasicElevator extends Thread {
    //eleFloor用于存储电梯当前所处楼层
    private int eleFloor = 1;
    private int destFloor = 1;
    //queue是请求队列
    private ReQueue queue;
    private ReQueue totalQueue;
    //rList用于存电梯内的人
    private ArrayList<Request> reList = new ArrayList<>();
    private Request mainR;
    private int mainRindex = 0;
    private char id = 'T';
    private long time = 400;
    private int capicity = 10;
    
    //初始化方法用于告诉电梯使用哪个调度器
    /*
    public BasicElevator(ReQueue q, ReQueue t) {
        queue = q;
        totalQueue = t;
    }
    */
    //一次执行代表完成一次主任务
    /* 电梯规则：按到来顺序，先到先服务ALS
     * 在有主需求的情况下移动过程中允许捎带
     * 在执行的开始判断是否还有输入的需求，没有则结束线程
     * 有，则首先试图获得主任务（按FIFO获取主任务）
     * 若没有主任务则会wait
     * 若wait时输入正常任务则会执行（执行try中的）
     * 否则说明是最后的终止唤醒了电梯，结束。
     * 然后处理主任务
     * */
    public void run() {
        while (true) {
            //获取主需求destFloor(去这里接主需求)
            try {
                destFloor = queue.getFirstRequest(0);
            } catch (Exception e) {
                break;
            }
            //运行到主需求所在楼层
            eleGotoGet(destFloor);
            //开门
            eleOpen();
            //接客
            mainR = eleGetIn(0);
            destFloor = mainR.getRequestMid();
            //关门
            eleClose();
            //运行送乘客,直到所有的需求都已经满足（即队列为空）
            while (reList.size() != 0) {
                //电梯运行，中途允许同向的需求进入
                eleGoto();
                //System.out.println(destFloor);
                //开门，走人，关门
                eleOpen();
                eleGetOut();
                eleClose();
            }
            //运行结束时需求队列空且输入队列结束则退出
            if (queue.isEnd() && queue.isEmpty()) {
                break;
            }
        }
        //System.out.println("Ele close");
    }
    
    /* 下面这个方法是电梯向dest方向移动一层楼
     * 其中在-1层与1层之间需要特殊处理
     * 如果dest就是当前楼层则不会进行移动（直接退出）
     * 爬一层楼，睡一定时间，打印arrive
     * */
    private void eleMoveOne(int dest) {
        if (dest > eleFloor) {
            //从下往上走，在-1层连加到1，其余++
            if (eleFloor != -1) {
                eleFloor++;
                waitTime(time);
                Output.printArrive(eleFloor, id);
            } else {
                eleFloor = 1;
                waitTime(time);
                Output.printArrive(eleFloor, id);
            }
        } else if (dest < eleFloor) {
            //从上往下走，在1层连减到-1
            if (eleFloor != 1) {
                eleFloor--;
                waitTime(time);
                Output.printArrive(eleFloor, id);
            } else {
                eleFloor = -1;
                waitTime(time);
                Output.printArrive(eleFloor, id);
            }
        }
    }
    
    /* 下面这个方法是电梯移动到主需求所在楼dest
     * 循环调用moveOne至达到dest
     * 中间不允许插入需求
     * */
    private void eleGotoGet(int dest) {
        while (dest != eleFloor) {
            eleMoveOne(dest);
        }
    }
    
    /* 下面这个方法是电梯移动到主需求目的楼dest
     * 循环调用moveOne至达到dest
     * 中间允许插入需求，每到一层调用eleGoPass
     * */
    private void eleGoto() {
        while (destFloor != eleFloor) {
            eleMoveOne(destFloor);
            if (capicity > 0) {
                eleGoPass();
            }
        }
    }
    
    /* 下面这个方法是序号为index的需求进入电梯
     * 输出in-id-楼层
     * 返回这个需求的request，并从需求列表中出列，进电梯列
     * */
    private Request eleGetIn(int index) {
        Request request;
        request = queue.outQueue(index);
        Output.printIn(request.getRequestId(), request.getRequestNow(), id);
        reList.add(request);
        capicity--;
        return request;
    }
    
    /* 下面这个方法是在电梯关门前检查需求列表看是否有捎带
     * 循环需求列表中所有需求，找是否有在本层且满足捎带条件的
     * eleGetIn
     * */
    private void eleByPass() {
        //index用于循环需求列表中的所有需求
        int index = 0;
        //用来描述电梯的行进方向
        int eleTowards = destFloor - eleFloor;
        int same = 1;
        //取出当前需求列表中第一项（若超出则返回NULL）
        Request r = queue.getRequest(index);
        //保证不超载
        if (capicity > 0) {
            //System.out.println(capicity);
            //
            while (r != null && capicity > 0) {
                //前一个因子是需求的方向，后一个是电梯的方向
                //相乘，若大于0则说明是同向
                same = (r.getRequestMid() - eleFloor) * eleTowards;
                //捎带条件：同向同楼层
                //System.out.println(same);
                if (same > 0 && r.getRequestNow() == eleFloor) {
                    eleGetIn(index);
                    //更新主需求
                    mainRenew();
                    index--;
                }
                index++;
                r = queue.getRequest(index);
            }
        }
    }
    
    /* 下面这个方法是在电梯抵达某楼层时检查需求列表看是否有捎带
     * 循环需求列表中所有需求，找是否有在本层且满足捎带条件的
     * eleGetIn
     * 和上一个方法相比需要在需求进入前开关门
     * */
    private void eleGoPass() {
        int index = 0;
        int eleTowards = destFloor - eleFloor;
        int same = 1;
        Request r = queue.getRequest(index);
        //保证不超载
        if (capicity > 0) {
            while (r != null && capicity > 0) {
                same = (r.getRequestMid() - eleFloor) * eleTowards;
                if (same > 0 && r.getRequestNow() == eleFloor) {
                    if (reList.size() == 0) {
                        break;
                    }
                    //同向，捎带
                    eleOpen();
                    eleGetIn(index);
                    //System.out.println("Go");
                    mainRenew();
                    eleClose();
                }
                index++;
                r = queue.getRequest(index);
            }
        }
    }
    
    /* 开门并等待并输出
     * */
    private void eleOpen() {
        Output.printOpen(eleFloor, id);
        waitTime(400);
    }
    
    /* 检查捎带然后关门
     * */
    private void eleClose() {
        eleByPass();
        Output.printClose(eleFloor, id);
    }
    
    /* 抵达dest楼层时让需求从电梯列中出列
     * 注意出列的可能有多个需求
     * */
    private void eleGetOut() {
        //i是电梯列的index
        int i = 0;
        Request r;
        //循环电梯列中所有需求，符合需求目的地等于当前楼层的出列
        while (i < reList.size()) {
            r = reList.get(i);
            if (r.getRequestMid() == eleFloor) {
                //打印状态
                r.setRequestNow(eleFloor);
                Output.printOut(r.getRequestId(), eleFloor, id);
                reList.remove(i);
                if (eleFloor != r.getRequestDest()) {
                    totalQueue.outRunQueue(r);
                    totalQueue.addQueue(r);
                    //System.out.println(r);
                } else {
                    totalQueue.outRunQueue(r);
                }
                capicity++;
                i--;
            }
            i++;
        }
        //如果电梯列中还有需求，对dest进行更新
        //先取第一个作为主需求
        if (reList.size() != 0) {
            mainR = reList.get(0);
            destFloor = mainR.getRequestMid();
        }
        //再将所有需求与这个需求作比较，得到合适的主需求
        mainRenew();
    }
    
    /* 更新主需求和dest
     * */
    private void mainRenew() {
        //确定运行方向
        int towards = destFloor - eleFloor;
        int i = 0;
        Request r;
        while (i < reList.size()) {
            r = reList.get(i);
            if (towards > 0) {
                //上行，取较小的目的地作为dest
                if (r.getRequestMid() < destFloor) {
                    mainR = r;
                    destFloor = r.getRequestMid();
                }
            } else {
                //下行，取较大的目的地作为dest
                if (r.getRequestMid() > destFloor) {
                    mainR = r;
                    destFloor = r.getRequestMid();
                }
            }
            i++;
        }
        //System.out.println(destFloor);
    }
    
    /* sleep一段时间sleeptime
     * */
    private void waitTime(long sleepTime) {
        try {
            sleep(sleepTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setQueue(ReQueue queue) {
        this.queue = queue;
    }
    
    public void setTotalQueue(ReQueue totalQueue) {
        this.totalQueue = totalQueue;
    }
    
    public void setId(char id) {
        this.id = id;
    }
    
    public void setTime(long time) {
        this.time = time;
    }
    
    public void setCapicity(int capicity) {
        this.capicity = capicity;
    }
}
