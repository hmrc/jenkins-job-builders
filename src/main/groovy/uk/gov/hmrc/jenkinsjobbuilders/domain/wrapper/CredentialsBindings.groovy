package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper

import uk.gov.hmrc.jenkinsjobbuilders.domain.Setting
import uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.model.ConjoinedSecretUsernamePassword
import uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.model.SecretText
import uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.model.SecretUsernamePassword

class CredentialsBindings implements Setting {

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

    static CredentialsBindings combineCredentialsBindings(CredentialsBindings first, CredentialsBindings second) {
        if (first == null) {
            return second
        } else if (second == null) {
            return first
        } else {
            return credentialsBindings(
                    first.secretTexts + second.secretTexts,
                    first.secretUsernamePasswords + second.secretUsernamePasswords,
                    first.conjoinedSecretUsernamePassword + second.conjoinedSecretUsernamePassword
            )
        }
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