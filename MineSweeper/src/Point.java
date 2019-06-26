public class Point {
    protected boolean pMine = false;
    protected int surroundMine = 0;
    
    public Point(){
        pMine = false;
    }
    
    public Point(boolean p, int surroundMine){
        pMine = p;
        this.surroundMine = surroundMine;
    }
    
    public void setpStatus() {
        this.pMine = true;
    }
    
    public void setSurroundMine(int surroundMine) {
        this.surroundMine = surroundMine;
    }
    
    public boolean ispMine() {
        return pMine;
    }
    
    public int getSurroundMine() {
        return surroundMine;
    }
    
    @Override
    public String toString() {
        if(ispMine()){
            return "*";
        }else{
            if(surroundMine==0){
                return " ";
            }
            return String.valueOf(surroundMine);
        }
    }
}
