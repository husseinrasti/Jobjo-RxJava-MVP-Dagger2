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

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.hosseinrasti.app.jobjo.R;
import ir.hosseinrasti.app.jobjo.data.entity.JobModel;
import ir.hosseinrasti.app.jobjo.ui.activites.job.JobSeekersActivity;
import ir.hosseinrasti.app.jobjo.ui.interfaces.OnItemJobClickListener;
import ir.hosseinrasti.app.jobjo.utils.Font;
import ir.hosseinrasti.app.jobjo.utils.enums.Action;

/**
 * Created by Hossein on 5/27/2018.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolderJob> {

    private List<JobModel> jobList;
    private Context context;
    private OnItemJobClickListener onItemClickListener;
    private Font font;

    public void setJobList( List<JobModel> jobList ) {
        this.jobList = jobList;
    }

    public void setContext( Context context ) {
        this.context = context;
    }

    public void setOnItemClickListener( OnItemJobClickListener onItemClickListener ) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setFont( Font font ) {
        this.font = font;
    }

    @NonNull
    @Override
    public ViewHolderJob onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        ViewGroup viewGroup = ( ViewGroup ) LayoutInflater.from( parent.getContext() )
                .inflate( R.layout.adapter_item_post, parent, false );
        return new ViewHolderJob( viewGroup );
    }


    @Override
    public void onBindViewHolder( @NonNull ViewHolderJob holder, int position ) {
        holder.itemJobPostAddress.setText( jobList.get( position ).getAddress() );
        holder.itemJobPostName.setText( jobList.get( position ).getNameJob() );
        holder.itemJobPostWorkGroup.setText( jobList.get( position ).getNameWorkGroup() );
        Picasso.with( context ).load( jobList.get( position ).getPicJob() ).into( holder.imgUserJobPostPic );
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public class ViewHolderJob extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.itemJobPostAddress)
        TextView itemJobPostAddress;
        @BindView(R.id.itemJobPostName)
        TextView itemJobPostName;
        @BindView(R.id.itemJobPostWorkGroup)
        TextView itemJobPostWorkGroup;
        @BindView(R.id.imgUserJobPostPic)
        ImageView imgUserJobPostPic;
        @BindView(R.id.postJobEdit)
        LinearLayout postJobEdit;
        @BindView(R.id.postJobDelete)
        LinearLayout postJobDelete;
        @BindView(R.id.postJobApply)
        LinearLayout postJobApply;
        @BindView(R.id.txtPostJobApply)
        TextView txtPostJobApply;
        @BindView(R.id.txtPostJobDelete)
        TextView txtPostJobDelete;
        @BindView(R.id.txtPostJobEdit)
        TextView txtPostJobEdit;

        public ViewHolderJob( View itemView ) {
            super( itemView );
            ButterKnife.bind( this, itemView );
            imgUserJobPostPic.setOnClickListener( this );
            itemJobPostName.setOnClickListener( this );
            itemJobPostAddress.setOnClickListener( this );
            itemJobPostWorkGroup.setOnClickListener( this );
            postJobEdit.setOnClickListener( this );
            postJobDelete.setOnClickListener( this );
            postJobApply.setOnClickListener( this );

            setFont();
        }

        private void setFont() {
            font.iran( txtPostJobApply );
            font.iran( txtPostJobDelete );
            font.iran( txtPostJobEdit );
            font.iran( itemJobPostAddress );
            font.iran( itemJobPostName );
            font.iran( itemJobPostWorkGroup );
        }

        @Override
        public void onClick( View v ) {
            int item = v.getId();
            switch ( item ) {
                case R.id.postJobEdit:
                    onItemClickListener.onItemClick( jobList.get( getAdapterPosition() ), Action.MODIFY );
                    break;
                case R.id.postJobDelete:
                    onItemClickListener.onItemClick( jobList.get( getAdapterPosition() ), Action.DELETE );
                    break;
                case R.id.postJobApply:
                    Intent intent = new Intent( context, JobSeekersActivity.class );
                    intent.putExtra( "job", ( Serializable )jobList.get( getAdapterPosition() ) );
                    context.startActivity( intent );
                    break;
                case R.id.itemJobPostAddress:
                case R.id.itemJobPostName:
                case R.id.itemJobPostWorkGroup:
                case R.id.imgUserJobPostPic:
                    onItemClickListener.onItemClick( jobList.get( getAdapterPosition() ), Action.CLICK );
                    break;
            }
        }

    }


}
