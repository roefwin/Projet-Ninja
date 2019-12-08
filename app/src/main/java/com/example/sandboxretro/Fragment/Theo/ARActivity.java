package com.example.sandboxretro.Fragment.Theo;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.example.sandboxretro.R;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.Material;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.ramotion.circlemenu.CircleMenuView;

import es.dmoral.toasty.Toasty;

public class ARActivity extends AppCompatActivity {

    private static final String TAG = ARActivity.class.getSimpleName();

    private ArFragment arFragment;
    private ModelRenderable my_object;
    private ModelRenderable my_object_ChangeColor;
    private Anchor anchor;

    private CircleMenuView mCircleMenuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_ar_activity);

        arFragment = (ArFragment)getSupportFragmentManager().findFragmentById(R.id.ar_fragment_theo);

        mCircleMenuView = findViewById(R.id.btn_circle_menu_theo);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            ModelRenderable.builder()
                    .setSource(this, Uri.parse("mustang.sfb"))
                    .build()
                    .thenAccept(renderable -> my_object = renderable).exceptionally(throwable -> {
                        Toasty.error(arFragment.getContext(), "Error:" + throwable.getMessage(), Toasty.LENGTH_LONG).show();
                        return null;
                    }
            );

            arFragment.setOnTapArPlaneListener(
                    (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                        if (my_object == null) {
                            return;
                        }
                        ColorPickerDialogBuilder
                                .with(ARActivity.this)
                                .setTitle("Choose color")
                                .initialColor(R.color.white)
                                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                                .density(10)
                                .setPositiveButton("ok", new ColorPickerClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                        anchor = hitResult.createAnchor();
                                        placeObject(arFragment, anchor, my_object, selectedColor);
                                        mCircleMenuView.setVisibility(View.VISIBLE);
                                    }
                                })
                                .build()
                                .show();
                    });

        }

        mCircleMenuView.setEventListener(new CircleMenuView.EventListener(){
            @Override
            public void onButtonClickAnimationEnd(@NonNull CircleMenuView view, int buttonIndex) {
                super.onButtonClickAnimationEnd(view, buttonIndex);
                switch (buttonIndex){
                    case 0:
                        //start moteur
                        break;
                    case 1:
                        //stop moteur
                        break;
                    case 2:
                        //delete object
                        removeAnchorNode();
                        break;
                    case 3:
                        //change color
                        ColorPickerDialogBuilder
                                .with(ARActivity.this)
                                .setTitle("Choose color")
                                .initialColor(R.color.white)
                                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                                .density(10)
                                .setPositiveButton("ok", new ColorPickerClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                        changeColorObject(selectedColor);
                                    }
                                })
                                .build()
                                .show();
                        break;
                    default:
                }
            }
        });
    }

    private void placeObject(ArFragment arFragment, Anchor anchor, ModelRenderable modelRenderable, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            my_object_ChangeColor = modelRenderable.makeCopy();
            Material changedMaterial = my_object_ChangeColor.getMaterial().makeCopy();
            changedMaterial.setFloat3("baseColorTint", new Color(color));
            my_object_ChangeColor.setMaterial(changedMaterial);

            addNodeToScene(arFragment, anchor, my_object_ChangeColor);
        }
    }

    private void addNodeToScene(ArFragment arFragment, Anchor anchor, Renderable renderable) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());
        node.setRenderable(renderable);
        node.setParent(anchorNode);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        node.select();
    }

    private void changeColorObject(int newColor){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Material changedMaterial = my_object_ChangeColor.getMaterial().makeCopy();
            changedMaterial.setFloat3("baseColorTint", new Color(newColor));
            my_object_ChangeColor.setMaterial(changedMaterial);
        }
    }

    private void removeAnchorNode() {
        AnchorNode anchorNode = new AnchorNode(anchor);
        arFragment.getArSceneView().getScene().removeChild(anchorNode);
        anchorNode.getAnchor().detach();
        anchorNode.setParent(null);
        TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());
        node.setRenderable(null);
    }
}
