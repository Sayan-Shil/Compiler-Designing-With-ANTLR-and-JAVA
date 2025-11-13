grammar Expr; /* The Grammar Name And File Name must be Exact Matched */

@header {
 package antlr;
}

// Start Variable
prog: (decl | expr)+ EOF    #Program
    ;

decl: ID ':' INT_TYPE '=' NUM  #Declaration
    ;

/* ANTLR resooves ambiguities in favour of alternatives given first */
expr: expr '*' expr   #Multiplication
| expr '+' expr       #Addition
| ID                  #Variable
| NUM                 #Number
;

/* Tokens */
ID: [a-z][a-zA-Z0-9_]* ; //Identifiers
NUM: '0' | '-'?[1-9][0-9]* ;
INT_TYPE: 'INT' ;
COMMENT : '--' ~[\r\n]* -> skip;
WS: [ \r\n\t]+ -> skip;