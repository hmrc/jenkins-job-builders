package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper

import javaposse.jobdsl.dsl.Job
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

import static uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.SecretUsernamePasswordCredentialsWrapper.secretUsernamePasswordCredentialsWrapper
import static uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.model.SecretUsernamePassword.secretUsernamePassword

class SecretUsernamePasswordCredentialsWrapperSpec extends AbstractJobSpec {

    void 'test XML output'() {
        given:

        def secretUsernamePasswords = [
            secretUsernamePassword("user0", "pass0", "cred0"),
            secretUsernamePassword("user1", "pass1", "cred1")
        ]

        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description')
                                       .withWrappers(secretUsernamePasswordCredentialsWrapper(secretUsernamePasswords))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            final def binding0 = buildWrappers
                    .'org.jenkinsci.plugins.credentialsbinding.impl.SecretBuildWrapper'
                    .bindings[0]
                    .'org.jenkinsci.plugins.credentialsbinding.impl.UsernamePasswordMultiBinding'

            binding0.credentialsId[0].value() == 'cred0'
            binding0.usernameVariable[0].value() == 'user0'
            binding0.passwordVariable[0].value() == 'pass0'

            final def binding1 = buildWrappers
                    .'org.jenkinsci.plugins.credentialsbinding.impl.SecretBuildWrapper'
                    .bindings[0]
                    .'org.jenkinsci.plugins.credentialsbinding.impl.UsernamePasswordMultiBinding'

            binding1.credentialsId[1].value() == 'cred1'
            binding1.usernameVariable[1].value() == 'user1'
            binding1.passwordVariable[1].value() == 'pass1'
        }
    }

}
