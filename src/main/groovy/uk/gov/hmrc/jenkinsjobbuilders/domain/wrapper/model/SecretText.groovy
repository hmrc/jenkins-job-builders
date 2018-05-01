package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.model

class SecretText {

    final String variable
    final String credentials

    SecretText(String variable, String credentials) {
        this.variable = variable
        this.credentials = credentials
    }

    static SecretText secretText(String variable, String credentials) {
        new SecretText(variable, credentials)
    }

}
