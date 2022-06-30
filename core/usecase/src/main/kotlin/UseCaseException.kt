sealed class UseCaseException : Exception() {
    data class DatabaseException(override val message: String?) : UseCaseException()
}

fun RepositoryException.toUseCaseException() = when (this) {
    is RepositoryException.DatabaseException -> UseCaseException.DatabaseException(message)
}

fun QueryServiceException.toUseCaseException() = when (this) {
    is QueryServiceException.DatabaseException -> UseCaseException.DatabaseException(message)
}
