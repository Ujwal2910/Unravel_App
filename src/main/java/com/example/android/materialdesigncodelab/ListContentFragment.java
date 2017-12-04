package com.example.android.materialdesigncodelab;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListContentFragment extends Fragment {

    private List<Project> projectList;
    private TextView mName;
    private TextView mDescription;
    private TextView mLanguage;
    private TextView mStars;
    private TextView mForks;

    public static Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_list, container, false);

        projectList = new ArrayList<>();

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view,container,false);
        ContentAdapter contentAdapter = new ContentAdapter(recyclerView.getContext(), projectList, new ProjectItemClickHandler() {
            @Override
            public void onListItemClick(Project clickedProject) {
                String url = clickedProject.getProjectUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        recyclerView.setAdapter(contentAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder>
    {
        private List<Project> mProjects;
        private ProjectItemClickHandler mOnClickListener;
        private Context mContext;

        public ContentAdapter(Context context, List<Project> projects, ProjectItemClickHandler listener){
            mContext = context;
            mProjects = projects;
            mOnClickListener = listener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()),parent);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            Context context = holder.mView.getContext();

            Project project = mProjects.get(position);
            holder.mProject = project;
            holder.mProjectName.setText(project.getProjectName());
            holder.mProjectDescription.setText(project.getProjectDescription());
        }

        @Override
        public int getItemCount() {
            return mProjects.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            public View mView;
            public Project mProject;
            public TextView mProjectName;
            public TextView mProjectDescription;
            public TextView mProjectLanguage;
            public TextView mProjectStars;
            public TextView mProjectForks;

            public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_list,parent,false));
                mView = itemView;
                mProjectName = (TextView) itemView.findViewById(R.id.list_title);
                mProjectDescription = (TextView) itemView.findViewById(R.id.list_desc);

                itemView.setOnClickListener(this);

            }
            @Override
            public void onClick(View v) {
                mOnClickListener.onListItemClick(mProject);
            }
        }
    }
}
