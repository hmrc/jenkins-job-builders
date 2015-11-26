package uk.gov.hmrc.jenkinsjobbuilders.domain.publisher

import static uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.PostBuildTaskPublisher.postBuildTaskPublisher

class CleanWorkspacePostBuildTaskPublisher implements Publisher {
    private final Publisher publisher

    private CleanWorkspacePostBuildTaskPublisher() {
        this.publisher = postBuildTaskPublisher('Started by(.*)', """\
                                                                  |#!/bin/bash
                                                                  |cd \${WORKSPACE}
                                                                  |find . -maxdepth 1 -not -path . -not -path '*target*' -not -path '*logs*' -not -path '*results*' -type d | xargs rm -rf
                                                                  |if [ -d target ]; then
                                                                  |  find target -mindepth 2 -not -path '*report*' \\( -type f -o -type d -empty \\) -delete
                                                                  |fi
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
