package uk.gov.hmrc.jenkinsjobbuilders.domain.builder

import javaposse.jobdsl.dsl.DslScriptException
import javaposse.jobdsl.dsl.Folder
import javaposse.jobdsl.dsl.JobManagement
import javaposse.jobdsl.plugin.JenkinsJobManagement
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.authorisation.Permission

import static uk.gov.hmrc.jenkinsjobbuilders.domain.authorisation.Permission.permissionSetting

class FolderBuilderSpec extends AbstractJobSpec {

    def 'it should create a folder using appropriate defaults for optional values'() {
        given:
        final String folderName = "my-first-folder"
        final FolderBuilder folderBuilder = new FolderBuilder(folderName)

        when:
        Folder folder = folderBuilder.build(JOB_PARENT)

        then:
        with(folder.node) {
            folderName == it.displayName.text()
            "Folder for ${folderName} jobs." == it.description.text()
            'All' == it.folderViews.primaryView.text()
            it.'properties'.'com.cloudbees.hudson.plugins.folder.properties.AuthorizationMatrixProperty'[0].value().isEmpty()
        }
    }

    def 'it correctly configures a fully populated example'() {

        given:
        final JobManagement jobManagement = new JenkinsJobManagement(System.out, [:], new File('.'))
        final String folderName = "my-first-folder"
        final String folderDescription = "my-first-folder-description"
        final String displayName = "my-first-folder-display-name"
        final String primaryView = "this-is-my-primary-view"
        final Set<Permission> permissions = [
                permissionSetting("ci-admin", "hudson.model.Item.Read"),
                permissionSetting("ci-admin", "hudson.model.Item.Build"),
                permissionSetting("ci-admin", "hudson.model.Item.Workspace"),
                permissionSetting("ci-admin", "hudson.model.Item.Delete"),
                permissionSetting("ci-admin", "hudson.model.Item.Configure"),
                permissionSetting("ci-admin", "hudson.model.Item.Discover"),
                permissionSetting("ci-admin", "hudson.model.Item.Cancel"),
        ]

        final FolderBuilder folderBuilder = new FolderBuilder(folderName)
            .withDescription(folderDescription)
            .withDisplayName(displayName)
            .withPrimaryView(primaryView)
            .withPermissions(permissions)

        when:
        Folder folder = folderBuilder.build(JOB_PARENT)

        then:
        with(folder.node) {
            displayName == it.displayName.text()
            folderDescription == it.description.text()
            primaryView == it.folderViews.primaryView.text()
            def configuredPermissions = it.'properties'.'com.cloudbees.hudson.plugins.folder.properties.AuthorizationMatrixProperty'[0].value()
            7 == configuredPermissions.size()

            permissions.each { permission ->
                configuredPermissions.contains { configuredPermission ->
                    'permission' == configuredPermission.name()
                    "${permission.permission}:${permission.ldapIdentifier}" == configuredPermission.text()
                }
            }
        }
    }

    def 'it should throw an exception when it cannot map a permission, identifiying the permission that cannot be used'() {
        given:
        final String folderName = "my-first-folder"
        final Set<Permission> permissions = [permissionSetting("ci-admin", "hudson.model.Item.MadeUpPermission")]
        final FolderBuilder folderBuilder = new FolderBuilder(folderName)
        folderBuilder.withPermissions(permissions)

        when:
        folderBuilder.build(JOB_PARENT)

        then:
        DslScriptException e = thrown()
        e.message.contains("Could not assign permission: hudson.model.Item.MadeUpPermission to LDAP identifier: ci-admin")
        e.cause instanceof DslScriptException
    }
}
