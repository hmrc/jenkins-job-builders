package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobParents
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

import static JobsTriggerStep.jobsTriggerStep

@Mixin(JobParents)
class JobsTriggerStepSpec extends Specification {

    void 'test XML output'() {
        given:
        Configure step = jobsTriggerStep(['test-project-1', 'test-project-2'],
                                    '*.properties',
                                    'FAIL',
                                    'SUCCESS',
                                    'UNSTABLE',
                                    'FAILURE')
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withConfigures(step)

        when:
        Job job = jobBuilder.build(jobParent())

        then:
        with(job.node) {
            def triggerConfig = builders
                    .'hudson.plugins.parameterizedtrigger.TriggerBuilder'.configs
                    .'hudson.plugins.parameterizedtrigger.BlockableBuildTriggerConfig'
            triggerConfig.projects.text() == 'test-project-1,test-project-2'
            def paramFactory = triggerConfig.configFactories.'hudson.plugins.parameterizedtrigger.FileBuildParameterFactory'
            paramFactory.filePattern.text() == '*.properties'
            paramFactory.noFilesFoundAction.text() == 'FAIL'
            triggerConfig.block.buildStepFailureThreshold.name.text() == 'SUCCESS'
            triggerConfig.block.failureThreshold.name.text() == 'UNSTABLE'
            triggerConfig.block.unstableThreshold.name.text() == 'FAILURE'
        }
    }
}
