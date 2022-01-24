package com.flethy.androidacademy.domain

import com.flethy.androidacademy.data.models.Movie

class MoviesDataSource {
    fun getMovies(): List<Movie> {
        return listOf(
            Movie("Avengers: End Game", "https://www.figma.com/file/p3e0HZexHmxwQaN9NcwAD9/image/dc120ed41c9f714c6b87b83e8cc0932e0c8576f7?fuid=743513597300855546", 13, false, "Action, Adventure, Drama", 4.0, 125, 137, "Some storyline", ActorsDataSource().getActors()),
            Movie("Tenet", "https://www.figma.com/file/p3e0HZexHmxwQaN9NcwAD9/image/c1ed9c571175fff3a3786edca69e746f7814f968?fuid=743513597300855546", 16, true, "Action, Sci-Fi, Thriller", 5.0, 98, 97, "Some storyline", ActorsDataSource().getActors()),
            Movie("Black Widow", "https://www.figma.com/file/p3e0HZexHmxwQaN9NcwAD9/image/984e84176361961de2f7a2eb504cdeec809d31b7?fuid=743513597300855546", 13, false, "Action, Adventure, Sci-Fi", 4.0, 38, 102, "Some storyline", ActorsDataSource().getActors()),
            Movie("Wonder Woman 1984", "https://www.figma.com/file/p3e0HZexHmxwQaN9NcwAD9/image/9b539e38b80ee9fe84a6f1891904326a948c37c8?fuid=743513597300855546", 13, false, "Action, Adventure, Fantasy", 5.0, 744, 120, "Some storyline", ActorsDataSource().getActors())
        )
    }
}