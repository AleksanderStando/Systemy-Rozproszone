import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class Executor implements DataMonitorListener, Watcher {
    private DataMonitor dataMonitor;
    private ZooKeeper zooKeeper;
    private String exec[];
    private static String znode = "/z";
    private Process child;

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("USAGE: hostPort program [args ...]");
            System.exit(2);
        }
        String hostPort = args[0];
        String exec[] = new String[args.length - 1];
        System.arraycopy(args, 1, exec, 0, exec.length);
        for(String execPart : exec){
            System.out.println(execPart);
        }

        try {
            new Executor(hostPort, znode, exec).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Executor(String hostPort, String znode,
                     String exec[]) throws IOException {
        this.exec = exec;
        zooKeeper = new ZooKeeper(hostPort, 3000, this);
        dataMonitor = new DataMonitor(zooKeeper, znode, this);
    }

    public void run() {
        printUsage();
        Scanner scanner = new Scanner(System.in);
        String line;
        try {
            activateWatchers(znode);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        while (!(line = scanner.nextLine()).toLowerCase().equals("quit")) {
            if (line.toLowerCase().equals("tree")) {
                try {
                    displayTree(znode);
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                printUsage();
            }
        }
        child.destroy();
    }

    private void printUsage() {
        System.out.println("USAGE:" );
        System.out.println("tree -> shows nodes tree");
        System.out.println("quit -> terminate program");
    }


    private void displayTree(String znode) throws KeeperException, InterruptedException {
        List childList = zooKeeper.getChildren(znode, true);
        System.out.println(znode);
        for (Object child: childList){
            displayTree(znode + "/" + child);
        }

    }

    private void activateWatchers(String znode) throws KeeperException, InterruptedException {
        List childList = zooKeeper.getChildren(znode, true);
        for (Object child: childList){
            activateWatchers(znode + "/" + child);
        }

    }

    @Override
    public void ensureRunning(){
        try {
            if (child != null) {
                return;
            }
            child = Runtime.getRuntime().exec(exec);
            zooKeeper.getChildren(znode, true);
        }  catch (IOException | InterruptedException | KeeperException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void ensureStopped(){
        if (child == null) {
            return;
        }
        child.destroy();
        child = null;
    }

    @Override
    public void process(WatchedEvent event) {
        dataMonitor.process(event);
    }
}