addSbtPlugin("com.dwijnand"      % "sbt-dynver"      % "2.0.0")
addSbtPlugin("com.dwijnand"      % "sbt-travisci"    % "1.1.1")
addSbtPlugin("com.geirsson"      % "sbt-scalafmt"    % "1.4.0")
addSbtPlugin("com.jsuereth"      % "sbt-pgp"         % "1.1.0")
addSbtPlugin("de.heikoseeberger" % "sbt-header"      % "4.1.0")
addSbtPlugin("org.foundweekends" % "sbt-bintray"     % "0.5.3")

libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.7.25" // Needed by sbt-git
