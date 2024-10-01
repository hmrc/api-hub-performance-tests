import sbt._

object Dependencies {

  val test = Seq(
    "uk.gov.hmrc"          %% "performance-test-runner"   % "6.1.0"        % Test,
    "org.apache.commons"    % "commons-text"              % "1.12.0"       % Test
  )

}
