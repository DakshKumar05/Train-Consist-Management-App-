package main;

import java.util.LinkedList;
import java.util.Queue;

/**
 * UC9: Train Consist Management - Bogie Queue Management
 * Demonstrates using a Queue (LinkedList) to manage bogies in FIFO order
 * for scheduling maintenance or boarding.
 */
public class UseCase9TrainConsistMgmt {

    private Queue<String> bogieQueue;

    public UseCase9TrainConsistMgmt() {
        this.bogieQueue = new LinkedList<>();
    }

    /**
     * Enqueues a bogie into the maintenance/boarding queue.
     * @param bogieType type of bogie to enqueue
     */
    public void enqueueBogie(String bogieType) {
        if (bogieType == null || bogieType.trim().isEmpty()) {
            throw new IllegalArgumentException("Bogie type cannot be null or empty.");
        }
        bogieQueue.offer(bogieType);
        System.out.println("Enqueued bogie: " + bogieType);
    }

    /**
     * Dequeues (processes) the next bogie in the queue.
     * @return the dequeued bogie type, or null if queue is empty
     */
    public String dequeueBogie() {
        String bogie = bogieQueue.poll();
        if (bogie != null) {
            System.out.println("Dequeued bogie: " + bogie);
        } else {
            System.out.println("Queue is empty. No bogie to dequeue.");
        }
        return bogie;
    }

    /**
     * Peeks at the next bogie without removing it.
     * @return the front bogie type, or null if empty
     */
    public String peekBogie() {
        return bogieQueue.peek();
    }

    /**
     * Returns the size of the queue.
     */
    public int getQueueSize() {
        return bogieQueue.size();
    }

    /**
     * Checks if the queue is empty.
     */
    public boolean isQueueEmpty() {
        return bogieQueue.isEmpty();
    }

    /**
     * Displays all bogies currently in the queue.
     */
    public void displayQueue() {
        System.out.println("Bogie Queue (" + bogieQueue.size() + " entries): " + bogieQueue);
    }

    public static void main(String[] args) {
        UseCase9TrainConsistMgmt mgmt = new UseCase9TrainConsistMgmt();

        // Enqueue bogies
        mgmt.enqueueBogie("Sleeper");
        mgmt.enqueueBogie("AC Chair Car");
        mgmt.enqueueBogie("First Class AC");
        mgmt.enqueueBogie("General");

        mgmt.displayQueue();

        // Peek
        System.out.println("Next bogie to process: " + mgmt.peekBogie());

        // Dequeue bogies
        mgmt.dequeueBogie();
        mgmt.dequeueBogie();

        mgmt.displayQueue();
        System.out.println("Queue size: " + mgmt.getQueueSize());
    }
}
