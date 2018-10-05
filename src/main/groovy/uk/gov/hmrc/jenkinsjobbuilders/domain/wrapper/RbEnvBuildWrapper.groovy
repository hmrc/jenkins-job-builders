package uk.gov.hmrc.jenkinsjobbuilders.domain.wrapper

import groovy.transform.Immutable

@Immutable
class RbEnvBuildWrapper implements Wrapper {

    String rubyVersionOverride
    boolean ignoreLocalRubyVersion
    List<String> gems
    String rbenvRoot

    static RbEnvBuildWrapper rbEnvBuildWrapper() {
        return new RbEnvBuildWrapper(null,
                                     false,
                                     null,
                                     null)
    }

    static RbEnvBuildWrapper rbEnvBuildWrapper(final String rubyVersionOverride,
                                               final boolean ignoreLocalRubyVersion,
                                               final List<String> gems,
                                               final String rbenvRoot) {
        return new RbEnvBuildWrapper(rubyVersionOverride,
                                     ignoreLocalRubyVersion,
                                     gems,
                                     rbenvRoot)
    }

    @Override
    Closure toDsl() {
        return {
            rbenv(rubyVersionOverride) {
                ignoreLocalVersion(ignoreLocalRubyVersion)

                if(this.gems) {
                    delegate.gems(*(this.gems))
                }

                if(this.rbenvRoot) {
                    root(rbenvRoot)
                }
            }
        }
    }
}
