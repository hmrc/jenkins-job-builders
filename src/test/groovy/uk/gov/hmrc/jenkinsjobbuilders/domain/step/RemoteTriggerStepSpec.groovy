package uk.gov.hmrc.jenkinsjobbuilders.domain.step

import javaposse.jobdsl.dsl.Job
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

class RemoteTriggerStepSpec extends AbstractJobSpec {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                withSteps(RemoteTriggerStep.remoteTriggerStep("https://example.com", "test-job", [id: "test-id"], false))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            builders.'org.jenkinsci.plugins.ParameterizedRemoteTrigger.RemoteBuildConfiguration'
            builders.'org.jenkinsci.plugins.ParameterizedRemoteTrigger.RemoteBuildConfiguration'.remoteJenkinsName.text() == "https://example.com"
            builders.'org.jenkinsci.plugins.ParameterizedRemoteTrigger.RemoteBuildConfiguration'.job.text() == "test-job"
            builders.'org.jenkinsci.plugins.ParameterizedRemoteTrigger.RemoteBuildConfiguration'.parameters.text() == "id=test-id"
            builders.'org.jenkinsci.plugins.ParameterizedRemoteTrigger.RemoteBuildConfiguration'.shouldNotFailBuild.text() == "true"
        }
    }
}
