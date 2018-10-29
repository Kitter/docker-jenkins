import hudson.model.*;
import jenkins.model.*;
import hudson.tools.*;
import hudson.tasks.Maven.MavenInstaller;
import hudson.tasks.Maven.MavenInstallation;
import org.jenkinsci.plugins.configfiles.maven.*
import org.jenkinsci.plugins.configfiles.maven.security.*

// Constants
def instance = Jenkins.getInstance()

Thread.start {
    sleep 10000

    // Maven
    println "--> Configuring Maven"
    def desc_MavenTool = instance.getDescriptor("hudson.tasks.Maven")
    def maven_installations = desc_MavenTool.getInstallations()

    def name="Maven"
    def maven_inst = new MavenInstallation(
      name, // Name
      "/usr/share/maven", // Home
    )

    def maven_inst_exists = false
    maven_installations.each {
      installation = (MavenInstallation) it
        if ( maven_inst.getName() ==  installation.getName() ) {
                maven_inst_exists = true
                println("Found existing installation: " + installation.getName())
        }
    }

    if (!maven_inst_exists) {
        maven_installations += maven_inst
    }

    desc_MavenTool.setInstallations((MavenInstallation[]) maven_installations)
    desc_MavenTool.save()

    // Configuring global maven settings
    def mirrorUrl = System.getenv("NEXUS_REPO")
    if (mirrorUrl) {
        println("--> Configuring global maven settings")
        def store = instance.getExtensionList('org.jenkinsci.plugins.configfiles.GlobalConfigFiles')[0]
        def configId =  'global-maven-settings'
        def configName = 'global-maven-settings'
        def configComment = 'Maven Mirror Settings'
        def configContent  = """<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.1.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.1.0 http://maven.apache.org/xsd/settings-1.1.0.xsd">
  <mirrors>
    <mirror>
      <id>nexus</id>
      <name>Local Maven Repository Manager</name>
      <url>${mirrorUrl}</url>
      <mirrorOf>*</mirrorOf>
    </mirror>
  </mirrors>
  <servers>
    <server>
      <id>deployment</id>
      <username>deployment</username>
      <password>deployment123</password>
    </server>
  </servers>
</settings>
"""
        def globalConfig = new GlobalMavenSettingsConfig(configId, configName, configComment, configContent, false, null)
        store.save(globalConfig)
    }

    // Save the state
    instance.save()
}
