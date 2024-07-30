package uk.gov.hmrc.jenkinsjobbuilders.domain.scm

import javaposse.jobdsl.dsl.Job
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder
import static uk.gov.hmrc.jenkinsjobbuilders.domain.scm.GitHubScm.gitHubScm

class GitHubComScmSpec extends AbstractJobSpec {
    private JobBuilder jobBuilder

    def setup() {
        jobBuilder = new JobBuilder('test-job', 'test-job-description')
    }


    def "default configuration"() {
        given:
        jobBuilder.withScm(gitHubScm("host", "repository", "branch", "ssh", "refspec", "credentials", "name"))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            scm.extensions.'hudson.plugins.git.extensions.impl.CloneOption'.depth.text() == "0"
            scm.extensions.'hudson.plugins.git.extensions.impl.CloneOption'.shallow.text() == "false"
            scm.extensions.'hudson.plugins.git.extensions.impl.CloneOption'.noTags.text() == "false"
            scm.extensions.'hudson.plugins.git.extensions.impl.CloneOption'.honorRefspec.text() == "false"
        }
    }

    def "depth and honorRefspec configured"() {
        given:
        jobBuilder.withScm(gitHubScm("host", "repository", "branch", "ssh", "refspec", "credentials", "name", 3, true))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            scm.extensions.'hudson.plugins.git.extensions.impl.CloneOption'.depth.text() == "3"
            scm.extensions.'hudson.plugins.git.extensions.impl.CloneOption'.shallow
            scm.extensions.'hudson.plugins.git.extensions.impl.CloneOption'.noTags.text() == "false"
            scm.extensions.'hudson.plugins.git.extensions.impl.CloneOption'.honorRefspec
        }
    }

    def "honorRefspec configured"() {
        given:
        jobBuilder.withScm(gitHubScm("host", "repository", "branch", "ssh", "refspec", "credentials", "name", 0, true))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            scm.extensions.'hudson.plugins.git.extensions.impl.CloneOption'.depth.text() == "0"
            scm.extensions.'hudson.plugins.git.extensions.impl.CloneOption'.shallow.text() == "false"
            scm.extensions.'hudson.plugins.git.extensions.impl.CloneOption'.noTags.text() == "false"
            scm.extensions.'hudson.plugins.git.extensions.impl.CloneOption'.honorRefspec
        }
    }

    def "depth configured"() {
        given:
        jobBuilder.withScm(gitHubScm("host", "repository", "branch", "ssh", "refspec", "credentials", "name", 9))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            scm.extensions.'hudson.plugins.git.extensions.impl.CloneOption'.depth.text() == "9"
            scm.extensions.'hudson.plugins.git.extensions.impl.CloneOption'.shallow
            scm.extensions.'hudson.plugins.git.extensions.impl.CloneOption'.noTags.text() == "false"
            scm.extensions.'hudson.plugins.git.extensions.impl.CloneOption'.honorRefspec.text() == "false"
        }
    }

    def "pullTags configured"() {
        given:
        jobBuilder.withScm(gitHubScm("host", "repository", "branch", "ssh", "refspec", "credentials", "name", 0, false, false))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            scm.extensions.'hudson.plugins.git.extensions.impl.CloneOption'.depth.text() == "0"
            scm.extensions.'hudson.plugins.git.extensions.impl.CloneOption'.shallow.text() == "false"
            scm.extensions.'hudson.plugins.git.extensions.impl.CloneOption'.noTags
            scm.extensions.'hudson.plugins.git.extensions.impl.CloneOption'.honorRefspec.text() == "false"
        }
    }

    def "Timeout configured"() {
        given:
        jobBuilder.withScm(gitHubScm("host", "repository", "branch", "ssh", "refspec", "credentials", 20, "name", 0, false, false))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            scm.extensions.'hudson.plugins.git.extensions.impl.CloneOption'.depth.text() == "0"
            scm.extensions.'hudson.plugins.git.extensions.impl.CloneOption'.shallow.text() == "false"
            scm.extensions.'hudson.plugins.git.extensions.impl.CloneOption'.noTags
            scm.extensions.'hudson.plugins.git.extensions.impl.CloneOption'.honorRefspec.text() == "false"
            scm.extensions.'hudson.plugins.git.extensions.impl.CloneOption'.timeout.text() == "20"
        }
    }
}