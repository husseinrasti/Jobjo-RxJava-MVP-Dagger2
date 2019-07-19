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
import ir.hosseinrasti.app.jobjo.data.entity.JobModel;
import ir.hosseinrasti.app.jobjo.ui.user.profileOthers.ProfileOthersActivity;
import ir.hosseinrasti.app.jobjo.utils.enums.Action;
import ir.hosseinrasti.app.jobjo.ui.interfaces.OnItemJobClickListener;
import ir.hosseinrasti.app.jobjo.ui.activites.modify.job.ModifyJobActivity;
import ir.hosseinrasti.app.jobjo.utils.Font;

/**
 * Created by Hossein on 5/27/2018.
 */

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolderJob> {

    private List<JobModel> jobList;
    private Context context;
    private OnItemJobClickListener onItemClickListener;
    private long idCurrentUser;
    private boolean isApplyJob;
    private boolean isGrantManager;
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

    public void setIdCurrentUser( long idCurrentUser ) {
        this.idCurrentUser = idCurrentUser;
    }

    public void setApplyJob( boolean applyJob ) {
        isApplyJob = applyJob;
    }

    public void setGrantManager( boolean grantManager ) {
        isGrantManager = grantManager;
    }

    public void setFont( Font font ) {
        this.font = font;
    }

    @NonNull
    @Override
    public ViewHolderJob onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        ViewGroup viewGroup = ( ViewGroup ) LayoutInflater.from( parent.getContext() )
                .inflate( R.layout.adapter_item_job, parent, false );
        return new ViewHolderJob( viewGroup );
    }


    @Override
    public void onBindViewHolder( @NonNull ViewHolderJob holder, int position ) {
        holder.itemJobAddress.setText( jobList.get( position ).getAddress() );
        holder.itemJobName.setText( jobList.get( position ).getNameJob() );
        holder.itemJobNameCreator.setText( jobList.get( position ).getNameJobCreator() );
        holder.itemJobWorkGroup.setText( jobList.get( position ).getNameWorkGroup() );
        holder.txtCountJobApply.setText( jobList.get( position ).getCountSeeker() );
        holder.txtCountJobComment.setText( jobList.get( position ).getCountComment() );

        if ( isGrantManager ) {
            holder.rootAccessManager.setVisibility( View.VISIBLE );
        }

        if ( isApplyJob ) {
            holder.imgUserJobApply.setImageResource( R.drawable.ic_dislike );
            holder.txtUserJobApply.setText( "لغو درخواست" );
        }

        if ( jobList.get( position ).getIdUserCreator() == idCurrentUser ) {
            holder.userJobApply.setVisibility( View.GONE );
        }
        Picasso.with( context ).load( jobList.get( position ).getPicJob() ).into( holder.imgUserJobPic );
        Picasso.with( context ).load( jobList.get( position ).getPicUser() ).into( holder.imgUserProfile );
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public class ViewHolderJob extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.txtUserJobApply)
        TextView txtUserJobApply;
        @BindView(R.id.imgUserJobApply)
        ImageView imgUserJobApply;
        @BindView(R.id.txtUserJobComment)
        TextView txtUserJobComment;
        @BindView(R.id.imgUserJobComment)
        ImageView imgUserJobComment;
        @BindView(R.id.imgUserJobShare)
        ImageView imgUserJobShare;
        @BindView(R.id.txtUserJobShare)
        TextView txtUserJobShare;
        @BindView(R.id.txtJobComment)
        TextView txtJobComment;
        @BindView(R.id.txtJobApply)
        TextView txtJobApply;
        @BindView(R.id.txtCountJobComment)
        TextView txtCountJobComment;
        @BindView(R.id.txtCountJobApply)
        TextView txtCountJobApply;
        @BindView(R.id.itemJobAddress)
        TextView itemJobAddress;
        @BindView(R.id.itemJobName)
        TextView itemJobName;
        @BindView(R.id.itemJobNameCreator)
        TextView itemJobNameCreator;
        @BindView(R.id.itemJobWorkGroup)
        TextView itemJobWorkGroup;
        @BindView(R.id.imgUserProfile)
        ImageView imgUserProfile;
        @BindView(R.id.imgUserJobPic)
        ImageView imgUserJobPic;
        @BindView(R.id.itemRootUser)
        ViewGroup itemRootUser;
        @BindView(R.id.userJobApply)
        ViewGroup userJobApply;
        @BindView(R.id.rootAccessManager)
        ViewGroup rootAccessManager;
        @BindView(R.id.imgDeleteAccessManager)
        ImageView imgDeleteAccessManager;
        @BindView(R.id.imgModifyAccessManager)
        ImageView imgModifyAccessManager;


        public ViewHolderJob( View itemView ) {
            super( itemView );
            ButterKnife.bind( this, itemView );
            imgUserJobPic.setOnClickListener( this );
            itemRootUser.setOnClickListener( this );
            userJobApply.setOnClickListener( this );
            itemJobAddress.setOnClickListener( this );
            itemJobName.setOnClickListener( this );
            itemJobWorkGroup.setOnClickListener( this );
            imgDeleteAccessManager.setOnClickListener( this );
            imgModifyAccessManager.setOnClickListener( this );
            imgUserJobComment.setOnClickListener( this );
            txtUserJobComment.setOnClickListener( this );
            imgUserJobShare.setOnClickListener( this );
            txtUserJobShare.setOnClickListener( this );

            setFont();
        }

        private void setFont() {
            font.koodak( itemJobNameCreator );
            font.iran( itemJobName );
            font.iran( itemJobWorkGroup );
            font.iran( itemJobAddress );
            font.iran( txtCountJobApply );
            font.iran( txtJobApply );
            font.iran( txtJobComment );
            font.iran( txtCountJobComment );
            font.iran( txtUserJobApply );
            font.iran( txtUserJobComment );
            font.iran( txtUserJobShare );
        }

        @Override
        public void onClick( View v ) {
            int item = v.getId();
            switch ( item ) {
                case R.id.imgDeleteAccessManager:
                    onItemClickListener.onItemClick( jobList.get( getAdapterPosition() ), Action.DELETE );
                    break;
                case R.id.imgUserJobShare:
                case R.id.txtUserJobShare:
                    onItemClickListener.onItemClick( jobList.get( getAdapterPosition() ), Action.SHARE );
                    break;
                case R.id.txtUserJobComment:
                case R.id.imgUserJobComment:
                    onItemClickListener.onItemClick( jobList.get( getAdapterPosition() ), Action.COMMENT );
                    break;
                case R.id.imgModifyAccessManager: {
                    Intent intent = new Intent( context, ModifyJobActivity.class );
                    intent.putExtra( "jobModel", jobList.get( getAdapterPosition() ) );
                    intent.putExtra( "mustUpdate", true );
                    context.startActivity( intent );
                    break;
                }
                case R.id.itemRootUser: {
                    Intent intent = new Intent( context, ProfileOthersActivity.class );
                    intent.putExtra( "idUser", jobList.get( getAdapterPosition() ).getIdUserCreator() );
                    context.startActivity( intent );
                    break;
                }
                case R.id.imgUserJobApply:
                case R.id.userJobApply:
                    onItemClickListener.onItemClick( jobList.get( getAdapterPosition() ), Action.APPLY );
                    notifyDataSetChanged();
                    break;
                case R.id.itemJobName:
                case R.id.itemJobAddress:
                case R.id.itemJobWorkGroup:
                case R.id.imgUserJobPic:
                    onItemClickListener.onItemClick( jobList.get( getAdapterPosition() ), Action.CLICK );
                    break;
            }
        }

    }


}
