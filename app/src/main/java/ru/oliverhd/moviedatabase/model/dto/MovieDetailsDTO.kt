package ru.oliverhd.moviedatabase.model.dto

data class MovieDetailsDTO(
    val genres: Array<GenreDTO>,
    val id: Int,
    val original_title: String,
    val overview: String?,
    val poster_path: String?,
    val release_date: String,
    val title: String,
    val vote_average: Double
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MovieDetailsDTO

        if (!genres.contentEquals(other.genres)) return false

        return true
    }

    override fun hashCode(): Int {
        return genres.contentHashCode()
    }
}