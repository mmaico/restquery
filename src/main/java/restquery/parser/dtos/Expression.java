package restquery.parser.dtos;


import org.apache.commons.lang3.builder.ToStringBuilder;
import restquery.parser.enums.ComparationOperator;

public class Expression {
    private String attribute;
    private String value;
    private ComparationOperator comparationOperator;

    public Expression() {
    }

    public Expression(String attribute, String value, ComparationOperator comparationOperator) {
        this.attribute = attribute;
        this.value = value;
        this.comparationOperator = comparationOperator;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ComparationOperator getComparationOperator() {
        return comparationOperator;
    }

    public void setComparationOperator(ComparationOperator comparationOperator) {
        this.comparationOperator = comparationOperator;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
