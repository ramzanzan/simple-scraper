Учебный проект цель которого освоние построения чистого HATEOAS api с использованием https://tools.ietf.org/html/rfc7807.
Приложение - простенький html скрапер.
См. пример запроса ниже.

todo https://docs.spring.io/spring-framework/docs/5.2.9.RELEASE/javadoc-api/org/springframework/context/support/ResourceBundleMessageSource.html

todo
4) openapi
5) tests
6) security
7) ....


Скрапит ингредиенты с рецептов с eda.ru.

{
  "pointer": {
    "pageUrlTemplate": "https://eda.ru/recepty?page={page}",
    "from": 1,
    "to": 3,
    "itemUrlSelector": "div.tile-list__horizontal-tile > div:nth-child(1) > div:nth-child(2) > h3:nth-child(2) > a:nth-child(1)",
    "offset": 0,
    "limit": 100
  },
  "excerptDefinitions": [
    {
      "name": "ing",
      "selector": "div.ingredients-list:nth-child(8) > div:nth-child(2) > p",
      "type": "TEXT",
      "attrName": "string",
      "limit": 100
    }
  ],
  "pageSize": 5,
  "withOrigin": true
}