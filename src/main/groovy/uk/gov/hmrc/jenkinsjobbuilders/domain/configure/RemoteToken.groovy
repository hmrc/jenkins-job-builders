package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

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

import uk.gov.hmrc.jenkinsjobbuilders.domain.configure.Configure

final class RemoteToken implements Configure {

    private final String remoteToken

    private RemoteToken(String token) {
        this.remoteToken = token
    }

    static RemoteToken remoteToken(String token) {
        new RemoteToken(token)
    }

    @Override
    Closure toDsl() {
        return {
            // using findAll here, to ensure that all RemoteBuildConfiguration entries are updated - the / operator only finds the first child
            (it / builders).value().findAll { it.name() == 'org.jenkinsci.plugins.ParameterizedRemoteTrigger.RemoteBuildConfiguration' }.each { config ->
                (config / token).setValue(remoteToken)
            }
        }
    }
}
