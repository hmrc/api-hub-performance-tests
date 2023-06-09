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
import uk.gov.hmrc.performance.conf.ServicesConfiguration

object ApplicationRequests extends ServicesConfiguration {
  val baseUrl: String = baseUrlFor("api-hub-applications")
  val route: String = "/test-only/sign-in?continue_url=https%3A%2F%2Fadmin.qa.tax.service.gov.uk%2Fapi-hub"
  val applicationId = "646e34e1225ba0552aa72707"
  val applicationRoute = "/application-details"

  val loginCredentials: Map[String, String] = Map("redirectUrl" -> "https://admin.qa.tax.service.gov.uk/api-hub", "email" -> "ade.oke@digital.hmrc.gov.uk", "principal" -> "ade-service")

  val navigateToLoginPage: HttpRequestBuilder =
    http("Navigate to Api Hub Login Page")
      .get("https://admin.staging.tax.service.gov.uk/api-hub/test-only/sign-in?continue_url=https%3A%2F%2Fadmin.qa.tax.service.gov.uk%2Fapi-hub")
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))
      .check(status.is(200))

  val loginOnLoginPage: HttpRequestBuilder =
    http("LDAP login")
      .post("https://admin.staging.tax.service.gov.uk//api-hub/test-only/sign-in?continue_url=https%3A%2F%2Fadmin.qa.tax.service.gov.uk%2Fapi-hub")
      .formParamMap(loginCredentials ++ Map("csrfToken" -> s"$${csrfToken}"))
      .check(status.is(303))

  val getApplication: HttpRequestBuilder =
    http("get specific application details")
      .get(s"https://admin.staging.tax.service.gov.uk/api-hub/application-details/$applicationId")
      .check(status.is(200))
}
