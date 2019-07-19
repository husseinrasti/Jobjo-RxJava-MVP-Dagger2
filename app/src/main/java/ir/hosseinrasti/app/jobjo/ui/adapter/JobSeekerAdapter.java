package ir.hosseinrasti.app.jobjo.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.hosseinrasti.app.jobjo.R;
import ir.hosseinrasti.app.jobjo.data.entity.UserModel;
import ir.hosseinrasti.app.jobjo.ui.interfaces.OnItemUserClickListener;
import ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.interview.ChatActivity;
import ir.hosseinrasti.app.jobjo.utils.Font;
import ir.hosseinrasti.app.jobjo.utils.enums.Action;

/**
 * Created by Hossein on 5/27/2018.
 */

public class JobSeekerAdapter extends RecyclerView.Adapter<JobSeekerAdapter.ViewHolderJob> {

    private List<UserModel> userModels;
    private OnItemUserClickListener onItemClickListener;
    private Context context;
    private Font font;

    public void setUserModels( List<UserModel> userModels ) {
        this.userModels = userModels;
    }

    public void setOnItemClickListener( OnItemUserClickListener onItemClickListener ) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setContext( Context context ) {
        this.context = context;
    }

    public void setFont( Font font ) {
        this.font = font;
    }

    @NonNull
    @Override
    public ViewHolderJob onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        ViewGroup viewGroup = ( ViewGroup ) LayoutInflater.from( context )
                .inflate( R.layout.adapter_item_follower, parent, false );
        return new ViewHolderJob( viewGroup );
    }


    @Override
    public void onBindViewHolder( @NonNull ViewHolderJob holder, int position ) {
        holder.itemFollowerAccess.setText( userModels.get( position ).getAccessibility() );
        holder.itemFollowerEmail.setText( userModels.get( position ).getMyExpertise() );
        holder.itemFollowerFullname.setText( userModels.get( position ).getFullname() );
        holder.itemFollowerUsername.setText( userModels.get( position ).getUsername() );
        Picasso.with( context ).load( userModels.get( position ).getPicUser() )
                .into( holder.itemFollowerPic );
    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }

    public class ViewHolderJob extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.itemFollowerUsername)
        TextView itemFollowerUsername;
        @BindView(R.id.itemFollowerFullname)
        TextView itemFollowerFullname;
        @BindView(R.id.itemFollowerEmail)
        TextView itemFollowerEmail;
        @BindView(R.id.itemFollowerAccess)
        TextView itemFollowerAccess;
        @BindView(R.id.txtShareWorkerFollower)
        TextView txtShareWorkerFollower;
        @BindView(R.id.txtBtnChat)
        TextView txtBtnChat;
        @BindView(R.id.itemFollowerPic)
        ImageView itemFollowerPic;
        @BindView(R.id.btnShareWorkerFollower)
        LinearLayout btnWorkerFollower;
        @BindView(R.id.itemRootFollowerAdapter)
        ViewGroup itemRootFollowerAdapter;
        @BindView(R.id.btnChat)
        ViewGroup btnChat;

        public ViewHolderJob( View itemView ) {
            super( itemView );
            ButterKnife.bind( this, itemView );
            itemRootFollowerAdapter.setOnClickListener( this );
            btnWorkerFollower.setOnClickListener( this );
            btnChat.setOnClickListener( this );

            font.koodak( txtShareWorkerFollower );
            font.koodak( txtBtnChat );
            font.iran( itemFollowerAccess );
            font.iran( itemFollowerEmail );
            font.iran( itemFollowerFullname );
            font.iran( itemFollowerUsername );
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
                case R.id.btnShareWorkerFollower:
                    onItemClickListener.onItemClick( userModels.get( getAdapterPosition() ), Action.SHARE );
                    break;
                case R.id.itemRootFollowerAdapter:
                    onItemClickListener.onItemClick( userModels.get( getAdapterPosition() ) ,Action.CLICK);
                    break;
            }
        }

    }


}
