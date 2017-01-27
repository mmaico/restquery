package restquery.parser.visitors;


import restquery.parser.antlr.LanguageBaseVisitor;
import restquery.parser.antlr.LanguageParser;
import restquery.parser.dtos.Expression;
import restquery.parser.enums.ComparationOperator;

public class ExpressionVisitor extends LanguageBaseVisitor<Expression> {

    @Override public Expression visitExpression(LanguageParser.ExpressionContext ctx) {
        AttributeVisitor attributeVisitor = new AttributeVisitor();
        ValueVisitor valueVisitor = new ValueVisitor();
        ComparationVisitor comparationVisitor = new ComparationVisitor();

        String attribute = ctx.attribute().accept(attributeVisitor);
        ComparationOperator comparationOperator = ctx.comparation().get(0).accept(comparationVisitor);
        String value = ctx.value().get(0).accept(valueVisitor);

        return new Expression(attribute, value, comparationOperator);
    }
}
