package uk.gov.hmrc.jenkinsjobbuilders.domain.step

import groovy.transform.CompileStatic
import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobParents
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.JobsTriggerStep.jobsTriggerStep

@Mixin(JobParents)
class JobsTriggerStepSpec extends Specification {

    void 'test XML output'() {
        given:
        Step step = jobsTriggerStep('test-project',
                                    ['key1': 'value1', 'key2': 'value2'],
                                    '*.properties',
                                    'FAIL',
                                    'SUCCESS',
                                    'UNSTABLE',
                                    'FAILURE')
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withSteps(step)

        when:
        Job job = jobBuilder.build(jobParent())

        then:
        with(job.node) {
            def buildTriggerConfig = builders
                    .'hudson.plugins.parameterizedtrigger.TriggerBuilder'.configs
                    .'hudson.plugins.parameterizedtrigger.BlockableBuildTriggerConfig'
            buildTriggerConfig.projects.text() == 'test-project'
            buildTriggerConfig.condition.text() == 'ALWAYS'
            buildTriggerConfig.configs.'hudson.plugins.parameterizedtrigger.PredefinedBuildParameters'
                    .properties.text() == "key1=value1\nkey2=value2"
            buildTriggerConfig.block.buildStepFailureThreshold.name.text() == 'SUCCESS'
            buildTriggerConfig.block.failureThreshold.name.text() == 'UNSTABLE'
            buildTriggerConfig.block.unstableThreshold.name.text() == 'FAILURE'
        }
    }
}
