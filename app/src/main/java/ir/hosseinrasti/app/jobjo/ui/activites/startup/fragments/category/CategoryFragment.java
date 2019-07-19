package ir.hosseinrasti.app.jobjo.ui.activites.startup.fragments.category;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.app.Fragment;
import androidx.core.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ir.hosseinrasti.app.jobjo.root.App;
import ir.hosseinrasti.app.jobjo.R;
import ir.hosseinrasti.app.jobjo.data.entity.WorkGroupModel;
import ir.hosseinrasti.app.jobjo.ui.activites.job.JobListByCategoryActivity;
import ir.hosseinrasti.app.jobjo.ui.adapter.CategoryAdapter;
import ir.hosseinrasti.app.jobjo.ui.interfaces.OnItemCategoryClickListener;
import ir.hosseinrasti.app.jobjo.utils.Font;
import ir.hosseinrasti.app.jobjo.utils.enums.Action;

/**
 * Created by Hossein on 5/27/2018.
 */

public class CategoryFragment extends Fragment implements
        OnItemCategoryClickListener,
        CategoryContract.View {

    private Unbinder unbinder;

    @BindView(R.id.recyclerCategory)
    public RecyclerView recyclerCategory;
    @BindView(R.id.fabAddCategory)
    public FloatingActionButton fabAddJob;
    @BindView(R.id.coordinatorCategory)
    public CoordinatorLayout coordinatorCategory;
    @BindView(R.id.edtCategory)
    public EditText edtCategory;
    @BindView(R.id.edtCategoryContainer)
    public TextInputLayout edtCategoryContainer;
    @BindView(R.id.progressBar)
    public ProgressBar progressBar;
    @BindView(R.id.txtEmpty)
    public TextView txtEmpty;
    @BindView(R.id.swipeRefresh)
    public SwipeRefreshLayout swipeRefresh;

    private List<WorkGroupModel> workGroupList = new ArrayList<>();

    @Inject
    CategoryContract.Presenter presenter;
    @Inject
    Font font;
    @Inject
    CategoryAdapter categoryAdapter;

    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View view = inflater.inflate( R.layout.fragment_category, container, false );
        unbinder = ButterKnife.bind( this, view );
        App.getComponent().inject( this );


        categoryAdapter.setContext( getContext() );
        categoryAdapter.setWorkGroupModels( workGroupList );
        categoryAdapter.setOnItemClickListener( this );
        categoryAdapter.setModify( false );
        categoryAdapter.setGrantManager( presenter.isGrantManager() );
        categoryAdapter.setFont( font );

        recyclerCategory.setLayoutManager( new GridLayoutManager( getActivity(), 2 ) );
        recyclerCategory.setItemAnimator( new DefaultItemAnimator() );
        recyclerCategory.setAdapter( categoryAdapter );

        presenter.takeView( this );
        try {
            if ( workGroupList.isEmpty() ) {
                presenter.fetchCategory();
            } else {
                categoryAdapter.notifyDataSetChanged();
            }
        } catch ( NullPointerException e ) {
            e.printStackTrace();
        }

        if ( !presenter.isGrantManager() ) {
            fabAddJob.setVisibility( View.GONE );
            edtCategory.setVisibility( View.GONE );
            edtCategoryContainer.setVisibility( View.GONE );
        }

        swipeRefresh.setColorSchemeColors( Color.parseColor( "#0077B5" ) );
        swipeRefresh.setOnRefreshListener( () -> {
            presenter.refresh();
            presenter.fetchCategory();
        } );

        fabAddJob.setOnClickListener( v -> {
                    try {
                        presenter.insertCategory();
                    } catch ( NullPointerException e ) {
                        e.printStackTrace();
                    }
                }
        );

        font.titr( txtEmpty );
        font.iran( edtCategory );
        return view;
    }


    public void dropData() {
        presenter.rxUnsubscribe();
        unbinder.unbind();
        presenter.dropView();
    }

    @Override
    public void onItemClick( WorkGroupModel workGroupModel, Action action, int color ) {
        switch ( action ) {
            case DELETE:
                showSnackDelete( workGroupModel );
                break;
            case CLICK:
                Intent intent = new Intent( getContext(), JobListByCategoryActivity.class );
                intent.putExtra( "idCategory", workGroupModel.getId() );
                intent.putExtra( "nameCategory", workGroupModel.getNameWorkGroup() );
                intent.putExtra( "color", color );
                startActivity( intent );
                break;
        }

    }


    @Override
    public void notifyAdapter( List<WorkGroupModel> workGroupList ) {
        this.workGroupList.clear();
        this.workGroupList.addAll( workGroupList );
        categoryAdapter.notifyDataSetChanged();
        if ( swipeRefresh.isRefreshing() ) {
            swipeRefresh.setRefreshing( false );
        }
    }

    @Override
    public void showSnack( String message ) {
        Snackbar snackbar = Snackbar.make( coordinatorCategory, message, Snackbar.LENGTH_LONG );
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById( android.support.design.R.id.snackbar_text );
        textView.setTextColor( Color.YELLOW );
        snackbar.show();
    }

    @Override
    public void showSnackDelete( WorkGroupModel model ) {
        Snackbar snackbar = Snackbar.make( coordinatorCategory
                , "دسته بندی " + model.getNameWorkGroup() + " پاک شود؟"
                , Snackbar.LENGTH_LONG );
        snackbar.setAction( "بله", v -> {
            try {
                presenter.removeCategory( model );
            } catch ( NullPointerException e ) {
                e.printStackTrace();
            }
        } );
        snackbar.setActionTextColor( Color.GREEN );
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById( android.support.design.R.id.snackbar_text );
        textView.setTextColor( Color.YELLOW );
        snackbar.show();
    }

    @Override
    public void setTextEmpty( String message ) {
        txtEmpty.setText( message );
    }

    @Override
    public void hideTextEmpty() {
        recyclerCategory.setVisibility( View.VISIBLE );
        txtEmpty.setVisibility( View.GONE );
    }

    @Override
    public void showTextEmpty() {
        recyclerCategory.setVisibility( View.GONE );
        txtEmpty.setVisibility( View.VISIBLE );
    }


    @Override
    public void showProgress() {
        progressBar.setVisibility( View.VISIBLE );
    }

    @Override
    public void hideProgress() {
        if ( swipeRefresh.isRefreshing() ) {
            swipeRefresh.setRefreshing( false );
        }
        progressBar.setVisibility( View.GONE );
    }

    @Override
    public String getTitleCategory() {
        return edtCategory.getText().toString();
    }

    @Override
    public void setList( List<WorkGroupModel> workGroupModels ) {
        workGroupList = workGroupModels;
    }

    @Override
    public List<WorkGroupModel> getList() {
        return workGroupList;
    }
}
