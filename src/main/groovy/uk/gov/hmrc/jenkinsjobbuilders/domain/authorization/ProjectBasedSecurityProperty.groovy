/*
 * Copyright 2018 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.jenkinsjobbuilders.domain.authorization

import uk.gov.hmrc.jenkinsjobbuilders.domain.authorisation.GroupPermission

class ProjectBasedSecurityProperty implements Authorization {

  private final boolean blockInheritance = false
  private final Set<GroupPermission> permissions

  private ProjectBasedSecurityProperty(final boolean blockInheritance,
                                       final Set<GroupPermission> permissions) {
    this.blockInheritance = blockInheritance
    this.permissions = permissions
  }

  static ProjectBasedSecurityProperty enableProjectBasedSecurity(final boolean blockInheritance,
                                                                 final Set<GroupPermission> permissions) {
    return new ProjectBasedSecurityProperty(blockInheritance,
                                            permissions)
  }

  @Override
  Closure toDsl() {
        return {
            blocksInheritance(this.blockInheritance)
            this.permissions.each { permission ->
                delegate.groupPermission(permission.permission, permission.ldapIdentifier)
            }
        }
    }
}
