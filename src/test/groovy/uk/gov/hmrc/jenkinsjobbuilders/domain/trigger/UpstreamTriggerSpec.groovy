package uk.gov.hmrc.jenkinsjobbuilders.domain.trigger

import javaposse.jobdsl.dsl.Job
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.Condition
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

import static uk.gov.hmrc.jenkinsjobbuilders.domain.trigger.UpstreamTrigger.upstreamTrigger

class UpstreamTriggerSpec extends AbstractJobSpec {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder =
                new JobBuilder('test', 'description')
                        .withTriggers(upstreamTrigger('upstream-job1,upstream-job2', Condition.SUCCESS))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            triggers.'jenkins.triggers.ReverseBuildTrigger'.upstreamProjects.text() == "upstream-job1,upstream-job2"
            triggers.'jenkins.triggers.ReverseBuildTrigger'.threshold.name.text() == "SUCCESS"
        }
    }
}
