package restquery.parser

import groovy.json.JsonSlurper
import infratest.SceneryLoaderHelper
import spock.lang.Specification
import spock.lang.Unroll

import static infratest.SceneryLoaderHelper.getRequestScenery

class ElasticsearchParserTest extends Specification {

    static QUERY_EXPECTEDS = "/elasticsearchQuerysExpecteds.json"

    def setup() {
        SceneryLoaderHelper.load(QUERY_EXPECTEDS)
    }

    @Unroll
    "should converter a simple expression in elasticsearch query" () {
        given: "a expression"
            def expression = "name.eq(maico)"
        when: "execute builder"
            def queryResult = new JsonSlurper().parseText(new ElasticsearchParser().parseToElastisearchQuery(expression))
            def queryExpected = new JsonSlurper().parseText(getRequestScenery("should converter a simple expression in elasticsearch query").json)
        then:
            queryResult == queryExpected
    }

    @Unroll
    "should converter a logical expression in elasticsearch query" () {
        given: "a expression"
            def expression = "name.eq(maico).or(lastName.contains(marcelo))"
        when: "execute builder"
            def queryResult = new JsonSlurper().parseText(new ElasticsearchParser().parseToElastisearchQuery(expression))
            def queryExpected = new JsonSlurper().parseText(getRequestScenery("should converter a logical expression in elasticsearch query").json)
        then:
            queryResult == queryExpected
    }

    @Unroll
    "should converter to prefix query" () {
        given: "a expression"
            def expression = "name.contains(maico*).or(lastName.contains(marcelo))"
        when: "execute builder"
            def queryResult = new JsonSlurper().parseText(new ElasticsearchParser().parseToElastisearchQuery(expression))
            def queryExpected = new JsonSlurper().parseText(getRequestScenery("should converter to prefix query").json)
        then:
            queryResult == queryExpected
    }

    @Unroll
    "should converter to a phrase query" () {
        given: "a expression"
            def expression = "name.contains(marcelo).or(address.street.contains(\"street one\"))"
        when: "execute builder"
            def queryResult = new JsonSlurper().parseText(new ElasticsearchParser().parseToElastisearchQuery(expression))
            def queryExpected = new JsonSlurper().parseText(getRequestScenery("should converter to a phrase query").json)
        then:
            queryResult == queryExpected
    }
}
