package com.example.task_4.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.task_4.R
import com.example.task_4.adapter.SegmentAdapter
import com.example.task_4.model.Segment
import com.google.android.material.slider.RangeSlider

class MainActivity : AppCompatActivity() {

    private val segmentAdapter = SegmentAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.seekbar_rv)
        recyclerView.adapter = segmentAdapter

        val segmentList = mutableListOf<Segment>()
        segmentList.add(Segment(0F, 0F, false, Color.parseColor("#EF5350")))

        segmentAdapter.initSegment(segmentList)

        segmentAdapter.setOnItemClickListener(object : SegmentAdapter.OnItemClickListener {
            override fun onItemClick(slider: RangeSlider, holder: SegmentAdapter.ViewHolder) {
                val value = slider.values[1]

                segmentAdapter.addSegment(
                    Segment(value + 1, 100F, false, Color.RED),
                    holder.adapterPosition + 1
                )


            }
        })

    }
}