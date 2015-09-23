package uk.gov.hmrc.jenkinsjobbuilders.domain.plugin

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobBuilder
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobParents

import static uk.gov.hmrc.jenkinsjobbuilders.domain.plugin.CucumberReportPlugin.cucumberReportsPlugin
import static uk.gov.hmrc.jenkinsjobbuilders.domain.plugin.SCoverageReportPlugin.sCoverageReportPlugin

@Mixin(JobParents)
class CucumberReportPluginSpec extends Specification {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withPlugins(cucumberReportsPlugin())

        when:
        Job job = jobBuilder.build(jobParent())

        then:
        with(job.node) {
            publishers.'net.masterthought.jenkins.CucumberReportPublisher'.jsonReportDirectory.text() == ''
        }
    }
}
