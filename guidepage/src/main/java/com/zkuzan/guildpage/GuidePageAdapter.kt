package com.zkuzan.guildpage

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.core.util.set
import androidx.recyclerview.widget.RecyclerView

class GuidePageAdapter : RecyclerView.Adapter<GuidePageAdapter.ImageHolder> {

    private var mDatas: List<Int>? = null

    private var mOnItemChildClickListener: OnItemChildClickListener? = null

    constructor(data: IntArray?) : this(data?.toList())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(GuidePageSpec.pageItemLayoutId, parent, false)
        return ImageHolder(view)
    }

    override fun getItemCount(): Int {
        return mDatas?.size ?: 0
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.setImageResource(getItem(position))
        holder.btnDone.apply {
            setOnClickListener {
                mOnItemChildClickListener?.onClick(it, position)
            }

            visibility = if (position == itemCount - 1) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        GuidePageSpec.onPageExtraViewCallback?.run {
            GuidePageSpec.pageExtraViewIds?.let {
                for(id in it){
                    onPageExtraView(holder.getView(id),position,itemCount)
                }
            }

        }

    }

    fun setOnItemChildClickListener(listener: OnItemChildClickListener?) {
        this.mOnItemChildClickListener = listener
    }

    private fun getItem(position: Int): Int {
        return mDatas?.get(position) ?: 0
    }

    constructor(data: List<Int>?) {
        mDatas = data
    }

    class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivPage: ImageView = itemView.findViewById(GuidePageSpec.pageImageViewId)
        var btnDone: View = itemView.findViewById(GuidePageSpec.pageDoneId)

        private var extraViews: SparseArray<View> = SparseArray()

        init {
            ivPage.scaleType = GuidePageSpec.pageScaleType
            GuidePageSpec.pageDoneDrawable?.let {
                (btnDone as? ImageView)?.setImageResource(it)
            }
        }

        fun <T : View> getView(@IdRes id: Int): T {
            var v = extraViews.get(id)
            if (v == null) {
                v = itemView.findViewById(id)
                this.extraViews[id] = v
            }

            return v as T
        }

        fun setImageResource(@DrawableRes resId: Int) {
            ivPage.setImageResource(resId)
        }

    }

    interface OnItemChildClickListener {
        fun onClick(v: View, position: Int)
    }
}