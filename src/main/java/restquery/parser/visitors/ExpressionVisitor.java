package restquery.parser.visitors;


import restquery.parser.antlr.LanguageBaseVisitor;
import restquery.parser.antlr.LanguageParser;
import restquery.parser.dtos.Expression;
import restquery.parser.enums.ComparationOperator;
import restquery.parser.exception.InvalidExpressionException;

import java.util.stream.Collectors;

import static restquery.helpers.ExceptionMessageUtils.messageAfter;

public class ExpressionVisitor extends LanguageBaseVisitor<Expression> {

    private static final String DOT = ".";
    private static final int FIRST_CONTEXT = 0;

    @Override public Expression visitExpression(LanguageParser.ExpressionContext ctx) {
        AttributeVisitor attributeVisitor = new AttributeVisitor();
        ValueVisitor valueVisitor = new ValueVisitor();
        ComparationVisitor comparationVisitor = new ComparationVisitor();

        String attribute = ctx.attribute().stream()
                .map(attributeContext -> attributeContext.accept(attributeVisitor))
                .collect(Collectors.joining(DOT));

        if (ctx.comparationOperator().isEmpty()) {
            throw new InvalidExpressionException(messageAfter(attribute, ComparationOperator.getOptions()));
        }

        ComparationOperator comparationOperator = ctx.comparationOperator().get(FIRST_CONTEXT).accept(comparationVisitor);

        String value = ctx.value().get(FIRST_CONTEXT).accept(valueVisitor);

        return new Expression(attribute, value, comparationOperator);
    }
}
