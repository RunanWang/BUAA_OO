public class Options {
    private int optionType;
    private int x;
    private int y;
    
    public Options(int optionType, int x, int y){
        this.optionType = optionType;
        this.x = x;
        this.y = y;
    }
    
    @Override
    public String toString() {
        String out;
        if(optionType==1){
            out = "click";
        }else{
            out = "guess";
        }
        out = out + " x=" + String.valueOf(x) + " y=" +String.valueOf(y);
        return out;
    }
}
