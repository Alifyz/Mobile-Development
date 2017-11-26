package com.alifyz.inventoryapp.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.text.Layout;
import android.util.Log;

import com.alifyz.inventoryapp.ProductDetails;
import com.alifyz.inventoryapp.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.alifyz.inventoryapp.Database.ProductDb;
import com.alifyz.inventoryapp.Database.ProductDb.ProductEntry;
import com.alifyz.inventoryapp.R;

import org.w3c.dom.Text;

/**
 * Created by alifyzpires on 2017-11-21.
 */

public class CursorAdapter extends android.widget.CursorAdapter{


    public CursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_list, parent,false);
    }

    //TODO - Fix the price dislay
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        String nameFromDb = cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_NAME));
        double priceFromDb = (cursor.getDouble(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRICE))) /100;
        int quantityFromDb = cursor.getInt(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_QUANTITY));
        int salesFromDb = ProductDetails.mCurrentSales;
        String SupplierFromDb = cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_SUPLIER_NAME));

        TextView productName = (TextView) view.findViewById(R.id.name);
        TextView productPrice = (TextView) view.findViewById(R.id.price);
        TextView productQuantity = (TextView) view.findViewById(R.id.quantity);
        TextView productSales = (TextView) view.findViewById(R.id.sales);
        TextView productAvailable = (TextView) view.findViewById(R.id.in_stock);
        TextView productSupplier = (TextView) view.findViewById(R.id.suplier);

        productName.setText(nameFromDb);
        productPrice.setText(context.getString(R.string.moneyTag));
        productPrice.append(String.valueOf(priceFromDb));
        productQuantity.setText(context.getString(R.string.qtdCode));
        productQuantity.append(String.valueOf(quantityFromDb));
        productSales.setText(String.valueOf(salesFromDb));
        productSales.append(" " + context.getString(R.string.salesInfo));
        productSupplier.setText(context.getString(R.string.supplier));
        productSupplier.append(" " + SupplierFromDb);

        if(quantityFromDb <= 0) {
            productAvailable.setText(context.getString(R.string.noStock));
        } else {
            productAvailable.setText(context.getString(R.string.inStock));
        }

    }
}