package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

import static uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.TimestampsWrapper.timestampsWrapper

class TimestampsWrapperSpec extends AbstractJobSpec {

    void 'test with timstamper'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description')
            .withWrappers(timestampsWrapper())

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            buildWrappers.'hudson.plugins.timestamper.TimestamperBuildWrapper'
        }
    }

    void 'test without timstamper'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description')

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            ! buildWrappers.'hudson.plugins.timestamper.TimestamperBuildWrapper'
        }
    }
}
