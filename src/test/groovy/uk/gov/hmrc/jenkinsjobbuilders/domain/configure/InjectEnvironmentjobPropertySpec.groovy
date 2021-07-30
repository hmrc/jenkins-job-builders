package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

import javaposse.jobdsl.dsl.Job
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

import static InjectEnvironmentJobProperty.injectEnvironmentJobProperty

class InjectEnvironmentJobPropertySpec extends AbstractJobSpec {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                            withConfigures(injectEnvironmentJobProperty())

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        println(job.node)
        with(job.node) {
            properties.EnvInjectJobProperty.info.propertiesContent.text() == ''
            properties.EnvInjectJobProperty.info.propertiesFile.text() == ''
            properties.EnvInjectJobProperty.info.scriptFile.text() == ''
            properties.EnvInjectJobProperty.info.script.text() == ''
        }
    }
     void 'test XML output with parameters'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                            withConfigures(injectEnvironmentJobProperty()
                                                .withPropertiesContent('PropertyContent')
                                                .withPropertiesFile('PropertyFile')
                                                .withScriptFile('PropertyScriptFile')
                                                .withScript('PropertyScript')
                                            )

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        println(job.node)
        with(job.node) {
            properties.EnvInjectJobProperty.info.propertiesContent.text() == 'PropertyContent'
            properties.EnvInjectJobProperty.info.propertiesFile.text() == 'PropertyFile'
            properties.EnvInjectJobProperty.info.scriptFile.text() == 'PropertyScriptFile'
            properties.EnvInjectJobProperty.info.script.text() == 'PropertyScript'
        }
    }
}

