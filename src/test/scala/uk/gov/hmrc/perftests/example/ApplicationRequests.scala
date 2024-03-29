/*
 * Copyright 2024 HM Revenue & Customs
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
import uk.gov.hmrc.perftests.example.config.{ApiHubTestingConfig, ApiHubTestingConfiguration}

object ApplicationRequests extends ApiHubTestingConfiguration {
  val testingConfig: ApiHubTestingConfig = loadConfig()
  val apiHubBaseUrl: String              = testingConfig.apiHubBaseUrl
  val ldapLoginUrl: String               = testingConfig.ldapLogin.baseUrl

  val loginCredentials: Map[String, String] = Map(
    "redirectUrl"                      -> apiHubBaseUrl,
    "email"                            -> testingConfig.ldapLogin.email,
    "principal"                        -> "performance-tests",
    "permissions[0].resourceTypes"     -> "api-hub-frontend",
    "permissions[0].resourceLocations" -> "support",
    "permissions[0].actions"           -> "WRITE"
  )

  val loginUsingLdap: HttpRequestBuilder =
    http("POST test-only/sign-in")
      .post(s"$ldapLoginUrl/test-only/sign-in?continue_url=$apiHubBaseUrl")
      .formParamMap(loginCredentials)
      .check(status.is(303))

  val getAllApplications: HttpRequestBuilder =
    http("GET /")
      .get(apiHubBaseUrl)
      .check(status.is(200))

  val getAllAccessRequests: HttpRequestBuilder =
    http("GET /admin/access-requests")
      .get(s"$apiHubBaseUrl/admin/access-requests")
      .check(status.is(200))

  val getApplicationDetails: HttpRequestBuilder =
    http("GET application/details")
      .get(s"$apiHubBaseUrl/application/details/${testingConfig.appId}")
      .check(status.is(200))

  val getApplicationApis: HttpRequestBuilder =
    http("GET /application/apis")
      .get(s"$apiHubBaseUrl/application/apis/${testingConfig.appId}")
      .check(status.is(200))

  val getDevelopmentEnvironmentAndCredentials: HttpRequestBuilder =
    http("GET application/environment-and-credentials/{app_id}#hip-development")
      .get(s"$apiHubBaseUrl/application/environment-and-credentials/${testingConfig.appId}#hip-development")
      .check(status.is(200))

  val getProductionEnvironmentAndCredentials: HttpRequestBuilder =
    http("GET application/environment-and-credentials/{app_id}#hip-production")
      .get(s"$apiHubBaseUrl/application/environment-and-credentials/${testingConfig.appId}#hip-production")
      .check(status.is(200))
}
