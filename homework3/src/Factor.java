import java.math.BigInteger;

public class Factor extends Com {
    private BigInteger coeff;
    private BigInteger degree;
    
    public Factor() {
        super.setType(1);
    }
    
    public BigInteger getCoeff() {
        return coeff;
    }
    
    public BigInteger getDegree() {
        return degree;
    }
    
    public void setCoeff(BigInteger coeff) {
        this.coeff = coeff;
    }
    
    public void setDegree(BigInteger degree) {
        this.degree = degree;
    }
    
    @Override
    public String toString() {
        if (this instanceof Poly) {
            return ((Poly) this).toString();
        } else if (this instanceof Triangle) {
            return ((Triangle) this).toString();
        } else {
            return ((Const) this).toString();
        }
    }
}
