import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Expression {
    private String rawExpress;
    private ArrayList<Term> expList = new ArrayList<Term>();
    
    public Expression(String expressIn) {
        rawExpress = expressIn;
    }
    
    public int ExpressionDivide() {
        //匹配表达式是没有到至多两个正负号+（数）+（乘号）+（x)+（乘方）
        String stand = "(\\s*[+-]?\\s*[+-]?\\s*\\d*\\s*[*]?" +
                "\\s*[x]?\\s*([\\^]\\s*[+-]?\\d*)?)";
        Pattern p = Pattern.compile(stand);
        if (spaceProblerm() == -1) {
            return -1;
        }
        Matcher m = p.matcher(rawExpress);
        int matchSta = 0;
        int matchEnd = 0;
        int matchLast = 0;
        while (m.find()) {
            matchSta = m.start();
            if (matchSta != matchLast) {
                System.out.println("WRONG FORMAT!");
                System.out.println("Wrong input!");
                return -1;
            }
            matchEnd = m.end();
            if (matchEnd < rawExpress.length()) {
                if (rawExpress.charAt(matchEnd) != '+' &&
                        rawExpress.charAt(matchEnd) != '-') {
                    System.out.println("WRONG FORMAT!");
                    System.out.println("missing op!");
                    return -1;
                }
            }
            if (matchSta != matchEnd) {
                Term term = new Term(rawExpress.substring(matchSta, matchEnd));
                if (term.checkTerm() == -1) {
                    return -1;
                }
                expList.add(term);
                //term.printTerm();
            } else if (matchEnd == 0) {
                System.out.println("WRONG FORMAT!");
                System.out.println("no input!");
                return -1;
            }
            matchLast = matchEnd;
        }
        //System.out.println(expList);
        expressionMerge();
        return 0;
    }
    
    public void printExpression() {
        Term item;
        int i = 0;
        for (i = 0; i < expList.size(); i++) {
            item = expList.get(i);
            BigInteger coeff = item.getCoeff();
            BigInteger degree = item.getDegree();
            if (i == expList.size() - 1) {
                if (coeff.intValue() != 0) {
                    if (coeff.intValue() == 1) {
                        if (degree.intValue() == 0) {
                            System.out.print("1");
                        } else if (degree.intValue() == 1) {
                            System.out.print("x");
                        } else {
                            System.out.print("x^" + degree.toString());
                        }
                    } else {
                        if (degree.intValue() == 0) {
                            System.out.print(coeff.toString());
                        } else if (degree.intValue() == 1) {
                            System.out.print(coeff.toString() + "*x");
                        } else {
                            System.out.print(coeff.toString() + "*x^"
                                    + degree.toString());
                        }
                    }
                    
                } else {
                    System.out.print("0");
                }
            } else {
                if (coeff.intValue() != 0) {
                    if (coeff.intValue() == 1) {
                        if (degree.intValue() == 0) {
                            System.out.print("1+");
                        } else if (degree.intValue() == 1) {
                            System.out.print("x" + "+");
                        } else {
                            System.out.print("x^" + degree.toString() + "+");
                        }
                    } else {
                        if (degree.intValue() == 0) {
                            System.out.print(coeff.toString() + "+");
                        } else if (degree.intValue() == 1) {
                            System.out.print(coeff.toString() + "*x" + "+");
                        } else {
                            System.out.print(coeff.toString() + "*x^"
                                    + degree.toString() + "+");
                        }
                    }
                    
                }
            }
        }
        if (i == 0) {
            System.out.print("0");
        }
    }
    
    private int spaceProblerm() {
        String form1 = "([+-]\\s*[+-]\\s\\s*\\d+)|(\\d+\\s+\\d+)";
        Pattern p = Pattern.compile(form1);
        Matcher m = p.matcher(rawExpress);
        if (m.find()) {
            System.out.println("WRONG FORMAT!");
            System.out.println("Space Error!");
            rawExpress = rawExpress.replaceAll("\\s*", "");
            return -1;
        }
        rawExpress = rawExpress.replaceAll("\\s*", "");
        return 0;
    }
    
    private void expressionMerge() {
        int i;
        int j;
        Term itemi;
        Term itemj;
        for (i = 0; i < expList.size(); i++) {
            itemi = expList.get(i);
            for (j = i + 1; j < expList.size(); j++) {
                itemj = expList.get(j);
                if (itemi.getDegree().compareTo(itemj.getDegree()) == 0) {
                    itemi.setCoeff(itemi.getCoeff().add(itemj.getCoeff()));
                    expList.remove(j);
                    i--;
                    break;
                }
            }
        }
    }
    
    public void expDer() {
        Term item;
        int i;
        for (i = 0; i < expList.size(); i++) {
            item = expList.get(i);
            item.termder();
            if (item.getCoeff().equals(BigInteger.valueOf(0))) {
                expList.remove(i);
                i--;
            }
        }
    }
}
