package uk.gov.hmrc.jenkinsjobbuilders.domain.authorisation

import javaposse.jobdsl.dsl.helpers.Permissions
import uk.gov.hmrc.jenkinsjobbuilders.domain.Setting

class Permission implements Setting {

    final Permissions permissionEnum
    final String ldapIdentifier

    private Permission(String ldapIdentifier, Permissions permissionEnum) {
        this.ldapIdentifier = ldapIdentifier
        this.permissionEnum = permissionEnum
    }

    static Permission permissionSetting(String ldapIdentifier, Permissions permission) {
        new Permission(ldapIdentifier, permission)
    }

    @Override
    Closure toDsl() {
        return {
            permission(permissionEnum, ldapIdentifier)
        }
    }
}
