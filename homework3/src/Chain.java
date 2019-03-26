public class Chain extends Principle {
    
    public void setChain(Com l, Com r) {
        setLfac(l);
        setRfac(r);
    }
    
    @Override
    public Mul der() {
        Chain p = new Chain();
        p.setChain(this.getLfac().der(), this.getRfac());
        Mul ans = new Mul();
        ans.setMul(p, this.getRfac().der());
        return ans;
    }
    
    @Override
    public String toString() {
        String out;
        if (getLfac() instanceof Triangle) {
            int ty = ((Triangle) getLfac()).getTriagleType();
            if (ty == 1) {
                out = ((Triangle) getLfac()).getCoeff() + "*sin(("
                        + getRfac().toString()
                        + "))^" + ((Triangle) getLfac()).getDegree();
            } else if (ty == 2) {
                out = ((Triangle) getLfac()).getCoeff() + "*cos(("
                        + getRfac().toString()
                        + "))^" + ((Triangle) getLfac()).getDegree();
            } else {
                out = "Not a triangle type!";
            }
        } else {
            Com l = ((Mul) getLfac()).getLfac();
            Com r = ((Mul) getLfac()).getRfac();
            int ty = ((Triangle) l).getTriagleType();
            if (ty == 1) {
                out = ((Triangle) l).getCoeff() + "*sin(("
                        + getRfac().toString() + "))^"
                        + ((Triangle) l).getDegree();
            } else if (ty == 2) {
                out = ((Triangle) l).getCoeff() + "*cos(("
                        + getRfac().toString() + "))^"
                        + ((Triangle) l).getDegree();
            } else {
                out = "Not a triangle type!";
            }
            out = out + "*";
            ty = ((Triangle) r).getTriagleType();
            if (ty == 1) {
                out = out + ((Triangle) r).getCoeff()
                        + "*sin((" + getRfac().toString() + "))^"
                        + ((Triangle) r).getDegree();
            } else if (ty == 2) {
                out = out + ((Triangle) r).getCoeff()
                        + "*cos((" + getRfac().toString() + "))^"
                        + ((Triangle) r).getDegree();
            } else {
                out = "Not a triangle type!";
            }
        }
        return out;
    }
}
