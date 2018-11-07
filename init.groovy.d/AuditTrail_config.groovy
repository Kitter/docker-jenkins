import jenkins.model.*;
import hudson.plugins.audit_trail.AuditTrailPlugin;
import hudson.plugins.audit_trail.ConsoleAuditLogger;
import java.util.logging.Logger

def env = System.getenv()
String dateFormat = env["Audit_Trail_DATE_FORMAT"];
Jenkins instance = Jenkins.getInstance();

Thread.start {
    if(dateFormat){
        ConsoleAuditLogger consoleAuditLogger = new ConsoleAuditLogger(ConsoleAuditLogger.Output.STD_OUT, dateFormat);
        AuditTrailPlugin plugin = instance.getPlugin(AuditTrailPlugin.class);
        plugin.loggers.add(consoleAuditLogger);
        plugin.save();
        plugin.start();
	}
}