package com.example.samriddha.shoppingappvolly.HelperClasses;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.samriddha.shoppingappvolly.Interface.RecyclerItemTouchHelperListner;
import com.example.samriddha.shoppingappvolly.ViewHolder.MyCartViewHolder;
import com.example.samriddha.shoppingappvolly.ViewHolder.MyWishlistViewHolder;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private RecyclerItemTouchHelperListner listner ;

    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs, RecyclerItemTouchHelperListner listner) {
        super(dragDirs, swipeDirs);
        this.listner = listner;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        if (listner!=null)
            listner.onSwiped(viewHolder,direction,viewHolder.getAdapterPosition());

    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder)  {

        if (viewHolder instanceof MyCartViewHolder) {
            View forgroundView = ((MyCartViewHolder) viewHolder).viewForeground;
            getDefaultUIUtil().clearView(forgroundView);
        }
        else if(viewHolder instanceof MyWishlistViewHolder){

            View forgroundView = ((MyWishlistViewHolder) viewHolder).viewForeground;
            getDefaultUIUtil().clearView(forgroundView);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if (viewHolder instanceof MyCartViewHolder) {
            View forgroundView = ((MyCartViewHolder) viewHolder).viewForeground;
            getDefaultUIUtil().onDraw(c, recyclerView, forgroundView, dX, dY, actionState, isCurrentlyActive);

        }else if (viewHolder instanceof MyWishlistViewHolder){

            View forgroundView = ((MyWishlistViewHolder) viewHolder).viewForeground;
            getDefaultUIUtil().onDraw(c, recyclerView, forgroundView, dX, dY, actionState, isCurrentlyActive);

        }


    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {

        if (viewHolder!=null) {

            if (viewHolder instanceof MyCartViewHolder) {
                View forgroundView = ((MyCartViewHolder) viewHolder).viewForeground;
                getDefaultUIUtil().onSelected(forgroundView);
            }
            else if (viewHolder instanceof MyWishlistViewHolder){

                View forgroundView = ((MyWishlistViewHolder) viewHolder).viewForeground;
                getDefaultUIUtil().onSelected(forgroundView);

            }
        }

    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {


        if (viewHolder instanceof MyCartViewHolder) {
            View forgroundView = ((MyCartViewHolder) viewHolder).viewForeground;
            getDefaultUIUtil().onDrawOver(c, recyclerView, forgroundView, dX, dY, actionState, isCurrentlyActive);

        }
        else if (viewHolder instanceof MyWishlistViewHolder){

            View forgroundView = ((MyWishlistViewHolder) viewHolder).viewForeground;
            getDefaultUIUtil().onDrawOver(c, recyclerView, forgroundView, dX, dY, actionState, isCurrentlyActive);
        }
        }
}
