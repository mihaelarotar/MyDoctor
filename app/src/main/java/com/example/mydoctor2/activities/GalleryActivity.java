package com.example.mydoctor2.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydoctor2.R;
import com.example.mydoctor2.data.PdfModel;
import com.example.mydoctor2.interfaces.ListenerPdf;
import com.example.mydoctor2.other.AdapterPdf;

import java.io.File;
import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {
    private Context mContext;
    private ArrayList<PdfModel> pdfList;
    private AdapterPdf adapterPdf;
    private RecyclerView recyclerView;
    private static final String TAG = "GALLERY_ACTIVITY";
    

//    @Override
//    public void onAttach(@NonNull Context context) {
//        mContext = context;
//        super.onAttach(context);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_gallery, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        CardView cvDocuments = view.findViewById(R.id.addDocument);
//
//        cvDocuments.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(mContext, ScanActivity.class));
//            }
//        });
//
//        recyclerView = view.findViewById(R.id.pdfList);
//        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//
//        pdfList = new ArrayList<>();
//        adapterPdf = new AdapterPdf(mContext, pdfList, new ListenerPdf() {
//            @Override
//            public void onPdfClick(PdfModel pdfModel, int position) {
//                Intent intent = new Intent(mContext, PDFViewActivity.class);
//                intent.putExtra("pdfUri", ""+pdfModel.getUri());
//                startActivity(intent);
//            }
//        });
//        Log.e(TAG, "load pdf documents " + adapterPdf.getItemCount());
//        recyclerView.setAdapter(adapterPdf);
//
//        loadPdfDocuments();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gallery);

        CardView cvDocuments = findViewById(R.id.addDocument);

        cvDocuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GalleryActivity.this, ScanActivity.class));
            }
        });

        recyclerView = findViewById(R.id.pdfList);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        pdfList = new ArrayList<>();
        adapterPdf = new AdapterPdf(getApplicationContext(), pdfList, new ListenerPdf() {
            @Override
            public void onPdfClick(PdfModel pdfModel, int position) {
                Intent intent = new Intent(getApplicationContext(), PDFViewActivity.class);
                intent.putExtra("pdfUri", ""+pdfModel.getUri());
                intent.putExtra("fileName", ""+pdfModel.getFile().getName());
                startActivity(intent);
            }
        });
        Log.e(TAG, "load pdf documents " + adapterPdf.getItemCount());
        recyclerView.setAdapter(adapterPdf);

        loadPdfDocuments();

    }

    private void loadPdfDocuments() {
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
}
