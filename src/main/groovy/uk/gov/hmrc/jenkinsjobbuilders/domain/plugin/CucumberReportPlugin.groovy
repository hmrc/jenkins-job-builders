package uk.gov.hmrc.jenkinsjobbuilders.domain.plugin

final class CucumberReportPlugin implements Plugin {

    private CucumberReportPlugin() {}

    Closure toDsl() {
        return {
            it / 'publishers' / 'net.masterthought.jenkins.CucumberReportPublisher' {
                'jsonReportDirectory'('')
                'pluginUrlPath'('')
                'skippedFails'('false')
                'undefinedFails'('false')
                'noFlashCharts'('false')
                'ignoreFailedTests'('false')
            }
        }
    }

    static CucumberReportPlugin cucumberReportsPlugin() {
        new CucumberReportPlugin()
    }
}