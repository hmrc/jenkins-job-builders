package uk.gov.hmrc.jenkinsjobbuilders.domain.authorisation

import javaposse.jobdsl.dsl.Job
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

import static uk.gov.hmrc.jenkinsjobbuilders.domain.authorisation.Permission.permissionSetting

class PermissionSpec extends AbstractJobSpec {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                withPermissions(permissionSetting("dev-tools", 'hudson.model.Item.Read'))


        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            properties.'hudson.security.AuthorizationMatrixProperty'.permission.text() == 'hudson.model.Item.Read:dev-tools'
        }
    }
}
