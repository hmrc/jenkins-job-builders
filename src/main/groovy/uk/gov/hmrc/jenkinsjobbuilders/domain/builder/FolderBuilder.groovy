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

package uk.gov.hmrc.jenkinsjobbuilders.domain.builder

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.DslScriptException
import javaposse.jobdsl.dsl.Folder
import uk.gov.hmrc.jenkinsjobbuilders.domain.authorisation.Permission
import uk.gov.hmrc.jenkinsjobbuilders.domain.configure.Configure

final class FolderBuilder implements Builder<Folder> {

    private final String name
    private String displayName

    private String description
    private String primaryView
    private final Set<Permission> permissions = []
    private final List<Configure> configures = []

    /**
     *
     * @param name This doesn't get used in the Jenkins GUI but instead acts as a unique key.
     * Unless there are multiple views with the same displayName this and displayName can share the same value.
     */
    FolderBuilder(String name) {
        this.name = name
        this.displayName = name
        this.description = "Folder for ${name} jobs."
    }

    FolderBuilder withDisplayName(final String displayName) {
        this.displayName = displayName
        return this
    }

    FolderBuilder withDescription(final String description) {
        this.description = description
        return this
    }

    FolderBuilder withPrimaryView(final String primaryView) {
        this.primaryView = primaryView
        return this
    }

    FolderBuilder withPermissions(final Set<Permission> perms) {
        this.permissions.addAll(perms)
        return this
    }

    FolderBuilder withConfigures(final List<Configure> configures) {
        this.configures.addAll(configures)
        return this
    }

    @Override
    Folder build(DslFactory dslFactory) {
        dslFactory.folder(this.name) {
            authorization {
                this.permissions.each { Permission perm ->
                    try {
                        permission(perm.permission, perm.ldapIdentifier)
                    }
                    catch (DslScriptException e) {
                        throw new DslScriptException("Could not assign permission: ${perm.permission} to LDAP identifier: ${perm.ldapIdentifier}", e)
                    }
                }
            }
            this.configures.each {
                configure(it.toDsl())
            }
            description(this.description)
            displayName(this.displayName)
            if(this.primaryView) {
                primaryView(this.primaryView)
            }
        }
    }
}
