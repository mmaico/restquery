package restquery.parser.visitors;

import restquery.parser.antlr.LanguageBaseVisitor;
import restquery.parser.antlr.LanguageParser;
import restquery.parser.dtos.Expression;
import restquery.parser.dtos.LanguageBuilt;
import restquery.parser.dtos.LogicalExpression;

import java.util.List;
import java.util.stream.Collectors;


public class LanguageVisitor extends LanguageBaseVisitor<LanguageBuilt> {

    @Override
    public LanguageBuilt visitLanguage(LanguageParser.LanguageContext ctx) {
        LanguageParser.ExpressionContext expressionContext = ctx.expression().get(0);

        ExpressionVisitor expressionVisitor = new ExpressionVisitor();
        Expression expressionFirst = expressionContext.accept(expressionVisitor);

        LogicalExpressionVisitor logicalOperatorVisitor = new LogicalExpressionVisitor();

        List<LogicalExpression> logicalExpressions = ctx.logicalExpression().stream()
                .map(logicalExpressionContext -> logicalExpressionContext.accept(logicalOperatorVisitor))
                .collect(Collectors.toList());

        return new LanguageBuilt(expressionFirst, logicalExpressions);
    }

}
