package com.launchpad.test.services.up;


import com.launchpad.test.adapters.ServiceDeploymentAdapter;
import com.launchpad.test.entities.Service;
import com.launchpad.test.enums.DeploymentServiceEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UpService {
    private ServiceDeploymentAdapter serviceDeploymentAdapter;

    @Autowired
    public UpService(ServiceDeploymentAdapter serviceDeploymentAdapter) {
        this.serviceDeploymentAdapter = serviceDeploymentAdapter;
    }

    public void setServiceType(DeploymentServiceEnum serviceType){
        this.serviceDeploymentAdapter.setServiceType(serviceType);
    }

    private static void DFS(Service node, TreeMap<Service, List<Service>> serviceDependencyGraph,
                            HashMap<Service, Boolean> visited, Stack<Service> stack) {
        visited.put(node, true);
        List<Service> neighbors = serviceDependencyGraph.get(node);
        if (neighbors != null) {
            for (Service it : neighbors) {
                if (!visited.get(it)) DFS(it, serviceDependencyGraph, visited, stack);
            }
        }
        stack.push(node);
    }

    private static List<Service> topologicalSort(TreeMap<Service, List<Service>> serviceDependencyGraph) {
        List<Service> servicesInTopologicalOrder = new ArrayList<>();
        Stack<Service> stack = new Stack<>();
        HashMap<Service, Boolean> visited = new HashMap<>();
        for (Service node : serviceDependencyGraph.keySet()) {
            visited.put(node, false);
        }

        for (Service node : serviceDependencyGraph.keySet()) {
            if (!visited.get(node)) {
                DFS(node, serviceDependencyGraph, visited, stack);
            }
        }

        while (!stack.isEmpty()) {
            servicesInTopologicalOrder.add(stack.pop());
        }

        return servicesInTopologicalOrder;
    }

    private static boolean cycleDetection(TreeMap<Service, List<Service>> serviceDependencyGraph,
                                          HashMap<Service, Integer> visited,
                                          Service node) {
        visited.put(node, 2);
        List<Service> neighbors = serviceDependencyGraph.get(node);
        if (neighbors != null) {
            for (Service neighbor : neighbors) {
                if (visited.get(neighbor) != null && visited.get(neighbor) == 2) {
                    return true;
                }

                if (visited.get(neighbor) == null || visited.get(neighbor) == 1) {
                    if (cycleDetection(serviceDependencyGraph, visited, neighbor)) {
                        return true;
                    }
                }
            }
        }
        visited.put(node, 3);
        return false;
    }

    private static boolean isThereCircularDependency(TreeMap<Service, List<Service>> serviceDependencyGraph) {
        HashMap<Service, Integer> visited = new HashMap<>();
        for (Service node : serviceDependencyGraph.keySet()) {
            visited.put(node, 1);
        }

        for (Service node : serviceDependencyGraph.keySet()) {
            if (visited.get(node) == 1) {
                if (cycleDetection(serviceDependencyGraph, visited, node)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void runServices(TreeMap<Service, List<Service>> serviceDependencyGraph){
        if(isThereCircularDependency(serviceDependencyGraph)){
            throw new IllegalArgumentException("Dependency graph has cycles.");
        }

        List<Service> servicesInTopologicalOrder = topologicalSort(serviceDependencyGraph);
        for(Service service: servicesInTopologicalOrder){
            this.serviceDeploymentAdapter.runService(service.getId());
        }
    }
}
