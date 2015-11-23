package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

final class CucumberReportsPublisher implements Configure {

    private CucumberReportsPublisher() {}

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

    static CucumberReportsPublisher cucumberReportsPublisher() {
        new CucumberReportsPublisher()
    }
}