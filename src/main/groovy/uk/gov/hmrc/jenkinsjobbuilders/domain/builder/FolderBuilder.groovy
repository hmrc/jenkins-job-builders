package uk.gov.hmrc.jenkinsjobbuilders.domain.builder

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.DslScriptException
import javaposse.jobdsl.dsl.Folder
import uk.gov.hmrc.jenkinsjobbuilders.domain.authorisation.Permission

final class FolderBuilder implements Builder<Folder> {

    private final String name
    private String displayName

    private String description
    private String primaryView
    private final Set<Permission> perms = []

    /**
     *
     * @param name This doesn't get used in the Jenkins GUI but instead acts as a unique key.
     * Unless there are multiple views with the same displayName this and displayName can share the same value.
     */
    FolderBuilder(String name) {
        this.name = name
        this.displayName = name
        this.description = "Folder for ${name} jobs."
    }

    FolderBuilder withDisplayName(final String displayName) {
        this.displayName = displayName
        return this
    }

    FolderBuilder withDescription(final String description) {
        this.description = description
        return this
    }

    FolderBuilder withPrimaryView(final String primaryView) {
        this.primaryView = primaryView
        return this
    }

    FolderBuilder withPermissions(final Set<Permission> perms) {
        this.perms.addAll(perms)
        return this
    }

    @Override
    Folder build(DslFactory dslFactory) {
        dslFactory.folder(this.name) {
            authorization {
                this.perms.each { Permission perm ->
                    try {
                        permission(perm.permission, perm.ldapIdentifier)
                    }
                    catch (DslScriptException e) {
                        throw new DslScriptException("Could not assign permission: ${perm.permission} to LDAP identifier: ${perm.ldapIdentifier}", e)
                    }
                }
            }
            description(this.description)
            displayName(this.displayName)
            if(this.primaryView) {
                primaryView(this.primaryView)
            }
        }
    }
}
