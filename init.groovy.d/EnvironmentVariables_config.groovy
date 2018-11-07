import hudson.EnvVars;
import hudson.slaves.EnvironmentVariablesNodeProperty;
import hudson.slaves.NodeProperty;
import hudson.slaves.NodePropertyDescriptor;
import hudson.util.DescribableList;
import jenkins.model.Jenkins;

def env = System.getenv()
def envPropertyFile = env["ENV_PROPERTY_FILE"]
Jenkins instance = Jenkins.getInstance();

Thread.start {
    DescribableList<NodeProperty<?>, NodePropertyDescriptor> globalNodeProperties = instance.getGlobalNodeProperties();
    List<EnvironmentVariablesNodeProperty> envVarsNodePropertyList = globalNodeProperties.getAll(EnvironmentVariablesNodeProperty.class);
    EnvVars envVars = null;
    if(envVarsNodePropertyList == null || envVarsNodePropertyList.size() == 0) {
        EnvironmentVariablesNodeProperty newEnvVarsNodeProperty = new EnvironmentVariablesNodeProperty();
        globalNodeProperties.add(newEnvVarsNodeProperty);
        envVars = newEnvVarsNodeProperty.getEnvVars();
    }else{
        envVars = envVarsNodePropertyList.get(0).getEnvVars();
    }
    
	if(envPropertyFile){
	    File propertiesFile = new File(envPropertyFile);
	    Properties properties = new Properties();
		if(propertiesFile.exists()){
            propertiesFile.withInputStream {
                properties.load(it);
            }
	        properties.each{
	            envVars.put(it.key, it.value);
	        }
	        instance.save();
		}
	}
}