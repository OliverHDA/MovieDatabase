package ru.oliverhd.moviedatabase.model.dto

data class MoviePreviewListDTO(
    val id: Int?,
    val page: Int?,
    val results: Array<MoviePreviewDTO>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MoviePreviewListDTO

        if (id != other.id) return false
        if (page != other.page) return false
        if (!results.contentEquals(other.results)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (page ?: 0)
        result = 31 * result + results.contentHashCode()
        return result
    }

}