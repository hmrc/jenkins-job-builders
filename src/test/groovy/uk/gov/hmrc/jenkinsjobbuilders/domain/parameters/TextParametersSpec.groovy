package uk.gov.hmrc.jenkinsjobbuilders.domain.parameters

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobParents

@Mixin(JobParents)
class TextParametersSpec extends Specification {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withParameters(TextParameter.textParameter("test-param", "", "some text parameter"))

        when:
        Job job = jobBuilder.build(jobParent())

        then:
        with(job.node) {
            properties.'hudson.model.ParametersDefinitionProperty'.parameterDefinitions.'hudson.model.TextParameterDefinition'[0].name.text() == 'test-param'
            properties.'hudson.model.ParametersDefinitionProperty'.parameterDefinitions.'hudson.model.TextParameterDefinition'[0].defaultValue.text() == ''
            properties.'hudson.model.ParametersDefinitionProperty'.parameterDefinitions.'hudson.model.TextParameterDefinition'[0].description.text() == 'some text parameter'
        }
    }

}
