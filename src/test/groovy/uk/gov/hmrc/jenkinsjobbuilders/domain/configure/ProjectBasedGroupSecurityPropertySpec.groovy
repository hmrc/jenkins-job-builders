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
import uk.gov.hmrc.jenkinsjobbuilders.domain.authorisation.Permission
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder
import static uk.gov.hmrc.jenkinsjobbuilders.domain.configure.InheritanceStrategy.NON_INHERITING_STRATEGY
import static uk.gov.hmrc.jenkinsjobbuilders.domain.configure.InheritanceStrategy.INHERIT_PARENT_STRATEGY
import static uk.gov.hmrc.jenkinsjobbuilders.domain.configure.InheritanceStrategy.INHERIT_GLOBAL_STRATEGY

import static uk.gov.hmrc.jenkinsjobbuilders.domain.configure.ProjectBasedSecurityProperty.enableProjectBasedSecurity as enableProjectNonGroupBasedSecurity
import static uk.gov.hmrc.jenkinsjobbuilders.domain.configure.ProjectBasedGroupSecurityProperty.enableProjectBasedSecurity

class ProjectBasedGroupSecurityPropertySpec extends AbstractJobSpec {

    void 'test XML output with non inheriting group perms'() {
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
                                               withConfigures(enableProjectBasedSecurity(NON_INHERITING_STRATEGY, desiredPermissions))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            def authorizationMatrixProperty  = properties.'hudson.security.AuthorizationMatrixProperty'
            //assert !authorizationMatrixProperty.inheritanceStrategy.nonInheriting.isEmpty()
            authorizationMatrixProperty.inheritanceStrategy.'@class'.text() == 'org.jenkinsci.plugins.matrixauth.inheritance.NonInheritingStrategy'
            desiredPermissions.size() == authorizationMatrixProperty.permission.size()
            desiredPermissions.each { expectedPermission ->
                assert authorizationMatrixProperty.permission.find() { actualPermission ->
                    "GROUP:${expectedPermission.permission}:${expectedPermission.ldapIdentifier}" == actualPermission.text()
                }
            }
        }
    }

    void 'test XML output with inheriting group perms'() {
        final Set<GroupPermission> desiredPermissions = [GroupPermission.permissionSetting("viewer", "hudson.model.Item.Read")]
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withConfigures(enableProjectBasedSecurity(INHERIT_PARENT_STRATEGY, desiredPermissions))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            def authorizationMatrixProperty  = properties.'hudson.security.AuthorizationMatrixProperty'
            authorizationMatrixProperty.inheritanceStrategy.'@class'.text() == 'org.jenkinsci.plugins.matrixauth.inheritance.InheritParentStrategy'
            desiredPermissions.size() == authorizationMatrixProperty.permission.size()
            desiredPermissions.each { expectedPermission ->
                assert authorizationMatrixProperty.permission.find() { actualPermission ->
                    "GROUP:${expectedPermission.permission}:${expectedPermission.ldapIdentifier}" == actualPermission.text()
                }
            }
        }
    }

    void 'test default XML output with global inheriting no group perms'() {
        final Set<GroupPermission> desiredPermissions = []
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withConfigures(enableProjectBasedSecurity(INHERIT_GLOBAL_STRATEGY, desiredPermissions))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            def authorizationMatrixProperty  = properties.'hudson.security.AuthorizationMatrixProperty'
            authorizationMatrixProperty.inheritanceStrategy.'@class'.text() == 'org.jenkinsci.plugins.matrixauth.inheritance.InheritGlobalStrategy'
            0 == authorizationMatrixProperty.permission.size()
        }
    }

    void 'test XML output with group global inheriting overriding non group parent inheritance'() {
        final Set<GroupPermission> groupPermissions = [GroupPermission.permissionSetting("viewer", "hudson.model.Item.Cancel")]
        final Set<Permission> permissions = [Permission.permissionSetting("viewer", "hudson.model.Item.Read")]

        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                    withConfigures(enableProjectNonGroupBasedSecurity(INHERIT_PARENT_STRATEGY, permissions)).
                                    withConfigures(enableProjectBasedSecurity(INHERIT_GLOBAL_STRATEGY, groupPermissions))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            def authorizationMatrixProperty  = properties.'hudson.security.AuthorizationMatrixProperty'
            authorizationMatrixProperty.inheritanceStrategy.'@class'.text() == 'org.jenkinsci.plugins.matrixauth.inheritance.InheritGlobalStrategy'
            1 == authorizationMatrixProperty.permission.size()
            authorizationMatrixProperty.permission[0].text() ==  "GROUP:hudson.model.Item.Cancel:viewer"
        }
    }
}

