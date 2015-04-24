package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher


final class JUnitReportsPublisher implements Publisher {
    private final String junitReportsPattern

    private JUnitReportsPublisher(String junitReportsPattern) {
        this.junitReportsPattern = junitReportsPattern
    }

    static JUnitReportsPublisher jUnitReportsPublisher(String junitReportsPattern) {
        new JUnitReportsPublisher(junitReportsPattern)
    }

    @Override
    Closure toDsl() {
        return {
            archiveJunit(junitReportsPattern) {
                testDataPublishers {
                    allowClaimingOfFailedTests()
                }
            }
        }
    }
}