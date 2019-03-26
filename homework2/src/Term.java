import java.math.BigInteger;
import java.util.ArrayList;

class Term {
    //这个类主要描述的是项，主要涉及项的检查以及多项式的存储、导数
    private BigInteger coeff;//系数
    private BigInteger degree1;//x次数
    private BigInteger degree2;//cos次数
    private BigInteger degree3;//sin次数
    private int flag;//符号
    private int pos = 0;
    private String termstring;
    private ArrayList<Factor> termList = new ArrayList<>();
    
    public Term(String termin) {
        termstring = termin;
    }
    
    public Term(BigInteger c, BigInteger d1, BigInteger d2, BigInteger d3) {
        termInit();
        coeff = c;
        degree1 = d1;
        degree2 = d2;
        degree3 = d3;
    }
    
    private void termInit() {
        coeff = BigInteger.valueOf(1);
        degree1 = BigInteger.valueOf(0);
        degree2 = BigInteger.valueOf(0);
        degree3 = BigInteger.valueOf(0);
        flag = 1;
    }
    
    public int checkTerm() {
        termInit();
        int posi = 0;
        //先处理符号
        posi = flagCompute();
        String restTerm = termstring.substring(posi);
        do {
            Factor factor = new Factor(restTerm);
            posi = factor.checkFactor();
            termList.add(factor);
            pos = pos + posi;
            if (posi >= restTerm.length()) {
                break;
            }
            if (restTerm.charAt(posi) == '*') {
                posi++;
                pos++;
                restTerm = restTerm.substring(posi);
            } else {
                break;
            }
        } while (true);
        termMerge();
        return pos;
    }
    
    private int flagCompute() {
        int i = 0;
        if (termstring.charAt(i) == '+') {
            i++;
            pos++;
            if (termstring.charAt(i) == '+') {
                i++;
                pos++;
            } else if (termstring.charAt(i) == '-') {
                i++;
                pos++;
                flag = -1 * flag;
            }
        } else if (termstring.charAt(i) == '-') {
            i++;
            pos++;
            flag = flag * (-1);
            if (termstring.charAt(i) == '+') {
                i++;
                pos++;
            } else if (termstring.charAt(i) == '-') {
                i++;
                pos++;
                flag = -1 * flag;
            }
        }
        return i;
    }
    
    public void printTerm() {
        Factor item;
        int i = 0;
        for (i = 0; i < termList.size(); i++) {
            item = termList.get(i);
            if (item.getFacCata() == 1) {
                System.out.print(item.getCoeff());
            } else if (item.getFacCata() == 2) {
                String out = item.getCoeff().toString() + "*x^"
                        + item.getDegree().toString();
                System.out.print(out);
            } else if (item.getFacCata() == 3) {
                String out = item.getCoeff().toString() + "*cos(x)^"
                        + item.getDegree().toString();
                System.out.print(out);
            } else if (item.getFacCata() == 4) {
                String out = item.getCoeff().toString() + "*sin(x)^"
                        + item.getDegree().toString();
                System.out.print(out);
            }
            if (i < termList.size() - 1) {
                System.out.print('*');
            }
        }
    }
    
    public void termMerge() {
        int i = 0;
        Factor item;
        for (i = 0; i < termList.size(); i++) {
            item = termList.get(i);
            coeff = coeff.multiply(item.getCoeff());
            if (item.getFacCata() == 2) {
                degree1 = degree1.add(item.getDegree());
            }
            if (item.getFacCata() == 3) {
                degree2 = degree2.add(item.getDegree());
            }
            if (item.getFacCata() == 4) {
                degree3 = degree3.add(item.getDegree());
            }
        }
        coeff = coeff.multiply(BigInteger.valueOf(flag));
        termList.clear();
        if (!degree1.equals(BigInteger.valueOf(0))) {
            Factor facd1 = new Factor(2, degree1);
            termList.add(facd1);
        }
        if (!degree2.equals(BigInteger.valueOf(0))) {
            Factor facd2 = new Factor(3, degree2);
            termList.add(facd2);
        }
        if (!degree3.equals(BigInteger.valueOf(0))) {
            Factor facd3 = new Factor(4, degree3);
            termList.add(facd3);
        }
        
    }
    
    public BigInteger getCoeff() {
        return coeff;
    }
    
    public void setCoeff(BigInteger sc) {
        coeff = sc;
    }
    
    public BigInteger getDegree1() {
        return degree1;
    }
    
    public BigInteger getDegree2() {
        return degree2;
    }
    
    public BigInteger getDegree3() {
        return degree3;
    }
    
}