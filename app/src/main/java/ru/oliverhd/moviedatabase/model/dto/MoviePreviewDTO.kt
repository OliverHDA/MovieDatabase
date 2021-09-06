package ru.oliverhd.moviedatabase.model.dto

data class MoviePreviewDTO(
    val genre_ids: Array<Int>,
    val id: Int,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MoviePreviewDTO

        if (!genre_ids.contentEquals(other.genre_ids)) return false
        if (id != other.id) return false
        if (poster_path != other.poster_path) return false
        if (release_date != other.release_date) return false
        if (title != other.title) return false
        if (vote_average != other.vote_average) return false

        return true
    }

    override fun hashCode(): Int {
        var result = genre_ids.contentHashCode()
        result = 31 * result + id
        result = 31 * result + poster_path.hashCode()
        result = 31 * result + release_date.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + vote_average.hashCode()
        return result
    }
}