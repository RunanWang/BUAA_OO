public class Mul extends Principle {
    public void setMul(Com l, Com r) {
        setLfac(l);
        setRfac(r);
    }
    
    public Add der() {
        Add ans = new Add();
        Mul mul1 = new Mul();
        Mul mul2 = new Mul();
        mul1.setMul(this.getLfac().der(), this.getRfac());
        mul2.setMul(this.getLfac(), this.getRfac().der());
        ans.setAdd(mul1, mul2);
        return ans;
    }
    
    @Override
    public String toString() {
        String out;
        if (getLfac().toString().contentEquals("0")) {
            out = "0";
        } else if (getLfac().toString().contentEquals("1")) {
            out = getRfac().toString();
        } else if (getRfac().toString().contentEquals("1")) {
            out = getLfac().toString();
        } else if (getRfac().toString().contentEquals("0")) {
            out = "0";
        } else {
            out = "(" + getLfac().toString() +
                    ")*(" + getRfac().toString() + ")";
        }
        
        return out;
    }
    
}
