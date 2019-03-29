package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.model

class ConjoinedSecretUsernamePassword {

    final String variableName
    final String credentials

    ConjoinedSecretUsernamePassword(String variableName, String credentials) {
        this.variableName = variableName
        this.credentials = credentials
    }

    static ConjoinedSecretUsernamePassword conjoinedSecretUsernamePassword(String variableName,
                                                         String credentials) {
        new ConjoinedSecretUsernamePassword(variableName, credentials)
    }

}
