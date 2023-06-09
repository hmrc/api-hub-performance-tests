package uk.gov.hmrc.perftests.example

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.conf.ServicesConfiguration

object ApplicationRequests extends ServicesConfiguration {
  val baseUrl: String = baseUrlFor("api-hub-applications")
  val route: String = "/test-only/sign-in?continue_url=https%3A%2F%2Fadmin.staging.tax.service.gov.uk%2Fapi-hub"
  val applicationId = "646e34e1225ba0552aa72707"
  val applicationRoute = "/application-details"

  val loginCredentials: Map[String, String] = Map("redirectUrl" -> "http://localhost:15018/api-hub/", "email" -> "ade.oke@digital.hmrc.gov.uk", "principal" -> "ade-service")

  val navigateToLoginPage: HttpRequestBuilder =
    http("Navigate to Api Hub Login Page")
      .get(s"$baseUrl$route")
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))
      .check(status.is(200))

  val loginOnLoginPage: HttpRequestBuilder =
    http("LDAP login")
      .post(s"$baseUrl$route")
      .formParamMap(loginCredentials ++ Map("csrfToken" -> s"$${csrfToken}"))
      .check(status.is(303))

  val getApplication: HttpRequestBuilder =
    http("get specific application details")
      .get(s"$baseUrl$applicationRoute$applicationId")
      .check(status.is(200))
}
