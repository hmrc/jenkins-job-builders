package uk.gov.hmrc.jenkinsjobbuilders.domain.authorisation

import uk.gov.hmrc.jenkinsjobbuilders.domain.Setting

class Permission implements Setting {

    final String permissionIdentifier
    final String ldapIdentifier

    private Permission(String ldapIdentifier, String permissionIdentifier) {
        this.ldapIdentifier = ldapIdentifier
        this.permissionIdentifier = permissionIdentifier
    }

    static Permission permissionSetting(String ldapIdentifier, String permission) {
        new Permission(ldapIdentifier, permission)
    }

    @Override
    Closure toDsl() {
        return {
            permission(permissionIdentifier, ldapIdentifier)
        }
    }
}
