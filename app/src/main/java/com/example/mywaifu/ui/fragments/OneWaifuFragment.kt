package com.example.mywaifu.ui.fragments

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions

import com.example.mywaifu.databinding.FragmentOneWaifuBinding
import com.example.mywaifu.ui.WaifuAdapter
import com.example.mywaifu.GlobalState
import com.example.mywaifu.R
import com.example.mywaifu.WaifuProxy

import com.example.mywaifu.ui.fragments.view_models.OneWaifuViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

class OneWaifuFragment: Fragment() {

    private var viewModel: OneWaifuViewModel? = null
    private var _binding: FragmentOneWaifuBinding? = null
    private val binding get() = _binding!!

    private val adapter: WaifuAdapter = WaifuAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOneWaifuBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val imageViews = listOf<View>(
            binding.imageViewSaved1,
            binding.imageViewSaved2,
            binding.imageViewSaved3
        )


        viewModel = ViewModelProvider(this, OneWaifuViewModel.getFactory())
            .get(OneWaifuViewModel::class.java)




        viewModel?.observeAddToFavorite()?.observe(viewLifecycleOwner) { urlList ->
//            binding.savedText.text = urlList[0]

            for (i in urlList.indices) {
                Glide.with(this)
                    .load(urlList[i])
                    .into(imageViews[i] as ImageView)
            }
        }

        viewModel?.observeToast()?.observe(viewLifecycleOwner) { message ->

            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        viewModel?.observeState()?.observe(viewLifecycleOwner) {
            render(it)
        }

        binding.getWaifu1.setOnClickListener {
            viewModel?.getMyWaifu("sfw", "dance", true)
        }

        binding.getWaifu2.setOnClickListener {
            viewModel?.getMyWaifu("sfw", "awoo", true)
        }

        // Кнопка перехода на третий экран внутри 1-го таба
        binding.openThirdButton.setOnClickListener {
            (parentFragment as? FirstTabHostFragment)?.openThirdInsideFirstTab()
        }





//        binding.addToFavoriteButton.setOnClickListener {
//            viewModel?.addToFavorite()
//        }


//        binding.goToFavoriteButton.setOnClickListener {
////            (parentFragment as? TabFragment)?.openFavorites()
//        }



    }

    private fun render(state: GlobalState) {
        when (state) {
            is GlobalState.Content -> showContent(state.url)
            is GlobalState.Error -> showError(state.message)
            is GlobalState.ManyContent -> showManuContent(state.manyUrl)
            GlobalState.Loading -> showLoading()

        }
    }




    private fun showContent(url: String) {
        binding.progressBar.visibility = View.INVISIBLE
        binding.imageView.visibility = View.VISIBLE

        Log.d("ImageLoad", "Начинаем загрузку: $url")

        // Показываем прогресс
        binding.progressBar.visibility = View.VISIBLE

        // Стратегия 1: Сначала прямой URL через Glide
        loadDirectWithGlide(url)

        // Стратегия 2: Если через 2 секунды не загрузилось - прокси
        view?.postDelayed({
            if (binding.imageView.drawable == null) {
                loadWithProxy(url)
            }
        }, 2000)
    }

    private fun loadDirectWithGlide(url: String) {
        Glide.with(this)
            .load(url)
            .apply(
                RequestOptions()
                    .timeout(10000)
                    .error(R.drawable.cat)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
            )

            .into(binding.imageView)
    }

    private fun loadWithProxy(url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Получаем проксированный URL
                val proxiedUrl = WaifuProxy.getProxiedUrl(url)
                Log.d("ProxyLoad", "Используем прокси: $proxiedUrl")

                // Загружаем через OkHttp
                val client = OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .build()

                val request = Request.Builder()
                    .url(proxiedUrl)
                    .header("User-Agent", "Mozilla/5.0")
                    .header("Accept", "image/*")
                    .header("Referer", "https://waifu.pics/")
                    .build()

                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    response.body()?.use { body ->
                        val inputStream = body.byteStream()
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        inputStream.close()

                        if (bitmap != null) {
                            withContext(Dispatchers.Main) {
                                binding.progressBar.visibility = View.INVISIBLE
                                binding.imageView.setImageBitmap(bitmap)
                                Toast.makeText(requireContext(),
                                    "Загружено через прокси!", Toast.LENGTH_SHORT).show()
                            }
                            return@launch
                        }
                    }
                }

                response.close()


            } catch (e: Exception) {
                Log.e("ProxyLoad", "Ошибка прокси", e)

            }
        }
    }





    private fun showError(message: String) {
        binding.progressBar.visibility = View.INVISIBLE
        binding.imageView.visibility = View.INVISIBLE

        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()

    }


    private fun showLoading() {


        binding.apply {

            progressBar.visibility = View.VISIBLE
            progressBar.repeatMode = LottieDrawable.RESTART
            progressBar.repeatCount = LottieDrawable.INFINITE
            progressBar.playAnimation()


            imageView.visibility = View.GONE


        }


    }


    private fun showManuContent(manyUrl: List<String>) {
        binding.progressBar.visibility = View.GONE
        binding.imageView.visibility = View.INVISIBLE

        adapter.waifu = manyUrl
        adapter.notifyDataSetChanged()

        Toast.makeText(requireContext(), "Много контента пришело", Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}