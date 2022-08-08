# Movie App

Movie App is a simple movie app which uses [The Movie Database (TMDB) API](https://www.themoviedb.org/documentation/api/) to fetch list of popular films from the API.

<img alt="MovieApp Movie List" height="600px" src="https://raw.githubusercontent.com/kantarix/AndroidAcademy/master/screenshots/screen_movie_list.png?token=GHSAT0AAAAAABWHW2JX3YND4NPS6YC4D3HGYXRMOYA" /> <img alt="MovieApp Movie Details" height="600px" src="https://raw.githubusercontent.com/kantarix/AndroidAcademy/master/screenshots/screen_movie_details.png?token=GHSAT0AAAAAABWHW2JX5V3HOYYN2GWTYJUQYXRMRKQ" />

## API key 🔑
You'll need to provide API key to fetch the films from the MovieDB Service (API). Currently the films is fetched from [The Movie Database API](https://www.themoviedb.org/documentation/api)

- Generate an API key from [The Movie Database API](https://www.themoviedb.org/settings/api)
- Create new file named `apikey.properties` in our project root folder
- Add the API key as shown below:
```
    MOVIE_API_KEY="<INSERT_YOUR_API_KEY>"
```
- Build the app

## Libraries and tools

- [Kotlin](https://kotlinlang.org/)
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
- [Glide](http://bumptech.github.io/glide/)
- Architecture components
- [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager)
- [Retrofit](https://square.github.io/retrofit/)
- [Room](https://developer.android.com/jetpack/androidx/releases/room)
- Other [Android Jetpack](https://developer.android.com/jetpack) components

## Architecture

The app uses MVVM [Model-View-ViewModel] architecture to have a unidirectional flow of data, separation of concern, testability, and a lot more.
