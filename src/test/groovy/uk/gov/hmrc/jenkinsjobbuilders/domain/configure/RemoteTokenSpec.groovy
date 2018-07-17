package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

import javaposse.jobdsl.dsl.Job
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder
import uk.gov.hmrc.jenkinsjobbuilders.domain.step.RemoteTriggerStep

class RemoteTokenSpec extends AbstractJobSpec {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                withSteps(RemoteTriggerStep.remoteTriggerStep("https://example.com", "test-job", [id: "test-id"], false)).
                withConfigures(RemoteToken.remoteToken('remote-trigger-token'))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            builders.'org.jenkinsci.plugins.ParameterizedRemoteTrigger.RemoteBuildConfiguration'
            builders.'org.jenkinsci.plugins.ParameterizedRemoteTrigger.RemoteBuildConfiguration'.token.text() == "remote-trigger-token"
        }
    }
}
