package atmatm6.javwebreborn;

import javafx.concurrent.Worker;

public class StateHandler {
    public static boolean isFailed(Worker<Void> worker){
        return worker.getState().equals(Worker.State.FAILED);
    }

    public static boolean isRunning(Worker<Void> worker){
        return worker.getState().equals(Worker.State.RUNNING);
    }

    public static boolean isReady(Worker<Void> worker){
        return worker.getState().equals(Worker.State.READY);
    }
    public static boolean isSucessful(Worker<Void> worker){
        return worker.getState().equals(Worker.State.SUCCEEDED);
    }
    public static boolean isScheduled(Worker<Void> worker){
        return worker.getState().equals(Worker.State.SCHEDULED);
    }

    public static boolean isCancelled(Worker<Void> worker){
        return worker.getState().equals(Worker.State.CANCELLED);
    }
}
