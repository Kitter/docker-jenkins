import hudson.model.*;
import jenkins.model.*;

// Variables
def env = System.getenv()
def root_Url = env["ROOT_URL"]
def admin_email = env["ADMIN_EMAIL"]

Thread.start {
    jlc = JenkinsLocationConfiguration.get()
	if(root_Url){
        jlc.setUrl(root_Url)
    }
	if(admin_email){
	    jlc.setAdminAddress(admin_email)
	}
    jlc.save()
}