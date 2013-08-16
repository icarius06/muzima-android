package com.muzima.view.forms;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.muzima.R;
import com.muzima.controller.FormController;
import com.muzima.tasks.DownloadMuzimaTask;
import com.muzima.view.MuzimaListFragment;

public abstract class FormsListFragment extends MuzimaListFragment {
    private static final String TAG = "FormsListFragment";

    protected FormController formController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View formsLayout = inflater.inflate(R.layout.layout_list_fragment, container, false);
        list = (ListView) formsLayout.findViewById(R.id.list);

        setupNoDataView(formsLayout);

        // Todo no need to do this check after all list adapters are implemented
        if (listAdapter != null) {
            list.setAdapter(listAdapter);
            list.setOnItemClickListener(this);
        }
        list.setEmptyView(formsLayout.findViewById(R.id.no_data_layout));

        return formsLayout;
    }

    public void tagsChanged() {
        listAdapter.reloadData();
    }

    @Override
    public void formDownloadComplete(Integer[] status) {
        Integer downloadStatus = status[0];
        String msg = "Download Complete with status " + downloadStatus;
        Log.i(TAG, msg);
        if (downloadStatus == DownloadMuzimaTask.SUCCESS) {
            msg = "Forms downloaded: " + status[1];
            if (listAdapter != null) {
                listAdapter.reloadData();
            }
        } else if (downloadStatus == DownloadMuzimaTask.DOWNLOAD_ERROR) {
            msg = "An error occurred while downloading forms";
        } else if (downloadStatus == DownloadMuzimaTask.AUTHENTICATION_ERROR) {
            msg = "Authentication error occurred while downloading forms";
        } else if (downloadStatus == DownloadMuzimaTask.DELETE_ERROR) {
            msg = "An error occurred while deleting existing forms";
        } else if (downloadStatus == DownloadMuzimaTask.SAVE_ERROR) {
            msg = "An error occurred while saving the downloaded forms";
        } else if (downloadStatus == DownloadMuzimaTask.CANCELLED) {
            msg = "Form download task has been cancelled";
        } else if (downloadStatus == DownloadMuzimaTask.CONNECTION_ERROR) {
            msg = "Connection error occurred while downloading forms";
        } else if (downloadStatus == DownloadMuzimaTask.PARSING_ERROR) {
            msg = "Parse exception has been thrown while fetching data";
        }
        Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
