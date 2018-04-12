package uk.gov.hmrc.jenkinsjobbuilders.domain

import javaposse.jobdsl.dsl.JobManagement
import javaposse.jobdsl.dsl.JobParent
import javaposse.jobdsl.plugin.JenkinsJobManagement
import org.junit.ClassRule
import org.jvnet.hudson.test.JenkinsRule
import spock.lang.Shared
import spock.lang.Specification

abstract class AbstractJobSpec extends Specification {

    protected JobManagement jobManagement = new JenkinsJobManagement(System.out, [:], new File('.'))

    @Shared
    @ClassRule
    JenkinsRule jenkinsRule = new JenkinsRule()

    protected JobParent createJobParent() {
        JobParent jobParent = new JobParent() {
            @Override
            Object run() {
                return null
            }
        }

        jobParent.setJm(jobManagement)
        jobParent
    }
}
