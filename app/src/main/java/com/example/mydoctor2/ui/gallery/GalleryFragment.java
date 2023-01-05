package com.example.mydoctor2.ui.gallery;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydoctor2.R;
import com.example.mydoctor2.activities.PDFViewActivity;
import com.example.mydoctor2.activities.ScanActivity;
import com.example.mydoctor2.data.PdfModel;
import com.example.mydoctor2.databinding.FragmentGalleryBinding;
import com.example.mydoctor2.interfaces.ListenerPdf;
import com.example.mydoctor2.other.AdapterPdf;

import java.io.File;
import java.util.ArrayList;

public class GalleryFragment extends Fragment {
    private Context mContext;
    private FragmentGalleryBinding binding;
    private ArrayList<PdfModel> pdfList;
    private AdapterPdf adapterPdf;
    private RecyclerView recyclerView;
    private static final String TAG = "GALLERY_FRAGMENT";

    @Override
    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadPdfDocuments();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.pdfList);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        CardView cvDocuments = view.findViewById(R.id.addDocument);

        cvDocuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ScanActivity.class));
                loadPdfDocuments();
            }
        });

        loadPdfDocuments();
    }

    private void showMoreOptions(PdfModel pdfModel, AdapterPdf.HolderPdf holder) {
        PopupMenu popupMenu = new PopupMenu(mContext, holder.more);
        popupMenu.getMenu().add(Menu.NONE, 0, 0, "Redenumire");
        popupMenu.getMenu().add(Menu.NONE, 1, 1, "Ștergere");
//        popupMenu.getMenu().add(Menu.NONE, 2, 2, "Trimitere");

        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == 0) {
                    renamePdf(pdfModel);
                } else if (itemId == 1) {
                    deletePdf(pdfModel);
                }
                return true;
            }
        });
    }

    private void deletePdf(PdfModel pdfModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Ștergere fișier")
                .setMessage("Ștergere " + pdfModel.getFile().getName())
                .setPositiveButton("ȘTERGERE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        pdfModel.getFile().delete();
                        Toast.makeText(mContext, "Fișierul a fost șters", Toast.LENGTH_SHORT).show();
                        loadPdfDocuments();
                    }
                })
                .setNegativeButton("ANULARE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private void renamePdf(PdfModel pdfModel) {
        Log.d(TAG, "rename PDF: " + pdfModel.getFile().getName());
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_rename, null);
        EditText pdfName = view.findViewById(R.id.pdfName);
        Button renameButton = view.findViewById(R.id.renameButton);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        renameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = pdfName.getText().toString().trim();

                if (newName.isEmpty()) {
                    Toast.makeText(mContext, "Numele nu poate fi gol", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                        File folder = new File(root.getAbsolutePath() + "/" + "PDF Folder");

                        File file = new File(folder, newName + ".pdf");
                        pdfModel.getFile().renameTo(file);
                        loadPdfDocuments();
                    } catch (Exception e) {
                        Log.e(TAG, "Failed to rename " + e.getMessage());
                    }
                    alertDialog.dismiss();
                }
            }
        });
    }

    private void loadPdfDocuments() {
        pdfList = new ArrayList<>();
        adapterPdf = new AdapterPdf(mContext, pdfList, new ListenerPdf() {
            @Override
            public void onPdfClick(PdfModel pdfModel, int position) {
                Intent intent = new Intent(mContext, PDFViewActivity.class);
                intent.putExtra("pdfUri", ""+pdfModel.getUri());
                intent.putExtra("fileName", ""+pdfModel.getFile().getName());
                startActivity(intent);
            }

            @Override
            public void onPdfMoreClick(PdfModel pdfModel, int position, AdapterPdf.HolderPdf holder) {
                showMoreOptions(pdfModel, holder);
            }
        });
        recyclerView.setAdapter(adapterPdf);

        File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        File folder = new File(root.getAbsolutePath() + "/" + "PDF Folder");
        if (folder.exists()) {
            File[] files = folder.listFiles();
            Log.d(TAG, "load pdf documents " + files.length);

            for (File fileEntry : files) {
                Log.d(TAG, "load pdf documents " + fileEntry.getName());

                Uri uri = Uri.fromFile(fileEntry);
                PdfModel pdfModel = new PdfModel(fileEntry, uri);
                pdfList.add(pdfModel);
                adapterPdf.notifyItemInserted(pdfList.size());
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}