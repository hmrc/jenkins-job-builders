package uk.gov.hmrc.jenkinsjobbuilders.domain.throttle

import uk.gov.hmrc.jenkinsjobbuilders.domain.Setting

class ThrottleConfiguration implements Setting {

    final List<String> categoriesList
    final int maxConcurrentPerNode
    final int maxConcurrentTotal
    final boolean disableThrottle

    private ThrottleConfiguration(List<String> categories, int maxConcurrentPerNode, int maxConcurrentTotal, boolean disableThrottle) {
        this.categoriesList = categories
        this.maxConcurrentPerNode = maxConcurrentPerNode
        this.maxConcurrentTotal = maxConcurrentTotal
        this.disableThrottle = disableThrottle
    }

    static ThrottleConfiguration throttleConfiguration(List<String> categories, int maxConcurrentPerNode, int maxConcurrentTotal, boolean throttleDisabled) {
        new ThrottleConfiguration(categories, maxConcurrentPerNode, maxConcurrentTotal, throttleDisabled)
    }

    @Override
    Closure toDsl() {
        return {
            maxPerNode(this.maxConcurrentPerNode)
            maxTotal(this.maxConcurrentTotal)
            categories(this.categoriesList)
            throttleDisabled(this.disableThrottle)
        }
    }
}
