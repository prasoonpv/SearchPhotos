package com.prasoon.task.ui.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prasoon.task.R
import com.prasoon.task.ui.BaseActivity
import com.prasoon.task.databinding.ActivitySearchPhotoBinding
import com.prasoon.task.ui.main.adapter.PhotoAdapter
import com.prasoon.task.ui.main.model.PhotoResp
import com.prasoon.task.ui.main.viewmodel.SearchPhotoViewModel
import com.prasoon.task.util.Utils
import dagger.android.AndroidInjection.inject
import java.util.ArrayList
import javax.inject.Inject

 class SearchPhotosActivity : BaseActivity<SearchPhotoViewModel>(), PhotoAdapter.Interaction , View.OnClickListener, View.OnTouchListener {

     @Inject
    lateinit var factory: ViewModelProvider.Factory

     @Inject
     lateinit var utils :Utils

    private lateinit var viewModel: SearchPhotoViewModel
     private lateinit var mLayoutManager: LinearLayoutManager
     private lateinit var adapter : PhotoAdapter
     var searchList : ArrayList<PhotoResp>? = null
     var pageNumber : Int = 0
     private var visibleItemCount: Int = 0
     private var totalItemCount: Int = 0
     private var pastVisiblesItems: Int = 0
     private var loading: Boolean = false
     private lateinit var text: String
     private val keyword: CharSequence? = null
     private lateinit var selectedType : String

    override fun getViewModel(): SearchPhotoViewModel {
        viewModel =
            ViewModelProviders.of(this, factory).get(SearchPhotoViewModel::class.java)
        return viewModel
    }

    private lateinit var binding : ActivitySearchPhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(this)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_search_photo
        )

        pageNumber = 1

        setToolBar(binding.includeAppbar.toolbar, "Photos")
        binding.llSearch.visibility = View.VISIBLE
        binding.ivLoading.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.loadingPanel.visibility = View.GONE

        // set adapter
        setAdapter()

        binding.rvSearchResult.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0)
                //check for scroll down
                {
                    visibleItemCount = mLayoutManager.childCount
                    totalItemCount = mLayoutManager.itemCount
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition()

                    if (loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            loading = false
                            Log.e("...", "Last Item Wow !")
                            //Do pagination.. i.e. fetch new data
                            binding.progressBar.visibility = View.VISIBLE
                            searchPhotos()
                        }
                    }
                }
            }
        })

        //touch event
        binding.etSearch.setOnTouchListener(this)

        //edit text
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (TextUtils.isEmpty(s)) {
                    binding.ivCancel.visibility = View.GONE
                    binding.rvSearchResult.setVisibility(View.GONE)
                    text = ""
                    pageNumber = 1
                } else {
                    binding.ivCancel.visibility = View.VISIBLE
                    binding.rvSearchResult.visibility = View.VISIBLE
                    text = s.toString()

                    // do search
                    if(!TextUtils.isEmpty(text) && text.length >2){
                        binding.loadingPanel.visibility = View.VISIBLE
                        searchPhotos()
                    }
                }
            }
        })

        //click events
        binding.ivCancel.setOnClickListener(this)

    }

     private fun searchPhotos() {
         if (utils.isConnectingToInternet) {

             viewModel.doSearch(text, "062a6c0c49e4de1d78497d13a7dbb360", pageNumber, 8).observe(this,
                 Observer { searchListResp ->

                 if(searchListResp != null && searchListResp.response.equals("ok")){
                     if (searchListResp.photos != null) {
                         binding.ivLoading.visibility = View.GONE
                         binding.progressBar.visibility = View.GONE
                         binding.loadingPanel.visibility = View.GONE
                         binding.RlNodata.visibility = View.GONE

                         adapter.addAll(searchListResp.photos?.photo)
                         loading = true

                         // increase page number by 10
                         pageNumber += 1
                         //  adapter.notifyDataSetChanged();

                     }
                 } else {
                     binding.ivLoading.visibility = View.GONE
                     binding.progressBar.visibility = View.GONE
                     binding.loadingPanel.visibility = View.GONE
                     if(pageNumber <= 1){
                         binding.loadingPanel.visibility = View.VISIBLE
                         binding.RlNodata.visibility = View.VISIBLE

                     }
                 }
             })
         } else{
             binding.ivLoading.visibility = View.GONE
             binding.loadingPanel.visibility = View.GONE
             utils.showSnackbar(
                 this,
                 getResources().getString(R.string.msg_alert_no_internet))
         }
     }

     private fun setAdapter() {
         binding.ivLoading.visibility = View.VISIBLE
         searchList = ArrayList<PhotoResp>()
         adapter = PhotoAdapter(searchList!!, this)
         mLayoutManager = GridLayoutManager(this, 2)
         binding.rvSearchResult.setLayoutManager(mLayoutManager)
         binding.rvSearchResult.setAdapter(adapter)
     }

     @SuppressLint("ClickableViewAccessibility")
     override fun onTouch(v: View, event: MotionEvent): Boolean {
         val id = v.id
         if (id == R.id.et_search) {
             binding.etSearch.isFocusableInTouchMode = true
             binding.etSearch.isCursorVisible = true
         }
         return false
     }

     override fun onClick(v: View) {
         if (v.id == R.id.iv_cancel) {
             binding.etSearch.setText("")
             adapter.clearAll()
         }
     }

     override fun onItemSelected(position: Int, item: PhotoResp) {
     }
}
