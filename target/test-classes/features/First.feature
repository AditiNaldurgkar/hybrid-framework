Feature: SauceDemo Multi User Flow

Scenario Outline: Login and checkout
  Given user logs in with "<username>" and "<password>"
  When user applies low to high price filter
  And user adds first product to cart
  And user navigates to cart
  And user proceeds to checkout
  Then user should reach checkout page

Examples:
  | username | password |