public class Add extends Principle {
    
    public void setAdd(Com l, Com r) {
        setLfac(l);
        setRfac(r);
    }
    
   
    public Add der() {
        Add ans = new Add();
        ans.setLfac(this.getLfac().der());
        ans.setRfac(this.getRfac().der());
        return ans;
    }
    
    @Override
    public String toString() {
        String out;
        if (getLfac().toString().contentEquals("0")) {
            out = getRfac().toString();
        } else if (getRfac().toString().contentEquals("0")) {
            out = getLfac().toString();
        } else {
            out = getLfac().toString() + "+" + getRfac().toString();
        }
        return out;
    }
}
