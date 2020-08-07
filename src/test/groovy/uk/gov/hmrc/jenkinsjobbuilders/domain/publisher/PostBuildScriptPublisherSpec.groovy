package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher

import javaposse.jobdsl.dsl.Job
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.PostBuildScriptPublisher.postBuildScriptPublisher

class PostBuildScriptPublisherSpec extends AbstractJobSpec {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withPublishers(postBuildScriptPublisher('test-script'))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            def postBuildScript = publishers.'org.jenkinsci.plugins.postbuildscript.PostBuildScript'
            postBuildScript.scriptOnlyIfSuccess.text() == 'false'
            postBuildScript.scriptOnlyIfFailure.text() == 'true'
            postBuildScript.markBuildUnstable.text() == 'false'
            postBuildScript.buildSteps.'hudson.tasks.Shell'.command.text() == 'test-script'
        }
    }
}
