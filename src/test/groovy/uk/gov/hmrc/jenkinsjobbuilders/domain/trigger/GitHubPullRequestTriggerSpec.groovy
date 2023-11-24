package uk.gov.hmrc.jenkinsjobbuilders.domain.trigger

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder


import static uk.gov.hmrc.jenkinsjobbuilders.domain.trigger.GitHubPullRequestTrigger.gitHubPullRequestTrigger

class GitHubPullRequestTriggerSpec extends AbstractJobSpec {

    /*
    Ideally we would test this functionality, but the org.jenkins-ci.plugins:ghprb plugin it depends on causes
    other plugins to be downgraded, breaking other tests
    */

    /*
    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withTriggers(gitHubPullRequestTrigger('my-context', 'my-github-org'))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            triggers.'org.jenkinsci.plugins.ghprb.GhprbTrigger'.orgslist.text() == "my-github-org"
            triggers.'org.jenkinsci.plugins.ghprb.GhprbTrigger'.extensions.'org.jenkinsci.plugins.ghprb.extensions.status.GhprbSimpleStatus'.commitStatusContext.text() == "my-context"
        }
    }
    */
}
