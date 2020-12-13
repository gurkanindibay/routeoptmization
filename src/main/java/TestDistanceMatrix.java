import com.google.gson.Gson;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.LatLng;

import java.io.BufferedWriter;
import java.io.Console;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.IntStream;

public class TestDistanceMatrix {
    public static void main(String[] args) throws InterruptedException, ApiException, IOException {

        GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyDWspiiu2xZwfxBCtt4_B9tcdHR-uEZYvY").build();
        //testWithStringAddress(context);
        long[][] distanceMatrix = testWithLatLong(context);

        for (long[] matrix : distanceMatrix) {
            for (long l : matrix) {
                System.out.print(l + " ");
            }
            System.out.println(" ");
        }
    }

    private static void testWithStringAddress(GeoApiContext context) throws ApiException, InterruptedException, IOException {
        String[] origins = new String[] { "Perth, Australia", "Sydney, Australia", "Melbourne, Australia", "Adelaide, Australia",
                "Brisbane, Australia", "Darwin, Australia", "Hobart, Australia", "Canberra, Australia" };
        String[] destinations = new String[] { "Uluru, Australia", "Kakadu, Australia", "Blue Mountains, Australia",
                "Bungle Bungles, Australia", "The Pinnacles, Australia" };
        DistanceMatrix matrix = DistanceMatrixApi.getDistanceMatrix(context, origins, destinations).await();

        for (int i = 0; i < matrix.rows.length; i++) {
            for (int j = 0; j < matrix.rows[i].elements.length; j++) {
                if (matrix.rows[i].elements[j].distance != null) {
                    System.out.println(matrix.rows[i].elements[j].distance.inMeters);
                }

                if (matrix.rows[i].elements[j].duration != null)
                    System.out.println(matrix.rows[i].elements[j].duration.inSeconds);
                if (matrix.rows[i].elements[j].durationInTraffic != null)
                    System.out.println(matrix.rows[i].elements[j].durationInTraffic.inSeconds);
            }
        }
        context.shutdown();

        System.out.println("Program finished");
    }

    public static long[][] testWithLatLong(GeoApiContext context) throws InterruptedException, ApiException, IOException {
        LatLng[] latLngs = { new LatLng(41.0150423, 29.0665842), new LatLng(40.950868, 29.271038),
                new LatLng(40.953409, 29.270741), new LatLng(40.953549, 29.270499), new LatLng(40.953609, 29.270499),
                new LatLng(40.953639, 29.270499), new LatLng(40.953489, 29.270499), new LatLng(40.953335, 29.270031),
                new LatLng(40.953459, 29.270499), new LatLng(40.9502847, 29.2679268), new LatLng(40.950262, 29.268154),
                new LatLng(40.950126, 29.267543), new LatLng(40.948062, 29.265899), new LatLng(40.948392, 29.266254),
                new LatLng(40.9480948, 29.2656448), new LatLng(40.9517750, 29.2692630), new LatLng(40.952556, 29.269481),
                new LatLng(41.0492621, 28.8018191), new LatLng(40.9519024, 29.2700718), new LatLng(40.9522499, 29.2696910),
                new LatLng(40.9519730, 29.2701860), new LatLng(40.951433, 29.27402), new LatLng(40.9514166, 29.2662819),
                new LatLng(40.9491911, 29.2697716), new LatLng(40.951876, 29.268037), new LatLng(40.9517888, 29.2680786),
                new LatLng(40.9503970, 29.2693150), new LatLng(40.9502572, 29.2689768), new LatLng(40.9498240, 29.2683660),
                new LatLng(40.9504926, 29.2716419), new LatLng(40.950432, 29.271604), new LatLng(40.9502415, 29.2705373),
                new LatLng(40.957824, 29.276571), new LatLng(40.951396, 29.272957), new LatLng(40.9515280, 29.2724260),
                new LatLng(40.9516786, 29.2723227), new LatLng(40.9512994, 29.2727766), new LatLng(40.95179, 29.2719560),
                new LatLng(40.9521728, 29.2713241), new LatLng(40.951733, 29.266932), new LatLng(40.954144, 29.270903),
                new LatLng(40.9533057, 29.2700404), new LatLng(40.953218, 29.269663), new LatLng(40.9546183, 29.2711257),
                new LatLng(40.9520347, 29.2697354), new LatLng(40.95110063, 29.269737), new LatLng(40.9526431, 29.2690317),
                new LatLng(40.9528756, 29.2690619), new LatLng(40.948798, 29.267777), new LatLng(40.9504140, 29.2701540),
                new LatLng(40.9510232, 29.2695993), new LatLng(40.950898, 29.266303), new LatLng(40.9616985, 29.2763302),
                new LatLng(40.949703, 29.270481), new LatLng(40.9518830, 29.2708000), new LatLng(40.95159, 29.270957),
                new LatLng(40.963671, 29.275206), new LatLng(40.9526120, 29.2664810), new LatLng(40.9531509, 29.2659027),
                new LatLng(40.951284, 29.264947), new LatLng(40.9536250, 29.2690420), new LatLng(40.9529290, 29.2669660),
                new LatLng(40.952864, 29.266995), new LatLng(40.9540990, 29.2666090), new LatLng(40.9531009, 29.2679970),
                new LatLng(40.9540920, 29.2658400), new LatLng(40.9527807, 29.2678290),

                new LatLng(40.956908, 29.2750810), new LatLng(40.9569380, 29.2750810), new LatLng(40.9564162, 29.2777235),
                new LatLng(40.9537700, 29.2747320), new LatLng(40.9539067, 29.2743692), new LatLng(40.955894, 29.274604),
                new LatLng(40.95584, 29.274478), new LatLng(40.955241, 29.276544), new LatLng(40.955853, 29.272125),
                new LatLng(40.955828, 29.27196199999999), new LatLng(40.955912, 29.272468), new LatLng(40.955372, 29.2729820),
                new LatLng(40.9557101, 29.2732734), new LatLng(40.9573530, 29.2747620), new LatLng(40.9559050, 29.2739114),
                new LatLng(40.956092, 29.273867), new LatLng(40.9560750, 29.2738420), new LatLng(40.9579661, 29.2728732),
                new LatLng(40.9580822, 29.2724531), new LatLng(40.9553269, 29.2752820), new LatLng(40.9551364, 29.2752761),
                new LatLng(40.955077, 29.275278), new LatLng(40.9551056, 29.2752277), new LatLng(40.9551656, 29.2752277),
                new LatLng(40.955107, 29.275278), new LatLng(40.9566749, 29.2767613), new LatLng(40.95826, 29.27393),
                new LatLng(40.95835, 29.27393), new LatLng(40.9582100, 29.2736369), new LatLng(40.95379, 29.27402),
                new LatLng(40.957175, 29.27393), new LatLng(40.9574435, 29.27315189999999), new LatLng(40.952455, 29.273382),
                new LatLng(40.952572, 29.275325), new LatLng(40.956848, 29.274047), new LatLng(40.954103, 29.273903),
                new LatLng(40.9542780, 29.2740350), new LatLng(40.9539101, 29.2731272), new LatLng(40.9544057, 29.2745584),
                new LatLng(40.958122, 29.274152), new LatLng(40.95870, 29.2725592), new LatLng(40.955761, 29.273616),
                new LatLng(40.9567209, 29.2730677), new LatLng(40.9564570, 29.2741383), new LatLng(40.9569741, 29.2758896),
                new LatLng(40.9559706, 29.2771690), new LatLng(40.95621, 29.277254), new LatLng(40.95618, 29.277254),
                new LatLng(40.957788, 29.275179), new LatLng(40.956323, 29.278417), new LatLng(40.9563230, 29.2784170),
                new LatLng(40.954454, 29.273171), new LatLng(40.9522080, 29.2737880), new LatLng(40.952203, 29.273804),
                new LatLng(40.9538530, 29.2727180), new LatLng(40.9538230, 29.2727180), new LatLng(40.962166, 29.274963),
                new LatLng(40.9574273, 29.2721122),

                new LatLng(41.03613002, 28.867766), new LatLng(41.035002, 28.864855), new LatLng(41.0391600, 28.862915),
                new LatLng(41.034400, 28.863813), new LatLng(41.038869, 28.859313), new LatLng(41.0719181, 28.8554344),
                new LatLng(41.033975, 28.850141), new LatLng(41.021342, 28.861343), new LatLng(41.018100001, 28.862394),
                new LatLng(41.01935, 28.859924), new LatLng(41.021334, 28.862142), new LatLng(41.021254, 28.859879),
                new LatLng(41.02263, 28.857857), new LatLng(41.023084, 28.859385), new LatLng(41.022970, 28.858091),
                new LatLng(41.023091, 28.857777), new LatLng(41.0186402, 28.8591980), new LatLng(41.025965, 28.896629),
                new LatLng(41.030894, 28.889891), new LatLng(41.031867, 28.890933), new LatLng(41.0443700, 28.881169),
                new LatLng(41.037762, 28.885463), new LatLng(41.043154, 28.882166), new LatLng(41.034710, 28.891724),
                new LatLng(41.035971, 28.8818212), new LatLng(41.033858, 28.877782), new LatLng(41.034876, 28.875985),
                new LatLng(41.0367138, 28.8771794), new LatLng(41.037971, 28.8739674), new LatLng(41.03872, 28.8791800),
                new LatLng(41.034451, 28.877342), new LatLng(41.034216, 28.877477), new LatLng(41.037243, 28.875545),
                new LatLng(41.037138, 28.87506), new LatLng(41.037097, 28.875033), new LatLng(41.02945, 28.8836848),
                new LatLng(41.0276701, 28.8834528), new LatLng(41.029268, 28.880657), new LatLng(41.029132, 28.8754664),
                new LatLng(41.02996, 28.8754879), new LatLng(41.022940, 28.8672582), new LatLng(41.026948, 28.854004),
                new LatLng(41.02498, 28.862825), new LatLng(41.026071, 28.862089), new LatLng(41.0157002, 28.877315),
                new LatLng(41.0112567, 28.8744999), new LatLng(41.011251, 28.8718906), new LatLng(41.0200695, 28.8662854),
                new LatLng(41.017791, 28.861756), new LatLng(41.0171000, 28.8649470), new LatLng(41.0167207, 28.8646257),
                new LatLng(41.01218, 28.893377), new LatLng(41.019805, 28.87338), new LatLng(41.022940, 28.877432),
                new LatLng(41.02137001, 28.8685856), new LatLng(41.0216401, 28.8752033), new LatLng(41.0211505, 28.8694427),
                new LatLng(41.023377, 28.873542), new LatLng(41.021321, 28.874638), new LatLng(41.0193702, 28.874072),
                new LatLng(41.01769, 28.878671), new LatLng(41.01561000, 28.894200), new LatLng(41.018329, 28.894740),
                new LatLng(41.018409, 28.893161),

        };
        long[][] distanceMatrix = new long[latLngs.length][latLngs.length];
        Gson gson = new Gson();
        try {

            int iterationCount = 0, newDivideResult = 0;
            int apiArrayLimit = 70;
            for (int k = 0; k < latLngs.length; k++) {

                iterationCount = latLngs.length / apiArrayLimit;
                int iterationCounter = 0;
                DistanceMatrix matrix = null;
                int counter = 0;
                while (iterationCounter <= iterationCount) {
                    int arrayLength =
                            iterationCounter < iterationCount ? apiArrayLimit : latLngs.length - iterationCounter * apiArrayLimit;
                    LatLng[] arrayPart = new LatLng[arrayLength];
                    System.arraycopy(latLngs, iterationCounter * apiArrayLimit, arrayPart, 0, arrayLength);
                    matrix = DistanceMatrixApi.newRequest(context).origins(latLngs[k]).destinations(arrayPart).await();
                    iterationCounter++;

                    for (int i = 0; i < matrix.rows.length; i++) {
                        for (int j = 0; j < matrix.rows[i].elements.length; j++) {

                            distanceMatrix[k][counter++] = matrix.rows[i].elements[j].distance == null ?
                                    0 :
                                    matrix.rows[i].elements[j].distance.inMeters;

                            //                if (matrix.rows[i].elements[j].duration != null)
                            //                    System.out.println(matrix.rows[i].elements[j].duration.inSeconds);
                            //                if (matrix.rows[i].elements[j].durationInTraffic != null)
                            //                    System.out.println(matrix.rows[i].elements[j].durationInTraffic.inSeconds);
                        }
                    }
                }
            }
            String str = gson.toJson(distanceMatrix);

            writeToFile(str);
            System.out.println("Data Fetch Operation is completed");
        } catch (Exception ex) {
            throw ex;
        } finally {
            context.shutdown();
        }

        return distanceMatrix;

    }

    private static void writeToFile(String str) throws IOException {
        BufferedWriter out = null;

        try {
            FileWriter fstream = new FileWriter("out.txt", false); //true tells to append data.
            out = new BufferedWriter(fstream);
            out.write(str);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
