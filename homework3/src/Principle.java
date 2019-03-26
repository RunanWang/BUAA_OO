public class Principle extends Com {
    private Com lfac;
    private Com rfac;
    
    public Principle() {
        super.setType(2);
    }
    
    public Com getLfac() {
        return lfac;
    }
    
    public Com getRfac() {
        return rfac;
    }
    
    public void setLfac(Com lfac) {
        this.lfac = lfac;
    }
    
    public void setRfac(Com rfac) {
        this.rfac = rfac;
    }
    
    @Override
    public String toString() {
        if (this instanceof Add) {
            return ((Add) this).toString();
        } else if (this instanceof Mul) {
            return ((Mul) this).toString();
        } else {
            return ((Chain) this).toString();
        }
    }
}
