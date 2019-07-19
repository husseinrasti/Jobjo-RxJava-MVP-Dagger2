package ir.hosseinrasti.app.jobjo.ui.adapter;

import android.content.Context;
import android.content.Intent;
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
import ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.interview.ChatActivity;
import ir.hosseinrasti.app.jobjo.ui.interfaces.OnItemUserClickListener;
import ir.hosseinrasti.app.jobjo.utils.Font;
import ir.hosseinrasti.app.jobjo.utils.enums.Action;

/**
 * Created by Hossein on 5/27/2018.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolderJob> {

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
                .inflate( R.layout.adapter_item_worker, parent, false );
        return new ViewHolderJob( viewGroup );
    }


    @Override
    public void onBindViewHolder( @NonNull ViewHolderJob holder, int position ) {
        holder.itemAccess.setText( userModels.get( position ).getAccessibility() );
        holder.itemEmail.setText( userModels.get( position ).getMyExpertise() );
        holder.itemFullname.setText( userModels.get( position ).getFullname() );
        holder.itemUsername.setText( userModels.get( position ).getUsername() );
        if ( !userModels.get( position ).getPicUser().isEmpty() ) {
            Picasso.with( context ).load( userModels.get( position ).getPicUser() )
                    .into( holder.itemWorkerPic );
        }
    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }

    public class ViewHolderJob extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.itemUsername)
        public TextView itemUsername;
        @BindView(R.id.itemFullname)
        public TextView itemFullname;
        @BindView(R.id.itemEmail)
        public TextView itemEmail;
        @BindView(R.id.itemAccess)
        public TextView itemAccess;
        @BindView(R.id.itemChat)
        public TextView itemChat;
        @BindView(R.id.btnChat)
        public ViewGroup btnChat;
        @BindView(R.id.itemWorkerPic)
        public ImageView itemWorkerPic;
        @BindView(R.id.itemRootWorkerAdapter)
        public ViewGroup itemRootWorkerAdapter;

        public ViewHolderJob( View itemView ) {
            super( itemView );
            ButterKnife.bind( this, itemView );
            itemRootWorkerAdapter.setOnClickListener( this );
            btnChat.setOnClickListener( this );

            font.iran( itemUsername );
            font.iran( itemFullname );
            font.iran( itemAccess );
            font.iran( itemEmail );
            font.iran( itemChat );
        }

        @Override
        public void onClick( View v ) {
            int item = v.getId();
            switch ( item ) {
                case R.id.btnChat:
                    Intent intent = new Intent( context, ChatActivity.class );
                    intent.putExtra( "userModel", userModels.get( getAdapterPosition() ) );
                    context.startActivity( intent );
                    break;
                case R.id.itemRootWorkerAdapter:
                    onItemClickListener.onItemClick( userModels.get( getAdapterPosition() ), Action.CLICK );
                    break;
            }
        }

    }


}
