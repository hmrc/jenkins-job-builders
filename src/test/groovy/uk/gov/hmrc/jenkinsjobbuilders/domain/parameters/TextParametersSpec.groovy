package uk.gov.hmrc.jenkinsjobbuilders.domain.parameters

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

class TextParametersSpec extends AbstractJobSpec {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withParameters(TextParameter.textParameter("test-param", "", "some text parameter"))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            properties.'hudson.model.ParametersDefinitionProperty'.parameterDefinitions.'hudson.model.TextParameterDefinition'[0].name.text() == 'test-param'
            properties.'hudson.model.ParametersDefinitionProperty'.parameterDefinitions.'hudson.model.TextParameterDefinition'[0].defaultValue.text() == ''
            properties.'hudson.model.ParametersDefinitionProperty'.parameterDefinitions.'hudson.model.TextParameterDefinition'[0].description.text() == 'some text parameter'
        }
    }

}
