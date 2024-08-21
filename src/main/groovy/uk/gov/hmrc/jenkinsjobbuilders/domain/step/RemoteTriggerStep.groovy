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

import uk.gov.hmrc.jenkinsjobbuilders.domain.step.Step

class RemoteTriggerStep implements Step {
    private final String remoteHost
    private final String remoteJob
    private final Map<String, Object> parameterMap
    private final boolean failIfRemoteBuildFails

    private RemoteTriggerStep(String remoteHost, String remoteJob, Map<String, Object> parameters, boolean failIfRemoteBuildFails) {
        this.remoteHost = remoteHost
        this.remoteJob = remoteJob
        this.parameterMap = parameters
        this.failIfRemoteBuildFails = failIfRemoteBuildFails
    }

    static Step remoteTriggerStep(String remoteHost, String remoteJob, Map<String, Object> parameters, boolean failIfRemoteBuildFails = true) {
        new RemoteTriggerStep(remoteHost, remoteJob, parameters, failIfRemoteBuildFails)
    }

    @Override
    Closure toDsl() {
        return {
            remoteTrigger(this.remoteHost, this.remoteJob) {
                pollInterval(10)
                parameters(this.parameterMap)
                blockBuildUntilComplete()
                shouldNotFailBuild(!failIfRemoteBuildFails)
            }
        }
    }
}
