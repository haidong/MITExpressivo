package expressivo;

import java.util.Map;

class Plus implements Expression {
    private final Expression left, right;
    
    public Plus(Expression l, Expression r) {
        this.left = l;
        this.right = r;
    }
    
    @Override
    public String toString(){
        return left.toString() + " + " + right.toString();
    }
    
    @Override
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Plus)) return false;
        Plus that = (Plus) thatObject;
        return this.left.equals(that.left) && this.right.equals(that.right);
    }
    
    @Override
    public int hashCode() {
        int result = 3;
        result = 37 * result + left.hashCode() + right.hashCode();
        return result;
    }

    @Override
    public Expression differentiate(String v) {
        return new Plus(left.differentiate(v), right.differentiate(v));
    }

    @Override
    public Expression simplify(String string, Map<String, Double> environment) {
        if (Expression.isNumeric(left.toString()) && Expression.isNumeric(right.toString())) {
            return new Number(Double.parseDouble(left.toString()) +
                    Double.parseDouble(right.toString()));
        } else {
            Expression l = left.simplify(string, environment);
            Expression r = right.simplify(string, environment);
            
            if (Expression.isNumeric(l.toString()) && Expression.isNumeric(r.toString())) {
                return new Number(Double.parseDouble(l.toString()) +
                        Double.parseDouble(r.toString()));
            }
            return new Plus(l, r);
        }
    }

}
