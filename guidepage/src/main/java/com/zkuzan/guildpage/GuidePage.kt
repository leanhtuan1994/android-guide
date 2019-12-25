package com.zkuzan.guildpage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import androidx.annotation.*
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2

class GuidePage private constructor(drawableIds: IntArray?) {
    private var mOptionsCompat: ActivityOptionsCompat? = null

    init {
        GuidePageSpec.reset()
        GuidePageSpec.drawableData = drawableIds
    }

    companion object {
        fun load(drawableIds: IntArray?): GuidePage {
            return GuidePage(drawableIds)
        }

        fun load(drawableIds: List<Int>?): GuidePage {
            return GuidePage(drawableIds?.toIntArray())
        }
    }

    fun pageLayoutId(@LayoutRes layoutId: Int): GuidePage {
        GuidePageSpec.pageLayoutId = layoutId
        return this
    }

    fun viewPagerId(@IdRes viewPagerId: Int): GuidePage {
        GuidePageSpec.viewPagerId = viewPagerId
        return this
    }

    fun indicatorId(@IdRes indicatorId: Int): GuidePage {
        GuidePageSpec.indicatorId = indicatorId
        return this
    }

    private fun dp2px(context: Context, dpValue: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpValue,
            context.resources.displayMetrics
        ).plus(0.5f).toInt()
    }

    private fun obtainIndicatorConfig(): IndicatorConfig {
        return GuidePageSpec.indicatorConfig ?: IndicatorConfig()
    }

    fun indicatorDrawableResource(@DrawableRes drawableId: Int): GuidePage {
        GuidePageSpec.indicatorConfig =
            obtainIndicatorConfig().apply { this.drawableResId = drawableId }
        return this
    }

    fun indicatorSizeResource(context: Context, @DimenRes resId: Int): GuidePage {
        GuidePageSpec.indicatorConfig = obtainIndicatorConfig().apply {
            val size = context.resources.getDimensionPixelSize(resId)
            this.width = size
            this.height = size
        }
        return this
    }

    fun indicatorSize(context: Context, dpSize: Float): GuidePage {
        indicatorSize(dp2px(context, dpSize))
        return this
    }

    fun indicatorSize(size: Int): GuidePage {
        GuidePageSpec.indicatorConfig = obtainIndicatorConfig().apply {
            this.width = size
            this.height = size
        }
        return this
    }

    fun indicatorConfig(indicatorConfig: IndicatorConfig?): GuidePage {
        GuidePageSpec.indicatorConfig = indicatorConfig
        return this
    }

    fun skipId(@IdRes skipId: Int): GuidePage {
        GuidePageSpec.skipId = skipId
        return this
    }

    fun skipTextAppearance(@StyleRes skipTextAppearance: Int): GuidePage {
        GuidePageSpec.skipTextAppearance = skipTextAppearance
        return this
    }

    fun skipText(skipText: CharSequence): GuidePage {
        GuidePageSpec.skipText = skipText
        return this
    }

    fun skipBackgroundResource(@DrawableRes drawableId: Int): GuidePage {
        GuidePageSpec.skipBackgroundResource = drawableId
        return this
    }

    fun showSkip(showSkip: Boolean): GuidePage {
        GuidePageSpec.showSkip = showSkip
        return this
    }

    fun lastPageHideSkip(lastPageHideSkip: Boolean): GuidePage {
        GuidePageSpec.lastPageHideSkip = lastPageHideSkip
        return this
    }

    fun pageItemLayoutId(@IdRes layoutId: Int): GuidePage {
        GuidePageSpec.pageItemLayoutId = layoutId
        return this
    }

    fun pageImageViewId(@IdRes pageImageViewId: Int): GuidePage {
        GuidePageSpec.pageImageViewId = pageImageViewId
        return this
    }

    fun pageDoneId(@IdRes pageDoneId: Int): GuidePage {
        GuidePageSpec.pageDoneId = pageDoneId
        return this
    }

    fun pageDoneDrawableResource(@DrawableRes drawableId: Int): GuidePage {
        GuidePageSpec.pageDoneDrawable = drawableId
        return this
    }


    fun pageImageViewScaleType(scaleType: ImageView.ScaleType): GuidePage {
        GuidePageSpec.pageScaleType = scaleType
        return this
    }


    fun onGuidePageChangeCallback(callback: OnGuidePageChangeCallback?): GuidePage {
        GuidePageSpec.onGuidePageChangeCallback = callback
        return this
    }


    fun autoFinish(autoFinish: Boolean): GuidePage {
        GuidePageSpec.isAutoFinish = autoFinish
        return this
    }


    fun theme(@StyleRes theme: Int): GuidePage {
        GuidePageSpec.theme = theme
        return this
    }

    fun screenOrientation(orientation: Int): GuidePage {
        GuidePageSpec.orientation = orientation
        return this
    }

    fun pageExtraViewIds(pageExtraViewIds: List<Int>?): GuidePage {
        GuidePageSpec.pageExtraViewIds = pageExtraViewIds?.toIntArray()
        return this
    }

    fun pageExtraViewIds(pageExtraViewIds: IntArray?): GuidePage {
        GuidePageSpec.pageExtraViewIds = pageExtraViewIds
        return this
    }

    fun onPageExtraViewCallback(callback: OnPageExtraViewCallback?): GuidePage {
        GuidePageSpec.onPageExtraViewCallback = callback
        return this
    }

    fun offscreenPageLimit(offscreenPageLimit: Int): GuidePage {
        GuidePageSpec.offscreenPageLimit = offscreenPageLimit
        return this
    }

    fun showIndicator(showIndicator: Boolean): GuidePage {
        GuidePageSpec.showIndicator = showIndicator
        return this
    }

    fun activityOptionsCompat(optionsCompat: ActivityOptionsCompat): GuidePage {
        this.mOptionsCompat = optionsCompat
        return this
    }

    private fun obtainActivityOptionsCompat(context: Context): ActivityOptionsCompat {
        return mOptionsCompat ?: ActivityOptionsCompat.makeCustomAnimation(
            context,
            R.anim.gp_anim_in,
            R.anim.gp_anim_out
        )
    }


    fun start(fragment: Fragment) {
        val intent = Intent(fragment.context, GuidePageActivity::class.java)
        val bundle = fragment.context?.let { obtainActivityOptionsCompat(it) }?.toBundle()
        fragment.startActivity(intent, bundle)
    }

    fun start(activity: Activity) {
        val intent = Intent(activity, GuidePageActivity::class.java)
        val bundle = obtainActivityOptionsCompat(activity).toBundle()
        activity.startActivity(intent, bundle)
    }

    interface OnGuidePageChangeCallback {

        fun onPageScrolled(position: Int, positionOffset: Float, @Px positionOffsetPixels: Int) {}

        fun onPageSelected(position: Int) {}

        fun onPageScrollStateChanged(@ViewPager2.ScrollState state: Int) {}

        fun onPageDone(skip: Boolean)
    }


    interface OnPageExtraViewCallback {
        fun onPageExtraView(v: View, position: Int, count: Int)
    }
}