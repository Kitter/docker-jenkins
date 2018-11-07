import jenkins.model.Jenkins;
import hudson.plugins.git.GitTool;
import hudson.tools.ToolProperty;

def env = System.getenv()
def git_name = env["GIT_NAME"]
def git_path = env["GIT_PATH"]

def instance = Jenkins.getInstance();

Thread.start {
    if(git_name && git_path){
        def desc = instance.getDescriptor("hudson.plugins.git.GitTool");
        
        GitTool tool = new GitTool(git_name, git_path, Collections.<ToolProperty<?>>emptyList());
        desc.setInstallations(tool);
        desc.save();
	}
}