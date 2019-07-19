package ir.hosseinrasti.app.jobjo.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.hosseinrasti.app.jobjo.R;
import ir.hosseinrasti.app.jobjo.data.entity.MessageModel;
import ir.hosseinrasti.app.jobjo.utils.Font;

/**
 * Created by Hossein on 5/27/2018.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolderJob> {

    private List<MessageModel> messageModelList;
    private Context context;
    private long idCurrentUser;
    private Font font;

    public void setMessageModelList( List<MessageModel> messageModelList ) {
        this.messageModelList = messageModelList;
    }

    public void setContext( Context context ) {
        this.context = context;
    }

    public void setIdCurrentUser( long idCurrentUser ) {
        this.idCurrentUser = idCurrentUser;
    }

    public void setFont( Font font ) {
        this.font = font;
    }

    @NonNull
    @Override
    public ViewHolderJob onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        ViewGroup viewGroup = ( ViewGroup ) LayoutInflater.from( context )
                .inflate( R.layout.adapter_item_chat, parent, false );
        return new ViewHolderJob( viewGroup );
    }

    @Override
    public void onBindViewHolder( @NonNull ViewHolderJob holder, int position ) {
        if ( messageModelList.get( position ).getIdUserSender() == idCurrentUser ) {
            holder.rootItemCardMessage.setCardBackgroundColor( Color.parseColor( "#FF5ECF7A" ) );
            holder.rootItemMessage.setGravity( Gravity.START );
        } else {
            holder.rootItemCardMessage.setCardBackgroundColor( Color.parseColor( "#dddddd" ) );
            holder.rootItemMessage.setGravity( Gravity.END );
        }
        holder.message.setText( messageModelList.get( position ).getMessage() );
        holder.createAt.setText( messageModelList.get( position ).getCreatedAt() );

    }

    public void insertMessage( MessageModel messageModel ) {
        messageModelList.add( messageModel );
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return messageModelList.size();
    }

    public class ViewHolderJob extends RecyclerView.ViewHolder {

        @BindView(R.id.message)
        public TextView message;
        @BindView(R.id.createAt)
        public TextView createAt;
        @BindView(R.id.rootItemMessage)
        LinearLayout rootItemMessage;
        @BindView(R.id.rootItemCardMessage)
        CardView rootItemCardMessage;

        public ViewHolderJob( View itemView ) {
            super( itemView );
            ButterKnife.bind( this, itemView );

            font.iran( message );
            font.ubuntu( createAt );
        }

    }

}
