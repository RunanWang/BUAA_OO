import java.math.BigInteger;

public class Const extends Factor {
    public Const() {
        setCoeff(BigInteger.ZERO);
        setDegree(BigInteger.ZERO);
    }
    
    @Override
    public Const der() {
        Const ans = new Const();
        return ans;
    }
    
    @Override
    public String toString() {
        return getCoeff().toString();
    }
}
