/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.perftests.example.config

import uk.gov.hmrc.performance.conf.Configuration

trait ApiHubTestsConfiguration extends Configuration {

  def apiHubTestingConfig(): ApiHubTestingConfig = {
    val appId = readProperty(s"testing.applications.appId", "")
    val ldapEmail = readProperty(s"testing.ldapLogin.email", "")

    if (configIsDefined(appId, ldapEmail)) {

      ApiHubTestingConfig(appId, LdapLogin(ldapEmail))
    } else {
      val confFile =
        if (runLocal)
          "services-local.conf"
        else
          "services.conf"
      throw ConfigNotFoundException(s"Missing api hub testing config in '$confFile'.")
    }
  }

  def configIsDefined(appId: String, ldapEmail: String): Boolean =
    appId.nonEmpty && ldapEmail.nonEmpty


}
