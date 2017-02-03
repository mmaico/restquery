package restquery.parser

import restquery.parser.enums.ComparationOperator
import restquery.parser.enums.LogicalOperator
import restquery.parser.exception.InvalidExpressionException
import spock.lang.Specification
import spock.lang.Unroll


class ObjectParserTest extends Specification {


    @Unroll
    "should converter simple expression"() {
        given: "A expression"
            def expression = "name.eq(maico)"
        when: "execute builder"
            def langBuilt = new ObjectParser().parserToLanguageObjects(expression)
        then:
            langBuilt.firstExpression.attribute == "name"
            langBuilt.firstExpression.comparationOperator == ComparationOperator.EQ
            langBuilt.firstExpression.value == "maico"
    }

    @Unroll
    "should convert a simple expression with a logical expression" () {
        given: "A expression with logical"
            def expression = "name.eq(marcelo).and(lastName.contains(maico))"
        when: "execute builder"
            def langBuilt = new ObjectParser().parserToLanguageObjects(expression)

        then:
            langBuilt.firstExpression.attribute == "name"
            langBuilt.firstExpression.comparationOperator == ComparationOperator.EQ
            langBuilt.firstExpression.value == "marcelo"
            langBuilt.logicalExpressions.size() == 1

            langBuilt.logicalExpressions.get(0).attribute == "lastName"
            langBuilt.logicalExpressions.get(0).comparationOperator == ComparationOperator.CONTAINS
            langBuilt.logicalExpressions.get(0).value == "maico"

    }

    @Unroll
    "should convert a expression with composite attributes" () {
        given: "A expression with logical"
            def expression = "name.eq(marcelo).and(address.street.contains(saint patrick))"
        when: "execute builder"
            def langBuilt = new ObjectParser().parserToLanguageObjects(expression)

        then:
            langBuilt.firstExpression.attribute == "name"
            langBuilt.firstExpression.comparationOperator == ComparationOperator.EQ
            langBuilt.firstExpression.value == "marcelo"
            langBuilt.logicalExpressions.size() == 1

            langBuilt.logicalExpressions.get(0).attribute == "address.street"
            langBuilt.logicalExpressions.get(0).comparationOperator == ComparationOperator.CONTAINS
            langBuilt.logicalExpressions.get(0).value == "saint patrick"

    }

    @Unroll
    "should return error when invalid logical operator" () {
        given: "A expression with logical"
            def expression = "name.eq(marcelo).invalid(lastName.contains(maico))"
        when: "execute builder"
            new ObjectParser().parserToLanguageObjects(expression)
        then:
            def ex = thrown(InvalidExpressionException)
        and:
            ex.message == "Invalid token found: [invalid] Expected: [and, or, not]"
        and: "the quantity logical operator in message expectes is 3"
            LogicalOperator.values().length == 3

    }

    @Unroll
    "should return error when invalid comparation operator" () {
        given: "A expression with logical"
            def expression = "name.invalid(marcelo).or(lastName.contains(maico))"
        when: "execute builder"
            new ObjectParser().parserToLanguageObjects(expression)
        then:
            def ex = thrown(InvalidExpressionException)
        and:
            ex.message == "Expected operator after: [name.invalid] [eq, gt, lt, gte, lte, contains, containsAll]"
        and: "the quantity comparation availables expected is 7"
            ComparationOperator.values().length == 7
    }

    @Unroll
    "should return error when invalid comparation inside logical expression" () {
        given: "A expression with logical"
            def expression = "name.eq(marcelo).or(lastName.invalid(maico))"
        when: "execute builder"
            new ObjectParser().parserToLanguageObjects(expression)
        then:
            def ex = thrown(InvalidExpressionException)
            ex.message == "Expected operator after: [lastName.invalid] [eq, gt, lt, gte, lte, contains, containsAll]"

    }
}
