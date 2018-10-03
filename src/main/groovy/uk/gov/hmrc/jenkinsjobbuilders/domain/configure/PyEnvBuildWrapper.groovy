package uk.gov.hmrc.jenkinsjobbuilders.domain.configure

class PyEnvBuildWrapper implements Configure {

  private final String defaultPythonVersion
  private final boolean ignoreProjectPythonVersion
  private final List<String> pipSpecfication

  private PyEnvBuildWrapper(final String defaultPythonVersion,
                            final boolean ignoreProjectPythonVersion,
                            final List<String> pipSpecfication) {

    this.defaultPythonVersion = defaultPythonVersion
    this.ignoreProjectPythonVersion = ignoreProjectPythonVersion
    this.pipSpecfication = pipSpecfication
  }

  static PyEnvBuildWrapper pyEnvBuildWrapper(final String defaultPythonVersion = null,
                                             final boolean ignoreProjectPythonVersion = false,
                                             final List<String> pipSpecfication = []) {
    new PyEnvBuildWrapper(defaultPythonVersion,
                          ignoreProjectPythonVersion,
                          pipSpecfication)
  }

  Closure toDsl() {
    return {
      it / 'buildWrappers' / 'ruby-proxy-object' / 'ruby-object'('ruby-class': 'Jenkins::Tasks::BuildWrapperProxy', pluginid: 'pyenv') {
        'pluginid'('pyenv', [pluginid: 'pyenv', 'ruby-class': 'String'])
        object('ruby-class': 'PyenvWrapper', pluginid: 'pyenv') {
          pyenv__root('$HOME/.pyenv', [pluginid: 'pyenv', 'ruby-class': 'String'])
          pip__list(pipSpecfication.sort().join(','), [pluginid: 'pyenv', 'ruby-class': 'String'])
          pyenv__revision('master', [pluginid: 'pyenv', 'ruby-class': 'String'])
          version(defaultPythonVersion, [pluginid: 'pyenv', 'ruby-class': 'String'])
          ignore__local__version('false', [pluginid: 'pyenv', 'ruby-class': ignoreProjectPythonVersion ? 'TrueClass' : 'FalseClass'])
          pyenv__repository('https://github.com/yyuu/pyenv.git', [pluginid: 'pyenv', 'ruby-class': 'String'])
        }
      }
    }
  }
}

