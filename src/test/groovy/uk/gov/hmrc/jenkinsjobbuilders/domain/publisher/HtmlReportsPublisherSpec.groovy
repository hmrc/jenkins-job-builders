package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher

import javaposse.jobdsl.dsl.Job
import spock.lang.Specification
import uk.gov.hmrc.jenkinsjobbuilders.domain.JobParents
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.HtmlReportsPublisher.htmlReportsPublisher

@Mixin(JobParents)
class HtmlReportsPublisherSpec extends Specification {

    void 'With keepAll'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                withPublishers(htmlReportsPublisher(['target/fun-browser-test-reports/html-report': 'ScalaTest (fun-browser) Results'], true))

        when:
        Job job = jobBuilder.build(jobParent())

        then:
        with(job.node) {

            publishers.'htmlpublisher.HtmlPublisher'.reportTargets.'htmlpublisher.HtmlPublisherTarget'.reportName.text() == 'ScalaTest (fun-browser) Results'
            publishers.'htmlpublisher.HtmlPublisher'.reportTargets.'htmlpublisher.HtmlPublisherTarget'.reportDir.text() == 'target/fun-browser-test-reports/html-report'
            publishers.'htmlpublisher.HtmlPublisher'.reportTargets.'htmlpublisher.HtmlPublisherTarget'.allowMissing.text() == 'true'
            publishers.'htmlpublisher.HtmlPublisher'.reportTargets.'htmlpublisher.HtmlPublisherTarget'.keepAll.text() == 'true'
        }
    }

    void 'Without keepAll (default)'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                withPublishers(htmlReportsPublisher(['target/fun-browser-test-reports/html-report': 'ScalaTest (fun-browser) Results']))

        when:
        Job job = jobBuilder.build(jobParent())

        then:
        with(job.node) {

            publishers.'htmlpublisher.HtmlPublisher'.reportTargets.'htmlpublisher.HtmlPublisherTarget'.reportName.text() == 'ScalaTest (fun-browser) Results'
            publishers.'htmlpublisher.HtmlPublisher'.reportTargets.'htmlpublisher.HtmlPublisherTarget'.reportDir.text() == 'target/fun-browser-test-reports/html-report'
            publishers.'htmlpublisher.HtmlPublisher'.reportTargets.'htmlpublisher.HtmlPublisherTarget'.allowMissing.text() == 'true'
            publishers.'htmlpublisher.HtmlPublisher'.reportTargets.'htmlpublisher.HtmlPublisherTarget'.keepAll.text() == 'false'
        }
    }

    void 'Without keepAll'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                withPublishers(htmlReportsPublisher(['target/fun-browser-test-reports/html-report': 'ScalaTest (fun-browser) Results'], false))

        when:
        Job job = jobBuilder.build(jobParent())

        then:
        with(job.node) {

            publishers.'htmlpublisher.HtmlPublisher'.reportTargets.'htmlpublisher.HtmlPublisherTarget'.reportName.text() == 'ScalaTest (fun-browser) Results'
            publishers.'htmlpublisher.HtmlPublisher'.reportTargets.'htmlpublisher.HtmlPublisherTarget'.reportDir.text() == 'target/fun-browser-test-reports/html-report'
            publishers.'htmlpublisher.HtmlPublisher'.reportTargets.'htmlpublisher.HtmlPublisherTarget'.allowMissing.text() == 'true'
            publishers.'htmlpublisher.HtmlPublisher'.reportTargets.'htmlpublisher.HtmlPublisherTarget'.keepAll.text() == 'false'
        }
    }
}
