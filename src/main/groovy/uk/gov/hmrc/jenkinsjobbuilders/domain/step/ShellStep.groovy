package uk.gov.hmrc.jenkinsjobbuilders.domain.step


class ShellStep implements Step {
    private final String command

    private ShellStep(String command) {
        this.command = command
    }

    static Step shellStep(String command) {
        new ShellStep(command)
    }

    @Override
    Closure toDsl() {
        return {
            shell(""" 
                  |#!/bin/bash
                  |${command}
                  """.stripMargin())
        }
    }
}
