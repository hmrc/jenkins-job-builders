package uk.gov.hmrc.jenkinsjobbuilders.domain.plugin

final class XvfbBuildPlugin implements Plugin {

    private final boolean parallel

    private XvfbBuildPlugin(boolean parallel) {
        this.parallel = parallel
    }

    Closure toDsl() {
        return {
            it / 'buildWrappers' / 'org.jenkinsci.plugins.xvfb.XvfbBuildWrapper' {
                'switch'('on')
                'installationName'('default')
                'screen'('1920x1080x24')
                'displayNameOffset'('1')
                'debug'('true')
                'timeout'('0')
                'parallelBuild'("$parallel")
                'shutdownWithBuild'('true')
            }
        }
    }

    static XvfbBuildPlugin xvfbBuildPlugin() {
        new XvfbBuildPlugin(false)
    }

    static XvfbBuildPlugin parallelXvfbBuildPlugin() {
        new XvfbBuildPlugin(true)
    }
}