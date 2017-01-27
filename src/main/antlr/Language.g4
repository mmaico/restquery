grammar Language;

@header {
    package restquery.parser.antlr;
}

language : expression + (logicalExpression)* ;

expression : attribute ('.' comparationOperator)+ ('('value')')+ ;
logicalExpression : ('.' operators) ('('expression')') ;

operators : 'and' | 'or' | 'not' ;

attribute : STRING ;

comparationOperator : 'eq' | 'gt' | 'lt' | 'gte' | 'lte' | 'contains' ;

value : (STRING (SPACE STRING)* | NUMBER);

SPACE : (' ' | '\t')+ -> skip ;
STRING : ('a'..'z'|'A'..'Z' | '*')+ ;
NUMBER : '0'..'9'+ ;

WS : [ \r\n]+ -> skip ;