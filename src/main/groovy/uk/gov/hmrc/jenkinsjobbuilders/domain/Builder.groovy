package uk.gov.hmrc.jenkinsjobbuilders.domain

import javaposse.jobdsl.dsl.DslFactory

interface Builder<T> {

    T build(DslFactory dslFactory)
}