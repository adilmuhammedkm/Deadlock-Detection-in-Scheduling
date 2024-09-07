import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

class Process {
    String name;
    int processId;
    List<Process> dependencies;

    public Process(String name, int processId) {
        this.name = name;
        this.processId = processId;
        this.dependencies = new ArrayList<>();
    }

    public void addDependency(Process process) {
        dependencies.add(process);
    }
}

public class DeadlockDetector {
    public static boolean detectDeadlock(List<Process> processes) {
        Map<Process, Boolean> visited = new HashMap<>();
        Map<Process, Boolean> stack = new HashMap<>();

        for (Process process : processes) {
            visited.put(process, false);
            stack.put(process, false);
        }

        for (Process process : processes) {
            if (!visited.get(process) && isCyclic(process, visited, stack)) {
                return true; 
            }
        }

        return false; 
    }

    private static boolean isCyclic(Process process, Map<Process, Boolean> visited, Map<Process, Boolean> stack) {
        visited.put(process, true);
        stack.put(process, true);

        for (Process dependency : process.dependencies) {
            if (!visited.get(dependency)) {
                if (isCyclic(dependency, visited, stack)) {
                    return true; 
                }
            } else if (stack.get(dependency)) {
                return true;
            }
        }

        stack.put(process, false);
        return false;
    }

    public static void main(String[] args) {
        Process process1 = new Process("Process 1", 1);
        Process process2 = new Process("Process 2", 2);
        Process process3 = new Process("Process 3", 3);

        process1.addDependency(process2);
        process2.addDependency(process3);
        process3.addDependency(process1);

        List<Process> processes = new ArrayList<>();
        processes.add(process1);
        processes.add(process2);
        processes.add(process3);

        if (detectDeadlock(processes)) {
            System.out.println("Deadlock detected.");
        } else {
            System.out.println("No deadlock detected.");
        }
    }
}
