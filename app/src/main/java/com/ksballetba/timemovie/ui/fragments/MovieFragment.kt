package com.ksballetba.timemovie.ui.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ksballetba.timemovie.R
import com.ksballetba.timemovie.utils.setTabWidth
import kotlinx.android.synthetic.main.fragment_movie.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MovieFragment : Fragment() {

    val fragmentList = ArrayList<Fragment>()
    lateinit var showingMovieFragment: ShowingMovieFragment
    lateinit var comingMovieFragment: ComingMovieFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        showingMovieFragment = ShowingMovieFragment()
        comingMovieFragment = ComingMovieFragment()
        fragmentList.add(showingMovieFragment)
        fragmentList.add(comingMovieFragment)
        movie_viewpager.adapter = KotlinPagerAdapter(fragmentList,childFragmentManager)
        movie_tablayout.setupWithViewPager(movie_viewpager)
        movie_tablayout.getTabAt(0)?.text = "正在热映"
        movie_tablayout.getTabAt(1)?.text = "即将上映"
        setTabWidth(movie_tablayout,75f,75f)
    }
}

class KotlinPagerAdapter(var mList : List<Fragment>, fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): android.support.v4.app.Fragment {
        return mList[position]
    }

    override fun getCount(): Int {
        return mList.size
    }

}
