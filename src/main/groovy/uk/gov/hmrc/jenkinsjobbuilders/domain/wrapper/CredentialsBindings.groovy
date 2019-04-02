package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper

import uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.model.SecretText
import uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.model.SecretUsernamePassword
import uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.model.ConjoinedSecretUsernamePassword

import static java.util.Arrays.asList

class CredentialsBindings implements Wrapper {

    private final List<SecretText> secretTexts

    private final List<SecretUsernamePassword> secretUsernamePasswords

    private final List<ConjoinedSecretUsernamePassword> conjoinedSecretUsernamePassword

    CredentialsBindings(List<SecretText> secretTexts, List<SecretUsernamePassword> secretUsernamePasswords, 
                        List<ConjoinedSecretUsernamePassword> conjoinedSecretUsernamePassword) {
        this.secretTexts = secretTexts
        this.secretUsernamePasswords = secretUsernamePasswords
        this.conjoinedSecretUsernamePassword = conjoinedSecretUsernamePassword
    }

    static CredentialsBindings credentialsBindings(List<SecretText> secretTexts,
                                                   List<SecretUsernamePassword> secretUsernamePasswords,
                                                   List<ConjoinedSecretUsernamePassword> conjoinedSecretUsernamePassword = []) {
        new CredentialsBindings(secretTexts, secretUsernamePasswords, conjoinedSecretUsernamePassword)
    }


    @Override
    Closure toDsl() {
        return {
            credentialsBinding {
                secretTexts.each { secretText ->
                    string(secretText.variable, secretText.credentials)
                }
                conjoinedSecretUsernamePassword.each { conjoinedSecretUsernamePassword ->
                    usernamePassword(conjoinedSecretUsernamePassword.variableName, conjoinedSecretUsernamePassword.credentials)
                }
                secretUsernamePasswords.each { s ->
                    usernamePassword(s.userVariableName, s.passwordVariableName, s.credentials)
                }
            }
        }
    }
}