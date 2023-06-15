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

package uk.gov.hmrc.perftests.example

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.perftests.example.config.{ApiHubTestingConfig, ApiHubTestsConfiguration}

object ApplicationRequests extends ApiHubTestsConfiguration {
  val testingConfig: ApiHubTestingConfig = apiHubTestingConfig()
  val baseUrl: String = testingConfig.apiHubBaseUrl
  val ldapLoginUrl: String = testingConfig.ldapLogin.baseUrl

  val loginCredentials: Map[String, String] = Map(
    "redirectUrl" -> baseUrl,
    "email" -> testingConfig.ldapLogin.email,
    "principal" -> "api-hub-performance-tests"
  )

  val navigateToLoginPage: HttpRequestBuilder =
    http("Navigate to Api Hub Login Page")
      .get(s"$ldapLoginUrl/test-only/sign-in?continue_url=$baseUrl")
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))
      .check(status.is(200))

  val loginOnLoginPage: HttpRequestBuilder =
    http("LDAP login")
      .post(s"$ldapLoginUrl/test-only/sign-in?continue_url=$baseUrl")
      .formParamMap(loginCredentials ++ Map("csrfToken" -> s"$${csrfToken}"))
      .check(status.is(303))

  val getApplication: HttpRequestBuilder =
    http("get specific application details")
      .get(s"$baseUrl/application-details/${testingConfig.appId}")
      .check(status.is(200))
}
