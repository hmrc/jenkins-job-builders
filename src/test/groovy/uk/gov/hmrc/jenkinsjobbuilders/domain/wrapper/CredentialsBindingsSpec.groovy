package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec

import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

import static uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.model.SecretText.secretText
import static uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.model.SecretUsernamePassword.secretUsernamePassword
import static uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.model.ConjoinedSecretUsernamePassword.conjoinedSecretUsernamePassword

class CredentialsBindingsSpec extends AbstractJobSpec {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                withWrappers(
                        CredentialsBindings.credentialsBindings(
                                [
                                        secretText('test-variable-1', 'test-credentials-1'),
                                        secretText('test-variable-2', 'test-credentials-2')
                                ],
                                [
                                        secretUsernamePassword("user0", "pass0", "cred0"),
                                        secretUsernamePassword("user1", "pass1", "cred1")
                                ],
                                [
                                        conjoinedSecretUsernamePassword("userpass0", "cred0"),
                                        conjoinedSecretUsernamePassword("userpass1", "cred1")
                                ]
                        ))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        println(job.node)

        with(job.node) {
            buildWrappers.'org.jenkinsci.plugins.credentialsbinding.impl.SecretBuildWrapper'
                    .bindings.'org.jenkinsci.plugins.credentialsbinding.impl.StringBinding'
                    .variable[0].value() == 'test-variable-1'

            buildWrappers.'org.jenkinsci.plugins.credentialsbinding.impl.SecretBuildWrapper'
                    .bindings.'org.jenkinsci.plugins.credentialsbinding.impl.StringBinding'
                    .credentialsId[0].value() == 'test-credentials-1'

            buildWrappers.'org.jenkinsci.plugins.credentialsbinding.impl.SecretBuildWrapper'
                    .bindings.'org.jenkinsci.plugins.credentialsbinding.impl.StringBinding'
                    .variable[1].value() == 'test-variable-2'

            buildWrappers.'org.jenkinsci.plugins.credentialsbinding.impl.SecretBuildWrapper'
                    .bindings.'org.jenkinsci.plugins.credentialsbinding.impl.StringBinding'
                    .credentialsId[1].value() == 'test-credentials-2'



            buildWrappers.'org.jenkinsci.plugins.credentialsbinding.impl.SecretBuildWrapper'
                    .bindings.'org.jenkinsci.plugins.credentialsbinding.impl.UsernamePasswordMultiBinding'
                    .credentialsId[0].value() == 'cred0'

            buildWrappers.'org.jenkinsci.plugins.credentialsbinding.impl.SecretBuildWrapper'
                    .bindings.'org.jenkinsci.plugins.credentialsbinding.impl.UsernamePasswordMultiBinding'
                    .usernameVariable[0].value() == 'user0'

            buildWrappers.'org.jenkinsci.plugins.credentialsbinding.impl.SecretBuildWrapper'
                    .bindings.'org.jenkinsci.plugins.credentialsbinding.impl.UsernamePasswordMultiBinding'
                    .passwordVariable[0].value() == 'pass0'



            buildWrappers.'org.jenkinsci.plugins.credentialsbinding.impl.SecretBuildWrapper'
                    .bindings.'org.jenkinsci.plugins.credentialsbinding.impl.UsernamePasswordMultiBinding'
                    .credentialsId[1].value() == 'cred1'

            buildWrappers.'org.jenkinsci.plugins.credentialsbinding.impl.SecretBuildWrapper'
                    .bindings.'org.jenkinsci.plugins.credentialsbinding.impl.UsernamePasswordMultiBinding'
                    .usernameVariable[1].value() == 'user1'

            buildWrappers.'org.jenkinsci.plugins.credentialsbinding.impl.SecretBuildWrapper'
                    .bindings.'org.jenkinsci.plugins.credentialsbinding.impl.UsernamePasswordMultiBinding'
                    .passwordVariable[1].value() == 'pass1'



            buildWrappers.'org.jenkinsci.plugins.credentialsbinding.impl.SecretBuildWrapper'
                    .bindings.'org.jenkinsci.plugins.credentialsbinding.impl.UsernamePasswordBinding'
                    .variable[0].value() == 'userpass0'

            buildWrappers.'org.jenkinsci.plugins.credentialsbinding.impl.SecretBuildWrapper'
                    .bindings.'org.jenkinsci.plugins.credentialsbinding.impl.UsernamePasswordBinding'
                    .credentialsId[0].value() == 'cred0'

            buildWrappers.'org.jenkinsci.plugins.credentialsbinding.impl.SecretBuildWrapper'
                    .bindings.'org.jenkinsci.plugins.credentialsbinding.impl.UsernamePasswordBinding'
                    .variable[1].value() == 'userpass1'

            buildWrappers.'org.jenkinsci.plugins.credentialsbinding.impl.SecretBuildWrapper'
                    .bindings.'org.jenkinsci.plugins.credentialsbinding.impl.UsernamePasswordBinding'
                    .credentialsId[1].value() == 'cred1'
        }
    }
}
