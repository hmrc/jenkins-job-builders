package uk.gov.hmrc.jenkinsjobbuilders.domain.variables

import uk.gov.hmrc.jenkinsjobbuilders.domain.Setting


public interface EnvironmentVariable {

    String getName()

    String getValue()
}