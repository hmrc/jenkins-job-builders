package uk.gov.hmrc.jenkinsjobbuilders.domain.step

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

 /* this class is for configuring a parameterized remote trigger step with plugin version 3.1.6.2+ */

import uk.gov.hmrc.jenkinsjobbuilders.domain.configure.Configure

class RemoteTriggerStep2 implements Configure {
    private final String remoteHost
    private final String remoteJob
    private final Map<String, String> parameterMap
    private final boolean failIfRemoteBuildFails
    private String credentialName = null
    private String token = null

    private RemoteTriggerStep2(String remoteHost, String remoteJob, Map<String, String> parameters, boolean failIfRemoteBuildFails) {
        this.remoteHost = remoteHost
        this.remoteJob = remoteJob
        this.parameterMap = parameters
        this.failIfRemoteBuildFails = failIfRemoteBuildFails
    }

    static Configure remoteTriggerStep(String remoteHost, String remoteJob, Map<String, String> parameters, boolean failIfRemoteBuildFails = true) {
        new RemoteTriggerStep2(remoteHost, remoteJob, parameters, failIfRemoteBuildFails)
    }

    RemoteTriggerStep2 withCredential(String credentialName) {
        this.credentialName = credentialName
        this
    }

    RemoteTriggerStep2 withRemoteJobToken(String token) {
        this.token = token
        this
    }

    @Override
    Closure toDsl() {
        return {
            it / builders / 'org.jenkinsci.plugins.ParameterizedRemoteTrigger.RemoteBuildConfiguration' {
                        'remoteJenkinsName'(this.remoteHost)
                        'job'(this.remoteJob)
                        'pollInterval'(10)
                        'blockBuildUntilComplete'(true)
                        'shouldNotFailBuild'(!this.failIfRemoteBuildFails)
                        if (this.credentialName != null) {
                            'auth2'(class: 'org.jenkinsci.plugins.ParameterizedRemoteTrigger.auth2.CredentialsAuth') {
                                credentials(this.credentialName)
                            }
                        }
                        if (this.token != null) {
                            'token'(this.token)
                        }
                        'parameters2'(class: 'org.jenkinsci.plugins.ParameterizedRemoteTrigger.parameters2.MapParameters') {
                            parameters {
                                this.parameterMap.each { n, v ->
                                    'org.jenkinsci.plugins.ParameterizedRemoteTrigger.parameters2.MapParameter' {
                                        'name'(n)
                                        'value'(v)
                                    }
                                }
                            }
                        }
            }
        }
    }
}
