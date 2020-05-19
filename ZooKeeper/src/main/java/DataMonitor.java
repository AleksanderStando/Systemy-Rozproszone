import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.KeeperException.Code;
import java.util.List;

public class DataMonitor implements Watcher, AsyncCallback.StatCallback {
    private ZooKeeper zooKeeper;
    private String znode;
    private DataMonitorListener listener;

    public DataMonitor(ZooKeeper zooKeeper, String znode,
                       DataMonitorListener listener) {
        this.zooKeeper = zooKeeper;
        this.znode = znode;
        this.listener = listener;

        zooKeeper.exists(znode, true, this, null);
    }

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        switch (rc) {
            case Code.Ok:
                this.listener.ensureRunning();
                break;
            case Code.NoNode:
                this.listener.ensureStopped();
                break;
            case Code.SessionExpired:
            case Code.NoAuth:
                listener.ensureStopped();
                return;
            default:
                // Retry errors
                zooKeeper.exists(znode, true, this, null);
                return;
        }
    }

    @Override
    public void process(WatchedEvent event) {
        String path = event.getPath();
        if (event.getType() == Event.EventType.None) {
            switch (event.getState()) {
                case SyncConnected:
                    break;
                case Expired:
                    listener.ensureStopped();
                    return;
            }
        }
        else if (event.getType() == Event.EventType.NodeChildrenChanged){
            try {
                System.out.println("Node '" + znode + "' children number: " + childrenAmount(znode));
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            if (path != null && path.equals(znode)) {
                zooKeeper.exists(znode, true, this, null);
            }
        }
    }

    private long childrenAmount(String path) throws KeeperException, InterruptedException {
        long amount = 0;
        List childList = zooKeeper.getChildren(path, true);
        for (Object s: childList){
            String childrenPath = path + "/" + s;
            amount += childrenAmount(childrenPath);
        }
        amount += childList.size();
        return amount;
    }
}