package mbeans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;


@Named
@ApplicationScoped
public class MBeanSessionLocalAgent {

    @Inject
    private Count countMBean;

    @Inject
    private Area areaMBean;

    private static final MBeanServer server;

    static {
        server = ManagementFactory.getPlatformMBeanServer();
    }

    @PostConstruct
    public void initAgentCount() {
        ObjectName countmBean;
        ObjectName areamBean;
        try {
            countmBean = new ObjectName("mbeans:name=MBeanCount");
            areamBean = new ObjectName("mbeans:name=MBeanArea");
            if (!server.isRegistered(countmBean)) {
                server.registerMBean(countMBean, countmBean);
            }
            if (!server.isRegistered(areamBean)) {
                server.registerMBean(areaMBean, areamBean);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void logSimpleAgentStarted() {
        System.out.println("CountMBean.logSimpleAgentStarted");
        System.out.println("AreaMBean.logSimpleAgentStarted");
    }

    public void startupCount(@Observes @Initialized(ApplicationScoped.class) Object context) {
        MBeanSessionLocalAgent a = new MBeanSessionLocalAgent();
        a.logSimpleAgentStarted();
    }


}