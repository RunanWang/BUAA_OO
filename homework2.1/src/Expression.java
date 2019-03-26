import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Expression {
    private String rawExpress;
    private ArrayList<Term> expList = new ArrayList<Term>();
    private ArrayList<Term> derList = new ArrayList<Term>();
    private int pre = 0;
    
    public Expression(String expressIn) {
        rawExpress = expressIn;
    }
    
    public int expressionDivide() {
        spaceProblem();
        String stand = "[+-]?[+-]?[+-]?(\\d|x|s|c)";
        Pattern p = Pattern.compile(stand);
        Matcher m = p.matcher(rawExpress);
        int matchSta = 0;
        int matchEnd = 0;
        int matchPos = 0;
        int matchLast = 0;
        while (m.find(matchPos)) {
            matchSta = m.start();
            matchEnd = m.end();
            optionCheck(matchSta, matchLast);
            if (matchSta != matchEnd || matchSta == 0) {
                Term term = new Term(rawExpress.substring(matchSta));
                matchPos = matchPos + term.checkTerm();
                expList.add(term);
                if (matchPos >= rawExpress.length()) {
                    break;
                }
                //term.printTerm();
            } else if (matchEnd == 0) {
                System.out.println("WRONG FORMAT!");
                System.out.println("no input!");
                System.exit(0);
            }
            matchLast = matchPos;
        }
        //System.out.println(expList);
        //expressionMerge();
        if (matchPos == 0) {
            System.out.println("WRONG FORMAT!");
            System.out.println("No input suits!");
            System.exit(0);
        }
        return 0;
    }
    
    public void printExpression() {
        Term item;
        int i = 0;
        for (i = 0; i < expList.size(); i++) {
            item = expList.get(i);
            item.printTerm();
            if (i != expList.size() - 1) {
                System.out.print("+");
            }
        }
        if (i == 0) {
            System.out.print("0");
        }
    }
    
    public void printDerExpression() {
        pre = 0;
        Term item;
        int i = 0;
        for (i = 0; i < derList.size(); i++) {
            pre = 0;
            item = derList.get(i);
            if (i != 0) {
                if (item.getCoeff().equals(BigInteger.ZERO)) {
                    continue;
                }
                if (item.getCoeff().compareTo(BigInteger.ZERO) == 1) {
                    System.out.print("+");
                } else {
                    System.out.print("-");
                }
            } else {
                if (item.getCoeff().compareTo(BigInteger.ZERO) == -1) {
                    System.out.print("-");
                }
            }
            if (!item.getCoeff().equals(BigInteger.ONE)) {
                printPoly(0, item);
            } else {
                printPoly(1, item);
            }
            if (!item.getDegree2().equals(BigInteger.ZERO)) {
                printCos(item);
            }
            if (!item.getDegree3().equals(BigInteger.ZERO)) {
                printSin(item);
            }
        }
        if (i == 0) {
            System.out.print("0");
        }
    }
    
    private void printPoly(int way, Term item) {
        if (way == 0) {
            if (!item.getDegree1().equals(BigInteger.ZERO)) {
                System.out.print(item.getCoeff().abs());
                if (!item.getDegree1().equals(BigInteger.ONE)) {
                    System.out.print("*x^");
                    System.out.print(item.getDegree1());
                    pre = 1;
                } else {
                    System.out.print("*x");
                    pre = 1;
                }
            } else {
                if (item.getDegree2().equals(BigInteger.ZERO)) {
                    if (item.getDegree3().equals(BigInteger.ZERO)) {
                        System.out.print(item.getCoeff().abs());
                    }
                }
            }
        } else {
            if (!item.getDegree1().equals(BigInteger.ZERO)) {
                if (item.getDegree2().equals(BigInteger.ZERO)) {
                    if (item.getDegree3().equals(BigInteger.ZERO)) {
                        System.out.print("1");
                    }
                }
                if (!item.getDegree1().equals(BigInteger.ONE)) {
                    System.out.print("x^");
                    pre = 1;
                    System.out.print(item.getDegree1());
                } else {
                    System.out.print("x");
                    pre = 1;
                }
            } else {
                if (item.getDegree2().equals(BigInteger.ZERO)) {
                    if (item.getDegree3().equals(BigInteger.ZERO)) {
                        System.out.print(item.getCoeff().abs());
                    }
                }
            }
        }
    }
    
    private void printCos(Term item) {
        if (item.getDegree2().equals(BigInteger.ONE)) {
            if (pre == 1) {
                System.out.print("*cos(x)");
            } else {
                if (!item.getCoeff().abs().equals(BigInteger.ONE)) {
                    System.out.print(item.getCoeff().abs());
                    System.out.print("*cos(x)");
                } else {
                    System.out.print("cos(x)");
                }
                pre = 1;
            }
        } else {
            if (pre == 1) {
                System.out.print("*cos(x)^");
                System.out.print(item.getDegree2());
            } else {
                if (!item.getCoeff().abs().equals(BigInteger.ONE)) {
                    System.out.print(item.getCoeff().abs());
                    System.out.print("*cos(x)^");
                } else {
                    System.out.print("cos(x)^");
                }
                System.out.print(item.getDegree2());
                pre = 1;
            }
        }
    }
    
    private void printSin(Term item) {
        if (item.getDegree3().equals(BigInteger.ONE)) {
            if (pre == 1) {
                System.out.print("*sin(x)");
            } else {
                if (!item.getCoeff().abs().equals(BigInteger.ONE)) {
                    System.out.print(item.getCoeff().abs());
                    System.out.print("*sin(x)");
                } else {
                    System.out.print("sin(x)");
                }
                pre = 1;
            }
        } else {
            if (pre == 1) {
                System.out.print("*sin(x)^");
                System.out.print(item.getDegree3());
            } else {
                if (!item.getCoeff().abs().equals(BigInteger.ONE)) {
                    System.out.print(item.getCoeff().abs());
                    System.out.print("*sin(x)^");
                } else {
                    System.out.print("sin(x)^");
                }
                System.out.print(item.getDegree3());
                pre = 1;
            }
        }
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
            System.out.println(rawExpress.substring(m.start(),m.end()));
            rawExpress = rawExpress.replaceAll("\\s*", "");
            System.exit(0);
        }
        rawExpress = rawExpress.replaceAll("\\s*", "");
    }
    
    public void expressionMerge() {
        Term itemi;
        Term itemj;
        int i = 0;
        int j = 0;
        for (i = 0; i < derList.size(); i++) {
            for (j = i + 1; j < derList.size(); j++) {
                itemi = derList.get(i);
                itemj = derList.get(j);
                if (itemi.getDegree1().equals(itemj.getDegree1())) {
                    if (itemi.getDegree2().equals(itemj.getDegree2())) {
                        if (itemi.getDegree3().equals(itemj.getDegree3())) {
                            itemi.setCoeff(itemi.getCoeff()
                                    .add(itemj.getCoeff()));
                            derList.remove(j);
                            i--;
                            break;
                        }
                    }
                }
            }
            
            
        }
    }
    
    private void optionCheck(int matchStart, int matchLast) {
        if (matchStart != matchLast) {
            System.out.println("WRONG FORMAT!");
            System.out.println("Wrong input!");
            System.exit(0);
        }
        if (matchStart != 0) {
            if (rawExpress.charAt(matchStart) != '+' &&
                    rawExpress.charAt(matchStart) != '-') {
                System.out.println("WRONG FORMAT!");
                System.out.println("missing op!");
                System.exit(0);
            }
        }
    }
    
    public void expDer() {
        Term item;
        Term temp;
        int i = 0;
        for (i = 0; i < expList.size(); i++) {
            item = expList.get(i);
            BigInteger c = item.getCoeff();
            BigInteger d1 = item.getDegree1();
            BigInteger d2 = item.getDegree2();
            BigInteger d3 = item.getDegree3();
            BigInteger co = c.multiply(d1);
            if (!co.equals(BigInteger.ZERO)) {
                BigInteger dd1 = d1.add(BigInteger.valueOf(-1));
                temp = new Term(co, dd1, d2, d3);
                derList.add(temp);
            }
            co = c.multiply(d2).multiply(BigInteger.valueOf(-1));
            if (!co.equals(BigInteger.ZERO)) {
                BigInteger dd2 = d2.add(BigInteger.valueOf(-1));
                BigInteger dd3 = d3.add(BigInteger.valueOf(1));
                temp = new Term(co, d1, dd2, dd3);
                derList.add(temp);
            }
            co = c.multiply(d3);
            if (!co.equals(BigInteger.ZERO)) {
                BigInteger dd2 = d2.add(BigInteger.valueOf(1));
                BigInteger dd3 = d3.add(BigInteger.valueOf(-1));
                temp = new Term(co, d1, dd2, dd3);
                derList.add(temp);
            }
        }
    }
}
