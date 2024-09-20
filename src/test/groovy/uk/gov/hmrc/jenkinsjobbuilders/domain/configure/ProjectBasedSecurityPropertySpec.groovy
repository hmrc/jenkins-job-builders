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

import javaposse.jobdsl.dsl.Job
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.authorisation.GroupPermission
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

import static uk.gov.hmrc.jenkinsjobbuilders.domain.configure.InheritanceStrategy.NON_INHERITING_STRATEGY
import static uk.gov.hmrc.jenkinsjobbuilders.domain.configure.ProjectBasedSecurityProperty.enableProjectBasedSecurity

class ProjectBasedSecurityPropertySpec extends AbstractJobSpec {

    void 'test default XML output'() {
        final Set<GroupPermission> desiredPermissions = [GroupPermission.permissionSetting("viewer", "hudson.model.Item.Read"),
                                                    GroupPermission.permissionSetting("admin", "hudson.model.Item.Build"),
                                                    GroupPermission.permissionSetting("admin", "hudson.model.Item.Cancel"),
                                                    GroupPermission.permissionSetting("admin", "hudson.model.Item.Configure"),
                                                    GroupPermission.permissionSetting("admin", "hudson.model.Item.Delete"),
                                                    GroupPermission.permissionSetting("admin", "hudson.model.Item.Discover"),
                                                    GroupPermission.permissionSetting("admin", "hudson.model.Item.Read"),
                                                    GroupPermission.permissionSetting("admin", "hudson.model.Item.Workspace"),
                                                    GroupPermission.permissionSetting("admin", "hudson.model.Run.Delete"),
                                                    GroupPermission.permissionSetting("admin", "hudson.model.Run.Update"),
                                                    GroupPermission.permissionSetting("admin", "hudson.scm.SCM.Tag")]
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withConfigures(enableProjectBasedSecurity(NON_INHERITING_STRATEGY,
                                                                                         desiredPermissions))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            def authorizationMatrixProperty  = properties.'hudson.security.AuthorizationMatrixProperty'
            print("HELLO WORLD")
            print(authorizationMatrixProperty)
            authorizationMatrixProperty.inheritanceStrategy.'@class'.text() == 'org.jenkinsci.plugins.matrixauth.inheritance.NonInheritingStrategy'
            desiredPermissions.size() == authorizationMatrixProperty.groupPermission.size()
            desiredPermissions.each { expectedPermission ->
                assert authorizationMatrixProperty.groupPermission.find() { actualPermission ->
                    "${expectedPermission.permission}:${expectedPermission.ldapIdentifier}" == actualPermission.text()
                }
            }
        }
    }
}

