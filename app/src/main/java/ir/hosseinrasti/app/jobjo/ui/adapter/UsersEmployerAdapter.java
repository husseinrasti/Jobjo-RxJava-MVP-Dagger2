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

public class UsersEmployerAdapter extends RecyclerView.Adapter<UsersEmployerAdapter.ViewHolderJob> {

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
        ViewGroup viewGroup = ( ViewGroup ) LayoutInflater.from( parent.getContext() )
                .inflate( R.layout.adapter_item_users_employer, parent, false );
        return new ViewHolderJob( viewGroup );
    }


    @Override
    public void onBindViewHolder( @NonNull ViewHolderJob holder, int position ) {
        holder.itemEmail.setText( userModels.get( position ).getAccessibility() );
        holder.itemFullname.setText( userModels.get( position ).getFullname() );
        if ( !userModels.get( position ).getPicUser().isEmpty() ) {
            Picasso.with( context ).load( userModels.get( position ).getPicUser() ).into( holder.itemUserPic );
        }
    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }

    public class ViewHolderJob extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.itemFullname)
        public TextView itemFullname;
        @BindView(R.id.itemEmail)
        public TextView itemEmail;
        @BindView(R.id.itemUserPic)
        public ImageView itemUserPic;
        @BindView(R.id.itemRootWorkerAdapter)
        public ViewGroup itemRootWorkerAdapter;

        public ViewHolderJob( View itemView ) {
            super( itemView );
            ButterKnife.bind( this, itemView );
            itemRootWorkerAdapter.setOnClickListener( this );
            font.iran( itemFullname );
            font.iran( itemEmail );
        }

        @Override
        public void onClick( View v ) {
            int item = v.getId();
            switch ( item ) {
                case R.id.itemRootWorkerAdapter:
                    onItemClickListener.onItemClick( userModels.get( getAdapterPosition() ) , Action.CLICK );
                    break;
            }
        }

    }


}
