Feature: Testando a REST API Wishlist

  Scenario: validando a chamada no endpoint x
    Given passar o parametro idProduct= "12345"
    When  chamar o servico addProduct
    Then  validar se o item foi adicionado na lista

