package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper

import uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.model.SecretText

import static java.util.Arrays.asList

class SecretTextCredentialsWrapper implements Wrapper {

    private final List<SecretText> secretTexts

    SecretTextCredentialsWrapper(List<SecretText> secretTexts) {
        this.secretTexts = secretTexts
    }

    static SecretTextCredentialsWrapper secretTextCredentials(List<SecretText> secretTexts) {
        new SecretTextCredentialsWrapper(secretTexts)
    }

    static SecretTextCredentialsWrapper secretTextCredentials(SecretText... secretTexts) {
        new SecretTextCredentialsWrapper(asList(secretTexts))
    }

    @Override
    Closure toDsl() {
        return {
            credentialsBinding {
                secretTexts.each { secretText ->
                    string(secretText.variable, secretText.credentials)
                }
            }
        }
    }
}