package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobParents
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.JobsTriggerPublisher.*

@Mixin(JobParents)
class JobsTriggerPublisherSpec extends Specification {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withPublishers(jobsTriggerPublisher('test-project',
                                                       'FAILED', ['key1': 'value1', 'key2': 'value2']))

        when:
        Job job = jobBuilder.build(jobParent())

        then:
        with(job.node) {

            def buildTriggerConfig = publishers
                    .'hudson.plugins.parameterizedtrigger.BuildTrigger'.configs
                    .'hudson.plugins.parameterizedtrigger.BuildTriggerConfig'
            buildTriggerConfig.projects.text() == 'test-project'
            buildTriggerConfig.condition.text() == 'FAILED'
            buildTriggerConfig.configs.'hudson.plugins.parameterizedtrigger.PredefinedBuildParameters'
                    .properties.text() == "key1=value1\nkey2=value2"
        }
    }
}
