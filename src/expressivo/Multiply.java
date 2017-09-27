package expressivo;

import java.util.Map;

class Multiply implements Expression {
    private final Expression left, right;
    
    public Multiply(Expression l, Expression r) {
        this.left = l;
        this.right = r;
    }
    
    @Override
    public String toString(){
        return left.toString() + " * " + right.toString();
    }
    
    @Override
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Multiply)) return false;
        Multiply that = (Multiply) thatObject;
        return this.left.equals(that.left) && this.right.equals(that.right);
    }
    
    @Override
    public int hashCode() {
        int result = 7;
        result = 37 * result + left.hashCode() + right.hashCode();
        return result;
    }

    @Override
    public Expression differentiate(String v) {
        Expression l = new Multiply(left, right.differentiate(v));
        Expression r = new Multiply(right, left.differentiate(v));
        return new Plus(l, r);
    }

    @Override
    public Expression simplify(String string, Map<String, Double> environment) {
        if (Expression.isNumeric(left.toString()) && Expression.isNumeric(right.toString())) {
            return new Number(Double.parseDouble(left.toString()) *
                    Double.parseDouble(right.toString()));
        } else {
            Expression l = left.simplify(string, environment);
            Expression r = right.simplify(string, environment);

            if (Expression.isNumeric(l.toString()) && Expression.isNumeric(r.toString())) {
                return new Number(Double.parseDouble(l.toString()) *
                        Double.parseDouble(r.toString()));
            }
            return new Multiply(l, r);
        }
    }
}
