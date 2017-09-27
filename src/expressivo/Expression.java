package expressivo;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import lib6005.parser.*;

/**
 * An immutable data type representing a polynomial expression of: + and *
 * nonnegative integers and floating-point numbers variables (case-sensitive
 * nonempty strings of letters)
 * 
 * <p>
 * PS1 instructions: this is a required ADT interface. You MUST NOT change its
 * name or package or the names or type signatures of existing methods. You may,
 * however, add additional methods, or strengthen the specs of existing methods.
 * Declare concrete variants of Expression in their own Java source files.
 */
public interface Expression {

    // Datatype definition
    // Expression = Variable(x:char) +
    // Number(n:Double) +
    // (Expression Plus Expression) +
    // (Expression Multiply Expression) +

    enum ExpressionGrammar {
        // ROOT, SUMCARTISIANPRODUCT, PRODUCTOFSUMPRODUCT, SUMOFSUMPRODUCT,
        // SUMPRODUCT, PRODUCT, SUM, PRIMITIVE, VARIABLEPAREN, DECIMALPAREN,
        // WHITESPACE, DECIMAL, VARIABLE
        ROOT, PRODUCT, SUM, PRIMITIVE, WHITESPACE, DECIMAL, VARIABLE
    };

    /**
     * Parse an expression.
     * 
     * @param input
     *            expression to parse, as defined in the PS1 handout.
     * @return expression AST for the input
     * @throws IllegalArgumentException
     *             if the expression is invalid
     */
    public static Expression parse(String input) {
        try {
            Parser<ExpressionGrammar> parser = GrammarCompiler.compile(new File("src/expressivo/Expression.g"),
                    ExpressionGrammar.ROOT);
            ParseTree<ExpressionGrammar> tree = parser.parse(input);
            Expression ast = buildAST(tree);
            return ast;
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public static boolean isNumeric(String input) {
        try {
            double d = Double.parseDouble(input);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static Expression buildAST(ParseTree<ExpressionGrammar> tree) {
        boolean first = true;
        Expression result = null;
        switch (tree.getName()) {
        case VARIABLE:
            return new Variable(tree.getContents());
        case DECIMAL:
            return new Number(Double.parseDouble(tree.getContents()));
        case PRIMITIVE:
            if (!(tree.childrenByName(ExpressionGrammar.DECIMAL).isEmpty())) {
                return buildAST(tree.childrenByName(ExpressionGrammar.DECIMAL).get(0));
            }
            if (!(tree.childrenByName(ExpressionGrammar.VARIABLE).isEmpty())) {
                return buildAST(tree.childrenByName(ExpressionGrammar.VARIABLE).get(0));
            }
            if (!(tree.childrenByName(ExpressionGrammar.PRODUCT).isEmpty())) {
                return buildAST(tree.childrenByName(ExpressionGrammar.PRODUCT).get(0));
            }
            if (!(tree.childrenByName(ExpressionGrammar.SUM).isEmpty())) {
                return buildAST(tree.childrenByName(ExpressionGrammar.SUM).get(0));
            }
        case SUM:
            first = true;
            result = null;
            for (ParseTree<ExpressionGrammar> child : tree.childrenByName(ExpressionGrammar.PRODUCT)) {
                if (first) {
                    result = buildAST(child);
                    first = false;
                } else {
                    result = new Plus(result, buildAST(child));
                }
            }
            if (first) {
                throw new RuntimeException("sum must have a non whitespace child: " + tree);
            }
            return result;
        case PRODUCT:
            first = true;
            result = null;
            for (ParseTree<ExpressionGrammar> child : tree.childrenByName(ExpressionGrammar.PRIMITIVE)) {
                if (first) {
                    result = buildAST(child);
                    first = false;
                } else {
                    result = new Multiply(result, buildAST(child));
                }
            }
            if (first) {
                throw new RuntimeException("product must have a non whitespace child: " + tree);
            }
            return result;
        case ROOT:
            if (!(tree.childrenByName(ExpressionGrammar.PRODUCT).isEmpty())) {
                return buildAST(tree.childrenByName(ExpressionGrammar.PRODUCT).get(0));
            }
            if (!(tree.childrenByName(ExpressionGrammar.SUM).isEmpty())) {
                return buildAST(tree.childrenByName(ExpressionGrammar.SUM).get(0));
            }
            if (!(tree.childrenByName(ExpressionGrammar.PRIMITIVE).isEmpty())) {
                return buildAST(tree.childrenByName(ExpressionGrammar.PRIMITIVE).get(0));
            }
        case WHITESPACE:
            throw new RuntimeException();
        }
        return null;
    }

    /**
     * @return a parsable representation of this expression, such that for all
     *         e:Expression, e.equals(Expression.parse(e.toString())).
     */
    @Override
    public String toString();

    /**
     * @param thatObject
     *            any object
     * @return true if and only if this and thatObject are structurally-equal
     *         Expressions, as defined in the PS1 handout.
     */
    @Override
    public boolean equals(Object thatObject);

    /**
     * @return hash code value consistent with the equals() definition of
     *         structural equality, such that for all e1,e2:Expression,
     *         e1.equals(e2) implies e1.hashCode() == e2.hashCode()
     */
    @Override
    public int hashCode();

    public Expression differentiate(String v);

    public Expression simplify(String string, Map<String, Double> environment);

    /*
     * Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires permission of course
     * staff.
     */
}
