import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Factor {
    private String facstring;
    private int stringPos = 0;
    private int facCata = 0;
    private BigInteger coeff;
    private BigInteger degree = BigInteger.valueOf(1);
    private ArrayList<Factor> facDerRes = new ArrayList<>();
    
    public Factor(String termin) {
        facstring = termin;
    }
    
    public Factor(int facC, BigInteger deg) {
        coeff = BigInteger.ONE;
        degree = deg;
        facCata = facC;
    }
    
    public Factor(int facC, BigInteger deg, BigInteger coe) {
        coeff = coe;
        degree = deg;
        facCata = facC;
    }
    
    public int checkFactor() {
        int flag = flagCompute();
        char firstchar = facstring.charAt(stringPos);
        if (firstchar >= '0' && firstchar <= '9') {
            facCata = 1;
            coeff = numCompute(flag);
            degree = BigInteger.valueOf(0);
        } else if (firstchar == 'x') {
            facCata = 2;
            stringPos++;
            coeff = BigInteger.valueOf(flag);
            if (stringPos < facstring.length()) {
                if (facstring.charAt(stringPos) == '^') {
                    stringPos++;
                    flag = flagCompute();
                    degree = numCompute(flag);
                }
            } else {
                degree = BigInteger.valueOf(1);
            }
        } else if (firstchar == 'c' || firstchar == 's') {
            facCata = triRec(firstchar);
            stringPos = stringPos + 6;
            coeff = BigInteger.valueOf(flag);
            if (stringPos < facstring.length()) {
                if (facstring.charAt(stringPos) == '^') {
                    stringPos++;
                    flag = flagCompute();
                    degree = numCompute(flag);
                } else {
                    degree = BigInteger.valueOf(1);
                }
            } else {
                degree = BigInteger.valueOf(1);
            }
        } else {
            printError(2);
        }
        return stringPos;
    }
    
    private void printError(int n) {
        if (n == 1) {
            System.out.println("WRONG FORMAT!");
            System.out.println("wrong triangle function!");
            System.exit(0);
        }
        if (n == 2) {
            System.out.println("WRONG FORMAT!");
            System.out.println("wrong factor!");
            System.exit(0);
        }
    }
    
    private int flagCompute() {
        int flag = 1;
        if (facstring.charAt(stringPos) == '+') {
            stringPos++;
        } else if (facstring.charAt(stringPos) == '-') {
            stringPos++;
            flag = flag * (-1);
        }
        return flag;
    }
    
    private BigInteger numCompute(int flag) {
        BigInteger ret;
        String subString = facstring.substring(stringPos);
        String num = "\\d*";
        Pattern p = Pattern.compile(num);
        Matcher m = p.matcher(subString);
        m.find();
        String temp = subString.substring(m.start(), m.end());
        stringPos = stringPos + m.end() - m.start();
        ret = new BigInteger(temp);
        ret = ret.multiply(BigInteger.valueOf(flag));
        return ret;
    }
    
    private int triRec(char firstchar) {
        if (firstchar == 'c') {
            String temp = facstring.substring(stringPos, stringPos + 6);
            if (temp.contentEquals("cos(x)")) {
                return 3;
            } else {
                printError(1);
            }
        } else {
            String temp = facstring.substring(stringPos, stringPos + 6);
            if (temp.contentEquals("sin(x)")) {
                return 4;
            } else {
                printError(1);
            }
        }
        return -1;
    }
    
    public BigInteger getCoeff() {
        return coeff;
    }
    
    public BigInteger getDegree() {
        return degree;
    }
    
    public int getFacCata() {
        return facCata;
    }
    
    public ArrayList<Factor> facDer() {
        if (facCata == 1) {
            Factor temp = new Factor(1, BigInteger.ZERO, BigInteger.ZERO);
            facDerRes.add(temp);
        } else if (facCata == 2) {
            BigInteger coe = coeff.multiply(degree);
            BigInteger deg = degree.add(BigInteger.valueOf(-1));
            Factor temp = new Factor(2, deg, coe);
            facDerRes.add(temp);
        } else if (facCata == 3) {//cos
            BigInteger coe = coeff.multiply(degree);
            coe = coe.multiply(BigInteger.valueOf(-1));
            BigInteger deg = degree.add(BigInteger.valueOf(-1));
            Factor temp = new Factor(3, deg, coe);
            facDerRes.add(temp);
            coe = BigInteger.ONE;
            deg = BigInteger.ONE;
            temp = new Factor(4, deg, coe);
            facDerRes.add(temp);
        } else if (facCata == 4) {//sin
            BigInteger coe = coeff.multiply(degree);
            BigInteger deg = degree.add(BigInteger.valueOf(-1));
            Factor temp = new Factor(4, deg, coe);
            facDerRes.add(temp);
            coe = BigInteger.ONE;
            deg = BigInteger.ONE;
            temp = new Factor(3, deg, coe);
            facDerRes.add(temp);
        }
        return facDerRes;
    }
    
    public void setDegree(BigInteger d) {
        degree = d;
    }
}
