package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

import javaposse.jobdsl.dsl.Folder
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.authorisation.Permission
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.FolderBuilder

import static uk.gov.hmrc.jenkinsjobbuilders.domain.authorisation.Permission.permissionSetting
import static uk.gov.hmrc.jenkinsjobbuilders.domain.configure.FolderInheritanceStrategy.folderInheritanceStrategy
import static uk.gov.hmrc.jenkinsjobbuilders.domain.configure.InheritanceStrategy.NON_INHERITING_STRATEGY

class FolderInheritanceStrategySpec extends AbstractJobSpec {

    def "text XML output"() {
        given:
        final InheritanceStrategy inheritanceStrategy = NON_INHERITING_STRATEGY

        final Set<Permission> permissions = [
                permissionSetting("ci-admin", "hudson.model.Item.Read")
        ]

        FolderBuilder jobBuilder = new FolderBuilder('test-folder')
                .withPermissions(permissions)
                .withConfigures([folderInheritanceStrategy(inheritanceStrategy)])

        when:
        Folder folder = jobBuilder.build(JOB_PARENT)

        then:
        with(folder.node) {
            final NodeList configuredPermissions = it.'properties'.'com.cloudbees.hudson.plugins.folder.properties.AuthorizationMatrixProperty'[0].value()
            configuredPermissions.size() == 2

            final Node inheritanceStrategyNode = configuredPermissions.first()
            'inheritanceStrategy' == inheritanceStrategyNode.name()
            "org.jenkinsci.plugins.matrixauth.inheritance.NonInheritingStrategy" == inheritanceStrategyNode["@class"]

            final Node permissionNode = configuredPermissions.last()
            'permission' == permissionNode.name() &&
            "hudson.model.Item.Read:ci-admin" == permissionNode.text()
        }
    }
}
