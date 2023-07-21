package pgdp.streams;

import java.util.*;
import java.util.stream.Collectors;

public class Penguin {
  private List<Geo> locations;
  private String trackID;

  public Penguin(List<Geo> locations, String trackID) {
    this.locations = locations;
    this.trackID = trackID;
  }

  @Override
  public String toString() {
    return "Penguin{" +
            "locations=" + locations +
            ", trackID='" + trackID + '\'' +
            '}';
  }

  public List<Geo> getLocations() {
    return locations;
  }

  public String getTrackID() {
    return trackID;
  }

  public String toStringUsingStreams() {
    List<Geo> sortedLocations = locations.stream()
            .sorted((p1, p2) -> {
              if (p1.getLatitude() != p2.getLatitude()) {
                int latComp = Double.compare(p2.getLatitude(), p1.getLatitude());
                return latComp;
              } else {
                int longComp = Double.compare(p2.getLongitude(), p1.getLongitude());
                return longComp;
              }
            }).collect(Collectors.toList());

    return "Penguin{locations=" + sortedLocations + ", trackID='"+ trackID + "'}";
  }
}
