package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

import javaposse.jobdsl.dsl.DslScriptException

final class SCoverageReportsPublisher implements Configure {

    private final String scalaVersion
    static private final List<String> validScalaVersions = ["2.10", "2.11", "2.12"]

    private SCoverageReportsPublisher(final String scalaVersion) {
        this.scalaVersion = scalaVersion
    }

    static void validateScalaVersion(final String scalaVersion) {
        if (! this.validScalaVersions.contains(scalaVersion) ) {
            throw new DslScriptException("Could not use the Scala version ${scalaVersion}. The only supported Scala versions are ${this.validScalaVersions.join(",")}")
        }
    }

    Closure toDsl() {
        return {
            it / 'publishers' / 'org.jenkinsci.plugins.scoverage.ScoveragePublisher' {
                'reportDir'("target/scala-${scalaVersion}/scoverage-report")
                'reportFile'('scoverage.xml')
            }
        }
    }

    static SCoverageReportsPublisher sCoverageReportsPublisher() {
        new SCoverageReportsPublisher("2.11")
    }

    static SCoverageReportsPublisher sCoverageReportsPublisher(final String scalaVersion) {
        validateScalaVersion(scalaVersion)
        new SCoverageReportsPublisher(scalaVersion)
    }
}