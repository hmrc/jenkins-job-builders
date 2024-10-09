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

package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

import uk.gov.hmrc.jenkinsjobbuilders.domain.configure.InheritanceStrategy
import uk.gov.hmrc.jenkinsjobbuilders.domain.authorisation.GroupPermission

class ProjectBasedGroupSecurityProperty implements Configure {

  private final InheritanceStrategy inheritanceStrategy
  private final Set<GroupPermission> permissions

  private ProjectBasedGroupSecurityProperty(final InheritanceStrategy inheritanceStrategy,
                                            final Set<GroupPermission> permissions) {
    this.inheritanceStrategy = inheritanceStrategy
    this.permissions = permissions
  }

  static ProjectBasedGroupSecurityProperty enableProjectBasedSecurity(  final InheritanceStrategy inheritanceStrategy,
                                                                        final Set<GroupPermission> permissions) {
    return new ProjectBasedGroupSecurityProperty(inheritanceStrategy,
                                            permissions)
  }

  @Override
  Closure toDsl() {
        return {
            it / 'properties' / 'hudson.security.AuthorizationMatrixProperty' {
                'inheritanceStrategy'('class': inheritanceStrategy.className)
                permissions.each { permission ->
                    delegate.permission("GROUP:${permission.permission}:${permission.ldapIdentifier}")
                }
            }
        }
    }
}
