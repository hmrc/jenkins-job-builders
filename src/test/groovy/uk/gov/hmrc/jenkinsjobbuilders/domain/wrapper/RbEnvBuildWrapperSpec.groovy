package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper

import javaposse.jobdsl.dsl.Job
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder

import static uk.gov.hmrc.jenkinsjobbuilders.domain.configure.PyEnvBuildWrapper.pyEnvBuildWrapper
import static uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper.RbEnvBuildWrapper.rbEnvBuildWrapper

class RbEnvBuildWrapperSpec extends AbstractJobSpec {
    void 'test default XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withWrappers(rbEnvBuildWrapper())

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            def rubyObject = buildWrappers.'ruby-proxy-object'.'ruby-object'

            rubyObject.'@ruby-class'.text() == 'Jenkins::Tasks::BuildWrapperProxy'
            rubyObject.'@pluginid'.text() == 'rbenv'
            rubyObject.pluginid.text() == 'rbenv'
            rubyObject.object.'@ruby-class'.text() == 'RbenvWrapper'
            rubyObject.object.'@pluginid'.text() == 'rbenv'
            rubyObject.object.'rbenv__root'.text() == '$HOME/.rbenv'
            rubyObject.object.'gem__list'.text() == ''
            rubyObject.object.'rbenv__revision'.text() == 'master'
            rubyObject.object.'version'.text() == 'null'
            rubyObject.object.'ignore__local__version'.text() == 'false'
        }
    }

    void 'test overridden XML output'() {
        given:
        final String overriddenRubyVersion = "2.5.1"
        final List<String> gemSpecification = ['bundler', 'gpgme']
        final JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description')
                .withWrappers(rbEnvBuildWrapper(overriddenRubyVersion,
                                                true,
                                                gemSpecification,
                                                "/tmp"))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            def rubyObject = buildWrappers.'ruby-proxy-object'.'ruby-object'

            rubyObject.'@ruby-class'.text() == 'Jenkins::Tasks::BuildWrapperProxy'
            rubyObject.'@pluginid'.text() == 'rbenv'
            rubyObject.pluginid.text() == 'rbenv'
            rubyObject.object.'@ruby-class'.text() == 'RbenvWrapper'
            rubyObject.object.'@pluginid'.text() == 'rbenv'
            rubyObject.object.'rbenv__root'.text() == '/tmp'
            rubyObject.object.'gem__list'.text() == 'bundler,gpgme'
            rubyObject.object.'rbenv__revision'.text() == 'master'
            rubyObject.object.'version'.text() == overriddenRubyVersion
            rubyObject.object.'ignore__local__version'.text() == 'true'
        }
    }
}
