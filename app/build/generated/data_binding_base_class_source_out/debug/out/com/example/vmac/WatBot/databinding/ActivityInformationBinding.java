// Generated by view binder compiler. Do not edit!
package com.example.vmac.WatBot.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.vmac.WatBot.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityInformationBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button addButton;

  @NonNull
  public final TextView addressTxt2;

  @NonNull
  public final TextView c;

  @NonNull
  public final CardView cardView;

  @NonNull
  public final RecyclerView commentTxt2;

  @NonNull
  public final LinearLayout mainLayout;

  @NonNull
  public final TextView nameTxt2;

  @NonNull
  public final RatingBar ratingBar2;

  @NonNull
  public final TextView typeTxt2;

  private ActivityInformationBinding(@NonNull LinearLayout rootView, @NonNull Button addButton,
      @NonNull TextView addressTxt2, @NonNull TextView c, @NonNull CardView cardView,
      @NonNull RecyclerView commentTxt2, @NonNull LinearLayout mainLayout,
      @NonNull TextView nameTxt2, @NonNull RatingBar ratingBar2, @NonNull TextView typeTxt2) {
    this.rootView = rootView;
    this.addButton = addButton;
    this.addressTxt2 = addressTxt2;
    this.c = c;
    this.cardView = cardView;
    this.commentTxt2 = commentTxt2;
    this.mainLayout = mainLayout;
    this.nameTxt2 = nameTxt2;
    this.ratingBar2 = ratingBar2;
    this.typeTxt2 = typeTxt2;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityInformationBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityInformationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_information, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityInformationBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.add_button;
      Button addButton = ViewBindings.findChildViewById(rootView, id);
      if (addButton == null) {
        break missingId;
      }

      id = R.id.address_txt2;
      TextView addressTxt2 = ViewBindings.findChildViewById(rootView, id);
      if (addressTxt2 == null) {
        break missingId;
      }

      id = R.id.c;
      TextView c = ViewBindings.findChildViewById(rootView, id);
      if (c == null) {
        break missingId;
      }

      id = R.id.cardView;
      CardView cardView = ViewBindings.findChildViewById(rootView, id);
      if (cardView == null) {
        break missingId;
      }

      id = R.id.comment_txt2;
      RecyclerView commentTxt2 = ViewBindings.findChildViewById(rootView, id);
      if (commentTxt2 == null) {
        break missingId;
      }

      LinearLayout mainLayout = (LinearLayout) rootView;

      id = R.id.name_txt2;
      TextView nameTxt2 = ViewBindings.findChildViewById(rootView, id);
      if (nameTxt2 == null) {
        break missingId;
      }

      id = R.id.ratingBar2;
      RatingBar ratingBar2 = ViewBindings.findChildViewById(rootView, id);
      if (ratingBar2 == null) {
        break missingId;
      }

      id = R.id.type_txt2;
      TextView typeTxt2 = ViewBindings.findChildViewById(rootView, id);
      if (typeTxt2 == null) {
        break missingId;
      }

      return new ActivityInformationBinding((LinearLayout) rootView, addButton, addressTxt2, c,
          cardView, commentTxt2, mainLayout, nameTxt2, ratingBar2, typeTxt2);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
