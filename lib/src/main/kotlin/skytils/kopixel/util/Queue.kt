package skytils.kopixel.util

class Queue<T> {

    /* Mutable list of generic T to contain the queue */
    private val queue = mutableListOf<T>()

    /* Returns and removes the next job off of the queue. Always returns at index 0 */
    fun next(): T {
        if (!hasJob()) error("There are no jobs to be returned.")
        return queue.removeFirst()
    }

    /* Returns if there are any jobs in the queue */
    fun hasJob(): Boolean = !queue.isEmpty()

    /* Returns the amount of jobs in the queue */
    fun length() = queue.size

    /* Add a job to the queue */
    fun add(job: T) {
        if (queue.contains(job)) return
        queue.add(job)
    }

    operator fun plusAssign(job: T) {
        add(job)
    }

    /* Clears all jobs from the queue */
    fun clear() = queue.clear()

    operator fun contains(v: T) = queue.contains(v)

    fun addAll(all: List<T>) {
        queue.addAll(all)
    }
}