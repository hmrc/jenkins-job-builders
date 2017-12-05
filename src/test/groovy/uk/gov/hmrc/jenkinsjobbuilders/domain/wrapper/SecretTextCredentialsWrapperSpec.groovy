package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobParents
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder
import uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.model.SecretText

import static uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.model.SecretText.secretText

@Mixin(JobParents)
class SecretTextCredentialsWrapperSpec extends Specification {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                withWrappers(
                        SecretTextCredentialsWrapper.secretTextCredentials(
                                secretText('test-variable-1', 'test-credentials-1'),
                                secretText('test-variable-2', 'test-credentials-2')
                        ))

        when:
        Job job = jobBuilder.build(jobParent())

        then:
        with(job.node) {
            buildWrappers.'org.jenkinsci.plugins.credentialsbinding.impl.SecretBuildWrapper'.bindings.'org.jenkinsci.plugins.credentialsbinding.impl.StringBinding'.variable[0].value() == 'test-variable-1'
            buildWrappers.'org.jenkinsci.plugins.credentialsbinding.impl.SecretBuildWrapper'.bindings.'org.jenkinsci.plugins.credentialsbinding.impl.StringBinding'.variable[1].value() == 'test-variable-2'
        }
    }

}
