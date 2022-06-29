sealed class RepositoryException : Exception() {
    data class DatabaseException(override val message: String?) : RepositoryException()
}
