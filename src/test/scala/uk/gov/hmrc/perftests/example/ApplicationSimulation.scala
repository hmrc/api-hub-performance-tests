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

import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.example.ApplicationRequests._

class ApplicationSimulation extends PerformanceTestRunner {

  setup("login-using-ldap", "Login to LDAP") withRequests loginUsingLdap

  setup("get-all-applications", "Get all applications") withRequests getAllApplications

  setup("get-admin-access-requests", "Get all access requests") withRequests getAllAccessRequests

  setup("get-application-details", "Get application details") withRequests getApplicationDetails

  setup("get-application-apis", "Get application apis") withRequests getApplicationApis

  setup(
    "get-development-environment-credentials",
    "Get development environment credentials"
  ) withRequests getDevelopmentEnvironmentAndCredentials

  setup(
    "get-production-environment-credentials",
    "Get production environment credentials"
  ) withRequests getProductionEnvironmentAndCredentials

  runSimulation()
}
