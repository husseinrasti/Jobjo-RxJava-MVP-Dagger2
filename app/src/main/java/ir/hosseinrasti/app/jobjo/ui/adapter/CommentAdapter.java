package ir.hosseinrasti.app.jobjo.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.hosseinrasti.app.jobjo.R;
import ir.hosseinrasti.app.jobjo.data.entity.CommentModel;
import ir.hosseinrasti.app.jobjo.ui.interfaces.OnItemCommentClickListener;
import ir.hosseinrasti.app.jobjo.utils.Font;
import ir.hosseinrasti.app.jobjo.utils.enums.Action;

/**
 * Created by Hossein on 5/27/2018.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolderComment> {

    private List<CommentModel> commentModels;
    private Context context;
    private OnItemCommentClickListener onItemClickListener;
    private Font font;

    public void setCommentModels( List<CommentModel> commentModels ) {
        this.commentModels = commentModels;
    }

    public void addCommentModel( CommentModel commentModels ) {
        this.commentModels.add( 0, commentModels );
        notifyDataSetChanged();
    }

    public void setContext( Context context ) {
        this.context = context;
    }

    public void setOnItemClickListener( OnItemCommentClickListener onItemClickListener ) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setFont( Font font ) {
        this.font = font;
    }

    @NonNull
    @Override
    public ViewHolderComment onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View viewGroup = ( View ) LayoutInflater.from( context )
                .inflate( R.layout.adapter_item_comment, parent, false );
        return new ViewHolderComment( viewGroup );
    }


    @Override
    public void onBindViewHolder( @NonNull ViewHolderComment holder, int position ) {
        holder.txtCommentOwner.setText( commentModels.get( position ).getNameUser() );
        holder.txtCommentDate.setText( commentModels.get( position ).getCreatedAt() );
        holder.txtCommentMessage.setText( commentModels.get( position ).getComment() );
        if ( !commentModels.get( position ).getUrlPicUser().isEmpty() ) {
            Picasso.with( context )
                    .load( commentModels.get( position ).getUrlPicUser() )
                    .into( holder.imgCommentOwner );
        }
    }

    @Override
    public int getItemCount() {
        return commentModels.size();
    }

    public class ViewHolderComment extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.imgCommentOwner)
        ImageView imgCommentOwner;
        @BindView(R.id.txtCommentOwner)
        TextView txtCommentOwner;
        @BindView(R.id.txtCommentDate)
        TextView txtCommentDate;
        @BindView(R.id.txtCommentMessage)
        TextView txtCommentMessage;

        ViewHolderComment( View itemView ) {
            super( itemView );
            ButterKnife.bind( this, itemView );
            itemView.setOnClickListener( this );
            font.iran( txtCommentMessage );
            font.iran( txtCommentOwner );
            font.ubuntu( txtCommentDate );
        }

        @Override
        public void onClick( View v ) {
            onItemClickListener.onItemClick( commentModels.get( getAdapterPosition() ), Action.CLICK );
        }

    }


}
