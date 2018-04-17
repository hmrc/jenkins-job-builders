package uk.gov.hmrc.jenkinsjobbuilders.domain.throttle

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec

import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

class ThrottleConfigurationSpec extends AbstractJobSpec {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                withThrottle(['deployment'], 0, 1, false)


        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            properties.'hudson.plugins.throttleconcurrents.ThrottleJobProperty'.maxConcurrentPerNode.text() == '0'
            properties.'hudson.plugins.throttleconcurrents.ThrottleJobProperty'.maxConcurrentTotal.text() == '1'
            properties.'hudson.plugins.throttleconcurrents.ThrottleJobProperty'.throttleEnabled.text() == 'true'
            properties.'hudson.plugins.throttleconcurrents.ThrottleJobProperty'.categories.string.text() == 'deployment'
        }
    }
}
