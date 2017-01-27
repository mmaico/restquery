package restquery.parser.dtos;


import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class LanguageBuilt {

    private final Expression firstExpression;
    private final List<LogicalExpression> logicalExpressions;

    public LanguageBuilt(Expression firstExpression, List<LogicalExpression> logicalExpressions) {
        this.firstExpression = firstExpression;
        this.logicalExpressions = logicalExpressions;
    }

    public Expression getFirstExpression() {
        return firstExpression;
    }

    public List<LogicalExpression> getLogicalExpressions() {
        return logicalExpressions;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
