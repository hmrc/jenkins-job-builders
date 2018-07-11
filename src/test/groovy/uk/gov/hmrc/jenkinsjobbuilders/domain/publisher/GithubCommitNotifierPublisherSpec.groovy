package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher

import javaposse.jobdsl.dsl.Job
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

import static GithubCommitNotifierPublisher.githubCommitStatusPublisher

class GithubCommitNotifierPublisherSpec extends AbstractJobSpec {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withPublishers(githubCommitStatusPublisher())

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            publishers.'com.cloudbees.jenkins.GitHubCommitNotifier'
        }
    }
}
