package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper

import uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.model.SecretText
import uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.model.SecretUsernamePassword

import static java.util.Arrays.asList

class CredentialsBindings implements Wrapper {

    private final List<SecretText> secretTexts

    private final List<SecretUsernamePassword> secretUsernamePasswords


    CredentialsBindings(List<SecretText> secretTexts, List<SecretUsernamePassword> secretUsernamePasswords) {
        this.secretTexts = secretTexts
        this.secretUsernamePasswords = secretUsernamePasswords
    }

    static CredentialsBindings credentialsBindings(List<SecretText> secretTexts,
                                                   List<SecretUsernamePassword> secretUsernamePasswords) {
        new CredentialsBindings(secretTexts, secretUsernamePasswords)
    }


    @Override
    Closure toDsl() {
        return {
            credentialsBinding {
                secretTexts.each { secretText ->
                    string(secretText.variable, secretText.credentials)
                }
                secretUsernamePasswords.each { s ->
                    usernamePassword(s.userVariableName, s.passwordVariableName, s.credentials)
                }
            }
        }
    }
}