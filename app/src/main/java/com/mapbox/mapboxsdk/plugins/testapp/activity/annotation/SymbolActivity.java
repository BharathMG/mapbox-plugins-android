package com.mapbox.mapboxsdk.plugins.testapp.activity.annotation;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.testapp.R;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;

import java.util.Random;

/**
 * Activity showcasing adding symbols using the annotation plugin
 */
public class SymbolActivity extends AppCompatActivity {

  private static final String MAKI_ICON_AIRPORT = "airport-15";
  private static final String MAKI_ICON_CAR = "car-15";
  private static final String MAKI_ICON_CAFE = "cafe-15";

  private final Random random = new Random();

  private MapView mapView;
  private Symbol symbol;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_annotation);
    mapView = findViewById(R.id.mapView);
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync(mapboxMap -> {
      mapboxMap.moveCamera(CameraUpdateFactory.zoomTo(2));

      // create symbol manager
      SymbolManager symbolManager = new SymbolManager(mapboxMap);
      // set non data driven properties
      symbolManager.setIconAllowOverlap(true);
      symbolManager.setTextAllowOverlap(true);

      // create a symbol
      symbol = symbolManager.createSymbol(new LatLng(6.687337, 0.381457));
      symbol.setIconImage(MAKI_ICON_AIRPORT);
      symbol.setIconSize(1.2f);

      // random add symbols across the globe
      Symbol currentSymbol;
      for (int i = 0; i < 20; i++) {
        currentSymbol = symbolManager.createSymbol(createRandomLatLng());
        currentSymbol.setIconImage(MAKI_ICON_CAR);
      }
    });
  }

  private LatLng createRandomLatLng() {
    return new LatLng((random.nextDouble() * -180.0) + 90.0,
      (random.nextDouble() * -360.0) + 180.0);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_marker, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.menu_action_icon) {
      symbol.setIconImage(MAKI_ICON_CAFE);
    } else if (item.getItemId() == R.id.menu_action_rotation) {
      symbol.setIconRotate(45.0f);
    } else if (item.getItemId() == R.id.menu_action_text) {
      symbol.setTextField("Hello world!");
    } else if (item.getItemId() == R.id.menu_action_anchor) {
      symbol.setIconAnchor(Property.ICON_ANCHOR_BOTTOM);
    } else if (item.getItemId() == R.id.menu_action_opacity) {
      symbol.setIconOpacity(0.5f);
    } else if (item.getItemId() == R.id.menu_action_offset) {
      symbol.setIconOffset(new Float[] {10.0f, 20.0f});
    } else if (item.getItemId() == R.id.menu_action_text_anchor) {
      symbol.setTextAnchor(Property.TEXT_ANCHOR_TOP);
    } else if (item.getItemId() == R.id.menu_action_text_color) {
      symbol.setTextColor(PropertyFactory.colorToRgbaString(Color.WHITE));
    } else if (item.getItemId() == R.id.menu_action_text_size) {
      symbol.setTextSize(22f);
    } else {
      return super.onOptionsItemSelected(item);
    }
    return true;
  }

  @Override
  protected void onStart() {
    super.onStart();
    mapView.onStart();
  }

  @Override
  protected void onResume() {
    super.onResume();
    mapView.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    mapView.onPause();
  }

  @Override
  protected void onStop() {
    super.onStop();
    mapView.onStop();
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    mapView.onLowMemory();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mapView.onDestroy();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mapView.onSaveInstanceState(outState);
  }
}