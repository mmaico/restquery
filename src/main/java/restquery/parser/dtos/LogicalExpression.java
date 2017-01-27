package restquery.parser.dtos;


import org.apache.commons.lang3.builder.ToStringBuilder;
import restquery.parser.enums.LogicalOperator;

public class LogicalExpression extends Expression {

    private LogicalOperator logical;

    public LogicalOperator getLogical() {
        return logical;
    }

    public void setLogical(LogicalOperator logical) {
        this.logical = logical;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
