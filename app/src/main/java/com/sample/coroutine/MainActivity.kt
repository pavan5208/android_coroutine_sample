package com.sample.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelLazy
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by ViewModelLazy(
        MainViewModel::class,
        {viewModelStore },
        { defaultViewModelProviderFactory }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        sampleSuspendFunc()
        viewModel.exampleMethodUsingAsync()
//        viewModel.sampleRunBlocking()
    }

    private fun sampleSuspendFunc() {
        GlobalScope.launch {
            val time = measureTimeMillis {
                val one = sampleOne()
                val two = sampleTwo()
                println("The answer is ${one + two}")
            }
            println("Completed in $time ms")
        }
        println("EOF")
    }

    private suspend fun sampleOne(): Int {
        println( "sampleOne"+System.currentTimeMillis())
        delay(1000L) // pretend we are doing something useful here
        return 10
    }

    private suspend fun sampleTwo(): Int {
        println( "sampleTwo"+System.currentTimeMillis())
        delay(1000L) // pretend we are doing something useful here, too
        return 10
    }
}