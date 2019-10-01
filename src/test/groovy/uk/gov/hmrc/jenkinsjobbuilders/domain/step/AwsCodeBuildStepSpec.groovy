package uk.gov.hmrc.jenkinsjobbuilders.domain.step

import javaposse.jobdsl.dsl.Job
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder
import uk.gov.hmrc.jenkinsjobbuilders.domain.configure.Configure


class AwsCodeBuildStepSpec extends AbstractJobSpec {

    private JobBuilder jobBuilder

    def setup() {
        jobBuilder = new JobBuilder('test-job', 'test-job-description')
    }


    def "it should add a code builder step to the job"() {
        given:
        final AwsCodeBuildStep step = new AwsCodeBuildStepBuilder()
                .withCredentialsId("some-credentials-id")
                .withProjectName("test-project-name")
                .build()
        jobBuilder.withConfigures([step] as List<Configure>)

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            builders.CodeBuilder[0].'credentialsType'.text().contains('jenkins')
            builders.CodeBuilder[0].'credentialsId'.text().contains('some-credentials-id')
            builders.CodeBuilder[0].'projectName'.text().contains('test-project-name')
            builders.CodeBuilder[0].'region'.text().contains('eu-west-2')
            builders.CodeBuilder[0].'gitCloneDepthOverride'.text()contains('')
        }
    }
}
