Feature: What race do I have in the database??
  Everybody wants to know what race I have in the database

  Scenario Outline: I have this or I dont have this Breed
    Given I have Nordic dogs and I have their breed UUIDs which are "<uuidbreed>"
    When I am consulting in BreedService
    Then i should get the breed "<breed>"

    Examples:
      | uuidbreed                             | breed                |
      | 7fcc6e8b-9752-45ab-9505-c9833bd93e34  | Husky                |