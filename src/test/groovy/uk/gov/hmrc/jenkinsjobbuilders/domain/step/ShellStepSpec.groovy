package uk.gov.hmrc.jenkinsjobbuilders.domain.step

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder


import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.ShellStep.shellStep

class ShellStepSpec extends AbstractJobSpec {

    void 'test XML output when no unstableReturnCode set'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withSteps(shellStep('test-shell'))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            builders.'hudson.tasks.Shell' [0].command.text().contains("test-shell")
            builders.'hudson.tasks.Shell' [0].unstableReturn == []
        }
    }

    void 'test optional unstableReturnCode'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                withSteps(shellStep('test-shell', 22))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            builders.'hudson.tasks.Shell' [0].command.text().contains("test-shell")
            builders.'hudson.tasks.Shell' [0].unstableReturn.text() == 22.toString()
        }
    }
}
