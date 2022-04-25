Feature: Is this dog Haskell?
  Everybody wants to know if this Dog is Haskell

  Scenario: This dog is Haskell
    Given The identifier of the dog that is "9907e5cb-a169-4fa9-ab86-84101bf0f3c5"
    When I check with the PetService
    Then the dog that returns its name must be "Haskell".

  Scenario: Haskell is a Husky
    Given The identifier of the dog that is "9907e5cb-a169-4fa9-ab86-84101bf0f3c5"
    When I check the breed with the PetService
    Then ths breed of Haskell will be "Husky"

  Scenario: This dog isn't Bolo
    Given The identifier of the dog that is "9907e5cb-a169-4fa9-ab86-84101bf0f3c5"
    When I check with the PetService
    Then the dog that returns its name must not be "Bolo".
