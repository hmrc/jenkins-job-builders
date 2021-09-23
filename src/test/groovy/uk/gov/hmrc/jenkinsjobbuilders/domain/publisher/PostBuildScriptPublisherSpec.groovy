package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher

import javaposse.jobdsl.dsl.Job
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder
import uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.Result

import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.PostBuildScriptPublisher.postBuildScriptPublisher

class PostBuildScriptPublisherSpec extends AbstractJobSpec {

    void 'test XML output with empty build stage result is still valid but it never runs'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description')
            .withPublishers(postBuildScriptPublisher(
                'test-script',
                []
            ))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            def postBuildScript = publishers.'org.jenkinsci.plugins.postbuildscript.PostBuildScript'
            postBuildScript.config.markBuildUnstable.text() == 'false'
            def postBuildStep = postBuildScript.config.buildSteps.'org.jenkinsci.plugins.postbuildscript.model.PostBuildStep'
            postBuildStep.buildSteps.'hudson.tasks.Shell'.command.text() == 'test-script'
            postBuildStep.results.text() == ''
        }
    }

    void 'test XML output with one build stage result'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description')
            .withPublishers(postBuildScriptPublisher(
                'test-script',
                [Result.SUCCESS]
            ))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            def postBuildScript = publishers.'org.jenkinsci.plugins.postbuildscript.PostBuildScript'
            postBuildScript.config.markBuildUnstable.text() == 'false'
            def postBuildStep = postBuildScript.config.buildSteps.'org.jenkinsci.plugins.postbuildscript.model.PostBuildStep'
            postBuildStep.buildSteps.'hudson.tasks.Shell'.command.text() == 'test-script'
            postBuildStep.results.string[0].text() == 'SUCCESS'
        }
    }

    void 'test XML output with two build stage results'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description')
            .withPublishers(postBuildScriptPublisher(
                'test-script',
                [Result.SUCCESS,Result.UNSTABLE]
            ))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            def postBuildScript = publishers.'org.jenkinsci.plugins.postbuildscript.PostBuildScript'
            postBuildScript.config.markBuildUnstable.text() == 'false'
            def postBuildStep = postBuildScript.config.buildSteps.'org.jenkinsci.plugins.postbuildscript.model.PostBuildStep'
            postBuildStep.buildSteps.'hudson.tasks.Shell'.command.text() == 'test-script'
            postBuildStep.results.string[0].text() == 'SUCCESS'
            postBuildStep.results.string[1].text() == 'UNSTABLE'
        }
    }
}
