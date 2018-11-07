import org.jenkinsci.plugins.StashBranchParameter.*;
import hudson.util.Secret;
import jenkins.model.*;

def env = System.getenv()
def stashApiUrl = env["STASH_API_URL"]
def userName = env["STASH_USER_NAME"]
def password = env["STASH_PASSWORD"]
def instance = Jenkins.getInstance();

Thread.start {
    def desc = instance.getDescriptor(StashBranchParameterDefinition)
    if(stashApiUrl){
        desc.setStashApiUrl(stashApiUrl)
    }
    if(userName){
        desc.setUsername(userName);
    }
    if(password){
        Secret secret = Secret.fromString(password);
        desc.setPassword(secret);
    }
    instance.save();
}