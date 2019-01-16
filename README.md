# weatherReport
Constraints: Only returns tempertures from cities in the Unitied States. 

A program that takes user input for a city located in the United States and uses OpenWeatherMap's API to return a temperture (https://openweathermap.org/appid).
The website recomended only searching for cities by their ID number, to ensure I did that I downloaded their .json file of cities and parsed
  out the US ones and made a new file that I traverse later to get the ID for the API call.

TODO
1. Needs more tests 
2. GeographicLocation Object (and thus Coordinate obj) don't actually do anything. If I find time I want to use them to display
  more data on the result.
3. Add data validation, or implement something that finds the city name closest to the input city.
