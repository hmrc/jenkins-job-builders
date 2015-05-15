# jenkins-job-builders
[ ![Download](https://api.bintray.com/packages/hmrc/releases/jenkins-job-builders/images/download.svg) ](https://bintray.com/hmrc/releases/jenkins-job-builders/_latestVersion)

Automated Jenkins job and view configuration builders, via the [Jenkins Job DSL Plugin](https://github.com/jenkinsci/job-dsl-plugin) ([wiki](https://github.com/jenkinsci/job-dsl-plugin/wiki)).

The problem with using a user interface for configuring a continuous build system like Jenkins is that it doesn't scale - teams should own their Jenkins configuration, but the normal approach in the user interface is to copy and paste jobs which quickly becomes unmaintainable when applying global fixes e.g. temporary diskspace management.

This can be solved in Jenkins by using the Jenkins Job DSL plugin, which allows job configuration to be automated and scales up to 10s/100s of jobs. Teams can configure their own microservice, frontend, stubs, and test jobs in their own product-specific configuration file, using a convenience Builder pattern that encapsulates the Jobs DSL (in [Groovy](http://groovy-lang.org/)). If and when the existing Builders are insufficient, the raw Jobs DSL can be used.

## Job Builders

`JobBuilder` allows a job to be constructed from a series of SCM settings, publishers, and plugins. The raw Jobs DSL can be used if necessary.

## View Builders

`ListViewBuilder` allows a job list to be constructed from a series of jobs. The raw Jobs DSL can be used if necessary.

`BuildMonitorViewBuilder` allows a job monitor to be constructed from a series of jobs. The raw Jobs DSL can be used if necessary.

## Building

1. Run `./gradlew clean test` locally to test your changes. The test suite will ensure the Builders are capable of producing the expected config XML for Jenkins.

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
