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

trait ApiHubTestingConfiguration extends Configuration {

  def loadConfig(): ApiHubTestingConfig = {
    val appId = readRequiredProperty(s"testing.applications.appId")
    val ldapEmail = readRequiredProperty(s"testing.ldapLogin.email")
    val apiHubBaseUrl: String = baseUrlFor("api-hub-frontend")
    val ldapLoginUrl: String = baseUrlFor("internal-auth-frontend")

    ApiHubTestingConfig(apiHubBaseUrl, appId, LdapLogin(ldapLoginUrl, ldapEmail))
  }

  private def readRequiredProperty(property: String): String = {
    val value = readProperty(property, "")
    if (value.isEmpty) {
      val confFile =
        if (runLocal)
          "services-local.conf"
        else
          "services.conf"
      throw ConfigNotFoundException(s"'$property' not defined in '$confFile'.")
    } else value

  }

  private def urlFor(protocol: String, host: String, port: String, path: String) =
    if (port.toInt == 80 || port.toInt == 443) s"$protocol://$host/$path" else s"$protocol://$host:$port/$path"

  private def baseUrlFor(serviceName: String): String = {
    val protocol = readProperty(s"services.$serviceName.protocol", "http")
    val host = readProperty(s"services.$serviceName.host", "localhost")
    val port = readProperty(s"services.$serviceName.port", "80")
    val path = readProperty(s"services.$serviceName.path", "")

    urlFor(protocol, host, port, path)
  }
}
