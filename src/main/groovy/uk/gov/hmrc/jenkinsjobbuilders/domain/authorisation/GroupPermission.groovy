package uk.gov.hmrc.jenkinsjobbuilders.domain.authorisation

import uk.gov.hmrc.jenkinsjobbuilders.domain.Setting

class GroupPermission implements Setting {

    final String permission
    final String ldapIdentifier

    private GroupPermission(String ldapIdentifier, String permission) {
        this.ldapIdentifier = ldapIdentifier
        this.permission = permission
    }

    static GroupPermission permissionSetting(String ldapIdentifier, String permission) {
        new GroupPermission(ldapIdentifier, permission)
    }

    @Override
    Closure toDsl() {
        return {
            delegate.groupPermission(permission, ldapIdentifier)
        }
    }
}
