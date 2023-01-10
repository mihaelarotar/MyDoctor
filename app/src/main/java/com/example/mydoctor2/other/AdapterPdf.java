package com.example.mydoctor2.other;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mydoctor2.MyApplication;
import com.example.mydoctor2.R;
import com.example.mydoctor2.data.PdfModel;
import com.example.mydoctor2.interfaces.ListenerPdf;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdapterPdf extends RecyclerView.Adapter<AdapterPdf.HolderPdf> {
    private Context context;
    private ArrayList<PdfModel> pdfList;
    private ListenerPdf listenerPdf;
    private static final String TAG = "ADAPTER_PDF_TAG";

    public AdapterPdf(Context context, ArrayList<PdfModel> pdfList, ListenerPdf listenerPdf) {
        this.context = context;
        this.pdfList = pdfList;
        this.listenerPdf = listenerPdf;
    }

    @NonNull
    @Override
    public HolderPdf onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_pdf, parent, false);
        return new HolderPdf(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderPdf holder, int position) {
        PdfModel pdfModel = pdfList.get(position);
        String name = pdfModel.getFile().getName();
        long timestamp = pdfModel.getFile().lastModified();
        String formattedDate = MyApplication.formatTimestamp(timestamp);
        loadThumbnailFromPdf(pdfModel, holder);
        
        holder.name.setText(name);
        holder.date.setText(formattedDate);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerPdf.onPdfClick(pdfModel, position);
            }
        });
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerPdf.onPdfMoreClick(pdfModel, position, holder);
            }
        });
    }

    private void loadThumbnailFromPdf(PdfModel pdfModel, HolderPdf holder) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new android.os.Handler(Looper.getMainLooper());
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Bitmap thumbnailBitmap = null;
                int pageCount = 0;
                try {
                    ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.open(pdfModel.getFile(), ParcelFileDescriptor.MODE_READ_ONLY);
                    PdfRenderer pdfRenderer = new PdfRenderer(parcelFileDescriptor);
                    pageCount = pdfRenderer.getPageCount();
                    if (pageCount<=0) {
                        Log.d(TAG, "No pages");
                    }
                    else {
                        PdfRenderer.Page currentPage = pdfRenderer.openPage(0);
                        thumbnailBitmap = Bitmap.createBitmap(currentPage.getWidth(), currentPage.getHeight(), Bitmap.Config.ARGB_8888);
                        currentPage.render(thumbnailBitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
                    }
                }
                catch (Exception e) {
                    Log.e(TAG, "load thumbnail", e);
                }
                Bitmap finalThumbnailBitmap = thumbnailBitmap;
                int finalPageCount = pageCount;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "Setting thumbnail");
                        Glide.with(context)
                                        .load(finalThumbnailBitmap)
                                                .fitCenter()
                                                        .placeholder(R.drawable.pdf)
                                                                .into(holder.thumbnail);
                        holder.pages.setText("" + finalPageCount + " Pages");
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return pdfList.size();
    }

    public class HolderPdf extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        public TextView name, pages, date;
        public ImageButton more;

        public HolderPdf(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            name = itemView.findViewById(R.id.name);
            pages = itemView.findViewById(R.id.pages);
            date = itemView.findViewById(R.id.date);
            more = itemView.findViewById(R.id.more);
        }
    }
}
