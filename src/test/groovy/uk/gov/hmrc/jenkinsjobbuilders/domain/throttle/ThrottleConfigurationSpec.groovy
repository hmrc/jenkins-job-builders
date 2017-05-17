package uk.gov.hmrc.jenkinsjobbuilders.domain.throttle

import javaposse.jobdsl.dsl.Job
import javaposse.jobdsl.dsl.helpers.Permissions
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobParents
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

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

@Mixin(JobParents)
class ThrottleConfigurationSpec extends Specification {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                withThrottle(['deployment'], 0, 1, false)


        when:
        Job job = jobBuilder.build(jobParent())

        then:
        with(job.node) {
            properties.'hudson.plugins.throttleconcurrents.ThrottleJobProperty'.maxConcurrentPerNode.text() == '0'
            properties.'hudson.plugins.throttleconcurrents.ThrottleJobProperty'.maxConcurrentTotal.text() == '1'
            properties.'hudson.plugins.throttleconcurrents.ThrottleJobProperty'.throttleEnabled.text() == 'true'
            properties.'hudson.plugins.throttleconcurrents.ThrottleJobProperty'.categories.string.text() == 'deployment'
        }
    }
}
