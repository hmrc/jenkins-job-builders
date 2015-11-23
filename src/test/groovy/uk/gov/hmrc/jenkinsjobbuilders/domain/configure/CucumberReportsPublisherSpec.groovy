package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobBuilder
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobParents

import static CucumberReportsPublisher.cucumberReportsPublisher

@Mixin(JobParents)
class CucumberReportsPublisherSpec extends Specification {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withConfigures(cucumberReportsPublisher())

        when:
        Job job = jobBuilder.build(jobParent())

        then:
        with(job.node) {
            publishers.'net.masterthought.jenkins.CucumberReportPublisher'.jsonReportDirectory.text() == ''
        }
    }
}
