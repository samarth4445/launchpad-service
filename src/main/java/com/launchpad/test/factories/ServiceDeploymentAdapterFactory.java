package com.launchpad.test.factories;

import com.launchpad.test.adapters.ServiceDeploymentAdapter;
import com.launchpad.test.enums.DeploymentServiceEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ServiceDeploymentAdapterFactory {
    private ApplicationContext applicationContext;

    @Autowired
    public ServiceDeploymentAdapterFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ServiceDeploymentAdapter getServiceDeploymentAdapter(DeploymentServiceEnum serviceType) {
        ServiceDeploymentAdapter serviceDeploymentAdapter = applicationContext.getBean(ServiceDeploymentAdapter.class);
        serviceDeploymentAdapter.setServiceType(serviceType);

        return serviceDeploymentAdapter;
    }
}
