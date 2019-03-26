import java.math.BigInteger;

public class Triangle extends Factor {
    //1为sin,2为cos
    private int triagleType = 0;
    
    public Triangle() {
        setCoeff(BigInteger.ZERO);
        setDegree(BigInteger.ZERO);
    }
    
    public void crT(BigInteger co, BigInteger de, int typ) {
        setCoeff(co);
        setDegree(de);
        setTriagleType(typ);
    }
    
    public void setTriagleType(int triagleType) {
        this.triagleType = triagleType;
    }
    
    public int getTriagleType() {
        return triagleType;
    }
    
    @Override
    public Com der() {
        Triangle temp1 = new Triangle();
        Triangle temp2 = new Triangle();
        Mul ans = new Mul();
        BigInteger c;
        BigInteger d;
        if (this.getDegree().equals(BigInteger.ONE)) {
            if (this.getTriagleType() == 1) {
                temp1.crT(this.getCoeff(), BigInteger.ONE, 2);
            } else {
                c = this.getCoeff().multiply(BigInteger.valueOf(-1));
                temp1.crT(c, BigInteger.ONE, 1);
            }
            return temp1;
        } else {
            if (this.getTriagleType() == 1) {
                c = this.getCoeff().multiply(this.getDegree());
                d = this.getDegree().add(BigInteger.valueOf(-1));
                temp1.crT(c, d, 1);
                temp2.crT(BigInteger.ONE, BigInteger.ONE, 2);
                ans.setMul(temp1, temp2);
            } else {
                c = this.getCoeff().multiply(this.getDegree());
                c = c.multiply(BigInteger.valueOf(-1));
                d = this.getDegree().add(BigInteger.valueOf(-1));
                temp1.crT(c, d, 2);
                temp2.crT(BigInteger.ONE, BigInteger.ONE, 1);
                ans.setMul(temp1, temp2);
            }
        }
        return ans;
    }
    
    @Override
    public String toString() {
        String out;
        String co = getCoeff().toString();
        String de = getDegree().toString();
        if (getDegree().equals(BigInteger.ONE)) {
            if (getCoeff().equals(BigInteger.ONE)) {
                out = printTri();
            } else if (getCoeff().equals(BigInteger.ZERO)) {
                out = "0";
            } else {
                out = co + "*" + printTri();
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
                out = printTri() + "^" + de;
            } else if (getCoeff().equals(BigInteger.ZERO)) {
                out = "0";
            } else {
                out = co + "*" + printTri() + "^" + de;
            }
        }
        return out;
    }
    
    private String printTri() {
        if (triagleType == 1) {
            return "sin(x)";
        } else if (triagleType == 2) {
            return "cos(x)";
        } else {
            return "not a triangle type!";
        }
    }
}
