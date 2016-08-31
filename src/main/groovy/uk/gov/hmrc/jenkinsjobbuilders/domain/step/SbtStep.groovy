package uk.gov.hmrc.jenkinsjobbuilders.domain.step


import static uk.gov.hmrc.jenkinsjobbuilders.domain.step.ShellStep.shellStep

class SbtStep implements Step {

    private static final String EOL = System.getProperty("line.separator")
    private final Step step

    private SbtStep(String bashScript, List<String> sbtCommands, String tmpDir) {
        this.step = shellStep(sbtCommands.inject(bashScript + EOL + "mkdir -p $tmpDir") {
                                string, item -> string + EOL + "sbt $item -Djava.io.tmpdir=$tmpDir"
                              })
    }

    static Step sbtStep(List<String> commands, String tmpDir) {
        sbtStep("", commands, tmpDir)
    }

    static Step sbtStep(String bashScript, List<String> commands, String tmpDir) {
        new SbtStep(bashScript, commands, tmpDir)
    }

    @Override
    Closure toDsl() {
        step.toDsl()
    }
}
