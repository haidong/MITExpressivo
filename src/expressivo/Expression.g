/* Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */

// grammar Expression;

/*
 *
 * You should make sure you have one rule that describes the entire input.
 * This is the "start rule". Below, "root" is the start rule.
 *
 * For more information, see the parsers reading.
 */
//root ::= sum;
//@skip whitespace{
	//sum ::= primitive ('+' primitive)*;
	//primitive ::= number | '(' sum ')';
//}
//number ::= [0-9]+;
//
//whitespace ::= [ ]+;

//@skip whitespace{
	//root ::= sumcartisianproduct;
	//sumcartisianproduct ::= sumofsumproduct | productofsumproduct;
	//sumofsumproduct ::= sumproduct '+' sumproduct;
	//productofsumproduct ::= sumproduct '*' sumproduct;
	//sumproduct ::= sum | product;
	//sum ::= primitive '+' primitive;	
	//product ::= primitive '*' primitive;	
	//primitive ::= variableparen | decimalparen | '(' variableparen ')' | '(' decimalparen ')';
	//variableparen ::= variable | '(' variable ')';
	//decimalparen ::= decimal | '(' decimal ')';
//}
//whitespace ::= [ \t\r\n];
//decimal ::= [0-9]+('.'[0-9]*)?;
//variable ::= [a-zA-Z]+;




@skip whitespace{
    root ::= product | sum | primitive;

    sum ::=  product ('+' product)*;
    product ::= primitive ('*' primitive)*;

    primitive ::= decimal | variable | '(' sum ')' | '(' product ')';
}
whitespace ::= [ \t\r\n]+;

decimal ::= [0-9]+ ('.' [0-9]*)? | '.' [0-9]+;
variable ::= [a-zA-Z]+;