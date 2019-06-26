package models.mstate;

public class MyUmlTrans {
    private MyUmlState src;
    private MyUmlState tar;
    
    public MyUmlTrans(MyUmlState s, MyUmlState t) {
        src = s;
        tar = t;
    }
    
    public MyUmlState getSrc() {
        return src;
    }
    
    public MyUmlState getTar() {
        return tar;
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean ans = ((MyUmlTrans) obj).getSrc().equals(this.getSrc()) &&
                ((MyUmlTrans) obj).getTar().equals(this.getTar());
        return ans;
    }
}

