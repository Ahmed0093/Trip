# Trip Sample Demo App 

This project is aimed for learning and to get hands dirty with MVVM architecture using Kotlin,Coroutines, Navigation Component , Retrofit with adding Unit Tests.


### Video Link of the Trip Sample App :
https://www.youtube.com/watch?v=a-Vbr1PYmI4

## Getting Started
### The app contains 3 screen

# Screen-1 : Trip Search Form
The search form has the following elements:
-  Title “Trip Search”
- Keyword search
- Include canceled trips
- Distance
- Time
- Search button
- When you click the search button, the app should filter down the array of recent trips to just the ones that match the search criteria, and go to the search results page showing all of the matching trips.
- By default, it will only consider “COMPLETE” trips, but if the “Include cancelled trips” is toggled, it will consider “CANCELED” trips as well.

# Screen-2 : Trip Search Results
- The trip search results shows a list of trips that match the search criteria.
- This screen should show the number of matching results, and for each one, it should render a box with some info about the trip and its status.
- Each box has the following:
- Trip start time
- Trip final cost
- Pickup location
- Dropoff location
- Rating
- Trip status
- When you click on a box, it takes you to the full trip details screen
- When you press back, it takes you back to the search screen with all your inputs still filled out

# Screen-3 : Trip Details
- The trip details screen is the final screen. It shows all the relevant information about the trip, including a map of the trip.
- This screen shows the following (but you can get creative and add more if you like):
- Pick up location
- Drop off location
- Request date/time
- Trip start time
- Trip end time
- Trip distance
- Trip duration
- Trip final price
- Driver name
- Picture of driver
- Picture of driver’s car
- Driver’s car make & model
- Rating
- Map of the trip

### TO DO
- Add UI Test
- Add Github Action


## Acknowledgments

