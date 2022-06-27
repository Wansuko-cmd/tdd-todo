sealed class QueryServiceException : Exception() {
    data class DatabaseException(override val message: String?) : QueryServiceException()
}
