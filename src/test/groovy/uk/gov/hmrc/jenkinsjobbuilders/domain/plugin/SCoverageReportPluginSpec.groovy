package uk.gov.hmrc.jenkinsjobbuilders.domain.plugin

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobBuilder
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobParents

import static uk.gov.hmrc.jenkinsjobbuilders.domain.plugin.SCoverageReportPlugin.sCoverageReportPlugin
import static uk.gov.hmrc.jenkinsjobbuilders.domain.plugin.XvfbBuildPlugin.parallelXvfbBuildPlugin
import static uk.gov.hmrc.jenkinsjobbuilders.domain.plugin.XvfbBuildPlugin.xvfbBuildPlugin

@Mixin(JobParents)
class SCoverageReportPluginSpec extends Specification {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withPlugins(sCoverageReportPlugin())

        when:
        Job job = jobBuilder.build(jobParent())

        then:
        with(job.node) {
            publishers.'org.jenkinsci.plugins.scoverage.ScoveragePublisher'.reportDir.text() == 'target/scala-2.11/scoverage-report'
        }
    }
}
