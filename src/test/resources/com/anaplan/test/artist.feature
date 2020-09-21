Feature: Spotify API?
  Artist endpoint get

  Scenario: Get Artist by Id
    Given I am logged in
    When I ask for artist "0OdUWJ0sBjDrqHygGUXeCF"
    Then I should get the name as "Band of Horses"