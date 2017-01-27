package restquery.parser.visitors;


import restquery.parser.antlr.LanguageBaseVisitor;
import restquery.parser.antlr.LanguageParser;
import restquery.parser.dtos.Expression;
import restquery.parser.dtos.LogicalExpression;
import restquery.parser.enums.LogicalOperator;

public class LogicalExpressionVisitor extends LanguageBaseVisitor<LogicalExpression> {

    @Override public LogicalExpression visitLogicalExpression(LanguageParser.LogicalExpressionContext ctx) {
        LogicalOperatorVisitor logicalOperatorVisitor = new LogicalOperatorVisitor();

        LogicalOperator logicalOperator = ctx.operators().accept(logicalOperatorVisitor);
        Expression expression = ctx.expression().accept(new ExpressionVisitor());

        LogicalExpression logicalExpression = new LogicalExpression();
        logicalExpression.setValue(expression.getValue());
        logicalExpression.setAttribute(expression.getAttribute());
        logicalExpression.setComparationOperator(expression.getComparationOperator());
        logicalExpression.setLogical(logicalOperator);

        return logicalExpression;
    }
}
