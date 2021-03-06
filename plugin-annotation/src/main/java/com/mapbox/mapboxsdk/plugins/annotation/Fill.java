// This file is generated.

package com.mapbox.mapboxsdk.plugins.annotation;

import android.support.annotation.ColorInt;
import android.graphics.PointF;
import android.support.annotation.UiThread;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.mapbox.geojson.*;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.utils.ColorUtils;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.mapbox.android.gestures.MoveDistancesObject;
import com.mapbox.mapboxsdk.maps.Projection;

import java.util.ArrayList;
import java.util.List;

import static com.mapbox.mapboxsdk.constants.GeometryConstants.MAX_MERCATOR_LATITUDE;
import static com.mapbox.mapboxsdk.constants.GeometryConstants.MIN_MERCATOR_LATITUDE;

@UiThread
public class Fill extends Annotation<Polygon> {

  private final AnnotationManager<?, Fill, ?, ?, ?, ?> annotationManager;

  /**
   * Create a fill.
   *
   * @param id            the id of the fill
   * @param jsonObject the features of the annotation
   * @param geometry the geometry of the annotation
   */
  Fill(long id, AnnotationManager<?, Fill, ?, ?, ?, ?> annotationManager, JsonObject jsonObject, Polygon geometry) {
    super(id, jsonObject, geometry);
    this.annotationManager = annotationManager;
  }

  @Override
  void setUsedDataDrivenProperties() {
    if (!(jsonObject.get("fill-opacity") instanceof JsonNull)) {
      annotationManager.enableDataDrivenProperty("fill-opacity");
    }
    if (!(jsonObject.get("fill-color") instanceof JsonNull)) {
      annotationManager.enableDataDrivenProperty("fill-color");
    }
    if (!(jsonObject.get("fill-outline-color") instanceof JsonNull)) {
      annotationManager.enableDataDrivenProperty("fill-outline-color");
    }
    if (!(jsonObject.get("fill-pattern") instanceof JsonNull)) {
      annotationManager.enableDataDrivenProperty("fill-pattern");
    }
  }

  /**
   * Set a list of lists of LatLng for the fill, which represents the locations of the fill on the map
   * <p>
   * To update the fill on the map use {@link FillManager#update(Annotation)}.
   * <p>
   *
   * @param latLngs a list of a lists of the locations of the polygon in a latitude and longitude pairs
   */
  public void setLatLngs(List<List<LatLng>> latLngs) {
    List<List<Point>> points = new ArrayList<>();
    for (List<LatLng> innerLatLngs : latLngs) {
      List<Point>innerList = new ArrayList<>();
      for (LatLng latLng : innerLatLngs) {
        innerList.add(Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude()));
      }
      points.add(innerList);
    }
    geometry = Polygon.fromLngLats(points);
  }

  /**
   * Get a list of lists of LatLng for the fill, which represents the locations of the fill on the map
   *
   * @return a list of a lists of the locations of the polygon in a latitude and longitude pairs
   */
  @NonNull
  public List<List<LatLng>> getLatLngs() {
    Polygon polygon = (Polygon) geometry;
    List<List<LatLng>> latLngs = new ArrayList<>();
    List<List<Point>> coordinates = polygon.coordinates();
    if (coordinates != null) {
      for (List<Point> innerPoints : coordinates) {
        List<LatLng> innerList = new ArrayList<>();
        for (Point point : innerPoints) {
          innerList.add(new LatLng(point.latitude(), point.longitude()));
        }
        latLngs.add(innerList);
      }
    }
    return latLngs;
  }

  // Property accessors

  /**
   * Get the FillOpacity property
   *
   * @return property wrapper value around Float
   */
  public Float getFillOpacity() {
    return jsonObject.get("fill-opacity").getAsFloat();
  }

  /**
   * Set the FillOpacity property
   * <p>
   * To update the fill on the map use {@link FillManager#update(Annotation)}.
   * <p>
   *
   * @param value constant property value for Float
   */
  public void setFillOpacity(Float value) {
    jsonObject.addProperty("fill-opacity", value);
  }

  /**
   * Get the FillColor property
   *
   * @return color value for String
   */
  @ColorInt
  public int getFillColor() {
    return ColorUtils.rgbaToColor(jsonObject.get("fill-color").getAsString());
  }

  /**
   * Set the FillColor property
   * <p>
   * To update the fill on the map use {@link FillManager#update(Annotation)}.
   * <p>
   *
   * @param color value for String
   */
  public void setFillColor(@ColorInt int color) {
    jsonObject.addProperty("fill-color", ColorUtils.colorToRgbaString(color));
  }

  /**
   * Get the FillOutlineColor property
   *
   * @return color value for String
   */
  @ColorInt
  public int getFillOutlineColor() {
    return ColorUtils.rgbaToColor(jsonObject.get("fill-outline-color").getAsString());
  }

  /**
   * Set the FillOutlineColor property
   * <p>
   * To update the fill on the map use {@link FillManager#update(Annotation)}.
   * <p>
   *
   * @param color value for String
   */
  public void setFillOutlineColor(@ColorInt int color) {
    jsonObject.addProperty("fill-outline-color", ColorUtils.colorToRgbaString(color));
  }

  /**
   * Get the FillPattern property
   *
   * @return property wrapper value around String
   */
  public String getFillPattern() {
    return jsonObject.get("fill-pattern").getAsString();
  }

  /**
   * Set the FillPattern property
   * <p>
   * To update the fill on the map use {@link FillManager#update(Annotation)}.
   * <p>
   *
   * @param value constant property value for String
   */
  public void setFillPattern(String value) {
    jsonObject.addProperty("fill-pattern", value);
  }

  @Override
  @Nullable
  Geometry getOffsetGeometry(@NonNull Projection projection, @NonNull MoveDistancesObject moveDistancesObject,
                             float touchAreaShiftX, float touchAreaShiftY) {
    List<List<Point>> originalPoints = geometry.coordinates();
    if (originalPoints != null) {
      List<List<Point>> resultingPoints = new ArrayList<>(originalPoints.size());
      for (List<Point> points : originalPoints) {
        List<Point> innerPoints = new ArrayList<>();
        for (Point jsonPoint : points) {
          PointF pointF = projection.toScreenLocation(new LatLng(jsonPoint.latitude(), jsonPoint.longitude()));
          pointF.x -= moveDistancesObject.getDistanceXSinceLast();
          pointF.y -= moveDistancesObject.getDistanceYSinceLast();

          LatLng latLng = projection.fromScreenLocation(pointF);
          if (latLng.getLatitude() > MAX_MERCATOR_LATITUDE || latLng.getLatitude() < MIN_MERCATOR_LATITUDE) {
            return null;
          }
          innerPoints.add(Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude()));
        }
        resultingPoints.add(innerPoints);
      }

      return Polygon.fromLngLats(resultingPoints);
    }

    return null;
  }
}
