# Http Rest Query filter


It's very good a language that allows api clients to have autonomy when filtering the data from your endpoint. But it not good practice to show the tecnology that you use in your backend,
then you need a wrapper language when do not show the backend tecnology and make easy
the sintax filter for your clients.

Well, with language ready your backend needs supports to recognize and parse a language
to other query language, in this case we will working with elasticsearch, but you
can create a driver to work with other language queries.

The expression support a logical operators (OR, AND, NOT) and comparators (eq, gt, lt, gte, lte, contains, containsAll).

A expression is composed by attribute [comparator] [value] and a logical expression is composed by [operator].(expression).


##Indice
* [installation](#installation)
* [Fist example](#first-example)
* [Comparations And logical supported](#comparations-and-logical-supported)


##installation:
```xml
    <dependency>
      <groupId>restquery</groupId>
      <artifactId>restquery</artifactId>
      <version>1.0.0</version>
    </dependency>
```

```xml
    compile 'restquery:restquery:1.0.0'
```

## Fist example

  ```javascript
    public static void main(String[] args) {
        //the name must contains marcelo and address.street must contains exactly "Street one"
        String filter = "name.contains(marcelo).or(address.street.contains(\"street one\"))"

        //Query elasticsearch
        String elasticSearchQuery = new ElasticsearchParser().parseToElastisearchQuery(expression);

        //alternatively you can convert to object and after and then create your own parser 
        LanguageBuilt languageBuilt = new ObjectParser().parserToLanguageObjects(expression);

    }
    
    //Query generated
    {
      "bool" : {
        "must" : {
          "match" : {
            "name" : {
              "query" : "marcelo",
              "type" : "boolean",
              "operator" : "OR"
            }
          }
        },
        "should" : {
          "match" : {
            "address.street" : {
              "query" : "\"street one\"",
              "type" : "phrase"
            }
          }
        }
      }
    }
  ```
  
## Comparations And logical supported

 Comparations: eq, gt, lt, gte, lte, contains, containsAll
 Logical: and, or, not
 
 Elastsearch driver:
    contains(value value2) <-- will be converted to match query OR
    contains(value*) <-- will be converted to prefix query
    contains("value") <-- will be converted to phrase query
    containsAll(value value2) <-- will be converted to match query AND
    (gt, lt, gte, lte) <-- will be converted to rangeQuery
    
 (and, or, not) <-- will be used a boolean query 


TO BE CONTINUE ...

 

 