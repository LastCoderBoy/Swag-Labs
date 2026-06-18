Feature: Product Items

  Background:
    Given the user is on the Swag Labs login page
    When the user enters username "standard_user"
    And the user enters password "secret_sauce"
    And the user clicks the login button

  Scenario: Verify that the products page data is displayed
    Then the products page header should display "Products"
    And the product items should be displayed

  Scenario: Verify that the product items are sorted in ascending order by default
    Then the product items should be sorted in ascending order by default

  Scenario Outline: Products should be sorted correctly when a filter is selected
    When the user selects "<filter_option>" from the sort dropdown
    Then the products should be sorted in "<direction>" order by "<order_by>"

    Examples:
      | filter_option       | direction  | order_by |
      | Name (A to Z)       | ascending  | name     |
      | Name (Z to A)       | descending | name     |
      | Price (low to high) | ascending  | price    |
      | Price (high to low) | descending | price    |

  Scenario Outline: Verify that clicking "Add To Cart" button adds the product to the cart and changes the button text to "Remove"
    When the user clicks the "Add To Cart" button on the "<product_item>"
    Then the "Add To Cart" button for the "<product item>" should change to "Remove"
    And the cart badge should be increased by 1

    Examples:
      | product_item            |
      | Sauce Labs Backpack     |
      | Sauce Labs Bike Light   |
      | Sauce Labs Bolt T-Shirt |

  Scenario Outline: Verify that clicking "Remove" button removes the product from the cart and changes the button text back to "Add To Cart"
    When the user clicks the "Add To Cart" button on the "<product_item>"
    And the user clicks the "Remove" button on the "<product_item>"
    Then the "Remove" button for the "<product_item>" should change back to "Add To Cart"
    And the cart badge should be decreased by 1

    Examples:
      | product_item            |
      | Sauce Labs Backpack     |
      | Sauce Labs Bike Light   |
      | Sauce Labs Bolt T-Shirt |