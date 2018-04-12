package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder


import static CheckStyleReportsPublisher.checkStyleReportsPublisher

class CheckStyleReportsPublisherSpec extends AbstractJobSpec {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withConfigures(checkStyleReportsPublisher())

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            publishers.'hudson.plugins.checkstyle.CheckStylePublisher'.pluginName.text() == '[CHECKSTYLE]'
        }
    }
}
