package com.example.task_4.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.task_4.R
import com.example.task_4.databinding.ItemSeekbarBinding
import com.example.task_4.model.Segment
import com.google.android.material.slider.RangeSlider

class SegmentAdapter : RecyclerView.Adapter<SegmentAdapter.ViewHolder>() {

    private var segmentList = mutableListOf<Segment>()
    private var mlistener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(seekBar: RangeSlider, holder: ViewHolder)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mlistener = listener
    }

    class ViewHolder(val binding: ItemSeekbarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(segment: Segment) {
            binding.segment = segment
            binding.rangeSlider.values = mutableListOf(segment.startValue, segment.endValue)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemSeekbarBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_seekbar, parent, false
        )
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(segmentList[position])

        holder.binding.rangeSlider.addOnSliderTouchListener(object :
            RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {

                if (holder.adapterPosition == 0 && slider.focusedThumbIndex == 0) {

                }
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                val s = segmentList[holder.adapterPosition]
                if (slider.focusedThumbIndex == 1 && !s.isChanged) {
                    s.isChanged = true
                    mlistener?.onItemClick(slider, holder)
                }
                else if (slider.focusedThumbIndex == 0) {
                    if (holder.adapterPosition != 0) {
                        segmentList[holder.adapterPosition - 1].endValue = slider.values[0]-1
                        notifyItemChanged(holder.adapterPosition - 1)
                    }

                } else if (slider.focusedThumbIndex == 1) {

                    if (s.isChanged) {
                        segmentList[holder.adapterPosition + 1].startValue = slider.values[1] -1
                        notifyItemChanged(holder.adapterPosition + 1)
                    }
                }
            }
        })

        holder.binding.rangeSlider.addOnChangeListener(RangeSlider.OnChangeListener { slider: RangeSlider,
                                                                                      value: Float,
                                                                                      fromUser: Boolean ->

            val s = segmentList[holder.adapterPosition]
            val size = segmentList.size

            if (slider.focusedThumbIndex == 0) {
                holder.binding.startTv.text = slider.values[0].toInt().toString()
            }
            else{
                holder.binding.endTv.text = slider.values[1].toInt().toString()
            }

        })
    }

    override fun getItemCount(): Int {
        return segmentList.size
    }


    fun initSegment(segmentList: List<Segment>) {
        this.segmentList = segmentList.toMutableList()
        notifyDataSetChanged()
    }

    fun addSegment(segment: Segment, position: Int) {
        segmentList.add(position, segment)
        notifyItemInserted(position)
    }


}