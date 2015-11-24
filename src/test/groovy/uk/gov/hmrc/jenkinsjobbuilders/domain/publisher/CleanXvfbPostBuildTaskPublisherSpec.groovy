package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobBuilder
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobParents

import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.CleanXvfbPostBuildTaskPublisher.cleanXvfbPostBuildTaskPublisher
import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.PostBuildTaskPublisher.postBuildTaskPublisher

@Mixin(JobParents)
class CleanXvfbPostBuildTaskPublisherSpec extends Specification {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withPublishers(cleanXvfbPostBuildTaskPublisher())

        when:
        Job job = jobBuilder.build(jobParent())

        then:
        with(job.node) {
            publishers.'hudson.plugins.postbuildtask.PostbuildTask'.tasks.'hudson.plugins.postbuildtask.TaskProperties'.logTexts.'hudson.plugins.postbuildtask.LogProperties'.logText.text() == 'Xvfb starting(.*)'
            publishers.'hudson.plugins.postbuildtask.PostbuildTask'.tasks.'hudson.plugins.postbuildtask.TaskProperties'.EscalateStatus.text() == 'true'
            publishers.'hudson.plugins.postbuildtask.PostbuildTask'.tasks.'hudson.plugins.postbuildtask.TaskProperties'.RunIfJobSuccessful.text() == 'false'
            publishers.'hudson.plugins.postbuildtask.PostbuildTask'.tasks.'hudson.plugins.postbuildtask.TaskProperties'.script.text().contains('pkill Xvfb')
        }
    }
}
