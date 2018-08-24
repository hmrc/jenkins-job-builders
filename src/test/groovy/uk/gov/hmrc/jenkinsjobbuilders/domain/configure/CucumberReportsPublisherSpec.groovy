package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

import javaposse.jobdsl.dsl.Job
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

import static CucumberReportsPublisher.cucumberReportsPublisher

class CucumberReportsPublisherSpec extends AbstractJobSpec {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withConfigures(cucumberReportsPublisher())

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            publishers.'net.masterthought.jenkins.CucumberReportPublisher'.jsonReportDirectory.text() == 'target'
            publishers.'net.masterthought.jenkins.CucumberReportPublisher'.fileIncludePattern.text() == 'cucumber.json'
            publishers.'net.masterthought.jenkins.CucumberReportPublisher'.fileExcludePattern.text() == ''
        }
    }
}
