package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder


import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.PostBuildTaskPublisher.postBuildTaskPublisher

class PostBuildTaskPublisherSpec extends AbstractJobSpec {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withPublishers(postBuildTaskPublisher("test-log-text", 'test-script'))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            publishers.'hudson.plugins.postbuildtask.PostbuildTask'.tasks.'hudson.plugins.postbuildtask.TaskProperties'.logTexts.'hudson.plugins.postbuildtask.LogProperties'.logText.text() == 'test-log-text'
            publishers.'hudson.plugins.postbuildtask.PostbuildTask'.tasks.'hudson.plugins.postbuildtask.TaskProperties'.EscalateStatus.text() == 'true'
            publishers.'hudson.plugins.postbuildtask.PostbuildTask'.tasks.'hudson.plugins.postbuildtask.TaskProperties'.RunIfJobSuccessful.text() == 'false'
            publishers.'hudson.plugins.postbuildtask.PostbuildTask'.tasks.'hudson.plugins.postbuildtask.TaskProperties'.script.text() == 'test-script'
        }
    }
}
