package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher

import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.PostBuildTaskPublisher.postBuildTaskPublisher

class CleanWorkspacePostBuildTaskPublisher implements Publisher {
    private final Publisher publisher

    private CleanWorkspacePostBuildTaskPublisher() {
        this.publisher = postBuildTaskPublisher('Started by(.*)', """\
                                                                  |#!/bin/bash
                                                                  |cd \${WORKSPACE}
                                                                  |find . -maxdepth 1 -not -path . -not -path '*target*' -not -path '*logs*' -type d | xargs rm -rf
                                                                  |find target -mindepth 2 -not -path '*report*' \\( -type f -o -type d -empty \\) -delete
                                                                  """.stripMargin())
    }

    static Publisher cleanWorkspacePostBuildTaskPublisher() {
        new CleanWorkspacePostBuildTaskPublisher()
    }

    @Override
    Closure toDsl() {
        return publisher.toDsl()
    }
}