package uk.gov.hmrc.jenkinsjobbuilders.domain

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.plugin.XvfbBuildPlugin
import uk.gov.hmrc.jenkinsjobbuilders.domain.step.ShellStep

import static uk.gov.hmrc.jenkinsjobbuilders.domain.Parameters.parameters
import static uk.gov.hmrc.jenkinsjobbuilders.domain.plugin.XvfbBuildPlugin.xvfbBuildPlugin
import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.ArtifactsPublisher.artifactsPublisher
import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.HtmlReportsPublisher.htmlReportsPublisher
import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.JUnitReportsPublisher.jUnitReportsPublisher
import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.JobsTriggerPublisher.jobsTriggerPublisher
import static uk.gov.hmrc.jenkinsjobbuilders.domain.scm.CronScmTrigger.cronScmTrigger
import static uk.gov.hmrc.jenkinsjobbuilders.domain.scm.GitHubComScm.gitHubComScm
import static uk.gov.hmrc.jenkinsjobbuilders.domain.scm.GitHubScmTrigger.gitHubScmTrigger
import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.ShellStep.shellStep

@Mixin(JobParents)
class JobBuilderSpec extends Specification {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description', 14, 10, gitHubComScm('example/example-repo')).
                                               withScmTriggers(cronScmTrigger('test-cron'), gitHubScmTrigger()).
                                               withSteps(shellStep('test-shell1'), shellStep('test-shell2')).
                                               withLabel('single-executor').
                                               withParameters(parameters(['NAME': 'TEST-PARAM'])).
                                               withPublishers(jUnitReportsPublisher('test-junit'),
                                                              htmlReportsPublisher(['target/test-reports/html-report': 'HTML Report']),
                                                              artifactsPublisher('test-artifacts'),
                                                              jobsTriggerPublisher('test-jobs')).
                                               withPlugins(xvfbBuildPlugin())

        when:
        Job job = jobBuilder.build(jobParent())

        then:
        job.name == 'test-job'

        with(job.node) {
            name() == 'project'
            description.text() == 'test-job-description'
            logRotator.daysToKeep.text() == '14'
            logRotator.numToKeep.text() == '10'
            assignedNode.text() == 'single-executor'
            properties.'hudson.model.ParametersDefinitionProperty'.parameterDefinitions.'hudson.model.StringParameterDefinition'.name.text() == 'NAME'
            properties.'hudson.model.ParametersDefinitionProperty'.parameterDefinitions.'hudson.model.StringParameterDefinition'.defaultValue.text() == 'TEST-PARAM'
            scm.userRemoteConfigs.'hudson.plugins.git.UserRemoteConfig'.url.text() == 'git@github.com:example/example-repo.git'
            scm.branches.'hudson.plugins.git.BranchSpec'.name.text() == 'master'
            triggers.'com.cloudbees.jenkins.gitHubPushTrigger'.spec.text() == ''
            triggers.'hudson.triggers.TimerTrigger'.spec.text() == 'test-cron'
            buildWrappers.'hudson.plugins.ansicolor.AnsiColorBuildWrapper'.colorMapName.text() == 'xterm'
            buildWrappers.'hudson.plugins.ws__cleanup.PreBuildCleanup'.deleteDirs.text() == 'false'
            builders.'hudson.tasks.Shell' [0].command.text().contains('test-shell1')
            builders.'hudson.tasks.Shell' [1].command.text().contains('test-shell2')
            publishers.'hudson.plugins.claim.ClaimPublisher'.text() == ''
            publishers.'hudson.tasks.junit.JUnitResultArchiver'.testResults.text() == 'test-junit'
            publishers.'htmlpublisher.HtmlPublisher'.reportTargets.'htmlpublisher.HtmlPublisherTarget'.reportDir[0].text() == 'target/test-reports/html-report'
            publishers.'htmlpublisher.HtmlPublisher'.reportTargets.'htmlpublisher.HtmlPublisherTarget'.reportName[0].text() == 'HTML Report'
            publishers.'hudson.tasks.ArtifactArchiver'.artifacts.text() == 'test-artifacts'
            publishers.'hudson.plugins.parameterizedtrigger.BuildTrigger'.configs.'hudson.plugins.parameterizedtrigger.BuildTriggerConfig' [0].projects.text() == 'test-jobs'
            publishers.'hudson.plugins.parameterizedtrigger.BuildTrigger'.configs.'hudson.plugins.parameterizedtrigger.BuildTriggerConfig' [0].condition.text() == 'SUCCESS'
        }
    }
}
