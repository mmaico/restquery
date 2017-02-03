package restquery.parser.visitors;

import restquery.parser.antlr.LanguageBaseVisitor;
import restquery.parser.antlr.LanguageParser;
import restquery.parser.enums.LogicalOperator;
import restquery.parser.exception.InvalidExpressionException;

import static restquery.helpers.ExceptionMessageUtils.message;
import static restquery.parser.enums.LogicalOperator.contains;


public class LogicalOperatorVisitor extends LanguageBaseVisitor<LogicalOperator> {


    @Override public LogicalOperator visitOperators(LanguageParser.OperatorsContext ctx) {
        if (!contains(ctx.getText())) {
            throw new InvalidExpressionException(message(ctx.getStart().getText(), LogicalOperator.getOptions()));
        }
        String operator = ctx.getText();
        return LogicalOperator.get(operator);
    }
}
