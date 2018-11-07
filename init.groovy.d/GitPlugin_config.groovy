import jenkins.model.*

def env = System.getenv()
def userName = env["GIT_GLOBAL_CONFIG_NAME"]
def userEmail = env["GIT_GLOBAL_CONFIG_EMAIL"]

def instance = Jenkins.getInstance()

Thread.start {
    def desc = instance.getDescriptor("hudson.plugins.git.GitSCM")
	if(userName){
        desc.setGlobalConfigName(userName)
	}
	if(userEmail){
        desc.setGlobalConfigEmail(userEmail)
	}
    desc.save()
}