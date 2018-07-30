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

import uk.gov.hmrc.jenkinsjobbuilders.domain.authorisation.Permission
import uk.gov.hmrc.jenkinsjobbuilders.domain.configure.Configure

class ProjectBasedSecurityProperty implements Configure {

  private final InheritanceStrategy inheritanceStrategy
  private final Set<Permission> permissions

  private ProjectBasedSecurityProperty(final InheritanceStrategy inheritanceStrategy,
                                       final Set<Permission> permissions) {
    this.inheritanceStrategy = inheritanceStrategy
    this.permissions = permissions
  }

  static ProjectBasedSecurityProperty enableProjectBasedSecurity(final InheritanceStrategy inheritanceStrategy,
                                                                 final Set<Permission> permissions) {
    return new ProjectBasedSecurityProperty(inheritanceStrategy,
                                            permissions)
  }

  @Override
  Closure toDsl() {
    return {
      it / 'properties' / 'hudson.security.AuthorizationMatrixProperty' {
        'inheritanceStrategy'('class': inheritanceStrategy.className)
        permissions.each { permission ->
          delegate.permission("${permission.permission}:${permission.ldapIdentifier}")
        }
      }
    }
  }

  enum InheritanceStrategy {
    INHERIT_GLOBAL_STRATEGY("org.jenkinsci.plugins.matrixauth.inheritance.InheritGlobalStrategy"),
    INHERIT_PARENT_STRATEGY("org.jenkinsci.plugins.matrixauth.inheritance.InheritParentStrategy"),
    NON_INHERITING_STRATEGY("org.jenkinsci.plugins.matrixauth.inheritance.NonInheritingStrategy")

    private final String className

    InheritanceStrategy(final String className) {
      this.className = className
    }

    String getClassName() {
      return className
    }
  }
}
