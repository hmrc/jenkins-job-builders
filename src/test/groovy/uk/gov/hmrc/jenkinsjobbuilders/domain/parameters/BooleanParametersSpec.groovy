package uk.gov.hmrc.jenkinsjobbuilders.domain.parameters

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobBuilder
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobParents
import uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.AbsoluteTimeoutWrapper

@Mixin(JobParents)
class BooleanParametersSpec extends Specification {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withParameters(BooleanParameter.booleanParameter("test-param", false, "some boolean parameter"))

        when:
        Job job = jobBuilder.build(jobParent())

        then:
        with(job.node) {
            properties.'hudson.model.ParametersDefinitionProperty'.parameterDefinitions.'hudson.model.BooleanParameterDefinition'[0].name.text() == 'test-param'
            properties.'hudson.model.ParametersDefinitionProperty'.parameterDefinitions.'hudson.model.BooleanParameterDefinition'[0].defaultValue.text() == 'false'
            properties.'hudson.model.ParametersDefinitionProperty'.parameterDefinitions.'hudson.model.BooleanParameterDefinition'[0].description.text() == 'some boolean parameter'
        }
    }

}
