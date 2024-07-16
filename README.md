>**We are aware that this codebase contains outdated language. This may be due to a third party dependency which provides essential functionality or because of links to other projects. We are committed to providing an inclusive experience and are actively engaging with our suppliers and the open source community to address these issues.**

# jenkins-job-builders
[Download here](https://open.artefacts.tax.service.gov.uk/maven2/uk/gov/hmrc/jenkins-job-builders/)

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
* 16.4.0 (16/07/2024) - Upgrade com.jfrog.artifactory plugin version to 4.33.1
* 16.3.0 (12/07/2024) - Upgrade Jenkins-job-builder and unblock failing tests 
* 16.2.0 (06/03/2024) - Add support for a post build workspace cleanup
* 16.0.0 (21/11/2023) - Add support for [timestamper](https://plugins.jenkins.io/timestamper/)
* 15.0.0 (21/07/2023) - Remove ruby-runtime plugin support
* 14.0.0 (29/06/2023) - Upgrade job-dsl to v1.82
* 13.4.0 (18/08/2022) - Provide a `scalac` version agnostic SCoverage publisher
* 13.3.0 (29/06/2022) - Disable "Show Committers" on build monitor views
* 13.1.0 (19/11/2021) - Remove deprecated properties from cucumber and xvfb plugins
* 13.0.0 (28/10/2021) - Only support Post Build Script plugin version 3.0.0 and higher
* 12.0.0 (23/09/2021) - Support multiple build results in postBuildScriptPublisher
* 11.42.0 (03/08/2021) - Don't add envInject properties if they are defined with a null value
* 11.41.0 (02/08/2021) - Updated DSL property name in InjectEnvironmentJobProperty
* 11.40.1 (02/08/2021) - Updated DSL property name in InjectEnvironmentJobProperty
* 11.40.0 (02/08/2021) - Add the InjectEnvironmentJobProperty class to perform pre-scm environment injection
* 11.39.0 (12/05/2021) - Allow ListViewBuilder to recurse into folders
* 11.38.0 (04/05/2021) - add BuildDescriptionStep to allow jobs to set their own descriptions
* 11.37.0 (23/03/2021) - Allow JobBuilder description to be modified after instantiation
* 11.36.0 (12/03/2021) - add an optional parameter to shellStep "unstableReturnCode" that sets build to unstable based of given return code value
* 11.35.0 (08/12/2020) - Release to hmrc-digital bintray account
* 11.34.0 (13/10/2020) - Allow configuration of alwaysLinkToLastBuild in HtmlReportsPublisher
* 11.33.0 (05/10/2020) - Modify cucumber reporter to mark build as failed when cucumber report marked as failed
* 11.32.0 (07/08/2020) - Add support for the PostBuildScript Publisher plugin
* 11.31.0 (17/01/2020) - Allow to specify the Scala version in the Coverage Report Publisher
* 11.30.0 (25/10/2019) - Add support for honoring the refspec in the initial git fetch, and parameterise whether tags are pulled or not
* 11.29.0 (01/10/2019) - Add support for [AWS CodeBuild with Jenkins plugin](https://wiki.jenkins.io/display/JENKINS/AWS+CodeBuild+Plugin)
* 11.27.0 (15/04/2019) - Fix name bug in BuildMonitorViewBuilder.
* 11.26.0 (02/04/2019) - Implement a conjoined username/password credentials binding.
* 11.25.0 (29/03/2019) - Upgrade test harness jenkins version to v2.150.3
* 11.24.0 (29/03/2019) - Add shallow SCM clone options and upgrade job-dsl-core to v1.72
* 11.23.0 (06/03/2019) - Upgrade to job-dsl-core v1.71.
* 11.22.0 (05/12/2018) - Add a method to insert a pre scm build job
* 11.21.0 (05/11/2018) - Add active choice reactive parameter
* 11.20.0 (31/10/2018) - Fix a bug where folders have spaces (for maven steps)
* 11.18.0 (19/10/2018) - Add the UpstreamTrigger class to enable building a job based on the successful completion of upstream jobs
* 11.17.0 (09/10/2018) - Add the GitHubPullRequestTrigger class and extend the GitHubScm class to allow refspec and name to be specified.
* 11.14.0 (24/08/2018) - Merged SecretText and SecretUsernamPassword wrapper together
* 11.13.0 (24/08/2018) - Added ability to recurse through sub folders in views
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
