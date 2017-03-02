package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper

class SecretTextCredentialsWrapper implements Wrapper {

    private final String variable
    private final String credentials

    SecretTextCredentialsWrapper(String variable, String credentials) {
        this.variable = variable
        this.credentials = credentials
    }

    static SecretTextCredentialsWrapper secretTextCredentials(String variable, String credentials) {
        new SecretTextCredentialsWrapper(variable, credentials)
    }

    @Override
    Closure toDsl() {
        return {
            credentialsBinding {
                string(this.variable, this.credentials)
            }
        }
    }
}