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
