package uk.gov.hmrc.jenkinsjobbuilders.domain.step

import javaposse.jobdsl.dsl.Job
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

class RemoteTriggerStep2Spec extends AbstractJobSpec {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                withConfigures(
                    RemoteTriggerStep2.remoteTriggerStep("https://example.com", "test-job", [id: "test-id", foo: "bar"], false)
                    .withRemoteJobToken("remote-token")
                )

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            builders.'org.jenkinsci.plugins.ParameterizedRemoteTrigger.RemoteBuildConfiguration'
            builders.'org.jenkinsci.plugins.ParameterizedRemoteTrigger.RemoteBuildConfiguration'.remoteJenkinsName.text() == "https://example.com"
            builders.'org.jenkinsci.plugins.ParameterizedRemoteTrigger.RemoteBuildConfiguration'.job.text() == "test-job"
            builders.'org.jenkinsci.plugins.ParameterizedRemoteTrigger.RemoteBuildConfiguration'.token.text() == "remote-token"
            builders.'org.jenkinsci.plugins.ParameterizedRemoteTrigger.RemoteBuildConfiguration'.shouldNotFailBuild.text() == "true"
            builders.'org.jenkinsci.plugins.ParameterizedRemoteTrigger.RemoteBuildConfiguration'.parameters2.parameters.'org.jenkinsci.plugins.ParameterizedRemoteTrigger.parameters2.MapParameter'[0].name.text() == "id"
            builders.'org.jenkinsci.plugins.ParameterizedRemoteTrigger.RemoteBuildConfiguration'.parameters2.parameters.'org.jenkinsci.plugins.ParameterizedRemoteTrigger.parameters2.MapParameter'[0].value.text() == "test-id"
            builders.'org.jenkinsci.plugins.ParameterizedRemoteTrigger.RemoteBuildConfiguration'.parameters2.parameters.'org.jenkinsci.plugins.ParameterizedRemoteTrigger.parameters2.MapParameter'[1].name.text() == "foo"
            builders.'org.jenkinsci.plugins.ParameterizedRemoteTrigger.RemoteBuildConfiguration'.parameters2.parameters.'org.jenkinsci.plugins.ParameterizedRemoteTrigger.parameters2.MapParameter'[1].value.text() == "bar"

        }
    }
}
