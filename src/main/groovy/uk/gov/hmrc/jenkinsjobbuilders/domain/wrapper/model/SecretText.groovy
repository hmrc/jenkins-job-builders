package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.model

import uk.gov.hmrc.jenkinsjobbuilders.domain.Setting

class SecretText implements Setting {

    final String variable
    final String credentials

    SecretText(String variable, String credentials) {
        this.variable = variable
        this.credentials = credentials
    }

    static SecretText secretText(String variable, String credentials) {
        new SecretText(variable, credentials)
    }

    @Override
    Closure toDsl() {
        return {
            string(this.variable, this.credentials)
        }
    }
}