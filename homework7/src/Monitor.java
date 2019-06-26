public class Monitor extends Thread {
    
    private ReQueue totalQueue;
    private Request request;
    private ReQueue queueA;
    private ReQueue queueB;
    private ReQueue queueC;
    
    public Monitor(ReQueue t, ReQueue a, ReQueue b, ReQueue c) {
        totalQueue = t;
        queueA = a;
        queueB = b;
        queueC = c;
    }
    
    public void run() {
        while (true) {
            //获取主需求destFloor(去这里接主需求)
            try {
                request = totalQueue.getFirstRe();
            } catch (Exception e) {
                queueA.setEnd();
                queueB.setEnd();
                queueC.setEnd();
                break;
            }
            setMid();
            totalQueue.outTotalQueue(0);
        }
        //System.out.println("Monitor Close");
    }
    
    private void setMid() {
        int nowFloor = request.getRequestNow();
        switch (nowFloor) {
            case -3:
                jobN3();
                break;
            case -2:
                jobN12();
                break;
            case -1:
                jobN12();
                break;
            case 1:
                job1();
                break;
            case 3:
                job3();
                break;
            case 15:
                job15();
                break;
            default:
                if (nowFloor >= 16) {
                    job16();
                    break;
                } else if (nowFloor % 2 == 0) {
                    jobEven();
                    break;
                } else {
                    jobOdd();
                    break;
                }
        }
    }
    
    private void jobN3() {
        int destFloor = request.getRequestDest();
        if (destFloor <= 1 || destFloor >= 15) {
            request.setRequestMid(destFloor);
            queueA.addQueue(request);
        } else {
            request.setRequestMid(1);
            queueA.addQueue(request);
        }
    }
    
    private void jobN12() {
        int destFloor = request.getRequestDest();
        if (destFloor == -3 || destFloor > 15) {
            request.setRequestMid(destFloor);
            queueA.addQueue(request);
        } else if (destFloor == 3) {
            request.setRequestMid(1);
            queueB.addQueue(request);
        } else {
            request.setRequestMid(destFloor);
            queueB.addQueue(request);
        }
    }
    
    private void job1() {
        int destFloor = request.getRequestDest();
        if (destFloor == -3 || destFloor > 15) {
            request.setRequestMid(destFloor);
            queueA.addQueue(request);
        } else if (destFloor == 3 || destFloor % 2 == 1) {
            request.setRequestMid(destFloor);
            queueC.addQueue(request);
        } else {
            request.setRequestMid(destFloor);
            queueB.addQueue(request);
        }
    }
    
    private void job3() {
        int destFloor = request.getRequestDest();
        if (destFloor > 0 && destFloor % 2 == 1) {
            request.setRequestMid(destFloor);
            queueC.addQueue(request);
        } else if (destFloor < 3) {
            request.setRequestMid(1);
            queueC.addQueue(request);
        } else {
            request.setRequestMid(5);
            queueC.addQueue(request);
        }
    }
    
    private void job15() {
        int destFloor = request.getRequestDest();
        if (destFloor == -3) {
            request.setRequestMid(destFloor);
            queueA.addQueue(request);
        } else if (destFloor > 15) {
            request.setRequestMid(destFloor);
            queueA.addQueue(request);
        } else if (destFloor > 0 && destFloor % 2 == 1) {
            request.setRequestMid(destFloor);
            queueC.addQueue(request);
        } else {
            request.setRequestMid(destFloor);
            queueB.addQueue(request);
        }
    }
    
    private void job16() {
        int destFloor = request.getRequestDest();
        if (destFloor <= 1 || destFloor >= 15) {
            request.setRequestMid(destFloor);
            queueA.addQueue(request);
        } else {
            request.setRequestMid(15);
            queueA.addQueue(request);
        }
    }
    
    private void jobEven() {
        //偶数
        int destFloor = request.getRequestDest();
        if (destFloor == -3) {
            request.setRequestMid(1);
            queueB.addQueue(request);
        } else if (destFloor > 15) {
            request.setRequestMid(15);
            queueB.addQueue(request);
        } else if (destFloor == 3) {
            if (request.getRequestNow() == 2) {
                request.setRequestMid(1);
                queueB.addQueue(request);
            } else {
                request.setRequestMid(destFloor - 1);
                queueB.addQueue(request);
            }
        } else {
            request.setRequestMid(destFloor);
            queueB.addQueue(request);
        }
        
    }
    
    private void jobOdd() {
        //奇数
        int destFloor = request.getRequestDest();
        if (destFloor == -3) {
            request.setRequestMid(1);
            queueC.addQueue(request);
        } else if (destFloor > 15) {
            request.setRequestMid(15);
            queueC.addQueue(request);
        } else if (destFloor > 0 && destFloor % 2 == 1) {
            request.setRequestMid(destFloor);
            queueC.addQueue(request);
        } else {
            request.setRequestMid(destFloor);
            queueB.addQueue(request);
        }
    }
}
