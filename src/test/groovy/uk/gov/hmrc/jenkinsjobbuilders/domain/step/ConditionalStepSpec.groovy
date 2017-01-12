package uk.gov.hmrc.jenkinsjobbuilders.domain.step

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobParents
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.ConditionalShellStep.conditionalShellStep
import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.StepCondition.EvaluationFailureBehaviour.DONT_RUN
import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.StepCondition.Cause.UPSTREAM_CAUSE
import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.StepCondition.runCondition

@Mixin(JobParents)
class ConditionalStepSpec extends Specification {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description')
            .withSteps(conditionalShellStep('hello-world', runCondition().isNot().causedBy(UPSTREAM_CAUSE).andIfFailure(DONT_RUN)))

        when:
        Job job = jobBuilder.build(jobParent())

        then:
        with(job.node) {
            println(builders.'org.jenkinsci.plugins.conditionalbuildstep.singlestep.SingleConditionalBuilder')
            builders.'org.jenkinsci.plugins.conditionalbuildstep.singlestep.SingleConditionalBuilder'.buildStep.command.text().contains('hello-world')
            builders.'org.jenkinsci.plugins.conditionalbuildstep.singlestep.SingleConditionalBuilder'.condition.condition.buildCause.text().contains('UPSTREAM_CAUSE')
        }
    }
}
