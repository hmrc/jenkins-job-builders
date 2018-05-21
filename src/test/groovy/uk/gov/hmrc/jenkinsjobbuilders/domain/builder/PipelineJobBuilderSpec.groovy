package uk.gov.hmrc.jenkinsjobbuilders.domain.builder

import groovy.xml.XmlUtil
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
import static uk.gov.hmrc.jenkinsjobbuilders.domain.scm.GitHubComScm.gitHubComScm
import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.SbtStep.sbtStep
import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.ShellStep.shellStep
import static uk.gov.hmrc.jenkinsjobbuilders.domain.trigger.BintrayArtifactTrigger.bintrayArtifactTrigger
import static uk.gov.hmrc.jenkinsjobbuilders.domain.trigger.CronTrigger.cronTrigger
import static uk.gov.hmrc.jenkinsjobbuilders.domain.trigger.GitHubPushTrigger.gitHubPushTrigger
import static uk.gov.hmrc.jenkinsjobbuilders.domain.variable.StringEnvironmentVariable.stringEnvironmentVariable
import static uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.ColorizeOutputWrapper.colorizeOutputWrapper
import static uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.NodeJsWrapper.nodeJsWrapper
import static uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.PreBuildCleanupWrapper.preBuildCleanUpWrapper
import static uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.UserVariablesWrapper.userVariablesWrapper

class PipelineJobBuilderSpec extends AbstractJobSpec {

    void 'test XML output'() {
        given:
        PipelineJobBuilder jobBuilder = new PipelineJobBuilder('test-job')

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        job.name == 'test-job-pipeline'

        println(job.node)

        with(job.node) {
            name() == XmlUtil.serialize(job.node)
            name() == 'project'
            scm.userRemoteConfigs.'hudson.plugins.git.UserRemoteConfig'.url.text() == 'git@github.com:example/example-repo.git'
            scm.branches.'hudson.plugins.git.BranchSpec'.name.text() == 'master'
            triggers.'com.cloudbees.jenkins.gitHubPushTrigger'.spec.text() == ''
            triggers.'hudson.triggers.TimerTrigger'.spec.text() == 'cron'
            triggers.'org.jenkinsci.plugins.urltrigger.URLTrigger'.spec.text().contains('*/2 * * * *')
        }
    }
}
