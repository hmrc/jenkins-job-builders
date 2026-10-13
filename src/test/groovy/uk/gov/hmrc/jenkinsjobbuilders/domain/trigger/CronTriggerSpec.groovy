package uk.gov.hmrc.jenkinsjobbuilders.domain.trigger

import javaposse.jobdsl.dsl.Job
import spock.lang.Unroll
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

import static uk.gov.hmrc.jenkinsjobbuilders.domain.trigger.CronTrigger.cronTrigger

class CronTriggerSpec extends AbstractJobSpec {

    void 'test XML output'() {
        given:
        JobBuilder jobBuilder =
                new JobBuilder('test', 'description')
                        .withTriggers(cronTrigger('H 4 * * 1-5'))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            triggers.'hudson.triggers.TimerTrigger'.spec.text() == 'H 4 * * 1-5'
        }
    }

    @Unroll
    void 'valid spec "#spec" should not throw'() {
        when:
        cronTrigger(spec)

        then:
        noExceptionThrown()

        where:
        spec << [
            'H 4 * * 1-5',
            '0 21 * * 5',
            '0 0 * * *',
            '@daily',
            'H(0-29) * * * *',
            'TZ=Europe/London\nH 4 * * 1-5',
            '15 * * * *\n45 0-3 * * *',
        ]
    }

    @Unroll
    void 'invalid spec "#spec" throws IllegalArgumentException containing spec'() {
        when:
        cronTrigger(spec)

        then:
        IllegalArgumentException e = thrown()
        e.message.startsWith("Invalid Jenkins cron spec '${spec}'")

        where:
        spec << [
            '0 0 21 ? * 6 *',
            '0  9 ? * 5L *',
            '0 22 * * 7/7',
            '60 9 * * 4',
            'test-cron',
        ]
    }
}
