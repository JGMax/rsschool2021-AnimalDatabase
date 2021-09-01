package gortea.jgmax.animalstorage.ui.list.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import gortea.jgmax.animalstorage.ui.utils.hasNext
import gortea.jgmax.animalstorage.ui.utils.hasPrevious

class VerticalItemDecorator(
    private val topDivider: Int,
    private val mediumDivider: Int,
    private val bottomDivider: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val holder = parent.getChildViewHolder(view)
        val position = parent
            .getChildAdapterPosition(view)
            .takeIf { it != RecyclerView.NO_POSITION } ?: holder.oldPosition
        val adapter = parent.adapter ?: return

        outRect.apply {
            top = if (adapter.hasPrevious(position)) mediumDivider / 2 else topDivider
            bottom = if (adapter.hasNext(position)) mediumDivider / 2 else bottomDivider
        }
    }
}