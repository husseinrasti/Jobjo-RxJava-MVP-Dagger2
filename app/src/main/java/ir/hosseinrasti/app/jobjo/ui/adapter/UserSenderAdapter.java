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
import ir.hosseinrasti.app.jobjo.data.entity.UserModel;
import ir.hosseinrasti.app.jobjo.ui.interfaces.OnItemUserClickListener;
import ir.hosseinrasti.app.jobjo.utils.Font;
import ir.hosseinrasti.app.jobjo.utils.enums.Action;

/**
 * Created by Hossein on 5/27/2018.
 */

public class UserSenderAdapter extends RecyclerView.Adapter<UserSenderAdapter.ViewHolderJob> {

    private List<UserModel> userModels;
    private Context context;
    private OnItemUserClickListener onItemClickListener;
    private Font font;

    public void setUserModels( List<UserModel> userModels ) {
        this.userModels = userModels;
    }

    public void setContext( Context context ) {
        this.context = context;
    }

    public void setOnItemClickListener( OnItemUserClickListener onItemClickListener ) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setFont( Font font ) {
        this.font = font;
    }

    @NonNull
    @Override
    public ViewHolderJob onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        ViewGroup viewGroup = ( ViewGroup ) LayoutInflater.from( context )
                .inflate( R.layout.adapter_item_sender, parent, false );
        return new ViewHolderJob( viewGroup );
    }


    @Override
    public void onBindViewHolder( @NonNull ViewHolderJob holder, int position ) {
        holder.itemSenderFullname.setText( userModels.get( position ).getFullname() );
        holder.itemSenderAccess.setText( userModels.get( position ).getAccessibility() );
        Picasso.with( context ).load( userModels.get( position ).getPicUser() ).into( holder.itemSenderPic );
    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }

    public class ViewHolderJob extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.itemSenderAccess)
        public TextView itemSenderAccess;
        @BindView(R.id.itemSenderFullname)
        public TextView itemSenderFullname;
        @BindView(R.id.itemSenderPic)
        public ImageView itemSenderPic;

        public ViewHolderJob( View itemView ) {
            super( itemView );
            ButterKnife.bind( this, itemView );
            itemView.setOnClickListener( this );
            font.iran( itemSenderFullname );
            font.iran( itemSenderAccess );
        }

        @Override
        public void onClick( View v ) {
            onItemClickListener.onItemClick( userModels.get( getAdapterPosition() ) , Action.CLICK );
        }

    }


}
