package expressivo;

import java.util.Map;

class Variable implements Expression {
    //variables, which are case-sensitive nonempty sequences of letters (e.g. y and Foo)
    private final String variable;
    
    public Variable(String v){
        this.variable = v;
    }
    
    public String toString(){
        return variable;
    }

    @Override
    public boolean equals(Object thatObject){
        if (!(thatObject instanceof Variable)) return false;
        Variable that = (Variable) thatObject;
        return this.variable.equals(that.variable);
    }

    @Override
    public int hashCode(){
        return variable.hashCode();
    }

    @Override
    public Expression differentiate(String v) {
        if (v.equals(variable)) return new Number(1);
        else return new Number(0);
    }

    @Override
    public Expression simplify(String string, Map<String, Double> environment) {
        Double value = environment.get(variable);
        
        if (value != null) {
            return new Number(value);
        } else {
            return new Variable(variable);
        }
    }
}
