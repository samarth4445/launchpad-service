package com.launchpad.test.services.up;


import com.launchpad.test.adapters.ServiceDeploymentAdapter;
import com.launchpad.test.enums.DeploymentServiceEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UpService {
    private ServiceDeploymentAdapter serviceDeploymentAdapter;
    private ApplicationContext applicationContext;

    @Autowired
    public UpService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void setServiceDeploymentAdapter(DeploymentServiceEnum serviceType){
        this.serviceDeploymentAdapter = new ServiceDeploymentAdapter(serviceType, this.applicationContext);
    }

    private static void DFS(String node, TreeMap<String, List<String>> serviceDependencyGraph,
                            HashMap<String, Boolean> visited, Stack<String> stack) {
        visited.put(node, true);
        List<String> neighbors = serviceDependencyGraph.get(node);
        if (neighbors != null) {
            for (String it : neighbors) {
                if (!visited.get(it)) DFS(it, serviceDependencyGraph, visited, stack);
            }
        }
        stack.push(node);
    }

    private static List<String> topologicalSort(TreeMap<String, List<String>> serviceDependencyGraph) {
        List<String> servicesInTopologicalOrder = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        HashMap<String, Boolean> visited = new HashMap<>();
        for (String node : serviceDependencyGraph.keySet()) {
            visited.put(node, false);
        }

        for (String node : serviceDependencyGraph.keySet()) {
            if (!visited.get(node)) {
                DFS(node, serviceDependencyGraph, visited, stack);
            }
        }

        while (!stack.isEmpty()) {
            servicesInTopologicalOrder.add(stack.pop());
        }

        return servicesInTopologicalOrder;
    }

    private static boolean cycleDetection(TreeMap<String, List<String>> serviceDependencyGraph,
                                          HashMap<String, Integer> visited,
                                          String node) {
        visited.put(node, 2);
        List<String> neighbors = serviceDependencyGraph.get(node);
        if (neighbors != null) {
            for (String neighbor : neighbors) {
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

    private static boolean isThereCircularDependency(TreeMap<String, List<String>> serviceDependencyGraph) {
        HashMap<String, Integer> visited = new HashMap<>();
        for (String node : serviceDependencyGraph.keySet()) {
            visited.put(node, 1);
        }

        for (String node : serviceDependencyGraph.keySet()) {
            if (visited.get(node) == 1) {
                if (cycleDetection(serviceDependencyGraph, visited, node)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void runServices(TreeMap<String, List<String>> serviceDependencyGraph){
        if(isThereCircularDependency(serviceDependencyGraph)){
            throw new IllegalArgumentException("Dependency graph has cycles.");
        }

        List<String> servicesInTopologicalOrder = topologicalSort(serviceDependencyGraph);
        for(String serviceId: servicesInTopologicalOrder){
            this.serviceDeploymentAdapter.runService(serviceId);
        }
    }
}
