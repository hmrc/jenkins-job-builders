package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher


final class HtmlReportsPublisher implements Publisher {
    private final Map<String, String> htmlReportDirs
    private boolean keep
    private boolean alwaysLinkLatest

    private HtmlReportsPublisher(Map<String, String> htmlReportDirs, boolean keep = false, boolean alwaysLinkLatest = false) {
        this.htmlReportDirs = htmlReportDirs
        this.keep = keep
        this.alwaysLinkLatest = alwaysLinkLatest
    }

    static HtmlReportsPublisher htmlReportsPublisher(Map<String, String> htmlReportDirs, boolean keep = false, boolean alwaysLinkLatest = false) {
        new HtmlReportsPublisher(htmlReportDirs, keep, alwaysLinkLatest)
    }

    @Override
    Closure toDsl() {
        return {
            publishHtml {
                htmlReportDirs.each { dir, name ->
                    report(dir) {
                        reportName(name)
                        allowMissing(true)
                        keepAll(keep)
                        alwaysLinkToLastBuild(alwaysLinkLatest)
                    }
                }
            }
        }
    }
}
