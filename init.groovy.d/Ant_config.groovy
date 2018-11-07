import hudson.model.*;
import jenkins.model.*;
import hudson.tools.*;
import hudson.tasks.Ant.AntInstaller;
import hudson.tasks.Ant.AntInstallation;

// Variables
def env = System.getenv()
def ant_name = env["ANT_NAME"]
def ant_home = env["ANT_HOME"]

// Constants
def instance = Jenkins.getInstance()

Thread.start {
    // Ant
    if(ant_name && ant_home) {
        def desc = instance.getDescriptor("Ant")
        def antInstallation = new AntInstallation(ant_name, ant_home, Collections.<ToolProperty<?>>emptyList())
        desc.setInstallations(antInstallation)
        desc.save()
        
        // Save the state
        instance.save()
	}
}