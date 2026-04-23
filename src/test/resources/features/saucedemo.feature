Feature: SauceDemo Flow

  Scenario Outline: Login and checkout
    Given user logs in with "<username>" and "<password>"
    When user applies low to high price filter
    And user adds first product to cart
    And user navigates to cart
    And user proceeds to checkout
    Then user should reach checkout page

    Examples:
    | username | password |
    | standard_user | secret_sauce |

  Scenario Outline: Login should fail
    Given user logs in with "<username>" and "<password>"
    Then user should see login error

    Examples:
    | username | password |
    | locked_out_user | secret_sauce |
    |  |  |
