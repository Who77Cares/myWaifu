package com.example.mywaifu.domain.waifu_api

import com.example.mywaifu.Resource
import java.util.concurrent.Executors

class WaifuInteractorImpl(
    private val repository: WaifuRepository
) : WaifuInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun getWaifu(
        type: String,
        category: String,
        consumer: WaifuInteractor.WaifuConsumer
    ) {
        executor.execute {

            val resource = repository.getWaifu(
                type = type,
                category = category
            )

            when (resource) {
                is Resource.Success -> {
                    consumer.consume(resource.data, null)
                }

                is Resource.Error -> {
                    consumer.consume(null, resource.message)
                }
            }

        }

    }
}