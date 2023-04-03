# Weather app

Weather app is based on  MVVM architecture and uses the following libraries/Jetpack components
- Compose UI
- LiveData
- LocationServices
- Retrofit
- JUnit
- Timber
- ViewModel
- Compose Navigation
- Coroutines via produceState
- Hilt for DI
- GSON
- Coil - Image library


## App functionality

This App makes two network calls, one is location based and the other one is search string/city based.
Once the user location is known it makes a network call to fetch the weather data. If location data is
not available then it makes use of the search value to make a network call and fetch weather data. In
any case if either of them are not provided it makes a network call by a default city.

The fetched data is sent to UI in a Unidirectional flow via repository to ViewModel.


## Limitations

If the search string is not of the below accepted types we receive an exception as a response.
Accepted types are: "city" / "city, CountryCode" / "city, StateCode, CountryCode"


