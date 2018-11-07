import com.cloudbees.plugins.credentials.impl.*;
import com.cloudbees.plugins.credentials.*;
import com.cloudbees.plugins.credentials.domains.*;

def env = System.getenv();
def scope = env["CREDENTIALS_SCOPE"];
def id = env["CREDENTIALS_ID"];
def userName = env["CREDENTIALS_USER_NAME"];
def password = env["CREDENTIALS_PASSWORD"];
def description = env["CREDENTIALS_DESCRIPTION"];

if(!id){
    id = java.util.UUID.randomUUID().toString();
}

if(userName && password){
    if(!scope || (CredentialsScope.GLOBAL.toString() != scope  && CredentialsScope.SYSTEM.toString() != scope)){
	    scope = CredentialsScope.GLOBAL;
	} else {
	    scope = CredentialsScope.valueOf(scope);
	}
    Credentials credentials = (Credentials) new UsernamePasswordCredentialsImpl(scope, id, description, userName, password);
    SystemCredentialsProvider.getInstance().getStore().addCredentials(Domain.global(), credentials);
}