package uk.gov.hmrc.jenkinsjobbuilders.domain.builder

import javaposse.jobdsl.dsl.Job
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec


import static java.util.Arrays.asList
import static uk.gov.hmrc.jenkinsjobbuilders.domain.authorisation.Permission.permissionSetting
import static uk.gov.hmrc.jenkinsjobbuilders.domain.parameters.ChoiceParameter.choiceParameter
import static uk.gov.hmrc.jenkinsjobbuilders.domain.parameters.StringParameter.stringParameter
import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.ArtifactsPublisher.artifactsPublisher
import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.BuildDescriptionPublisher.buildDescriptionByRegexPublisher
import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.ClaimBrokenBuildsPublisher.claimBrokenBuildsPublisher
import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.HtmlReportsPublisher.htmlReportsPublisher
import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.JUnitReportsPublisher.jUnitReportsPublisher
import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.JobsTriggerPublisher.jobsTriggerPublisher
import static uk.gov.hmrc.jenkinsjobbuilders.domain.trigger.BintrayArtifactTrigger.bintrayArtifactTrigger
import static uk.gov.hmrc.jenkinsjobbuilders.domain.trigger.CronTrigger.cronTrigger
import static uk.gov.hmrc.jenkinsjobbuilders.domain.scm.GitHubComScm.gitHubComScm
import static uk.gov.hmrc.jenkinsjobbuilders.domain.trigger.GitHubPushTrigger.gitHubPushTrigger
import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.SbtStep.sbtStep
import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.ShellStep.shellStep
import static uk.gov.hmrc.jenkinsjobbuilders.domain.variable.StringEnvironmentVariable.stringEnvironmentVariable
import static uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.ColorizeOutputWrapper.colorizeOutputWrapper
import static uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.UserVariablesWrapper.userVariablesWrapper
import static uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.NodeJsWrapper.nodeJsWrapper
import static uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.PreBuildCleanupWrapper.preBuildCleanUpWrapper

class JobBuilderSpec extends AbstractJobSpec {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withPermissions(permissionSetting("dev-tools", "hudson.model.Item.Read")).
                                               withLogRotator(14, 10).
                                               withScm(gitHubComScm('example/example-repo', 'test-credentials')).
                                               withTriggers(cronTrigger('test-cron'), gitHubPushTrigger(), bintrayArtifactTrigger("H * * * *", "hmrc", "release-candidates", ["test", "test-frontend"])).
                                               withSteps(shellStep('test-shell1'), sbtStep("ls test", ['clean test', 'dist publish'], '/tmp')).
                                               withPreScmSteps(shellStep('echo prescm')).
                                               withEnvironmentVariables(stringEnvironmentVariable('ENV_KEY', 'ENV_VALUE')).
                                               withEnvironmentVariablesScriptContent("mkdir -p \${TMP}").
                                               withEnvironmentVariablesGroovyScript("println \"Hello\"").
                                               withWrappers(nodeJsWrapper(), colorizeOutputWrapper(), preBuildCleanUpWrapper(), userVariablesWrapper()).
                                               withLabel('single-executor').
                                               withParameters(stringParameter('STRING-PARAM', 'STRING-VALUE'), choiceParameter('CHOICE-PARAM', asList('CHOICE-VALUE-1', 'CHOICE-VALUE-2'), 'CHOICE-DESC')).
                                               withPublishers(claimBrokenBuildsPublisher(),
                                                              jUnitReportsPublisher('test-junit'),
                                                              htmlReportsPublisher(['target/test-reports/html-report': 'HTML Report']),
                                                              artifactsPublisher('test-artifacts'),
                                                              jobsTriggerPublisher('test-jobs'),
                                                              buildDescriptionByRegexPublisher('test-regex')).
                                                withThrottle(['deployment'], 0, 1, false)
        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        job.name == 'test-job'

        println(job.node)

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
            properties.'hudson.security.AuthorizationMatrixProperty'.permission.text() == 'hudson.model.Item.Read:dev-tools'
            properties.'hudson.plugins.throttleconcurrents.ThrottleJobProperty'.maxConcurrentPerNode.text() == '0'
            properties.'hudson.plugins.throttleconcurrents.ThrottleJobProperty'.maxConcurrentTotal.text() == '1'
            properties.'hudson.plugins.throttleconcurrents.ThrottleJobProperty'.throttleEnabled.text() == 'true'
            properties.'hudson.plugins.throttleconcurrents.ThrottleJobProperty'.categories.string.text() == 'deployment'
            scm.userRemoteConfigs.'hudson.plugins.git.UserRemoteConfig'.url.text() == 'git@github.com:example/example-repo.git'
            scm.branches.'hudson.plugins.git.BranchSpec'.name.text() == 'master'
            triggers.'com.cloudbees.jenkins.gitHubPushTrigger'.spec.text() == ''
            triggers.'hudson.triggers.TimerTrigger'.spec.text() == 'test-cron'
            triggers.'org.jenkinsci.plugins.urltrigger.URLTrigger'.spec.text().contains('H * * * *')
            triggers.'org.jenkinsci.plugins.urltrigger.URLTrigger'.entries.'org.jenkinsci.plugins.urltrigger.URLTriggerEntry' [0].
                    url.text().contains('https://api.bintray.com/packages/hmrc/release-candidates/test/')
            triggers.'org.jenkinsci.plugins.urltrigger.URLTrigger'.entries.'org.jenkinsci.plugins.urltrigger.URLTriggerEntry' [0].contentTypes.
            'org.jenkinsci.plugins.urltrigger.content.JSONContentType'.jsonPaths.'org.jenkinsci.plugins.urltrigger.content.JSONContentEntry'.
                    jsonPath.text().contains('latest_version')
            triggers.'org.jenkinsci.plugins.urltrigger.URLTrigger'.entries.'org.jenkinsci.plugins.urltrigger.URLTriggerEntry' [1].
                    url.text().contains('https://api.bintray.com/packages/hmrc/release-candidates/test-frontend/')
            triggers.'org.jenkinsci.plugins.urltrigger.URLTrigger'.entries.'org.jenkinsci.plugins.urltrigger.URLTriggerEntry' [1].contentTypes.
            'org.jenkinsci.plugins.urltrigger.content.JSONContentType'.jsonPaths.'org.jenkinsci.plugins.urltrigger.content.JSONContentEntry'.
                    jsonPath.text().contains('latest_version')
            buildWrappers.'hudson.plugins.ansicolor.AnsiColorBuildWrapper'.colorMapName.text() == 'xterm'
            buildWrappers.'org-jenkinsci-plugins-builduser-BuildUser'.value.text() == ''
            buildWrappers.'hudson.plugins.ws__cleanup.PreBuildCleanup'.deleteDirs.text() == 'false'
            buildWrappers.'jenkins.plugins.nodejs.tools.NpmPackagesBuildWrapper'.nodeJSInstallationName.text() == 'node 0.10.28'
            buildWrappers.'EnvInjectBuildWrapper'.info.propertiesContent.text().contains('ENV_KEY=ENV_VALUE') == true
            buildWrappers.'EnvInjectBuildWrapper'.info.scriptContent.text().contains("mkdir -p \${TMP}") == true
            buildWrappers.'EnvInjectBuildWrapper'.info.groovyScriptContent.text().contains("println \"Hello\"") == true
            buildWrappers.'org.jenkinsci.plugins.preSCMbuildstep.PreSCMBuildStepsWrapper'.buildSteps.'hudson.tasks.Shell' [0].command.text().contains('echo prescm')
            builders.'hudson.tasks.Shell' [0].command.text().contains('test-shell1')
            builders.'hudson.tasks.Shell' [1].command.text().contains('ls test')
            builders.'hudson.tasks.Shell' [1].command.text().contains('mkdir -p /tmp')
            builders.'hudson.tasks.Shell' [1].command.text().contains('sbt clean test -Djava.io.tmpdir=/tmp')
            builders.'hudson.tasks.Shell' [1].command.text().contains('sbt dist publish -Djava.io.tmpdir=/tmp')
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

    void 'test scm clone options'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withScm(gitHubComScm('example/example-repo', 'test-credentials', 'master', 10))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        job.name == 'test-job'

        println(job.node)

        with(job.node) {
            scm.branches.'hudson.plugins.git.BranchSpec'.name.text() == 'master'
            scm.extensions.'hudson.plugins.git.extensions.impl.CloneOption'.shallow.text() == 'true'
            scm.extensions.'hudson.plugins.git.extensions.impl.CloneOption'.noTags.text() == 'false'
            scm.extensions.'hudson.plugins.git.extensions.impl.CloneOption'.depth.text() == '10'
        }
    }

    void 'test able to modify description after instantiation'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description')
            .withScm(gitHubComScm('example/example-repo', 'test-credentials', 'master', 10))
            .withExtendedDescription(' - appended')

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        job.name == 'test-job'

        println(job.node)

        with(job.node) {
            description.text() == 'test-job-description - appended'
        }
    }
}
