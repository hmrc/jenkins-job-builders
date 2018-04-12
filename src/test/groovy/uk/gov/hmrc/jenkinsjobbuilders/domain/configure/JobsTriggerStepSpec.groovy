package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec

import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

import static JobsTriggerStep.jobsTriggerStep

class JobsTriggerStepSpec extends AbstractJobSpec {

    void 'test XML output'() {
        given:
        Configure step = jobsTriggerStep(['test-project-1', 'test-project-2'],
                                         '*.properties',
                                         'FAIL',
                                         JobsTriggerStep.Threshold.SUCCESS,
                                         JobsTriggerStep.Threshold.UNSTABLE,
                                         JobsTriggerStep.Threshold.FAILURE)
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withConfigures(step)

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            def triggerConfig = builders
                    .'hudson.plugins.parameterizedtrigger.TriggerBuilder'.configs
                    .'hudson.plugins.parameterizedtrigger.BlockableBuildTriggerConfig'
            triggerConfig.projects.text() == 'test-project-1,test-project-2'
            triggerConfig.condition.text() == 'ALWAYS'
            def paramFactory = triggerConfig.configFactories.'hudson.plugins.parameterizedtrigger.FileBuildParameterFactory'
            paramFactory.filePattern.text() == '*.properties'
            paramFactory.noFilesFoundAction.text() == 'FAIL'

            assert 3 == triggerConfig.block[0].children().size()

            triggerConfig.block.buildStepFailureThreshold.name.text() == 'SUCCESS'
            triggerConfig.block.buildStepFailureThreshold.ordinal.text() == '0'
            triggerConfig.block.buildStepFailureThreshold.color.text() == 'BLUE'
            triggerConfig.block.buildStepFailureThreshold.completeBuild.text() == 'true'

            triggerConfig.block.failureThreshold.name.text() == 'UNSTABLE'
            triggerConfig.block.failureThreshold.ordinal.text() == '1'
            triggerConfig.block.failureThreshold.color.text() == 'YELLOW'
            triggerConfig.block.failureThreshold.completeBuild.text() == 'true'

            triggerConfig.block.unstableThreshold.name.text() == 'FAILURE'
            triggerConfig.block.unstableThreshold.ordinal.text() == '2'
            triggerConfig.block.unstableThreshold.color.text() == 'RED'
            triggerConfig.block.unstableThreshold.completeBuild.text() == 'true'
        }
    }

    void 'it should not write XML elements for thresholds of never'() {
        given:
        Configure step = jobsTriggerStep(['test-project-1', 'test-project-2'],
                                         '*.properties',
                                         'FAIL',
                                         JobsTriggerStep.Threshold.SUCCESS,
                                         JobsTriggerStep.Threshold.NEVER,
                                         JobsTriggerStep.Threshold.FAILURE)
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                withConfigures(step)

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            def triggerConfig = builders
                    .'hudson.plugins.parameterizedtrigger.TriggerBuilder'.configs
                    .'hudson.plugins.parameterizedtrigger.BlockableBuildTriggerConfig'

            assert 2 == triggerConfig.block[0].children().size() // There is no 'failureThreshold' element

            triggerConfig.block.buildStepFailureThreshold.name.text() == 'SUCCESS'
            triggerConfig.block.buildStepFailureThreshold.ordinal.text() == '0'
            triggerConfig.block.buildStepFailureThreshold.color.text() == 'BLUE'
            triggerConfig.block.buildStepFailureThreshold.completeBuild.text() == 'true'

            triggerConfig.block.unstableThreshold.name.text() == 'FAILURE'
            triggerConfig.block.unstableThreshold.ordinal.text() == '2'
            triggerConfig.block.unstableThreshold.color.text() == 'RED'
            triggerConfig.block.unstableThreshold.completeBuild.text() == 'true'
        }
    }

}
