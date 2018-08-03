package uk.gov.hmrc.jenkinsjobbuilders.domain.builder

import javaposse.jobdsl.dsl.DslScriptException
import javaposse.jobdsl.dsl.Folder
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.authorisation.Permission

import static uk.gov.hmrc.jenkinsjobbuilders.domain.authorisation.Permission.permissionSetting
import static uk.gov.hmrc.jenkinsjobbuilders.domain.configure.FolderInheritanceStrategy.folderInheritanceStrategy
import static uk.gov.hmrc.jenkinsjobbuilders.domain.configure.InheritanceStrategy.NON_INHERITING_STRATEGY

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
            ! it.'properties'.'com.cloudbees.hudson.plugins.folder.properties.AuthorizationMatrixProperty'.inheritanceStrategy
        }
    }

    def 'it correctly configures a fully populated example'() {
        given:
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
            .withConfigures([folderInheritanceStrategy(NON_INHERITING_STRATEGY)])

        when:
        Folder folder = folderBuilder.build(JOB_PARENT)

        then:
        with(folder.node) {
            it.displayName.text() == displayName
            it.description.text() == folderDescription
            it.folderViews.primaryView.text() == primaryView
            final NodeList configuredPermissions = it.'properties'.'com.cloudbees.hudson.plugins.folder.properties.AuthorizationMatrixProperty'[0].value()
            configuredPermissions.size() == 8

            final def inheritanceStrategy = configuredPermissions.first()
            'inheritanceStrategy' == inheritanceStrategy.name()
            "org.jenkinsci.plugins.matrixauth.inheritance.NonInheritingStrategy" == inheritanceStrategy["@class"]

            permissions.each { permission ->
                configuredPermissions.tail().find { configuredPermission ->
                    'permission' == configuredPermission.name() &&
                    "${permission.permission}:${permission.ldapIdentifier}" == configuredPermission.text()
                }
            }
        }
    }

    def 'it should throw an exception when it cannot map a permission, identifiying the permission that cannot be used'() {
        given:
        final String folderName = "my-first-folder"
        final Set<Permission> permissions = [permissionSetting("ci-admin", "hudson.model.Item.MadeUpPermission")]
        final FolderBuilder folderBuilder = new FolderBuilder(folderName).withPermissions(permissions)

        when:
        folderBuilder.build(JOB_PARENT)

        then:
        DslScriptException e = thrown()
        e.message.contains("Could not assign permission: hudson.model.Item.MadeUpPermission to LDAP identifier: ci-admin")
        e.cause instanceof DslScriptException
    }
}
