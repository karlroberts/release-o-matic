This is a tool to help sanitise a maven release process.

If your project is depended upon by a bunch of other projectsi that your org builds, but it is in a deeply nested tree, eg it is a utility library that you are updateing and you need that utility used consistantly up the dependency tree, that your organisation builds, and you need to make a new release of your project release it and then cause all the dependant projects to pick up the change and release themselfs up the dependency tree, this tool is going to help you do it in a single command.

mvn sucks!
