This is a tool to help sanitise a maven release process.

If your project is depended upon by a bunch of other projectsi that your org builds, but it is in a deeply nested tree, eg it is a utility library that you are updateing and you need that utility used consistantly up the dependency tree, that your organisation builds, and you need to make a new release of your project release it and then cause all the dependant projects to pick up the change and release themselfs up the dependency tree, this tool is going to help you do it in a single command.


To get started you will need sbt 0.11.2 and scala 2.9.1

Read the Setup instruction from sbt at 
https://github.com/harrah/xsbt/wiki/Getting-Started-Setup

download the sbt-launch.jar
and create a script called sbt in your path as directedi.

cd to the project dir and type "sbt test". It should download all the necessary stuff and scala 2.9.1

That said I normally set up scala myself in /usr/local/scala-latest which is link to the current latest version, then point my IDE to use it's sclac etc.

I usually dev with intellij. There is a nice plugin that will create all the .idea files to set up the project.
see https://github.com/mpeltonen/sbt-idea

to use it add the following to your $HOME/.sbt/plugins/built.sbt file and the next time you do "sbt gen-idea" it will install the plugin and run it to create the intellij project files. you will then be able to open the project from intellij as an existing intelij project.

//----- add to ~/.sbt/plugins/build.sbt
// intelij plugin that builds .idea files
resolvers += "sbt-idea-repo" at "http://mpeltonen.github.com/maven/"

resolvers += "Scala-Tools Maven2 Snapshots Repository" at "http://scala-tools.org/repo-snapshots"

addSbtPlugin("com.github.mpeltonen" %% "sbt-idea" % "1.0.0")

//mvn sucks!
