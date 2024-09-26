package uk.gov.hmrc.jenkinsjobbuilders.domain.authorisation

import uk.gov.hmrc.jenkinsjobbuilders.domain.Setting

class UserPermission implements Setting {

    final String permission
    final String ldapIdentifier

    private UserPermission(String ldapIdentifier, String permission) {
        this.ldapIdentifier = ldapIdentifier
        this.permission = permission
    }

    static UserPermission permissionSetting(String ldapIdentifier, String permission) {
        new UserPermission(ldapIdentifier, permission)
    }

    @Override
    Closure toDsl() {
        return {
            delegate.userPermission(permission, ldapIdentifier)
        }
    }
}
