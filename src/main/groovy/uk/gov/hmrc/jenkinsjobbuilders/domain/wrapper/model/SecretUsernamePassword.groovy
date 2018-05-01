package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.model

class SecretUsernamePassword {

    final String userVariableName
    final String passwordVariableName
    final String credentials

    SecretUsernamePassword(String userVariableName, String passwordVariableName, String credentials) {
        this.userVariableName = userVariableName
        this.passwordVariableName = passwordVariableName
        this.credentials = credentials
    }

    static SecretUsernamePassword secretUsernamePassword(String userVariableName,
                                                         String passwordVariableName,
                                                         String credentials) {
        new SecretUsernamePassword(userVariableName, passwordVariableName, credentials)
    }

}
