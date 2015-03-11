name := "rules-evaluator"

version := "0.1.0"

organization := "com.example"

scalaVersion := "2.11.2"

libraryDependencies ++= Seq(
    "com.jayway.jsonpath" % "json-path"       % "1.2.0",
    "com.novocode"        % "junit-interface" % "0.11"    % "test",
    "org.mockito"         % "mockito-core"    % "1.10.19" % "test"
)

antlr4Settings

antlr4GenListener in Antlr4 := true

antlr4GenVisitor in Antlr4 := true

antlr4Dependency in Antlr4 := "org.antlr" % "antlr4" % "4.5"

antlr4PackageName in Antlr4 := Some("com.example.rules.grammar")

assemblyMergeStrategy in assembly := {
    case PathList("org", "antlr", "runtime", xs @ _*) => MergeStrategy.first
    case PathList("org", "stringtemplate", "v4", xs @ _*) => MergeStrategy.first
    case x =>
        val oldStrategy = (assemblyMergeStrategy in assembly).value
        oldStrategy(x)
}
