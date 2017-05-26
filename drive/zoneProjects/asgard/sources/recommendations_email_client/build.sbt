name := """recommendations_email_client"""

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  // Uncomment to use Akka
  //"com.typesafe.akka" % "akka-actor_2.11" % "2.3.9",
  "org.hamcrest"      % "hamcrest-all"    % "1.3",
  "junit"             % "junit"           % "4.12"
      exclude ("org.hamcrest" , "hamcrest-core"),
  "org.mockito"       % "mockito-all"     % "1.10.19"
      exclude ("org.hamcrest" , "hamcrest-core"),
  "org.slf4j"         % "slf4j-api"       % "1.7.13",
  "ch.qos.logback"    % "logback-classic" % "1.1.3",
  "ch.qos.logback"    % "logback-core"    % "1.1.3",
  "org.apache.commons"% "commons-lang3"   % "3.4",
  "com.google.code.gson"% "gson"          % "2.5",
  "commons-codec"     % "commons-codec"   % "1.10",
  "org.apache.commons"% "commons-collections4" % "4.1",
  "org.powermock"     % "powermock-module-junit4" % "1.6.4",
  "org.powermock"     % "powermock-api-mockito" % "1.6.4"

)
