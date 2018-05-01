package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper

import uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.model.SecretUsernamePassword

class SecretUsernamePasswordCredentialsWrapper implements Wrapper {

  List<SecretUsernamePassword> secretUsernamePasswords

  SecretUsernamePasswordCredentialsWrapper(final List<SecretUsernamePassword> secretUsernamePasswords) {
    this.secretUsernamePasswords = secretUsernamePasswords
  }

  static SecretUsernamePasswordCredentialsWrapper secretUsernamePasswordCredentialsWrapper(
          final List<SecretUsernamePassword> secretUsernamePasswords) {

    return new SecretUsernamePasswordCredentialsWrapper(secretUsernamePasswords)

  }

  @Override
  Closure toDsl() {
    return {
      credentialsBinding {
        secretUsernamePasswords.each { s ->
          usernamePassword(s.userVariableName, s.passwordVariableName, s.credentials)
        }
      }
    }
  }
}
