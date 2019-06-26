public class GamePoint extends Point {
    protected boolean coverdFlag = true;
    
    public GamePoint(){
        super();
    }
    
    public GamePoint(Point p){
        super(p.pMine,p.surroundMine);
    }
    
    public boolean isCovered(){
        return coverdFlag;
    }
    
    public void uncovered() {
        this.coverdFlag = false;
    }
    
    @Override
    public String toString() {
        if (!coverdFlag) {
            return super.toString();
        } else {
            return " ";
        }
    }
}
