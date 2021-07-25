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

package skytils.hylin.request

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin

@Suppress("EXPERIMENTAL_API_USAGE", "UNCHECKED_CAST", "DeferredResultUnused")
open class AsyncRequest<T>(private val scope: CoroutineScope, private val block: suspend CoroutineScope.() -> T) {
    private lateinit var deferred: Deferred<T>
    private var catch: (Exception) -> Unit = { throw it }

    /**
     * Launch the async coroutine
     */
    fun launch(): AsyncRequest<T> {
        deferred = scope.async {
            try {
                block()
            } catch (e: Exception) {
                catch(e)
                deferred.cancelAndJoin()
            }
        } as Deferred<T>
        return this
    }

    /**
     * Wait until the coroutine is finished
     * @return The result
     */
    suspend fun await(): T {
        return deferred.await()
    }

    /**
     * Run `whenComplete` when the task completes
     * @param whenComplete The task to run when the task completes
     */
    fun whenComplete(whenComplete: suspend (T) -> Unit): AsyncRequest<T> {
        deferred.invokeOnCompletion {
            if (!deferred.isCancelled) {
                val result = deferred.getCompleted()
                scope.async {
                    whenComplete(result)
                }
            }
        }
        return this
    }

    /**
     * Define what should happen when the request should catch an exception
     * @param onError A lambda to be called when an exception is caught
     */
    fun catch(onError: (Exception) -> Unit): AsyncRequest<T> {
        catch = onError
        return this
    }
}
