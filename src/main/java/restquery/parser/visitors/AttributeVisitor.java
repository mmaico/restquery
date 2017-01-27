package restquery.parser.visitors;

import restquery.parser.antlr.LanguageBaseVisitor;
import restquery.parser.antlr.LanguageParser;
import restquery.parser.enums.ComparationOperator;


public class AttributeVisitor extends LanguageBaseVisitor <String> {


    @Override public String visitAttribute(LanguageParser.AttributeContext ctx) {
        return ctx.getText();
    }


}
