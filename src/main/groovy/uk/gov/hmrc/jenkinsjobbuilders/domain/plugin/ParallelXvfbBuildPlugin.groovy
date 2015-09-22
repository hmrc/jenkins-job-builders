package uk.gov.hmrc.jenkinsjobbuilders.domain.plugin

final class ParallelXvfbBuildPlugin implements Plugin {

    private ParallelXvfbBuildPlugin() {}

    Closure toDsl() {
        return {
            it / 'buildWrappers' / 'org.jenkinsci.plugins.xvfb.XvfbBuildWrapper' {
                'switch'('on')
                'installationName'('default')
                'screen'('1920x1080x24')
                'displayNameOffset'('1')
                'debug'('true')
                'timeout'('0')
                'parallelBuild'('true')
                'shutdownWithBuild'('true')
            }
        }
    }

    static ParallelXvfbBuildPlugin xvfbBuildPlugin() {
        new ParallelXvfbBuildPlugin()
    }
}