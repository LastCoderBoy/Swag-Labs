Feature: Login

  Background:
    Given the user is on the Swag Labs login page

  @smoke
  Scenario: Successful login with valid credentials
    When the user enters username "standard_user"
    And the user enters password "secret_sauce"
    And the user clicks the login button
    Then the products page header should be displayed

  @smoke
  Scenario: Login attempt with locked out user
    When the user enters username "locked_out_user"
    And the user enters password "secret_sauce"
    And the user clicks the login button
    Then the error message should be "Epic sadface: Sorry, this user has been locked out."

  @regression
  Scenario: Login attempt with invalid credentials
    When the user enters username "invalid_user"
    And the user enters password "invalid_pass"
    And the user clicks the login button
    Then the error message should be "Epic sadface: Username and password do not match any user in this service"