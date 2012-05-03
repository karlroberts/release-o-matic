NAME
        release-o-matic - manage configuration across an enterprise's environments

DESCRIPTION
        If your project is depended upon by a bunch of other projects that your org builds,
        but it is in a deeply nested tree, eg it is a utility library that you are updating and you need that utility
        imported (depended upon) consistently up the dependency tree, and you need to make a new build of your project
        and release it and then cause all the dependant projects to pick up the change and release themselves all the
         way up the dependency tree, this tool is going to help you do it in a single command.


USAGE
        todo

STATE
        The `relese-o-matic` code is in pre-alpha dev mode. It is not suitable for use... yet


AUTHOR
        Karl Roberts <karl.roberts@owtelse.com>
        Karen Davis <karenbdavis@gmail.com>

NOTES
        1. official repository
           https://github.com/karlroberts/release-o-matic
        2. site and documentation
           http://??
        3. license (3 point BSD style)
           https://github.com/karlroberts/release-o-matic/blob/master/LICENSE



GET STARTED
        To get started you will need sbt 0.11.2 and scala 2.9.1

        Read the Setup instruction from sbt at
        https://github.com/harrah/xsbt/wiki/Getting-Started-Setup

        download the sbt-launch.jar
        and create a script called sbt in your path as directedi.

        cd to the project dir and type "sbt test". It should download all the necessary stuff and scala 2.9.1

        That said I normally set up scala myself in /usr/local/scala-latest which is link to the current latest version,
        then point my IDE to use it's sclac etc.

        I usually dev with intellij. There is a nice plugin that will create all the .idea files to set up the project.
        see https://github.com/mpeltonen/sbt-idea

        to use it add the following to your $HOME/.sbt/plugins/built.sbt file and the next time you do "sbt gen-idea" it will install the plugin and run it to create the intellij project files. you will then be able to open the project from intellij as an existing intelij project.

        //----- add to ~/.sbt/plugins/build.sbt
        // intelij plugin that builds .idea files
        resolvers += "sbt-idea-repo" at "http://mpeltonen.github.com/maven/"

        resolvers += "Scala-Tools Maven2 Snapshots Repository" at "http://scala-tools.org/repo-snapshots"

        addSbtPlugin("com.github.mpeltonen" %% "sbt-idea" % "1.0.0")

        //mvn sucks!
