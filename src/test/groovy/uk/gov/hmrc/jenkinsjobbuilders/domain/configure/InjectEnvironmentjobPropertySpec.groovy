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
            properties.envInjectJobProperty.info.propertiesContent.text() == ''
            properties.envInjectJobProperty.info.propertiesFile.text() == ''
            properties.envInjectJobProperty.info.scriptFile.text() == ''
            properties.envInjectJobProperty.info.script.text() == ''
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
            properties.envInjectJobProperty.info.propertiesContent.text() == 'PropertyContent'
            properties.envInjectJobProperty.info.propertiesFile.text() == 'PropertyFile'
            properties.envInjectJobProperty.info.scriptFile.text() == 'PropertyScriptFile'
            properties.envInjectJobProperty.info.script.text() == 'PropertyScript'
        }
    }
}

