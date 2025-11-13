grammar Expr; /* The Grammar Name And File Name must be Exact Matched */

@header {
 package antlr;
}

// Start Variable
prog: (decl | expr)+ EOF
    ;

decl: ID ':' INT_TYPE '=' NUM
    ;

/* ANTLR resooves ambiguities in favour of alternatives given first */
expr: expr '*' expr
| expr '+' expr
| ID
| NUM
;

/* Tokens */
ID: [a-z][a-zA-Z0-9_]* ; //Identifiers
NUM: '0' | '-'?[1-9][0-9]* ;
INT_TYPE: 'INT' ;
COMMENT : '--' ~[\r\n]* -> skip;
WS: [ \r\n\t]+ -> skip;