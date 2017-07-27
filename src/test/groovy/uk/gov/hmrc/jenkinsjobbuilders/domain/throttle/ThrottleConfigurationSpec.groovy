package uk.gov.hmrc.jenkinsjobbuilders.domain.throttle

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobParents
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

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
