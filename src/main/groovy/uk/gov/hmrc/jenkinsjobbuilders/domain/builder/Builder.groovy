package uk.gov.hmrc.jenkinsjobbuilders.domain.builder

import javaposse.jobdsl.dsl.DslFactory

interface Builder<T> {

    T build(DslFactory dslFactory)
}