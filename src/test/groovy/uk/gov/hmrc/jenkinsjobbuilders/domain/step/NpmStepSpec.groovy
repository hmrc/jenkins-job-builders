package uk.gov.hmrc.jenkinsjobbuilders.domain.step

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobParents
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.NpmStep.npmStep

@Mixin(JobParents)
class NpmStepSpec extends Specification {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withSteps(npmStep(["test"]))

        when:
        Job job = jobBuilder.build(jobParent())

        then:
        with(job.node) {
            builders.'hudson.tasks.Shell' [0].command.text().contains("npm run test")
        }
    }

    void 'test prepended bash script'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withSteps(npmStep("nvm use 4.8.4", ["test"]))

        when:
        Job job = jobBuilder.build(jobParent())

        then:
        with(job.node) {
            builders.'hudson.tasks.Shell' [0].command.text().contains("nvm use 4.8.4")
            builders.'hudson.tasks.Shell' [0].command.text().contains("npm run test")
        }
    }
}
