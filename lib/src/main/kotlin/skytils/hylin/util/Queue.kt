/*
 * Hylin - Hypixel API Wrapper in Kotlin
 * Copyright (C) 2021  Skytils
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package skytils.hylin.util

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