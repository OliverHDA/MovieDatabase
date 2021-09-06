package ru.oliverhd.moviedatabase.model.dto

data class GenreListDTO(
    val genres: Array<GenreDTO>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GenreListDTO

        if (!genres.contentEquals(other.genres)) return false

        return true
    }

    override fun hashCode(): Int {
        return genres.contentHashCode()
    }
}