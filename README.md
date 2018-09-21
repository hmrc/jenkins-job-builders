# jenkins-job-builders
[ ![Download](https://api.bintray.com/packages/hmrc/releases/jenkins-job-builders/images/download.svg) ](https://bintray.com/hmrc/releases/jenkins-job-builders/_latestVersion)

Automated Jenkins job and view configuration builders, via the [Jenkins Job DSL Plugin](https://github.com/jenkinsci/job-dsl-plugin) ([wiki](https://github.com/jenkinsci/job-dsl-plugin/wiki)).

The problem with using a user interface for configuring a continuous build system like Jenkins is that it doesn't scale - teams should own their Jenkins configuration, but the normal approach in the user interface is to copy and paste jobs which quickly becomes unmaintainable when applying global fixes e.g. temporary diskspace management.

This can be solved in Jenkins by using the Jenkins Job DSL plugin, which allows job configuration to be automated and scales up to 10s/100s of jobs. Teams can configure their own microservice, frontend, stubs, and test jobs in their own product-specific configuration file, using a convenience Builder pattern that encapsulates the Jobs DSL (in [Groovy](http://groovy-lang.org/)). If and when the existing Builders are insufficient, the raw Jobs DSL can still be used.

## Job Builders

`JobBuilder` allows a job to be constructed from a series of SCM settings, publishers, and plugins. The raw Jobs DSL can be used if necessary.

## View Builders

`ListViewBuilder` allows a job list to be constructed from a series of jobs. The raw Jobs DSL can be used if necessary.

`BuildMonitorViewBuilder` allows a job monitor to be constructed from a series of jobs. The raw Jobs DSL can be used if necessary.

## Building

1. Run `./gradlew clean test` locally to test your changes. The test suite will ensure the Builders are capable of producing the expected config XML for Jenkins.

## Installing

Create a Jenkins jobs repository akin to www.github.com/hmrc/jenkins-jobs, and then create a Jenkins job that weaves together this library with the jobs repository as follows:

1. Gradle Step
    * Use Gradle Wrapper = true
    * From Root Build Script Dir = true
    * Tasks = clean build
2. Process Jobs DSL Step
    * Look On Filesystem = true
    * DSL Scripts = DIR/*.groovy
    * Removed Jobs = Delete
    * Removed Views = Delete
    * Context To Use For Relative Job Names = Jenkins Root
    * Additional Classpath = DIR/*.jar (where jenkins-job-builders is in DIR)

## Examples

The [open HMRC Jenkins jobs](https://github.com/hmrc/jenkins-jobs) are one example of how to use this library.

## Release Notes

* 11.13.0 (24/08/2018) - Added abilitiy to recurse through sub folders in views
* 11.12.0 (24/08/2018) - Modify cucumber reporter to use target/cucumber.json file
* 11.11.0 (10/08/2018) - Support regex job filter in view builder.
* 11.10.0 (03/08/2018) - Support inheritance strategies in Folders.
* 11.9.0 (30/07/2018) - Provide the capability of configuring permissions on a per-job basis.
* 11.8.0 (17/07/2018) - Add RemoteTriggerStep & RemoteToken.
* 11.7.0 (11/07/2018) - Add GithubCommitNotifierPublisher.
* 11.1.0 (18/04/2018) - Upgrade to job-dsl-core v1.69.
* 11.0.0 (17/04/2018) - Upgrade to job-dsl-core v1.68.
* 10.13.0 - Update views to align with job-dsl-core 1.38.
* 10.12.0 - Add JobsTriggerStep which adds ability to trigger a downstream job as a build step.
    * This required an upgrade to job-dsl-core from 1.29 to 1.38.

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
