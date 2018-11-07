import jenkins.model.*;
import org.jenkinsci.plugins.stashNotifier.*;

def env = System.getenv()
def rootUrl = env["STASH_ROOT_URL"]
def credentialsId = env["STASH_CREDENTIALS_ID"]

def instance = Jenkins.getInstance();

Thread.start {
    def desc = instance.getDescriptor(StashNotifier)
    if(rootUrl){
        desc.stashRootUrl = rootUrl
	}
	if(credentialsId){
        desc.credentialsId = credentialsId 
	}
    
    stash.save()
}