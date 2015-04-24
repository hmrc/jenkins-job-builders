package uk.gov.hmrc.jenkinsjobbuilders.domain.plugin

final class XvfbBuildPlugin implements Plugin {

    private XvfbBuildPlugin() {}

    Closure toDsl() {
        return {
            it / 'buildWrappers' / 'org.jenkinsci.plugins.xvfb.XvfbBuildWrapper' {
                'switch'('on')
                'installationName'('default')
                'displayName'('99')
                'screen'('1920x1080x24')
                'displayNameOffset'('1')
                'debug'('true')
                'timeout'('0')
                'shutdownWithBuild'('true')
            }
        }
    }

    static XvfbBuildPlugin xvfbBuildPlugin() {
        new XvfbBuildPlugin()
    }
}