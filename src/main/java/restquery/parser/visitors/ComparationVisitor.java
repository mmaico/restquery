package restquery.parser.visitors;

import restquery.parser.antlr.LanguageBaseVisitor;
import restquery.parser.antlr.LanguageParser;
import restquery.parser.enums.ComparationOperator;
import restquery.parser.exception.InvalidExpressionException;

import static restquery.helpers.ExceptionMessageUtils.message;
import static restquery.parser.enums.ComparationOperator.contains;


public class ComparationVisitor extends LanguageBaseVisitor <ComparationOperator> {


    @Override public ComparationOperator visitComparationOperator(LanguageParser.ComparationOperatorContext ctx) {
        if (!contains(ctx.getText())) {
            throw new InvalidExpressionException(message(ctx.getStart().getText(), ComparationOperator.getOptions()));
        }
        String comparation = ctx.getText();
        return ComparationOperator.get(comparation);
    }

}
