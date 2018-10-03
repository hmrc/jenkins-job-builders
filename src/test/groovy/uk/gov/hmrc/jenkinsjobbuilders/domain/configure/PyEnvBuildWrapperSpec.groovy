package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

import javaposse.jobdsl.dsl.Job
import uk.gov.hmrc.jenkinsjobbuilders.domain.AbstractJobSpec
import uk.gov.hmrc.jenkinsjobbuilders.domain.builder.JobBuilder
import static uk.gov.hmrc.jenkinsjobbuilders.domain.configure.PyEnvBuildWrapper.pyEnvBuildWrapper

class PyEnvBuildWrapperSpec extends AbstractJobSpec {
    void 'test default XML output'() {
        given:
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
                                               withConfigures(pyEnvBuildWrapper())

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            def rubyObject = buildWrappers.'ruby-proxy-object'.'ruby-object'
            rubyObject.'@ruby-class'.text() == 'Jenkins::Tasks::BuildWrapperProxy'
            rubyObject.'@pluginid'.text() == 'pyenv'

            rubyObject.pluginid.'@pluginid'.text() == 'pyenv'
            rubyObject.pluginid.'@ruby-class'.text() == 'String'
            rubyObject.pluginid.text() == 'pyenv'

            rubyObject.object.'@ruby-class'.text() == 'PyenvWrapper'
            rubyObject.object.'@pluginid'.text() == 'pyenv'

            rubyObject.object.'pyenv__root'.'@pluginid'.text() == 'pyenv'
            rubyObject.object.'pyenv__root'.'@ruby-class'.text() == 'String'
            rubyObject.object.'pyenv__root'.text() == '$HOME/.pyenv'

            rubyObject.object.'pip__list'.'@pluginid'.text() == 'pyenv'
            rubyObject.object.'pip__list'.'@ruby-class'.text() == 'String'
            rubyObject.object.'pip__list'.text() == ''

            rubyObject.object.'pyenv__revision'.'@pluginid'.text() == 'pyenv'
            rubyObject.object.'pyenv__revision'.'@ruby-class'.text() == 'String'
            rubyObject.object.'pyenv__revision'.text() == 'master'

            rubyObject.object.'version'.'@pluginid'.text() == 'pyenv'
            rubyObject.object.'version'.'@ruby-class'.text() == 'String'
            rubyObject.object.'version'.text() == 'null'

            rubyObject.object.'ignore__local__version'.'@pluginid'.text() == 'pyenv'
            rubyObject.object.'ignore__local__version'.'@ruby-class'.text() == 'FalseClass'

            rubyObject.object.'pyenv__repository'.'@pluginid'.text() == 'pyenv'
            rubyObject.object.'pyenv__repository'.'@ruby-class'.text() == 'String'
            rubyObject.object.'pyenv__repository'.text() == 'https://github.com/yyuu/pyenv.git'
        }
    }

    void 'test overridden XML output'() {
        given:
        final String defaultPythonVersion = "2.7.14"
        final List<String> pipSpecification = ['tox', 'retry==0.9.2']
        JobBuilder jobBuilder = new JobBuilder('test-job', 'test-job-description').
            withConfigures(pyEnvBuildWrapper(defaultPythonVersion,
                                             true,
                                             pipSpecification))

        when:
        Job job = jobBuilder.build(JOB_PARENT)

        then:
        with(job.node) {
            def rubyObject = buildWrappers.'ruby-proxy-object'.'ruby-object'
            rubyObject.object.'pip__list'.text() == 'retry==0.9.2,tox'

            rubyObject.object.'version'.text() == defaultPythonVersion

            rubyObject.object.'ignore__local__version'.'@ruby-class'.text() == 'TrueClass'
        }
    }

}
