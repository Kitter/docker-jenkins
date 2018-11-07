import jenkins.model.*
import hudson.util.*
import hudson.slaves.NodeProperty
import hudson.slaves.NodePropertyDescriptor
import org.jenkinsci.plugins.envinject.*

def env = System.getenv()
def name = env["GLOBAL_PASS_NAME"]
def password = env["GLOBAL_PASS_PASSWORD"]

def instance = Jenkins.getInstance()

Thread.start {
    if(name){
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> globalNodeProperties  = instance.getGlobalNodeProperties();
        
        envInjectNodeProperty= new EnvInjectNodeProperty(false, "")
        propDescriptor = envInjectNodeProperty.getDescriptor()
        
        //password entry
        def passEntry = new EnvInjectGlobalPasswordEntry(name, password)
        
        //password entries list, add you global password here
        List<EnvInjectGlobalPasswordEntry> envInjectGlobalPasswordEntriesList= [passEntry];
        propDescriptor.envInjectGlobalPasswordEntries = 
                  envInjectGlobalPasswordEntriesList.toArray(
                  new EnvInjectGlobalPasswordEntry[envInjectGlobalPasswordEntriesList.size()]
                 );
        propDescriptor.save();
	}
}