package pgdp.streams;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class DataScience {
  public static Stream<Penguin> getDataByTrackId(Stream<PenguinData> stream) {
    final Map<String, List<Geo>> filteredData = new HashMap<>();
    stream.forEach(penguinData -> {
      if (penguinData != null) {
        List<Geo> l = filteredData.get(penguinData.getTrackID());
        if (l == null)
          filteredData.put(penguinData.getTrackID(), (l = new ArrayList<>()));
        l.add(penguinData.getGeom());
      }
    });

    return filteredData.entrySet().stream().map(entry -> {
      String IDs = entry.getKey();
      List<Geo> geo = entry.getValue();
      return new Penguin(geo, IDs);
    });
    }


//  public static void outputPenguinStream() {
//    long total = getDataByTrackId(CSVReading.processInputFile()).count();
//    System.out.println(total);
//    List<Penguin> penguinsByTrackid = new ArrayList<>(getDataByTrackId(CSVReading.processInputFile()).toList());
//    penguinsByTrackid.sort(Comparator.comparing(Penguin::getTrackID));
//    penguinsByTrackid.forEach(penguin -> {
//      System.out.println(penguin.toStringUsingStreams());
//    });
//  }

  public static void outputPenguinStream() {
    long total = getDataByTrackId(CSVReading.processInputFile()).count();
    System.out.println(total);
    List<Penguin> penguinsByTrackid = new ArrayList<>(getDataByTrackId(CSVReading.processInputFile()).sorted(Comparator.comparing(Penguin::getTrackID)).toList());
    penguinsByTrackid.stream().forEach(x -> System.out.println(x.toStringUsingStreams()));
  }

  public static void outputLocationRangePerTrackid(Stream<PenguinData> stream) {
    List<Penguin> pingus = getDataByTrackId(stream).toList();
    StringBuilder stringBuilder = new StringBuilder();
    pingus.stream().forEach(x -> {

      double allLatitudes = x.getLocations().stream().map(Geo::getLatitude).reduce(0.0, (z, y) -> z + y);
      double average = allLatitudes/(x.getLocations().size());

      double minLongitude = x.getLocations().stream().mapToDouble(y -> y.getLongitude()).min().getAsDouble();
      double maxLongitude = x.getLocations().stream().mapToDouble(y -> y.getLongitude()).max().getAsDouble();
      double averageLongitude = x.getLocations().stream().mapToDouble(y -> y.getLongitude()).average().getAsDouble();
      double minLatitude = x.getLocations().stream().mapToDouble(y -> y.getLatitude()).min().getAsDouble();
      double maxLatitude = x.getLocations().stream().mapToDouble(y -> y.getLatitude()).max().getAsDouble();
      double averageLatitude = x.getLocations().stream().mapToDouble(y -> y.getLatitude()).average().getAsDouble();
      System.out.println(x.getTrackID());
      System.out.println("Min Longitude: " + minLongitude + " Max Longitude: " + maxLongitude + " Avg Longitude: " + averageLongitude + " Min Latitude: " + minLatitude + " Max Latitude: " + maxLatitude + " Avg Latitude: " + averageLatitude);
   //   System.out.println(stringBuilder.toString());
    });
  }

  public static void main(String[] args) {
    //outputLocationRangePerTrackid(CSVReading.processInputFile());
   //getDataByTrackId(CSVReading.processInputFile()).forEach(x -> System.out.println(x));
    //CSVReading.processInputFile().forEach(o -> System.out.println(o.toString()));
    outputPenguinStream();

//    Stream<PenguinData> x = CSVReading.processInputFile();
//    outputLocationRangePerTrackid(x);

  }
}
