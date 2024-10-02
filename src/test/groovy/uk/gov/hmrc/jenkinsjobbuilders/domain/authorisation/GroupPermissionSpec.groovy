package uk.gov.hmrc.jenkinsjobbuilders.domain.authorisation

import javaposse.jobdsl.dsl.Job
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

import static uk.gov.hmrc.jenkinsjobbuilders.domain.authorisation.GroupPermission.permissionSetting

class GroupPermissionSpec extends AbstractJobSpec {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                withGroupPermissions(permissionSetting("dev-tools", 'hudson.model.Item.Read'))


        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            def authorizationMatrixProperty  = properties.'hudson.security.AuthorizationMatrixProperty'

            authorizationMatrixProperty.permission.text() == 'GROUP:hudson.model.Item.Read:dev-tools'
            authorizationMatrixProperty.blocksInheritance.text() == "false"
        }
    }
}
