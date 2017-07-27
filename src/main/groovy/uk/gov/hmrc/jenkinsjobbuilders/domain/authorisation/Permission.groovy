package uk.gov.hmrc.jenkinsjobbuilders.domain.authorisation

import uk.gov.hmrc.jenkinsjobbuilders.domain.Setting

class Permission implements Setting {

    final String permissionValue
    final String ldapIdentifier

    private Permission(String ldapIdentifier, String permission) {
        this.ldapIdentifier = ldapIdentifier
        this.permissionValue = permission
    }

    static Permission permissionSetting(String ldapIdentifier, String permission) {
        new Permission(ldapIdentifier, permission)
    }

    @Override
    Closure toDsl() {
        return {
            permission(permissionValue, ldapIdentifier)
        }
    }
}
