import com.oocourse.elevator3.PersonRequest;

public class Request {
    private int requestId = 0;
    private int requestFrom = 0;
    private int requestDest = 0;
    private int requestMid = 0;
    private int requestNow = 0;
    
    public Request(PersonRequest r) {
        requestDest = r.getToFloor();
        requestFrom = r.getFromFloor();
        requestMid = 0;
        requestNow = r.getFromFloor();
        requestId = r.getPersonId();
    }
    
    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }
    
    public void setRequestMid(int requestMid) {
        this.requestMid = requestMid;
    }
    
    public int getRequestDest() {
        return requestDest;
    }
    
    public int getRequestId() {
        return requestId;
    }
    
    public int getRequestMid() {
        return requestMid;
    }
    
    public int getRequestNow() {
        return requestNow;
    }
    
    public void setRequestNow(int requestNow) {
        this.requestNow = requestNow;
    }
    
    @Override
    public String toString() {
        return String.format("%d-FROM-%d-TO-%d",
                requestId, requestFrom, requestDest);
    }
}
