import jenkins.model.*;

def env = System.getenv();
def stmpHost = env["EMAIL_EXT_STMP_HOST"];
def defaultSubject = env["EMAIL_EXT_DEFAULT_SUBJECT"];
def instance = Jenkins.getInstance();

Thread.start {
    def desc = instance.getDescriptor("hudson.plugins.emailext.ExtendedEmailPublisher");
	if(stmpHost){
        desc.smtpHost = stmpHost;
	}
	if(defaultSubject){
        desc.defaultSubject = defaultSubject;
	}
    desc.save();
}