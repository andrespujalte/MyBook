package com.example.MyBook.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.MyBook.Clases.Book
import com.example.MyBook.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_main_book_detail_container.*
import com.google.android.material.tabs.TabLayoutMediator

lateinit var tablayout : TabLayout
lateinit var viewpager : ViewPager2
lateinit var v : View
lateinit var selectedBook : Book


class MainBookDetailContainer : Fragment() {
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_main_book_detail_container, container, false)
        tablayout = v.findViewById(R.id.tabLayout)
        viewpager = v.findViewById(R.id.viewPager)
        return v
    }

    override fun onStart() {
        super.onStart()
        viewPager.setAdapter(ViewPagerAdapter(requireActivity()))
        selectedBook = MainBookDetailContainerArgs.fromBundle(requireArguments()).SelectedBook
        TabLayoutMediator(tabLayout, viewPager, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            when (position) {
                0 -> tab.text = "Sinopsis"
                1 -> tab.text = "Detalles"
                else -> tab.text = "Sinopsis"
            }
        }).attach()
    }

    class ViewPagerAdapter(fragmentActivity:FragmentActivity):FragmentStateAdapter(fragmentActivity){
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> MainBookDetailSynopsis.newInstance(selectedBook)
                1 -> MainBookDetailOther.newInstance(selectedBook)
                else -> MainBookDetailSynopsis.newInstance(selectedBook)
            }
        }
        override fun getItemCount(): Int {
            return TAB_COUNT
        }

        companion object {
            private const val TAB_COUNT = 2
        }
    }


}