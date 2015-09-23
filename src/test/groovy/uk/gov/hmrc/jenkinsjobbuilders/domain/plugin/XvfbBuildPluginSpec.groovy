package uk.gov.hmrc.jenkinsjobbuilders.domain.plugin

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobBuilder
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobParents

import static java.util.Arrays.asList
import static uk.gov.hmrc.jenkinsjobbuilders.domain.parameters.ChoiceParameter.choiceParameter
import static uk.gov.hmrc.jenkinsjobbuilders.domain.parameters.StringParameter.stringParameter
import static uk.gov.hmrc.jenkinsjobbuilders.domain.plugin.CheckStyleReportPlugin.checkStyleReportPlugin
import static uk.gov.hmrc.jenkinsjobbuilders.domain.plugin.CucumberReportPlugin.cucumberReportsPlugin
import static uk.gov.hmrc.jenkinsjobbuilders.domain.plugin.SCoverageReportPlugin.sCoverageReportPlugin
import static uk.gov.hmrc.jenkinsjobbuilders.domain.plugin.XvfbBuildPlugin.parallelXvfbBuildPlugin
import static uk.gov.hmrc.jenkinsjobbuilders.domain.plugin.XvfbBuildPlugin.xvfbBuildPlugin
import static uk.gov.hmrc.jenkinsjobbuilders.domain.plugin.XvfbBuildPlugin.xvfbBuildPlugin
import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.ArtifactsPublisher.artifactsPublisher
import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.BuildDescriptionPublisher.buildDescriptionByRegexPublisher
import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.HtmlReportsPublisher.htmlReportsPublisher
import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.JUnitReportsPublisher.jUnitReportsPublisher
import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.JobsTriggerPublisher.jobsTriggerPublisher
import static uk.gov.hmrc.jenkinsjobbuilders.domain.scm.CronScmTrigger.cronScmTrigger
import static uk.gov.hmrc.jenkinsjobbuilders.domain.scm.GitHubComScm.gitHubComScm
import static uk.gov.hmrc.jenkinsjobbuilders.domain.scm.GitHubScmTrigger.gitHubScmTrigger
import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.SbtStep.sbtStep
import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.ShellStep.shellStep
import static uk.gov.hmrc.jenkinsjobbuilders.domain.variables.ClasspathEnvironmentVariable.classpathEnvironmentVariable
import static uk.gov.hmrc.jenkinsjobbuilders.domain.variables.JdkEnvironmentVariable.JDK8
import static uk.gov.hmrc.jenkinsjobbuilders.domain.variables.PathEnvironmentVariable.pathEnvironmentVariable
import static uk.gov.hmrc.jenkinsjobbuilders.domain.variables.StringEnvironmentVariable.stringEnvironmentVariable
import static uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.NodeJsWrapper.nodeJsWrapper

@Mixin(JobParents)
class XvfbBuildPluginSpec extends Specification {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withPlugins(xvfbBuildPlugin())

        when:
        Job job = jobBuilder.build(jobParent())

        then:
        with(job.node) {
            buildWrappers.'org.jenkinsci.plugins.xvfb.XvfbBuildWrapper'.switch.text() == 'on'
            buildWrappers.'org.jenkinsci.plugins.xvfb.XvfbBuildWrapper'.parallelBuild.text() == 'false'
            buildWrappers.'org.jenkinsci.plugins.xvfb.XvfbBuildWrapper'.displayName.isEmpty()
            buildWrappers.'org.jenkinsci.plugins.xvfb.XvfbBuildWrapper'.displayNameOffset.text() == '1'
            buildWrappers.'org.jenkinsci.plugins.xvfb.XvfbBuildWrapper'.screen.text() == '1920x1080x24'
        }
    }

    void 'test parallel XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withPlugins(parallelXvfbBuildPlugin())

        when:
        Job job = jobBuilder.build(jobParent())

        then:
        with(job.node) {
            buildWrappers.'org.jenkinsci.plugins.xvfb.XvfbBuildWrapper'.parallelBuild.text() == 'true'
        }
    }
}
