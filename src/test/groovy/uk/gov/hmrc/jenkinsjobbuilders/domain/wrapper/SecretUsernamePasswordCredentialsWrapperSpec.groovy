package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper

import javaposse.jobdsl.dsl.Job
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

class SecretUsernamePasswordCredentialsWrapperSpec extends AbstractJobSpec {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withWrappers(SecretUsernamePasswordCredentialsWrapper.secretUsernamePasswordCredentialsWrapper('user-name-var',
                                                                                                                                              'password-var',
                                                                                                                                              'my-secret-credentials-id'))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            final def binding = buildWrappers.'org.jenkinsci.plugins.credentialsbinding.impl.SecretBuildWrapper'.bindings.'org.jenkinsci.plugins.credentialsbinding.impl.UsernamePasswordMultiBinding'
            binding.credentialsId[0].value() == 'my-secret-credentials-id'
            binding.usernameVariable [0].value() == 'user-name-var'
            binding.passwordVariable [0].value() == 'password-var'
        }
    }

}
