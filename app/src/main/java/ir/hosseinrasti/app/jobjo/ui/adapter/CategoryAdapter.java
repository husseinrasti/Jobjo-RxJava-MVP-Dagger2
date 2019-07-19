package ir.hosseinrasti.app.jobjo.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.hosseinrasti.app.jobjo.R;
import ir.hosseinrasti.app.jobjo.data.entity.WorkGroupModel;
import ir.hosseinrasti.app.jobjo.ui.interfaces.OnItemCategoryClickListener;
import ir.hosseinrasti.app.jobjo.utils.Font;
import ir.hosseinrasti.app.jobjo.utils.enums.Action;

/**
 * Created by Hossein on 5/27/2018.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolderJob> {

    private boolean isGrantManager;
    private List<WorkGroupModel> workGroupModels;
    private Context context;
    private OnItemCategoryClickListener onItemClickListener;
    private boolean isModify;
    private Font font;
    private List<Integer> colors = new ArrayList<>();

    public void setGrantManager( boolean grantManager ) {
        isGrantManager = grantManager;
    }

    public void setWorkGroupModels( List<WorkGroupModel> workGroupModels ) {
        this.workGroupModels = workGroupModels;
    }

    public void setContext( Context context ) {
        this.context = context;
    }

    public void setOnItemClickListener( OnItemCategoryClickListener onItemClickListener ) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setModify( boolean modify ) {
        isModify = modify;
    }

    public void setFont( Font font ) {
        this.font = font;
    }

    @NonNull
    @Override
    public ViewHolderJob onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        ViewGroup viewGroup = ( ViewGroup ) LayoutInflater.from( parent.getContext() )
                .inflate( R.layout.adapter_item_category, parent, false );
        return new ViewHolderJob( viewGroup );
    }


    private void setColor( int position, int color ) {
        colors.add( position, color );
    }

    @Override
    public void onBindViewHolder( @NonNull ViewHolderJob holder, int position ) {
        int color = randomColor();
        holder.container.setBackgroundColor( color );
        setColor( position, color );
        if ( isModify ) {
            holder.itemCategory.setTextSize( 10 );
            holder.container.setMinimumHeight( 72 );
            holder.imgCategoryPic.setVisibility( View.GONE );
        }
        if ( isGrantManager ) {
            holder.itemDeleteCategory.setVisibility( View.VISIBLE );
        }
        if ( !workGroupModels.get( position ).getPic().isEmpty() ) {
            Picasso.with( context ).load( workGroupModels.get( position ).getPic() ).into( holder.imgCategoryPic );
        }
        holder.itemCategory.setText( workGroupModels.get( position ).getNameWorkGroup() );
    }

    private int counterColor = -1;

    private int randomColor() {
        String[] colors = {
                "#f57d00" , "#3b5999" , "#00c300" , "#bd081c" , "#00AFF0" , "#ff6600" , "#02b875" , "#0084ff" ,
                "#ff0084" , "#25D366" , "#df2029" , "#0077b5" , "#410093" , "#2ecc71" , "#e4405f" };
        counterColor++;
        if ( counterColor >= 15 ) {
            counterColor = 0;
        }
        return Color.parseColor( colors[counterColor] );
    }

    @Override
    public int getItemCount() {
        return workGroupModels.size();
    }

    public class ViewHolderJob extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.container)
        ViewGroup container;
        @BindView(R.id.itemCategory)
        TextView itemCategory;
        @BindView(R.id.itemDeleteCategory)
        ImageView itemDeleteCategory;
        @BindView(R.id.imgCategoryPic)
        ImageView imgCategoryPic;

        public ViewHolderJob( View itemView ) {
            super( itemView );
            ButterKnife.bind( this, itemView );
            itemCategory.setOnClickListener( this );
            imgCategoryPic.setOnClickListener( this );
            itemDeleteCategory.setOnClickListener( this );
            font.iran( itemCategory );
        }

        @Override
        public void onClick( View v ) {
            switch ( v.getId() ) {
                case R.id.itemDeleteCategory:
                    onItemClickListener.onItemClick( workGroupModels.get( getAdapterPosition() ), Action.DELETE
                            , colors.get( getAdapterPosition() ) );
                    break;
                case R.id.imgCategoryPic:
                case R.id.itemCategory:
                    onItemClickListener.onItemClick( workGroupModels.get( getAdapterPosition() ), Action.CLICK
                            , colors.get( getAdapterPosition() ) );
                    break;
            }
        }

    }


}
