package expressivo;

import java.util.Map;

class Number implements Expression {
    //nonnegative numbers in decimal representation, 
    //which consist of digits and an optional decimal point (e.g. 7 and 4.2)
    private final double number;
    
    public Number(double n){
        this.number=n;
    }
    
    @Override
    public String toString() {
        return Double.toString(number);
    }
    
    @Override
    public boolean equals(Object thatObject){
        if (!(thatObject instanceof Number)) return false;
        Number that = (Number) thatObject;
        return this.number == that.number;
    }

    @Override
    public int hashCode(){
        return Double.hashCode(number);
    }

    @Override
    public Expression differentiate(String v) {
        return new Number(0);
    }

    @Override
    public Expression simplify(String string, Map<String, Double> environment) {
        return new Number(number);
    }

}
