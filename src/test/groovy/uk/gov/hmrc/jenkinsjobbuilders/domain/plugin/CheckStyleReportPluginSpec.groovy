package uk.gov.hmrc.jenkinsjobbuilders.domain.plugin

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobBuilder
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobParents

import static uk.gov.hmrc.jenkinsjobbuilders.domain.plugin.CheckStyleReportPlugin.checkStyleReportPlugin
import static uk.gov.hmrc.jenkinsjobbuilders.domain.plugin.CucumberReportPlugin.cucumberReportsPlugin

@Mixin(JobParents)
class CheckStyleReportPluginSpec extends Specification {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withPlugins(checkStyleReportPlugin())

        when:
        Job job = jobBuilder.build(jobParent())

        then:
        with(job.node) {
            publishers.'hudson.plugins.checkstyle.CheckStylePublisher'.pluginName.text() == '[CHECKSTYLE]'
        }
    }
}
