import java.math.BigInteger;
class Matrix {
    public BigInteger[][] mat;
    
    public Matrix() {
        mat = null;
    }
    
    public Matrix(int order) {
        mat = new BigInteger[order][order];
    }
    
    public Matrix(String str) {
        int order;
        int index = 0;
        int check = 0;
        int count = 0;
        if(str.contentEquals("")){
            System.out.println("Empty Matrix!");
            System.exit(0);
        }
        if(str.charAt(index)!='{'){
            System.out.println("Illegal Input!");
            System.exit(0);
        }
        for(index=0;index<str.length();index++){
            if(str.charAt(index)=='{'){
                check++;
                if(check>=3){
                    System.out.println("Illegal Input!");
                    System.exit(0);
                }
            }
            if(str.charAt(index)=='}'){
                check--;
                if(check<0){
                    System.out.println("Illegal Input!");
                    System.exit(0);
                }
            }
        }
        if(check!=0){
            System.out.println("Illegal Input!");
            System.exit(0);
        }
        String[] strs = str.split("[{},]");
        int i = 0, j = 0;
        for (i = 2; i < strs.length; i++) {
            if (!strs[i].equals("")) {
                continue;
            } else {
                break;
            }
        }
        for (index = 2; index < strs.length; index++) {
            if (!strs[index].equals("")) {
                count++;
            }
        }
        order = i - 2;
        if(count!=(order)*(order)){
            System.out.println("Illegal Input!");
            System.exit(0);
        }
        if (order == 0) {
            System.out.println("Empty Matrix!");
            System.exit(0);
        }
        BigInteger[][] m = new BigInteger[order][order];
        for (i=2 ; i < strs.length; i += 2 + order) {
            for (j=0 ; j < order; j++)
                try {
                    //m[i / (2 + order)][j] = Integer.parseInt(strs[i + j]);
                    m[i / (2 + order)][j] = BigInteger.valueOf(Integer.parseInt(strs[i + j]));
                } catch (Exception NumberFormatException) {
                    System.out.println("Illegal Input!");
                    System.exit(0);
                }
        }
        mat = m;
    }
    
    protected int getOrder() {
        return mat.length;
    }
    
    protected Matrix add(Matrix addThis) {
        int i = 0, j = 0;
        int order = getOrder();
        int thisOrder = addThis.getOrder();
        if(order!=thisOrder){
            System.out.println("Illegal Operation!");
            System.exit(0);
        }
        Matrix temp = new  Matrix(order);
        for (; i < order; i++) {
            for (j = 0; j < order; j++) {
                temp.mat[i][j] = mat[i][j].add(addThis.mat[i][j]);
            }
        }
        return temp;
    }
    
    protected Matrix sub(Matrix subThis) {
        int i = 0, j = 0;
        int order = getOrder();
        int thisOrder = subThis.getOrder();
        if(order!=thisOrder){
            System.out.println("Illegal Operation!");
            System.exit(0);
        }
        Matrix temp = new Matrix(order);
        for ( ; i < order; i++) {
            for (j = 0 ; j < order; j++) {
                temp.mat[i][j] = mat[i][j].add(subThis.mat[i][j].negate());
            }
        }
        return temp;
    }
    
    protected Matrix transpose() {
        int order;
        order = getOrder();
        Matrix temp = new Matrix(order);
        int i = 0, j = 0;
        for ( ; i < order; i++) {
            for (j = 0 ; j < order; j++) {
                temp.mat[i][j] = mat[j][i];
            }
        }
        return temp;
    }
    
    protected Matrix multiply(Matrix multiplyThis) {
        int i = 0, j = 0, k = 0;
        int order = getOrder();
        int thisOrder = multiplyThis.getOrder();
        if(order!=thisOrder){
            System.out.println("Illegal Operation!");
            System.exit(0);
        }
        BigInteger element;
        Matrix temp = new Matrix(order);
        for (i = 0; i < order; i++) {
            for (j = 0; j < order; j++) {
                element = BigInteger.valueOf(0);
                for (k = 0; k < order; k++) {
                    element =element.add(mat[i][k].multiply(multiplyThis.mat[k][j]));
                }
                temp.mat[i][j] = element;
            }
        }
        return temp;
    }
    
    public String toString() {
        String s = new String();
        int i = 0, j = 0;
        int order = getOrder();
        for ( ; i < order; i++) {
            for (j = 0 ; j < order; j++) {
                s += String.valueOf(mat[i][j]);
                s += '\t';
            }
            s = s + '\n';
        }
        return s;
    }
}

