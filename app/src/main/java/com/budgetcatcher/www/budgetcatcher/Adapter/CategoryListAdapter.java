package com.budgetcatcher.www.budgetcatcher.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.budgetcatcher.www.budgetcatcher.BudgetCatcher;
import com.budgetcatcher.www.budgetcatcher.Config;
import com.budgetcatcher.www.budgetcatcher.Model.Category;
import com.budgetcatcher.www.budgetcatcher.Model.ModifyCategory;
import com.budgetcatcher.www.budgetcatcher.Network.QueryCallback;
import com.budgetcatcher.www.budgetcatcher.R;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by User on 11-Dec-17.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryListAdapterHolder> {

    private LayoutInflater inflater;
    private Activity activity;
    private ArrayList<Category> categories;
    private String fragmentTag, userID;

    public CategoryListAdapter(Activity activity, ArrayList<Category> categories, String fragmentTag) {

        inflater = LayoutInflater.from(activity);
        this.activity = activity;
        this.categories = categories;
        this.fragmentTag = fragmentTag;
        userID = activity.getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE).getString(Config.SP_USER_ID, "");

    }

    @Override
    public CategoryListAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new CategoryListAdapter.CategoryListAdapterHolder(inflater.inflate(R.layout.category_item_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(CategoryListAdapterHolder holder, int position) {

        Category category = categories.get(position);
        holder.category = category;

        holder.categoryName.setText(category.getCategoryName());

    }

    public void clearList() {

        categories.clear();
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class CategoryListAdapterHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.category_name)
        TextView categoryName;
        @BindView(R.id.item_body)
        RelativeLayout itemBody;

        private Category category;

        private ProgressDialog dialog;

        CategoryListAdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            dialog = ProgressDialog.show(activity, "",
                    activity.getResources().getString(R.string.loading), true);
            dialog.dismiss();

            itemBody.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    final ProgressDialog dialog = ProgressDialog.show(activity, "",
                            activity.getResources().getString(R.string.loading), true);
                    dialog.dismiss();

                    final AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                    builder1.setCancelable(false);

                    builder1.setTitle("Please select a action");
                    builder1.setMessage("For " + category.getCategoryName());

                    builder1.setPositiveButton(
                            activity.getString(R.string.edit),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {


                                }
                            }).setNegativeButton(activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            dialog.cancel();
                        }
                    }).setNeutralButton(activity.getString(R.string.delete), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    final AlertDialog alert11 = builder1.create();

                    alert11.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {
                            alert11.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.RED);
                            alert11.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(activity.getResources().getColor(R.color.deep_sea_dive));
                            alert11.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(activity.getResources().getColor(R.color.hinoki));
                            alert11.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    alert11.dismiss();
                                    modifyCategory();

                                }
                            });

                            alert11.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    deleteCategory();
                                    alert11.dismiss();

                                }
                            });

                            alert11.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    alert11.dismiss();

                                }
                            });

                        }
                    });

                    alert11.show();

                    return false;
                }
            });

        }

        private void deleteCategory() {

            dialog.show();
            BudgetCatcher.apiManager.deleteCategory(category.getCategoryId(), new QueryCallback<String>() {
                @Override
                public void onSuccess(String data) {

                    dialog.dismiss();
                    Toast.makeText(activity, activity.getString(R.string.successfully_deleted), Toast.LENGTH_SHORT).show();
                    categories.remove(getAdapterPosition());
                    notifyDataSetChanged();

                }

                @Override
                public void onFail() {

                    Toast.makeText(activity, activity.getString(R.string.delete_failed), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }

                @Override
                public void onError(Throwable th) {

                    dialog.dismiss();
                    Log.e("SerVerErr", th.toString());
                    if (th instanceof SocketTimeoutException) {
                        Toast.makeText(activity, activity.getResources().getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity, activity.getResources().getString(R.string.server_reach_error), Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }

        private void modifyCategory() {

            final AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
            builder1.setCancelable(true);

            LayoutInflater inflater = activity.getLayoutInflater();
            View alertView = inflater.inflate(R.layout.add_category_alert_box, null);
            builder1.setView(alertView);

            final EditText categoryName = alertView.findViewById(R.id.add_catego);
            categoryName.setText(category.getCategoryName());

            builder1.setPositiveButton(
                    activity.getResources().getString(R.string.done),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {


                        }
                    }).setNeutralButton(activity.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                    dialog.cancel();
                }
            });

            final AlertDialog alert11 = builder1.create();

            alert11.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    alert11.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.RED);
                    alert11.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(activity.getResources().getColor(R.color.colorAccent));
                    alert11.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (!categoryName.getText().toString().equals("")) {

                                ModifyCategory modifyCat = new ModifyCategory(categoryName.getText().toString(), Config.CATEGORY_BILL_TAG_ID);

                                dialog.show();

                                BudgetCatcher.apiManager.modifyCategory(userID, category.getCategoryId(), modifyCat, new QueryCallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {

                                        dialog.dismiss();
                                        alert11.dismiss();
                                        Toast.makeText(activity, activity.getString(R.string.successfully_updated), Toast.LENGTH_SHORT).show();
                                        categories.get(getAdapterPosition()).setCategoryName(categoryName.getText().toString());
                                        notifyDataSetChanged();

                                    }

                                    @Override
                                    public void onFail() {

                                        Toast.makeText(activity, activity.getString(R.string.updated_failed), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();

                                    }

                                    @Override
                                    public void onError(Throwable th) {

                                        dialog.dismiss();
                                        Log.e("SerVerErr", th.toString());
                                        if (th instanceof SocketTimeoutException) {
                                            Toast.makeText(activity, activity.getResources().getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(activity, activity.getResources().getString(R.string.server_reach_error), Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                            } else {

                                categoryName.setError("Empty");

                            }

                        }
                    });

                }
            });

            alert11.show();

        }

    }

}

