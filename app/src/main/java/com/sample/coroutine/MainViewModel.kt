package com.sample.coroutine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class MainViewModel :ViewModel() {

    // Job and Dispatcher are combined into a CoroutineContext which
    // will be discussed shortly
    private val job =  SupervisorJob()
    private val ioScope by lazy { CoroutineScope(job + Dispatchers.IO) }

    fun exampleMethodUsingLaunch() {
        // Starts a new coroutine within the scope
        ioScope.launch {
            // New coroutine that can call suspend functions
            fetchData()
            //To Switch the context of Dispatchers
            withContext(Dispatchers.Main){
            }
        }
        viewModelScope
    }

    suspend fun fetchData(): String {
        delay(3000L) // simulate long running task
        return "Did something that was 3 seconds long"
    }

    fun exampleMethodUsingAsync(){
        println("First statement of Async")

        viewModelScope.launch {
                val one = async { sampleOne()}
                val two = async { sampleTwo()}
            if(one.await() && two.await()){
                println("Both returned true")
            }else {
                println("Someone returned false")
            }
        }
        println("Last statement of Async")
    }

    private suspend fun sampleOne(): Boolean {
        delay(4000L)
        return true
    }
    private suspend fun sampleTwo(): Boolean {
        delay(3000L)
        return false
    }

    fun sampleRunBlocking(){
        println("First statement of runBlocking")
        runBlocking {
            delay(3000L)
            println("Middle  statement of runBlocking")
        }
        println("Last statement of runBlocking")

    }

    override fun onCleared() {
        super.onCleared()
        ioScope.cancel()
    }

}
