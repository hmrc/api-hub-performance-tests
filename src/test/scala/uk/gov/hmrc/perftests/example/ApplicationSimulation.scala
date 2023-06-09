package uk.gov.hmrc.perftests.example

import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.example.ApplicationRequests.{getApplication, loginOnLoginPage, navigateToLoginPage}

class ApplicationSimulation extends PerformanceTestRunner {

  setup("home-page", "Home Page") withRequests navigateToLoginPage

  setup("login-on-login-page", "Login with csrf details") withRequests loginOnLoginPage

  setup("get-application-by-id", "Get application by id") withRequests getApplication

  runSimulation()
}