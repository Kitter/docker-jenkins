import jenkins.model.*;

def env = System.getenv();
def stmpHost = env["EMAIL_STMP_HOST"];
def instance = Jenkins.getInstance();

Thread.start {
    if(stmpHost){
        def desc = instance.getDescriptor("hudson.tasks.Mailer");
        desc.smtpHost = stmpHost;
        desc.save();
	}
}