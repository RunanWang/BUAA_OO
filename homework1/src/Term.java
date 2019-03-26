import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Term {
    //这个类主要描述的是项，主要涉及项的检查以及多项式的存储、导数
    private BigInteger coeff;//系数
    private BigInteger degree;//次数
    private int flag;//符号
    private String termstring;
    
    public Term(String termin) {
        termstring = termin;
    }
    
    public int checkTerm() {
        coeff = BigInteger.valueOf(1);
        degree = BigInteger.valueOf(0);
        flag = 1;
        int i = 0;
        int matchStart = 0;
        int matchEnd = 0;
        
        //先处理符号
        i = flagCompute();
        
        //下面处理系数
        String restTerm = termstring.substring(i);
        i = 0;
        if (restTerm.charAt(i) >= '0' && restTerm.charAt(i) <= '9') {
            String num = "\\d*";
            Pattern p = Pattern.compile(num);
            Matcher m = p.matcher(restTerm);
            m.find();
            matchStart = m.start();
            matchEnd = m.end();
            i = matchEnd;
            if (matchStart != matchEnd) {
                String temp = restTerm.substring(matchStart, matchEnd);
                coeff = new BigInteger(temp);
            }
            if (flag == -1) {
                coeff = coeff.multiply(BigInteger.valueOf(-1));
            }
            if (i < restTerm.length()) {
                if (restTerm.charAt(i) == '*') {
                    i++;
                    if (restTerm.charAt(i) == 'x') {
                        degree = BigInteger.valueOf(1);
                        i++;
                    } else {
                        printError();
                        return -1;
                    }
                } else {
                    printError();
                    return -1;
                }
            } else {
                return 0;
            }
        } else if (restTerm.charAt(i) != 'x') {
            printError();
            return -1;
        } else {
            i++;
            if (flag == -1) {
                coeff = coeff.multiply(BigInteger.valueOf(-1));
            }
            degree = BigInteger.valueOf(1);
        }
        
        //下面处理次数
        return (degreeCompute(i, restTerm));
    }
    
    private int flagCompute() {
        int i = 0;
        if (termstring.charAt(i) == '+') {
            i++;
            if (termstring.charAt(i) == '+') {
                flag = 1;
                i++;
            } else if (termstring.charAt(i) == '-') {
                i++;
                flag = flag * (-1);
            }
        } else if (termstring.charAt(i) == '-') {
            i++;
            flag = flag * (-1);
            if (termstring.charAt(i) == '+') {
                i++;
            } else if (termstring.charAt(i) == '-') {
                i++;
                flag = flag * (-1);
            }
        }
        return i;
    }
    
    private int degreeCompute(int ini, String restTerm) {
        int i = ini;
        if (i == restTerm.length()) {
            return 0;
        } else {
            if (restTerm.charAt(i) != '^') {
                printError();
                return -1;
            }
            i++;
        }
        String restrestTerm = restTerm.substring(i);
        if (restrestTerm.length() == 0) {
            System.out.println("WRONG FORMAT!");
            System.out.println("missing degree!");
            return -1;
        }
        degree = new BigInteger(restrestTerm);
        return 0;
    }
    
    private void printError() {
        System.out.println("WRONG FORMAT!");
        System.out.println("missing of term");
    }
    
    public void printTerm() {
        System.out.println(coeff + "x^" + degree);
    }
    
    public BigInteger getCoeff() {
        return coeff;
    }
    
    public BigInteger getDegree() {
        return degree;
    }
    
    public void setCoeff(BigInteger setv) {
        coeff = setv;
    }
    
    public void termder() {
        BigInteger c = new BigInteger("-1");
        coeff = coeff.multiply(degree);
        degree = degree.add(c);
    }
}