package uk.gov.hmrc.jenkinsjobbuilders.domain.builder

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobParents

import static java.util.Arrays.asList
import static uk.gov.hmrc.jenkinsjobbuilders.domain.parameters.ChoiceParameter.choiceParameter
import static uk.gov.hmrc.jenkinsjobbuilders.domain.parameters.StringParameter.stringParameter
import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.ArtifactsPublisher.artifactsPublisher
import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.BuildDescriptionPublisher.buildDescriptionByRegexPublisher
import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.ClaimBrokenBuildsPublisher.claimBrokenBuildsPublisher
import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.HtmlReportsPublisher.htmlReportsPublisher
import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.JUnitReportsPublisher.jUnitReportsPublisher
import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.JobsTriggerPublisher.jobsTriggerPublisher
import static uk.gov.hmrc.jenkinsjobbuilders.domain.scm.CronScmTrigger.cronScmTrigger
import static uk.gov.hmrc.jenkinsjobbuilders.domain.scm.GitHubComScm.gitHubComScm
import static uk.gov.hmrc.jenkinsjobbuilders.domain.scm.GitHubScmTrigger.gitHubScmTrigger
import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.BlockingJobsTriggerStep.jobsTriggerStep
import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.SbtStep.sbtStep
import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.ShellStep.shellStep
import static uk.gov.hmrc.jenkinsjobbuilders.domain.variable.StringEnvironmentVariable.stringEnvironmentVariable
import static uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.ColorizeOutputWrapper.colorizeOutputWrapper
import static uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.NodeJsWrapper.nodeJsWrapper
import static uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.PreBuildCleanupWrapper.preBuildCleanUpWrapper

@Mixin(JobParents)
class JobBuilderSpec extends Specification {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withLogRotator(14, 10).
                                               withScm(gitHubComScm('example/example-repo', 'test-credentials')).
                                               withScmTriggers(cronScmTrigger('test-cron'), gitHubScmTrigger()).
                                               withSteps(shellStep('test-shell1'), sbtStep('clean test', 'dist publish'), jobsTriggerStep('test-job2,test-job3',[foo : "bar", boo : "baz"])).
                                               withEnvironmentVariables(stringEnvironmentVariable('ENV_KEY', 'ENV_VALUE')).
                                               withWrappers(nodeJsWrapper(), colorizeOutputWrapper(), preBuildCleanUpWrapper()).
                                               withLabel('single-executor').
                                               withParameters(stringParameter('STRING-PARAM', 'STRING-VALUE'), choiceParameter('CHOICE-PARAM', asList('CHOICE-VALUE-1', 'CHOICE-VALUE-2'), 'CHOICE-DESC')).
                                               withPublishers(claimBrokenBuildsPublisher(),
                                                              jUnitReportsPublisher('test-junit'),
                                                              htmlReportsPublisher(['target/test-reports/html-report': 'HTML Report']),
                                                              artifactsPublisher('test-artifacts'),
                                                              jobsTriggerPublisher('test-jobs'),
                                                              buildDescriptionByRegexPublisher('test-regex'))

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
            properties.'hudson.model.ParametersDefinitionProperty'.parameterDefinitions.'hudson.model.StringParameterDefinition'.name.text() == 'STRING-PARAM'
            properties.'hudson.model.ParametersDefinitionProperty'.parameterDefinitions.'hudson.model.StringParameterDefinition'.defaultValue.text() == 'STRING-VALUE'
            properties.'hudson.model.ParametersDefinitionProperty'.parameterDefinitions.'hudson.model.ChoiceParameterDefinition'.name.text() == 'CHOICE-PARAM'
            properties.'hudson.model.ParametersDefinitionProperty'.parameterDefinitions.'hudson.model.ChoiceParameterDefinition'.description.text() == 'CHOICE-DESC'
            properties.'hudson.model.ParametersDefinitionProperty'.parameterDefinitions.'hudson.model.ChoiceParameterDefinition'.choices.isEmpty() == false
            scm.userRemoteConfigs.'hudson.plugins.git.UserRemoteConfig'.url.text() == 'git@github.com:example/example-repo.git'
            scm.branches.'hudson.plugins.git.BranchSpec'.name.text() == 'master'
            triggers.'com.cloudbees.jenkins.gitHubPushTrigger'.spec.text() == ''
            triggers.'hudson.triggers.TimerTrigger'.spec.text() == 'test-cron'
            buildWrappers.'hudson.plugins.ansicolor.AnsiColorBuildWrapper'.colorMapName.text() == 'xterm'
            buildWrappers.'hudson.plugins.ws__cleanup.PreBuildCleanup'.deleteDirs.text() == 'false'
            buildWrappers.'jenkins.plugins.nodejs.tools.NpmPackagesBuildWrapper'.nodeJSInstallationName.text() == 'node 0.10.28'
            buildWrappers.'EnvInjectBuildWrapper'.info.propertiesContent.text().contains('ENV_KEY=ENV_VALUE') == true
            builders.'hudson.tasks.Shell' [0].command.text().contains('test-shell1')
            builders.'hudson.tasks.Shell' [1].command.text().contains('sbt clean test -Djava.io.tmpdir=${WORKSPACE}/tmp')
            builders.'hudson.tasks.Shell' [1].command.text().contains('sbt dist publish -Djava.io.tmpdir=${WORKSPACE}/tmp')
            builders.'hudson.plugins.parameterizedtrigger.TriggerBuilder'.configs.'hudson.plugins.parameterizedtrigger.BlockableBuildTriggerConfig' [0].projects.text() == 'test-job2,test-job3'
            builders.'hudson.plugins.parameterizedtrigger.TriggerBuilder'.configs.'hudson.plugins.parameterizedtrigger.BlockableBuildTriggerConfig' [0].condition.text() == 'ALWAYS'
            builders.'hudson.plugins.parameterizedtrigger.TriggerBuilder'.configs.'hudson.plugins.parameterizedtrigger.BlockableBuildTriggerConfig' [0].triggerWithNoParameters.text() == 'false'
            builders.'hudson.plugins.parameterizedtrigger.TriggerBuilder'.configs.'hudson.plugins.parameterizedtrigger.BlockableBuildTriggerConfig' [0].configs.'hudson.plugins.parameterizedtrigger.PredefinedBuildParameters'.properties.text() == 'foo=bar\nboo=baz'
            builders.'hudson.plugins.parameterizedtrigger.TriggerBuilder'.configs.'hudson.plugins.parameterizedtrigger.BlockableBuildTriggerConfig' [0].block.unstableThreshold.name.text() == 'UNSTABLE'
            builders.'hudson.plugins.parameterizedtrigger.TriggerBuilder'.configs.'hudson.plugins.parameterizedtrigger.BlockableBuildTriggerConfig' [0].block.buildStepFailureThreshold.name.text() == 'FAILURE'
            builders.'hudson.plugins.parameterizedtrigger.TriggerBuilder'.configs.'hudson.plugins.parameterizedtrigger.BlockableBuildTriggerConfig' [0].block.failureThreshold.name.text() == 'FAILURE'
            publishers.'hudson.plugins.claim.ClaimPublisher'.text() == ''
            publishers.'hudson.tasks.junit.JUnitResultArchiver'.testResults.text() == 'test-junit'
            publishers.'htmlpublisher.HtmlPublisher'.reportTargets.'htmlpublisher.HtmlPublisherTarget'.reportDir[0].text() == 'target/test-reports/html-report'
            publishers.'htmlpublisher.HtmlPublisher'.reportTargets.'htmlpublisher.HtmlPublisherTarget'.reportName[0].text() == 'HTML Report'
            publishers.'hudson.tasks.ArtifactArchiver'.artifacts.text() == 'test-artifacts'
            publishers.'hudson.plugins.parameterizedtrigger.BuildTrigger'.configs.'hudson.plugins.parameterizedtrigger.BuildTriggerConfig' [0].projects.text() == 'test-jobs'
            publishers.'hudson.plugins.parameterizedtrigger.BuildTrigger'.configs.'hudson.plugins.parameterizedtrigger.BuildTriggerConfig' [0].condition.text() == 'SUCCESS'
            publishers.'hudson.plugins.descriptionsetter.DescriptionSetterPublisher'.regexp.text() == 'test-regex'
        }
    }
}
