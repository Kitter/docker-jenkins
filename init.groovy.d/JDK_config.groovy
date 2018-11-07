import jenkins.model.*;
import hudson.model.JDK;
import hudson.model.JDK.DescriptorImpl;

def env = System.getenv()
def systemJdkName = env["SYSTEM_JDK_NAME"];
def systemJdkHome = env["SYSTEM_JDK_HOME"];
def buildJdkName = env["BUILD_JDK_NAME"]
def buildJdkHome = env["BUILD_JDK_HOME"]

def instance = Jenkins.getInstance()

Thread.start {
    def desc = instance.getDescriptor("JDK");
	JDK systemJdk = null;
	JDK buildJdk = null;
	if(systemJdkName && systemJdkHome){
        systemJdk = new JDK(systemJdkName, systemJdkHome);
	}
	if(buildJdkName && buildJdkHome){
        buildJdk = new JDK(buildJdkName, buildJdkHome);
	}
	if(systemJdk){
	   if(buildJdk){
	       desc.setInstallations(systemJdk, buildJdk);
	   } else {
	       desc.setInstallations(systemJdk);
	   }
	} else {
	   if(buildJdk){
	       desc.setInstallations(buildJdk);
	   }
	}
	instance.save();
}