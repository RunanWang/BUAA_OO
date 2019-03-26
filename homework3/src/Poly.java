import java.math.BigInteger;

public class Poly extends Factor {
    public Poly() {
        setCoeff(BigInteger.ZERO);
        setDegree(BigInteger.ZERO);
    }
    
    public void crT(BigInteger co, BigInteger de) {
        setCoeff(co);
        setDegree(de);
    }
    
    @Override
    public Poly der() {
        Poly ans = new Poly();
        BigInteger c = this.getCoeff().multiply(this.getDegree());
        BigInteger d = this.getDegree().add(BigInteger.valueOf(-1));
        ans.crT(c, d);
        return ans;
    }
    
    @Override
    public String toString() {
        String out;
        String co = getCoeff().toString();
        String de = getDegree().toString();
        if (getDegree().equals(BigInteger.ONE)) {
            if (getCoeff().equals(BigInteger.ONE)) {
                out = "x";
            } else if (getCoeff().equals(BigInteger.ZERO)) {
                out = "0";
            } else {
                out = co + "*x";
            }
        } else if (getDegree().equals(BigInteger.ZERO)) {
            if (getCoeff().equals(BigInteger.ONE)) {
                out = "1";
            } else if (getCoeff().equals(BigInteger.ZERO)) {
                out = "0";
            } else {
                out = co;
            }
        } else {
            if (getCoeff().equals(BigInteger.ONE)) {
                out = "x^" + de;
            } else if (getCoeff().equals(BigInteger.ZERO)) {
                out = "0";
            } else {
                out = co + "*x^" + de;
            }
        }
        return out;
    }
}
