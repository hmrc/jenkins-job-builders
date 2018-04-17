package uk.gov.hmrc.jenkinsjobbuilders.domain.step

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec

import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.InjectEnvironmentVariablesStep.injectEnvironmentVariablesStep

class InjectEnvironmentVariablesStepSpec extends AbstractJobSpec {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withSteps(injectEnvironmentVariablesStep('env.properties'))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            builders.'EnvInjectBuilder' [0].info.propertiesFilePath.text().contains("env.properties")
        }
    }
}
