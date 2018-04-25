package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper

class SecretUsernamePasswordCredentialsWrapper implements Wrapper {

  private final String userVariableName
  private final String passwordVariableName
  private final String credentials

    SecretUsernamePasswordCredentialsWrapper(final String userVariableName,
                                             final String passwordVariableName,
                                             final String credentials) {
    this.userVariableName = userVariableName
    this.passwordVariableName = passwordVariableName
    this.credentials = credentials
  }

  static SecretUsernamePasswordCredentialsWrapper secretUsernamePasswordCredentialsWrapper(final String userVariableName,
                                                                                           final String passwordVariableName,
                                                                                           final String credentials) {
    return new SecretUsernamePasswordCredentialsWrapper(userVariableName,
                                                        passwordVariableName,
                                                        credentials)
  }

  @Override
  Closure toDsl() {
    return {
      credentialsBinding {
        usernamePassword(this.userVariableName, this.passwordVariableName, this.credentials)
      }
    }
  }
}
