package com.example.myblog

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myblog.Interface.PostInterface
import com.example.myblog.adapter.PostsListAdapter
import com.example.myblog.model.Post
import com.example.myblog.retrofit.RetrofitManager
import com.example.myblog.utils.Constants.CREATE_POST_ACTIVITY
import com.example.myblog.utils.Constants.EDIT_POST_ACTIVITY
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.log


class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener, PostInterface {

    val TAG: String = "로그"

    // 데이터를 담을 그릇
    var postList = ArrayList<Post>()

    // lateinit 을 통해 나중에 메모리에 올라가도 된다.
    private lateinit var postListAdapter: PostsListAdapter

    private var page : Int = 0

    private var isFetchedAll: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        swipe_refresh.setOnRefreshListener(this)

        floating_button.setOnClickListener {

            Log.d(TAG, "MainActivity - 플로팅 버튼 클릭!")
            posts_recyclerview.smoothScrollToPosition(0)
//            posts_recyclerview.scrollToPosition(0)
        }

        page = 1


        RetrofitManager.instance.getPostsPaginate(page = page, completion = {

            Log.d(TAG, "MainActivity - onCreate() / 컴플레션 블럭이 호출되었다. 넘어온 배열 크기 : ${it.size} / page: $page")

            this.postList.addAll(it)
            this.postListAdapter.submitList(it)
            this.postListAdapter.notifyDataSetChanged()


        })


        // api 호출을 통해 데이터를 가져올 예정

        // 어답터를 메모리에 올린다.
        postListAdapter = PostsListAdapter()


        // 리사이클러뷰를 설정한다.
        posts_recyclerview.apply {

            layoutManager = LinearLayoutManager(this@MainActivity,
                                LinearLayoutManager.VERTICAL,
                                false
                            )

            adapter = postListAdapter

            this.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                // 스크롤 되었을때
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                    Log.d(TAG, "MainActivity - onScrolled() called / dx: $dx / dy: $dy")


//                    Log.d(TAG, "MainActivity - recyclerView.adapter?.itemCount : ${recyclerView.adapter?.itemCount}")

//                    recyclerView.adapter.


                    // 아래로 스크롤 하면
                    if(dy > 200){

//                        val fadeInAnimation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.fade_in)
//
//                        floating_button.startAnimation(fadeInAnimation)
                        if(floating_button.visibility == View.INVISIBLE){
                            floating_button.visibility = View.VISIBLE
                        }


                    }
                    else if (dy < -20) {
//                        floating_button.visibility = View.INVISIBLE
                    }

                }


                // 스크롤의 상태가 변경 되었을때
                override fun onScrollStateChanged(
                    recyclerView: RecyclerView,
                    newState: Int
                ) {

//                    Log.d(TAG, "MainActivity - onScrollStateChanged() called")



                    // 리사이클러뷰의 맨 처음일 때
                    if (!recyclerView.canScrollVertically(-1)) {
                        Log.d(TAG, "Top of list")

                        if(floating_button.visibility == View.VISIBLE){
                            val fadeOutAnimation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.fade_out)

                            floating_button.startAnimation(fadeOutAnimation)

                            floating_button.visibility = View.INVISIBLE
                        }



                    } else if (!recyclerView.canScrollVertically(1)) {

                        page = page + 1

//                        Log.i(TAG, "End of list / page : ${page}")

                        // 데이터를 다 가져오지 않았을때
                        if(isFetchedAll == false){
                            progress_bar.visibility = View.VISIBLE
                        }


                        GlobalScope.launch {
                            // launch new coroutine in background and continue delay(1000L)
                            // non-blocking delay for 1 second (default time unit is ms) println("World!")
                            // print after delay
                            delay(1000)

                            Log.d(TAG, "MainActivity - 1초 지났음")
//                            Toast.makeText(this@MainActivity, "1초 지났음", Toast.LENGTH_SHORT).show()

                            // 데이터를 가져온다.
                            RetrofitManager.instance.getPostsPaginate(page = page, completion = {
                                Log.d(TAG, "MainActivity - onCreate() / 컴플레션 블럭이 호출되었다. 넘어온 배열 크기 : ${it.size}")

                                progress_bar.visibility = View.INVISIBLE



                                // 데이터가 더이상 없을때
                                if (it.size < 1){

                                    // 데이터를 다 가져왔다고 설정한다.
                                    isFetchedAll = true

                                    // 빈유형의 포스트가 마지막 녀석이 아니면
                                    if(postList.last().isFetchedAll == false){



                                        postList.add(Post(isFetchedAll = true))
                                        postListAdapter.addEmptyPost(Post(isFetchedAll = true))
                                        postListAdapter.notifyDataSetChanged()
                                        recyclerView.setPadding(0,0,0,0)
                                    }
//

                                } else { // 데이터가 있을때
                                    postList.addAll(it)
                                    postListAdapter.addList(it)
                                    postListAdapter.notifyDataSetChanged()
                                }


                            })

                        }

                    } else {
                        Log.i(TAG, "idle")

                    }
                }
            })

        }


    }

    // 해당 부분이 들어왔을때
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {


        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        return true
    }


    // actions on click menu items
    //
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.menu_add_post -> {
            Log.d(TAG, "MainActivity - 메뉴 추가 클릭")

            val intent = Intent(this, CreatePostActivity::class.java).apply {

            }

            startActivityForResult(intent, CREATE_POST_ACTIVITY)

            true
        }
        R.id.menu_profile -> {
            Log.d(TAG, "MainActivity - 메뉴 프로필 클릭")
            true
        }
        R.id.menu_setting -> {
            Log.d(TAG, "MainActivity - 메뉴 설정 클릭")
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    // 리프레시 땅겼음
    override fun onRefresh() {
        Log.d(TAG, "MainActivity - onRefresh() called/ 땅겼음")


        reloadData()

    }

    // 데이터를 갱신한다.
    private fun reloadData(){

        Log.d(TAG, "MainActivity - reloadData() called")

        // 비우고
        this.postList.clear()
        this.postListAdapter.clearList()

        // api 호출해서 새로운 데이터 가져오기

        page = 1

        RetrofitManager.instance.getPostsPaginate(page = page, completion = {
            Log.d(TAG, "MainActivity - onCreate() / 컴플레션 블럭이 호출되었다. 넘어온 배열 크기 : ${it.size}")

            this.postList.addAll(it)
            this.postListAdapter.submitList(it)
            this.postListAdapter.notifyDataSetChanged()
            swipe_refresh.isRefreshing = false

            isFetchedAll = false



            // 패딩 변경
            posts_recyclerview.setPadding(0,0,0, 300)

        })
    }


    // 포스트 인터페이스 메소드
    // 블로그 포스팅이 만들어 졌다.
    override fun blogPostCreated() {
        Log.d(TAG, "MainActivity - blogPostCreated() called")
    }

    override fun onResume() {
        super.onResume()
        reloadData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){

            Log.d(TAG, "MainActivity - onActivityResult() called / resultCode : $resultCode")

            when(requestCode) {

                CREATE_POST_ACTIVITY -> {

                    reloadData()

                }

            }

        }

    }


}
