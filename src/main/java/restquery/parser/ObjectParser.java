package restquery.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.atn.PredictionMode;
import restquery.parser.antlr.LanguageLexer;
import restquery.parser.antlr.LanguageParser;
import restquery.parser.dtos.LanguageBuilt;
import restquery.parser.visitors.LanguageVisitor;


public class ObjectParser {


    public LanguageBuilt parserToLanguageObjects(String queryString) {

        ANTLRInputStream input  = new ANTLRInputStream(queryString);
        LanguageLexer lexer = new LanguageLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        LanguageParser parser = new LanguageParser(tokens);
        parser.removeErrorListeners();
        parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
        LanguageVisitor lang = new LanguageVisitor();

        return lang.visitLanguage(parser.language());
    }
}
