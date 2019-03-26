import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class In {
    private String rawExpress;
    private int posi;
    private Com total;
    
    public In(String inin) {
        rawExpress = inin;
    }
    
    public Com getTotal() {
        spaceProblem();
        total = expression(1);
        if (posi < rawExpress.length()) {
            System.out.println("WRONG FORMAT!");
            System.out.println("Wrong ) exists!");
            System.exit(0);
        }
        return total;
    }
    
    private Com expression(int f) {
        int flag = 1;
        Add ans = new Add();
        Com temp1;
        Com temp2;
        flag = computeFlag() * f;
        temp1 = term(flag);
        if (posi >= rawExpress.length()) {
            return temp1;
        }
        do {
            if (rawExpress.charAt(posi) == '+') {
                flag = 1;
                posi++;
            } else if (rawExpress.charAt(posi) == '-') {
                flag = -1;
                posi++;
            } else if (rawExpress.charAt(posi) == ')') {
                break;
            } else {
                System.out.println("WRONG FORMAT!");
                System.out.println("No op between terms!");
                System.exit(0);
            }
            temp2 = term(flag);
            ans.setLfac(temp1);
            ans.setRfac(temp2);
            temp1 = ans;
            ans = new Add();
            
        } while (posi < rawExpress.length());
        return temp1;
    }
    
    private Com term(int eflag) {
        Mul ans = new Mul();
        Com temp1;
        Com temp2;
        int flag = computeFlag() * eflag;
        temp1 = factor(flag);
        if (posi >= rawExpress.length()) {
            return temp1;
        }
        if (rawExpress.charAt(posi) != '*') {
            return temp1;
        }
        while (rawExpress.charAt(posi) == '*') {
            posi++;
            if (posi >= rawExpress.length()) {
                System.out.println("WRONG FORMAT!");
                System.out.println("No term after *!");
                System.exit(0);
            }
            temp2 = factor(1);
            ans.setMul(temp1, temp2);
            temp1 = ans;
            ans = new Mul();
            if (posi >= rawExpress.length()) {
                break;
            }
        }
        return temp1;
    }
    
    private Com factor(int tflag) {
        Com ans = new Com();
        if (rawExpress.charAt(posi) == '(') {
            posi++;
            ans = expression(tflag);
            if (rawExpress.charAt(posi) == ')') {
                posi++;
            } else {
                System.out.println("WRONG FORMAT!");
                System.out.println("No ) in expression fac!");
                System.exit(0);
            }
        } else if (rawExpress.charAt(posi) == '+'
                || rawExpress.charAt(posi) == '-'
                || rawExpress.charAt(posi) >= '0'
                && rawExpress.charAt(posi) <= '9') {
            BigInteger c = computeNum(tflag);
            Const cc = new Const();
            cc.setCoeff(c);
            ans = cc;
        } else if (rawExpress.charAt(posi) == 'x') {
            ans = computePoly(tflag);
        } else if (rawExpress.charAt(posi) == 's'
                || rawExpress.charAt(posi) == 'c') {
            ans = computeT(tflag);
        } else {
            System.out.println("WRONG FORMAT!");
            System.out.println(rawExpress.substring(posi));
            System.out.println("Not a factor!");
            System.exit(0);
        }
        return ans;
    }
    
    private int computeFlag() {
        int flag = 1;
        printOutError();
        if (rawExpress.charAt(posi) == '+') {
            posi++;
            flag = 1;
        } else if (rawExpress.charAt(posi) == '-') {
            flag = -1;
            posi++;
        }
        return flag;
    }
    
    private Com computePoly(int flag) {
        Poly ans = new Poly();
        if (rawExpress.charAt(posi) == 'x') {
            posi++;
        }
        if (posi >= rawExpress.length()) {
            ans.setDegree(BigInteger.ONE);
            ans.setCoeff(BigInteger.valueOf(flag));
            return ans;
        }
        if (rawExpress.charAt(posi) == '^') {
            posi++;
            ans.setDegree(computeNum(1));
            if (ans.getDegree().abs().
                    compareTo(BigInteger.valueOf(10000)) == 1) {
                System.out.println("WRONG FORMAT!");
                System.out.println("Too big!");
                System.exit(0);
            }
            ans.setCoeff(BigInteger.valueOf(flag));
        } else {
            ans.setDegree(BigInteger.ONE);
            ans.setCoeff(BigInteger.valueOf(flag));
        }
        return ans;
    }
    
    private Com computeT(int flag) {
        Com ans = new Com();
        int start = 0;
        int end = 0;
        String tri = "cos[(]x[)]|sin[(]x[)]";
        Pattern p = Pattern.compile(tri);
        String subString = rawExpress.substring(posi);
        Matcher m = p.matcher(subString);
        if (!m.find()) {
            ans = computeChain(flag);
        } else {
            start = m.start();
            end = m.end();
            if (start != 0 || end == 0) {
                ans = computeChain(flag);
            } else {
                if (end != start + 6) {
                    System.out.println("WRONG FORMAT!");
                    System.out.println("No sin in sin or no cos!");
                    System.exit(0);
                }
                ans = computeTri(flag, end);
            }
        }
        return ans;
    }
    
    private Triangle computeTri(int flag, int end) {
        Triangle ans = new Triangle();
        if (rawExpress.charAt(posi) == 's') {
            ans.setTriagleType(1);
        } else {
            ans.setTriagleType(2);
        }
        ans.setCoeff(BigInteger.valueOf(flag));
        posi = posi + end;
        if (posi >= rawExpress.length()) {
            ans.setDegree(BigInteger.ONE);
            return ans;
        }
        if (rawExpress.charAt(posi) == '^') {
            posi++;
            BigInteger d = computeNum(1);
            ans.setDegree(d);
            if (d.abs().compareTo(BigInteger.valueOf(10000)) == 1) {
                System.out.println("WRONG FORMAT!");
                System.out.println("Too big!");
                System.exit(0);
            }
        } else {
            ans.setDegree(BigInteger.ONE);
        }
        return ans;
    }
    
    private Chain computeChain(int flag) {
        Chain ans = new Chain();
        Triangle l = new Triangle();
        String tri = "cos[(]|sin[(]";
        Pattern p = Pattern.compile(tri);
        String subString = rawExpress.substring(posi);
        Matcher m = p.matcher(subString);
        if (!m.find()) {
            printError();
        }
        ;
        if (m.start() != 0 || m.end() == 0) {
            System.out.println("WRONG FORMAT!");
            System.out.println("Chain:No sin in sin or no cos!");
            System.exit(0);
        }
        if (rawExpress.charAt(posi) == 's') {
            l.setTriagleType(1);
        } else {
            l.setTriagleType(2);
        }
        posi = posi + 4;
        if (rawExpress.charAt(posi) >= '0' && rawExpress.charAt(posi) <= '9') {
            //printError();
        }
        Com r;
        r = factor(1);
        if (posi >= rawExpress.length()) {
            printError();
        }
        if (rawExpress.charAt(posi) == ')') {
            posi++;
            if (posi >= rawExpress.length()) {
                l.setCoeff(BigInteger.valueOf(flag));
                l.setDegree(BigInteger.ONE);
                ans.setChain(l, r);
                return ans;
            }
            if (rawExpress.charAt(posi) == '^') {
                posi++;
                l.setCoeff(BigInteger.valueOf(flag));
                BigInteger d = computeNum(1);
                if (d.abs().compareTo(BigInteger.valueOf(10000)) == 1) {
                    System.out.println("WRONG FORMAT!");
                    System.out.println("Too big!");
                    System.exit(0);
                }
                l.setDegree(d);
            } else {
                l.setCoeff(BigInteger.valueOf(flag));
                l.setDegree(BigInteger.ONE);
            }
            ans.setChain(l, r);
        } else {
            printError();
        }
        return ans;
    }
    
    private void printError() {
        System.out.println("WRONG FORMAT!");
        System.out.println("Chain:No ) suits!");
        System.out.println(rawExpress.substring(posi));
        System.exit(0);
    }
    
    private void printOutError() {
        if (posi >= rawExpress.length()) {
            System.out.println("WRONG FORMAT!");
            System.out.println("out of range!");
            System.exit(0);
        }
    }
    
    private BigInteger computeNum(int flag) {
        int st = 0;
        int end = 0;
        String num = "[+-]?\\d+";
        Pattern p = Pattern.compile(num);
        String subString = rawExpress.substring(posi);
        Matcher m = p.matcher(subString);
        if (m.find()) {
            st = m.start();
            end = m.end();
        } else {
            System.out.println("WRONG FORMAT!");
            System.out.println("flags!");
            System.out.println(subString);
            System.exit(0);
        }
        
        if (st != 0 || end == 0) {
            System.out.println("WRONG FORMAT!");
            System.out.println("more flags!");
            System.exit(0);
        }
        
        String temp = subString.substring(st, end);
        BigInteger c = new BigInteger(temp);
        c = c.multiply(BigInteger.valueOf(flag));
        posi = posi + end;
        return c;
    }
    
    private void spaceProblem() {
        if (rawExpress.contentEquals("")) {
            System.out.println("WRONG FORMAT!");
            System.out.println("No input!");
            System.exit(0);
        }
        String form1 = "([+-]\\s*[+-]\\s*[+-]\\s\\s*\\d+)" +
                "|([+-]\\s*[+-]\\s*[+-]\\s*[xcs])" +
                "|(\\d+\\s+\\d+)" +
                "|[\\^]\\s*[+-]\\s\\s*\\d+|[*]\\s*[+-]\\s\\s*\\d+" +
                "|s\\s\\s*i\\s*n|s\\s*i\\s\\s*n" +
                "|c\\s\\s*o\\s*s|c\\s*o\\s\\s*s|" +
                "[\\v]|[\\r]|[\\f]|[*|^][+-][xsc]";
        Pattern p = Pattern.compile(form1);
        Matcher m = p.matcher(rawExpress);
        if (m.find()) {
            System.out.println("WRONG FORMAT!");
            System.out.println("Space Error!");
            System.out.println(rawExpress.substring(m.start(), m.end()));
            rawExpress = rawExpress.replaceAll("\\s*", "");
            System.exit(0);
        }
        rawExpress = rawExpress.replaceAll("\\s*", "");
    }
}
