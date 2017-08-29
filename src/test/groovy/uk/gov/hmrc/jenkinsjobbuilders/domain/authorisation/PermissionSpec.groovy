package uk.gov.hmrc.jenkinsjobbuilders.domain.authorisation

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobParents
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

import static uk.gov.hmrc.jenkinsjobbuilders.domain.authorisation.Permission.permissionSetting

@Mixin(JobParents)
class PermissionSpec extends Specification {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                withPermissions(permissionSetting("dev-tools", "hudson.model.Item.Read"))


        when:
        Job job = jobBuilder.build(jobParent())

        then:
        with(job.node) {
            properties.'hudson.security.AuthorizationMatrixProperty'.permission.text() == 'hudson.model.Item.Read:dev-tools'
        }
    }
}
